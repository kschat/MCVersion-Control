package com.mcvs.model;

import java.util.*;
import java.io.*;

import com.mcvs.core.*;
import com.mcvs.core.platformManager.*;

public class MCVSModel {
	private static MCVSModel INSTANCE = null;
	private FileManager fileManager;
	private PlatformManager platformManager;
	
	private MCVSModel() {
		platformManager = PlatformManager.getInstance();
		fileManager = FileManager.getInstance(platformManager.getHomeDirectory());
	}
	
	public static MCVSModel getInstance() {
		if(INSTANCE==null) {
			INSTANCE = new MCVSModel();
		}
		
		return INSTANCE;
	}
	
	public void launchMinecraft() {
		try {
			Runtime.getRuntime().exec(platformManager.getMinecraftRunDirectory());
		} 
		catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	public void closeMCVS() {
		System.exit(0);
	}
	
	/*
	 * Method used to return a list of entities read from each entity file in the
	 * versions directory.
	 */
	public Vector<Entity> getEntities() {
		Vector<Entity> entities = new Vector<Entity>();
		File[] files = new File(platformManager.getDataDirectory()+"/versions").listFiles();
		
		try {
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					String[] temp;
					temp = FileManager.readLinesFromFile(new File(files[i]+"/entity.txt"));
					entities.add(new Entity(temp[0], temp[1], files[i].getPath()));
				}
			}
		}
		catch (IOException ex) {
			//TODO Add handler and error reporter
			ex.printStackTrace();
		}
		
		return entities;
	}
	
	/*
	 * Method used to read the current version set for minecraft to run
	 * Returns the version if all went well, null if an exception is thrown
	 */
	public String readCurrentVersion() {
		String[] temp;
		try {
			temp = FileManager.readLinesFromFile(new File(this.getClass().getResource("/data/currentVer.txt").getPath()));
		} 
		catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return null;
		}
		
		return temp[0];
	}
	
	/*
	 * Gets every minecraft version released
	 * Returns null if error occurred, otherwise returns a String array
	 */
	public String[] getAllMCVersions() {
		String[] temp;
		try {
			temp = FileManager.readLinesFromFile(new File(platformManager.getDataDirectory()+"/comboBoxVers"));
			
			return temp;
		} 
		catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return null;
		}
	}
}
