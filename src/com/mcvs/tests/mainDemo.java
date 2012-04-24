package com.mcvs.tests;

import javax.swing.*;
import com.mcvs.controller.*;
import com.mcvs.model.*;
import com.mcvs.view.*;

public class mainDemo {
	public static void main(String[] args) {
		/*
		 * Moves the JMenuBar from the JFrame to the OS X top tool bar to make the application
		 * look more native. Also changes the display name of the application on OS X from 
		 * the package name to 'Minecraft Version Control'
		 */
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Minecraft Version Control");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (UnsupportedLookAndFeelException ex) {ex.printStackTrace();} 
		catch (ClassNotFoundException ex) {ex.printStackTrace();} 
		catch (InstantiationException ex) {ex.printStackTrace();} 
		catch (IllegalAccessException ex) {ex.printStackTrace();}
		
		MCVSView view = new MCVSView("Minecraft Version Control", 650, 450);
		MCVSModel model = MCVSModel.getInstance();
		MCVSController controller = MCVSController.getInstance(view, model);
	}
}
