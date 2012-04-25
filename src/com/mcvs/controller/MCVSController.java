package com.mcvs.controller;

import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

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
		
		//Add action listeners to the add jar dialog controls
		view.getAddJarDialog().addSubmitButtonListener(new AddJarSubmitButtonListener());
		view.getAddJarDialog().addCancelButtonListener(new AddJarCancelButtonListener());
		
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
			
		}
	}
	
	class AddJarCancelButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.getAddJarDialog().setVisible(false);
		}
	}
	
	class VersionTableMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) { }

		@Override
		public void mouseEntered(MouseEvent e) { }

		@Override
		public void mouseExited(MouseEvent e) { }

		@Override
		public void mousePressed(MouseEvent e) {
			//Checks if the user double clicked using the left mouse button
			if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
				JTable tempTable = view.getVersionTable();
				
				/*Makes sure the current version saved in the model doesn't match with the current version
				 * being displayed by the view.
				 */
				if(!model.getCurrentVersion(false).equals(tempTable.getValueAt(tempTable.getSelectedRow(), 1))) {
					int opt = JOptionPane.showConfirmDialog(view, "Are you sure you want to" +
							" change to this version?", "Change version", JOptionPane.YES_NO_OPTION);
					
					if(opt == JOptionPane.YES_OPTION) {
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
						
						model.updateCurrentVersion(tempVer);
						view.setCurrentVersionLabel(tempVer);
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON3) {
				JTable temp = view.getVersionTable();
				int r = temp.rowAtPoint(e.getPoint());
				
				if (r >= 0 && r < temp.getRowCount()) {
					temp.setRowSelectionInterval(r, r);
				} else {
					temp.clearSelection();
				}
	            
	            int rowindex = temp.getSelectedRow();
	            if (rowindex < 0)
	                return;
	            if (e.isPopupTrigger() && e.getComponent() instanceof JTable ) {
	                view.showPopupMenu(e.getComponent(), e.getX(), e.getY());
	            }
			}
		}
	}
}