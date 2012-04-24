package com.mcvs.controller;

import java.awt.event.*;
import java.io.IOException;

import javax.swing.JOptionPane;

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
		 * the getEntites method
		 */
		view.getVersionTable().setModel(new VersionTableModel(model.getEntities()));
		
		//Add action listeners to the views controls
		view.addLaunchButtonListener(new LaunchButtonListener());
		view.addJarItemListener(new AddJarListener());
		view.addExitListener(new ExitListener());
		view.addReportBugItemListener(new ReportBugListener());
		view.addAboutItemListener(new AboutListener());
		
		//Add action listeners to the add jar dialog controls
		view.getAddJarDialog().addSubmitButtonListener(new AddJarSubmitButtonListener());
		view.getAddJarDialog().addCancelButtonListener(new AddJarCancelButtonListener());
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
			JOptionPane.showMessageDialog(view, "Program: Minecraft Controller Switcher\nCreated by: Kyle Schattler\nVersion: v0.2a", 
					"About", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	class AddJarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			view.getAddJarDialog().setVisible(true);
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
}