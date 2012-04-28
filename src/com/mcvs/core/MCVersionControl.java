package com.mcvs.core;

import javax.swing.*;
import com.mcvs.controller.*;
import com.mcvs.model.*;
import com.mcvs.view.*;
import com.mcvs.core.*;
import java.io.*;

public class MCVersionControl {
	public static void main(String[] args) {
		/*
		 * Moves the JMenuBar from the JFrame to the OS X top tool bar to make the application
		 * look more native. Also changes the display name of the application on OS X from 
		 * the package name to 'Minecraft Version Control'
		 */
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Minecraft Version Control");
		
		/*
		 * Sets the look and feel to the current systems default look and feel
		 */
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (UnsupportedLookAndFeelException ex) {ex.printStackTrace();} 
		catch (ClassNotFoundException ex) {ex.printStackTrace();} 
		catch (InstantiationException ex) {ex.printStackTrace();} 
		catch (IllegalAccessException ex) {ex.printStackTrace();}
		
		/*
		 * Builds the main components to run Minecraft Version Control with
		 * a GUI.
		 */
		MCVSView view = new MCVSView("Minecraft Version Control", 650, 450);
		MCVSModel model = MCVSModel.getInstance();
		MCVSController controller = MCVSController.getInstance(view, model);
	}
}
