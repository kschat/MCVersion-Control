package com.mcvs.model;

import java.util.*;
import java.io.*;

import com.mcvs.core.*;
import com.mcvs.core.platformManager.*;

public class MCVSModel {
	private static MCVSModel INSTANCE = null;
	private FileManager fileManager;
	private PlatformManager platformManager;
	private String currentVersion = null;
	
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
	/*
	 * TODO Remove try statements from Model, replace with throws and handle
	 * the exception in the Controller.
	 */
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
	 * 
	 * TODO Remove try statements from Model, replace with throws and handle
	 * the exception in the Controller.
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
	 * Method used to get the current Minecraft version. If the read boolean is
	 * true then the method will read the version saved in the currentVer file and
	 * return the value; else just return the value in currentVersion
	 * 
	 * TODO Remove try statements from Model, replace with throws and handle
	 * the exception in the Controller.
	 */
	public String getCurrentVersion(boolean read) {
		if(read) {
			String[] temp;
			try {
				temp = FileManager.readLinesFromFile(new File(this.getClass().getResource("/data/currentVer.txt").getPath()));
				currentVersion = temp[0];
			} 
			catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
		
		return currentVersion;
	}
	
	/*
	 * TODO Remove try statements from Model, replace with throws and handle
	 * the exception in the Controller.
	 */
	public void updateCurrentVersion(String ver) {
		try {
			FileManager.writeToFile(new File(this.getClass().getResource("/data/currentVer.txt").getPath()), ver);
			currentVersion = ver;
		}
		catch (IOException ex) {
			//TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	/*
	 * Gets every minecraft version released
	 * Returns null if error occurred, otherwise returns a String array.
	 * 
	 * TODO Remove try statements from Model, replace with throws and handle
	 * the exception in the Controller.
	 */
	public String[] getAllMCVersions() {
		String[] temp = null;
		try {
			temp = FileManager.readLinesFromFile(new File(platformManager.getDataDirectory()+"/comboBoxVers"));
		} 
		catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		return temp;
	}
	
	/*
	 * TODO Remove try statements from Model, replace with throws and handle
	 * the exception in the Controller.
	 */
	public String getMCVSVersion() {
		String[] temp = null;
		try {
			temp = FileManager.readLinesFromFile(new File(platformManager.getDataDirectory()+"/MCVSver"));
			
		} 
		catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		return temp[0];
	}
	
	public void moveVersion(String version, String filename) throws IOException {
		FileManager.moveFile(new File(platformManager.getDataDirectory()+"/versions/"+version+"/"+filename),
				new File(platformManager.getMinecraftDirectory()+"minecraft.jar"));
	}
}
