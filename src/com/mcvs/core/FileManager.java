package com.mcvs.core;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class FileManager {
	private static FileManager INSTANCE = null;
	private static FileChannel fileWriter = null;
	private static FileChannel fileReader = null;
	private String basePath;
	private File fileBrowser;
	
	/*
	 * Prevents construction of a FileManager object
	 * without using the getInstance method 
	 */
	private FileManager() { 
		this.setBasePath(System.getProperty("user.home"));
	}
	
	/*
	 * Another private constructor that sets the default
	 * path for the file manager.
	 */
	private FileManager(String bPath) {
		this.setBasePath(bPath);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.mcvs.core.Singleton#getInstance()
	 * Creates an instance of the FileManager class if and only
	 * if there isn't one already created.
	 */
	public static FileManager getInstance() {
		if(INSTANCE==null) {
			INSTANCE = new FileManager();
		}
		
		return INSTANCE;
	}
	
	/*
	 * Creates an instance of the FileManager class if and only
	 * if there isn't one already created.
	 */
	public static FileManager getInstance(String bPath) {
		if(INSTANCE==null) {
			INSTANCE = new FileManager(bPath);
		}
		
		return INSTANCE;
	}
	
	/*
	 * Method used to set the base path of the FileManager
	 */
	public void setBasePath(String bPath) {
		basePath = bPath;
	}
	
	public File getFileBrowser() {
		return fileBrowser;
	}
	
	public void setFileBrowser(File path) {
		fileBrowser = path;
	}
	
	/*
	 * Creates a file and returns true if the file doesn't already exist.
	 * Does nothing and returns false otherwise.
	 */
	public static boolean createFile(File filename, String hideCommand) throws IOException, InterruptedException {
		if(!filename.exists()) {
			filename.createNewFile();
			
			if(!hideCommand.trim().equals("")) {
				//hideFile(filename);
			}
			return true;
		}
		
		return false;
	}
	
	/*
	 * Checks if directory doesn't already exist, then makes the file and 
	 * returns true if all went well. Returns false if the file directory
	 * already exists.
	 */
	public static boolean createDirectory(File dir, String hideCommand) throws IOException, InterruptedException {
		if(!dir.exists()) {
			dir.mkdir();
			
			if(!hideCommand.trim().equals("")) {
				//hideFile(dir);
			}
			return true;
		}
		
		return false;
	}
	
	/*
	 * Creates a file at filename if file doesn't exist. Writes
	 * value to that file using FileChannel.
	 */
	public static void writeToFile(File filename, String value, String hideCommand) throws IOException, InterruptedException {
		if(!hideCommand.trim().equals("")) {
			//unhideFile(filename);
		}
		
		if(!filename.exists()) {
			filename.createNewFile();
		}
		
		byte[] bytes = value.getBytes();
		ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
		for(int i=0; i<bytes.length; i++) {
			buffer.put(bytes[i]);
		}
		buffer.rewind();
		
		try {
			
			fileWriter = new FileOutputStream(filename).getChannel();
			fileWriter.write(buffer);
		}
		finally {
			fileWriter.close();
		}
		
		if(!hideCommand.trim().equals("")) {
			//hideFile(filename);
		}
	}
	
	/*
	 * Method used to move a file from sourceFile to destFile
	 */
	public static void moveFile(File sourceFile, File destFile) throws IOException {
		FileChannel source = null;
		FileChannel destination = null;
		
		try {
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			
			long count = 0;
			long size = source.size();
			source.transferTo(count, size, destination);
		}
		finally {
			if(source!=null) {
				source.close();
			}
			
			if(destination!=null) {
				destination.close();
			}
		}
	}
	
	/*
	 * Checks if file exists, if it doesn't return null. If it does
	 * reads each line from the file and returns an Array of Strings,
	 * each index being 1 line from the file. 
	 */
	public static String[] readLinesFromFile(File filename) throws IOException {
		BufferedReader reader = null;
		//TODO: Convert to stringbuilder
		String lines = "";
		String temp = "";
		
		if(!filename.exists()) {
			return null;
		}
		
		try {
			reader = new BufferedReader(new FileReader(filename));
			temp = reader.readLine();
			
			while(temp != null) {
				lines += temp + "\n";
				temp = reader.readLine();
			}
		}
		finally {
			reader.close();
		}
		
		return lines.split("\n");
	}
	
	/*
	 * TODO: Finish method.
	 */
	public static String readBytesFromFile(File filename) throws IOException, OverlappingFileLockException {
		if(!filename.exists()) {
			return null;
		}
		
		
		try {
			ByteBuffer buffer = ByteBuffer.allocate(((int)filename.getTotalSpace() * 4));
			fileReader = new FileInputStream(filename).getChannel();
			FileLock lock = fileReader.tryLock();
			
			if(lock!=null) {
				fileReader.read(buffer);
			}
			else {
				throw new OverlappingFileLockException();
			}
		}
		finally {
			fileWriter.close();
		}
		
		return "";
	}
	
	/*
	 * Deletes the file at the location passed. If the location is a 
	 * directory the method will return false.
	 * Returns true if and only if the file was deleted;
	 * returns false otherwise
	 */
	public static boolean deleteFile(File file) {
		if(file.isFile()) {
			if(file.delete()) {
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * Deletes the directory at the location passed. If deleteIfFull is true
	 * then the method gets all the files in the directory and tries to delete 
	 * them. If deleteIfFull is false the method will attempt to delete the 
	 * directory. If the directory contains files, the method will return false.
	 * Returns true if and only if the directory was deleted;
	 * returns false otherwise
	 */
	public static boolean deleteDirectory(File dir, boolean deleteIfFull) {
		//Checks if the file exists and if it is actually a directory
		if(dir.exists() && dir.isDirectory()) {
			//Checks if deleteIfFull is true
			if(deleteIfFull) {
				//Goes through each file in the directory and attempts to delete them
				File[] files = dir.listFiles();
				if(files!=null) { 
			        for(File f: files) {
			            f.delete();
			        }
			    }
			}
			//If the directory was deleted successfully then return true
			if(dir.delete()) {
				return true;
			}
		}
		
		//Return false otherwise
		return false;
	}
	
	
	//TODO Fix the hide and unhide file API to work on all platforms
	public static void hideFile(File path) throws IOException, InterruptedException {
		String[] args = {path.getPath()};
		Process p = Runtime.getRuntime().exec(args);
		p.waitFor();
	}
	
	public static void unhideFile(File path) throws IOException, InterruptedException {
		String[] args = {path.getPath()};
		Process p = Runtime.getRuntime().exec(args);
		p.waitFor();
	}
}
