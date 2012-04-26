package com.mcvs.controller;

import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import com.mcvs.view.*;
import com.mcvs.core.*;
import com.mcvs.model.*;

public class MCVSController {
	private static MCVSController INSTANCE = null;
	private MCVSView view;
	private MCVSModel model;
	
	/*
	 * Controller for the application. Listens for events from the view
	 * and uses the model to perform appropriate actions
	 */
	private MCVSController(MCVSView v, MCVSModel m) {
		view = v;
		model = m;
		
		/*
		 * Gets the views JTable and sets the model with the data gathered from
		 * the getEntites method. Sets the current version label as well.
		 */
		view.getVersionTable().setModel(new VersionTableModel(model.getEntities()));
		view.setCurrentVersionLabel(model.getCurrentVersion(true));
		
		/*
		 * Gets the views reference to the add jar dialog object and sets its 
		 * combo box model to a new VersionComboBoxModel. The VersionComboBoxModel
		 * is passed an array of strings returned by the getAllMCVersions function
		 * in the MCVSModel class.
		 */
		view.getAddJarDialog().getVersionComboBox().setModel(new VersionComboBoxModel(model.getAllMCVersions()));
		
		//Add action listeners to the views controls
		view.addLaunchButtonListener(new LaunchButtonListener());
		view.addJarItemListener(new AddJarListener());
		view.addExitListener(new ExitListener());
		view.addReportBugItemListener(new ReportBugListener());
		view.addAboutItemListener(new AboutListener());
		view.addVersionTableListener(new VersionTableMouseListener());
		view.addRenameItemListener(new RenameItemListener());
		
		//Add action listeners to the add jar dialog controls
		view.getAddJarDialog().addSubmitButtonListener(new AddJarSubmitButtonListener());
		view.getAddJarDialog().addCancelButtonListener(new AddJarCancelButtonListener());
		view.getAddJarDialog().addNameTextDocumentListener(new AddJarNameTextDocumentListener());
		
		/*
		 * Once everything is built, display the GUI
		 */
		view.setVisible(true);
	}
	
	public static MCVSController getInstance(MCVSView v, MCVSModel m) {
		if(INSTANCE==null) {
			INSTANCE = new MCVSController(v, m);
		}
		
		return INSTANCE;
	}
	
	/*
	 * Inner classes implementing the ActionListener interface
	 * used to listen for events on the view.
	 */
	
