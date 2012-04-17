import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.nio.channels.FileChannel;

import javax.swing.UIManager;

import com.alee.laf.WebLookAndFeel;

public class MCVersionSwap {
	private static MCVersionSwap INSTANCE = null;
	private File fileBrowser;
	private static BufferedWriter fileWriter;
	private static BufferedReader fileReader;
	private static String filePath, currVer;
	private static Vector<Entity> entities;
	private static OS os;
	private static ArrayList<String> mcVersions;
	
	private MCVersionSwap() throws IOException {
		MCVersionSwap.setRunningStatus(true);
		
		File[] files;
		entities = new Vector<Entity>();
		currVer = this.readCurrentVersion();
		mcVersions = readVersions();
		
		if(System.getProperty("os.name").equals("Mac OS X")) {
			os=OS.mac;
			fileBrowser = new File("/Users/kyleschattler/Library/Application Support/minecraft/bin/versions");
		}
		else if(System.getProperty("os.name").equals("Windows 7")) {
			os=OS.windows;
			fileBrowser = new File(System.getenv("APPDATA") + "/.minecraft/bin/versions");
		}
		
		filePath = fileBrowser.getPath();
		files = fileBrowser.listFiles();
		
		for(int i=0; i<files.length; i++) {
			if(files[i].isDirectory()) {
				fileReader = new BufferedReader(new FileReader(files[i]+"/entity.txt"));
				String[] temp = fileReader.readLine().split(",");
				entities.add(new Entity(temp[0], temp[1], files[i].getPath()));
			}
		}
		
		fileReader.close();
	}
	
	/*
	 * Method to get instance of MCVersionSwap. 
	 * MCVersionSwap is a singleton class.
	 */
	public static MCVersionSwap getInstance() throws IOException {
		if(INSTANCE == null) {
			INSTANCE = new MCVersionSwap();
		}
		return INSTANCE;
	}
	
	public static void firstTimeRunning() {
		
	}
	
	
	/*
	 * TODO: use to switch over to new way of detecting files.
	 */
	public String matchFile(String file) {
		String temp="";
		Pattern pattern = Pattern.compile("/[0-9]{1}\\.[0-9]{1}(\\.[0-9]){0,1}/.*\\.jar$");
		Matcher match = pattern.matcher(file);
		
		while(match.find()) {
			temp = match.group();
		}
		
		return temp;
	}
	
	private static boolean checkIfRunning() throws IOException {
		File file = new File(MCVersionSwap.class.getResource("data").getPath()+"/isRunning.txt");
		if(file.exists()) {
			return true;
		}
		return false;
	}
	
	private static void setRunningStatus(boolean running) throws IOException {
		File file = new File(MCVersionSwap.class.getResource("data").getPath()+"/isRunning.txt");
		if(running) {
			file.createNewFile();
		}
		else {
			file.delete();
		}
	}
	
	public Object[] getDirectories() {
		return entities.toArray();
	}
	
	public static Vector<Entity> getEntities() {
		return entities;
	}
	
