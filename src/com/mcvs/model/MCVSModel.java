package com.mcvs.model;

import java.util.*;
import java.io.*;
import com.mcvs.core.*;
import com.mcvs.core.platformManager.*;

public class MCVSModel {
	private static MCVSModel INSTANCE = null;
	private FileManager fileManager;	//TODO Decide if fileManager should be static class
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
		File[] files = new File(platformManager.getDataDirectory()+"versions").listFiles();
		
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
				temp = FileManager.readLinesFromFile(new File(platformManager.getDataDirectory()+"currentVer.txt"));
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
	 * Updates the currentVer text file with the version value
	 * passed in by ver
	 * 
	 * TODO Remove try statements from Model, replace with throws and handle
	 * the exception in the Controller.
	 */
	public void updateCurrentVersion(String ver) {
		try {
			FileManager.writeToFile(new File(platformManager.getDataDirectory()+"currentVer.txt"), ver);
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
			temp = FileManager.readLinesFromFile(new File(platformManager.getDataDirectory()+"comboBoxVers"));
		} 
		catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		return temp;
	}
	
	/*
	 * Reads the Minecraft Version Control version number from the 
	 * MCVSver text file
	 * 
	 * TODO Remove try statements from Model, replace with throws and handle
	 * the exception in the Controller.
	 */
	public String getMCVSVersion() {
		String[] temp = null;
		try {
			temp = FileManager.readLinesFromFile(new File(platformManager.getDataDirectory()+"MCVSver"));
			
		} 
		catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		return temp[0];
	}
	
	/*
	 * Moves the file in the directory built by version and filename to the active
	 * Minecraft directory.
	 * 
	 * TODO Implement more efficient method of storing files
	 */
	public void moveVersion(String version, String filename) throws IOException {
		FileManager.moveFile(new File(platformManager.getVersionsDirectory()+version+"/"+filename),
				new File(platformManager.getMinecraftDirectory()+"minecraft.jar"));
	}
	
	public void addVersion(Entity source, Entity dest) throws IOException {
		FileManager.moveFile(new File(source.getDirectory()+source.getName()),
				new File(platformManager.getVersionsDirectory()+dest.getVersion()+"/"+dest.getName()));
		
		FileManager.createFile(new File(platformManager.getVersionsDirectory()+dest.getVersion()+"/entity.txt"));
		FileManager.writeToFile(new File(platformManager.getVersionsDirectory()+dest.getVersion()+"/entity.txt"), 
				dest.getName()+"\n"+dest.getVersion().trim());
	}
	
	public boolean makeDirectory(String path) {
		boolean created = new File(platformManager.getVersionsDirectory()+path).mkdir();
		
		return created;
	}
	
	/*
	 * Deletes a version file in the data directory. 
	 * Returns true if the file was deleted, false if something went wrong.
	 */
	public boolean deleteVersionFile(String version) {
		boolean deleted = (FileManager.deleteDirectory(new File(platformManager.getVersionsDirectory()+version), true));
		
		return deleted;
	}
	
	/*
	 * Writes the name and version to a entity file
	 */
	public void writeEntityFile(String name, String ver) throws IOException {
		FileManager.writeToFile(new File(platformManager.getVersionsDirectory()+ver+"/"+name), name+"\n"+ver);
	}
}
