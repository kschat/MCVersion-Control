package com.mcvs.tests;

import com.mcvs.core.*;
import com.mcvs.installer.*;

import java.io.*;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class tester {
	
	public static void main(String[] args) throws IOException {
		/*
		 * Moves the JMenuBar from the JFrame to the OS X top tool bar to make the application
		 * look more native. Also changes the display name of the application on OS X from 
		 * the package name to 'Minecraft Version Control'
		 */
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Installer");
		
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
		
		InstallerGUI view = new InstallerGUI("Install Minecraft Version Control");
		Installer model = Installer.getInstance();
		InstallerController controller = InstallerController.getInstance(model, view);
	}
}