	public static void moveFile(File sourceFile, File destFile) throws IOException {
		FileChannel source = null;
		FileChannel destination = null;
		
		try {
			System.out.println(sourceFile.getCanonicalPath());
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
	
	public static String getPath() {
		return filePath;
	}
	
	public static OS getOS() {
		return os;
	}
	
	/*
	 * Updates the current version text file with the value passed in ver
	 */
	public static void updateCurrentVersion(String ver) throws IOException {
		fileWriter=null;
		try {
			fileWriter = new BufferedWriter(new FileWriter(MCVersionSwap.class.getResource("currentVer.txt").getPath()));
			fileWriter.write(ver);
			fileWriter.flush();
			currVer=ver;
		}
		finally {
			if(fileWriter!=null) {
				fileWriter.close();
			}
		}
	}
	
	/*
	 * returns the value in the current version text file
	 */
	private String readCurrentVersion() throws IOException {
		fileReader=null;
		String temp;
		try {
			fileReader = new BufferedReader(new FileReader(this.getClass().getResource("currentVer.txt").getPath()));
			temp=fileReader.readLine();
		}
		catch(IOException ex) {
			return null;
		}
		finally {
			if(fileReader!=null) {
				fileReader.close();
			}
		}
		
		return temp;
	}
	
	/*
	 * returns the value stored in currVer
	 */
	public static String getCurrentVersion() {
		return currVer;
	}
	
	public void addJarFiles(File[] files) throws IOException {
		for(int i=0; i<files.length; i++) {
			//this.moveFile(files[i], new File(filePath));
			//System.out.println(filePath);
		}
	}
	
	/*
	 * Runs the minecraft application
	 */
	public static void runMinecraft() throws IOException {
		if(os.equals(OS.mac)) {
			Process p = Runtime.getRuntime().exec(new String[] {"open", "/Applications/Minecraft/Minecraft.app"});
		}
		
		if(os.equals(OS.windows)) {
			Runtime.getRuntime().exec(new String[] {"C:\\Users\\Kyle\\Desktop\\Games\\Minecraft.exe"});
		}
	}
	
	/*
	 * Reads the version text file and returns an ArrayList with the values
	 */
	public static ArrayList<String> readVersions() throws IOException {
		fileReader=null;
		String line;
		ArrayList<String> ver = new ArrayList<String>();
		
		try {
			fileReader = new BufferedReader(new FileReader(MCVersionSwap.class.getResource("versions.txt").getPath()));
			
			line = fileReader.readLine();
			while(line!=null) {
				ver.add(line);
				line = fileReader.readLine();
			}
		}
		catch(IOException ex) {
			return null;
		}
		finally {
			if(fileReader!=null) {
				fileReader.close();
			}
		}
		
		return ver;
	}
	
	public static void writeEntityFile(String name, String ver) throws IOException {
		fileWriter = null;
		
		try {
			File temp = new File(getPath()+"/"+ver+"/entity.txt");
			if(!temp.exists()) {
				temp.createNewFile();
			}
			fileWriter = new BufferedWriter(new FileWriter(MCVersionSwap.getPath()+"/"+ver+"/entity.txt"));
			fileWriter.write(name+",");
			fileWriter.write(ver);
			fileWriter.flush();
		}
		finally {
			fileWriter.close();
		}
	}
	
	public static void deleteEntityFile(String ver) {
		File folder = new File(MCVersionSwap.buildPath(ver));
		
		File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	        	System.out.println(f.getPath());
	            f.delete();
	        }
	    }
	    folder.delete();
	}
	
	public static void updateEntityFile(String ver) {
		File folder = new File(MCVersionSwap.buildPath(ver));
		
		File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	        	System.out.println(f.getPath());
	            f.delete();
	        }
	    }
	    folder.delete();
	}
	
	/*
	 * TODO: Implement buildPath method where applicable
	 */
	public static String buildPath(String ver, String fName) {
		return MCVersionSwap.getPath()+"/"+ver+"/"+fName;
	}
	
	public static String buildPath(String ver) {
		return MCVersionSwap.getPath()+"/"+ver+"/";
	}
	
	/*
	 * returns a new ArrayList with the values in mcVersions
	 */
	public static ArrayList<String> getMCVersions() {
		return new ArrayList<String>(mcVersions);
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	public static void closeProgram() {
		try {
			MCVersionSwap.setRunningStatus(false);
		} catch (IOException e) {
			File file = new File("");
			file.renameTo(new File(MCVersionSwap.class.getResource("isRunning.error.txt").getPath()));
		}
		finally {
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		try {
			if(MCVersionSwap.checkIfRunning()) {
				System.exit(0);
			}
		} catch (IOException e) { }
		
		try {
			UIManager.setLookAndFeel(WebLookAndFeel.class.getCanonicalName ());
		}
		catch(Throwable e) {
			
		}
		
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Minecraft Swap");
		new GUI();
	}
}