	class LaunchButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			model.launchMinecraft();
		}
	}
	
	class ExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			model.closeMCVS();
		}
	}
	
	class ReportBugListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.getBugDialog().setVisible(true);
		}
	}
	
	class AboutListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(view, "Program: Minecraft Controller Switcher\nCreated by: Kyle Schattler\nVersion: " +
					model.getMCVSVersion(), "About", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	class AddJarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			FileDialog fileDialog = view.getFileChooser();
			fileDialog.setVisible(true);
			
			if(fileDialog.getFile()!=null) {
				AddJarDialog jarDialog = view.getAddJarDialog();
				ArrayList<Entity> temp = new ArrayList<Entity>();
				
				temp.add(new Entity(fileDialog.getFile(), fileDialog.getDirectory()));
				jarDialog.setListViewData(temp);
				jarDialog.setVisible(true);
			}
		}
	}
	
	class AddJarSubmitButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			AddJarDialog temp = view.getAddJarDialog();
			Entity entity = temp.getSelectedEntity();
			int opt = 0;
			
			if(!model.makeDirectory((String)temp.getVersion())) {
				opt = JOptionPane.showConfirmDialog(view, "This version of Minecraft already" +
						" exists, do you want to overwrite it?", "File already exists", JOptionPane.YES_NO_OPTION);
			}
			
			if(opt==JOptionPane.NO_OPTION) 
				return;
			if(opt==JOptionPane.YES_OPTION) {
				//TODO Delete existing minecraft file
			}
			try {
				model.addVersion(entity.getDirectory()+entity.getName(), (String)temp.getVersion(), temp.getNameText());
			} 
			catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
	}
	
	class AddJarCancelButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.getAddJarDialog().setVisible(false);
		}
	}
	
	class AddJarNameTextDocumentListener implements DocumentListener {
		private AddJarDialog temp = view.getAddJarDialog();
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			if(temp.getNameText().length()==0) {
				temp.setSubmitButtonEnabled(false);
			}
			else {
				temp.setSubmitButtonEnabled(true);
			}
		}

		@Override
		public void insertUpdate(DocumentEvent e) { 
			if(temp.getNameText().length()==0) {
				temp.setSubmitButtonEnabled(false);
			}
			else {
				temp.setSubmitButtonEnabled(true);
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			if(temp.getNameText().length()==0) {
				temp.setSubmitButtonEnabled(false);
			}
			else {
				temp.setSubmitButtonEnabled(true);
			}
		}
	}
	
	class VersionTableMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) { 
			//Checks if the user double clicked using the left mouse button
			if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
				JTable tempTable = view.getVersionTable();
				
				/* 
				 * Makes sure the current version saved in the model doesn't match with the current version
				 * being displayed by the view.
				 */
				if(!model.getCurrentVersion(false).equals(tempTable.getValueAt(tempTable.getSelectedRow(), 1))) {
					
					//Prompts the user to make sure they really meant to switch versions
					int opt = JOptionPane.showConfirmDialog(view, "Are you sure you want to" +
							" change to this version?", "Change version", JOptionPane.YES_NO_OPTION);
					
					//If they click the yes button
					if(opt == JOptionPane.YES_OPTION) {
						
						//Store temp values for the version and filename of the selected row 
						String tempVer = (String) tempTable.getValueAt(tempTable.getSelectedRow(), 1);
						String tempName = (String) tempTable.getValueAt(tempTable.getSelectedRow(), 0);
						try {
							model.moveVersion(tempVer, tempName);
						}
						catch (FileNotFoundException ex) {
							JOptionPane.showConfirmDialog(view, "Something went wrong when moving the " +
									"Minecraft jar file. Hit okay to send error to developer.", "Error", JOptionPane.WARNING_MESSAGE);
						}
						catch (IOException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
						}
						
						/*
						 * Updates the currentVer text file and sets the current version label
						 * to the selected value
						 */
						model.updateCurrentVersion(tempVer);
						view.setCurrentVersionLabel(tempVer);
					}
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) { }

		@Override
		public void mouseExited(MouseEvent e) { }

		@Override
		public void mousePressed(MouseEvent e) {
			//Checks of the right mouse button was released
			if(e.getButton() == MouseEvent.BUTTON3) {
				/*
				 * Stores a temp reference of the views JTable and gets the row
				 * that contains the point at e.getPoint
				 */
				JTable temp = view.getVersionTable();
				int r = temp.rowAtPoint(e.getPoint());
				
				/*
				 * Makes sure the value of r is within the bounds of the tables rows
				 * if it is set that row as the selected row; else clear the current
				 * selected row.
				 */
				if (r >= 0 && r < temp.getRowCount()) {
					temp.setRowSelectionInterval(r, r);
				} else {
					temp.clearSelection();
				}
	            
				/*
				 * Get the row index by getting the selected row, if that index is
				 * less than 0, return out of the function.
				 */
	            int rowindex = temp.getSelectedRow();
	            if (rowindex < 0) {
	                return;
	            }
	            
	            //Display the popup menu at the clicked point
	            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
	                view.showPopupMenu(e.getComponent(), e.getX(), e.getY());
	            }
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			//Checks of the right mouse button was released
			if(e.getButton() == MouseEvent.BUTTON3) {
				/*
				 * Stores a temp reference of the views JTable and gets the row
				 * that contains the point at e.getPoint
				 */
				JTable temp = view.getVersionTable();
				int r = temp.rowAtPoint(e.getPoint());
				
				/*
				 * Makes sure the value of r is within the bounds of the tables rows
				 * if it is set that row as the selected row; else clear the current
				 * selected row.
				 */
				if (r >= 0 && r < temp.getRowCount()) {
					temp.setRowSelectionInterval(r, r);
				} else {
					temp.clearSelection();
				}
	            
				/*
				 * Get the row index by getting the selected row, if that index is
				 * less than 0, return out of the function.
				 */
	            int rowindex = temp.getSelectedRow();
	            if (rowindex < 0) {
	                return;
	            }
	            
	            //Display the popup menu at the clicked point
	            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
	                view.showPopupMenu(e.getComponent(), e.getX(), e.getY());
	            }
			}
		}
	}
	
	class RenameItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showInputDialog(view, "Enter new name for file:", "New name");
		}
	}
}