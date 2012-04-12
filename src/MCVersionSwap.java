import java.util.*;
import java.io.*;
import java.nio.channels.FileChannel;

public class MCVersionSwap {
	private File fileBrowser;
	private BufferedWriter fileWriter;
	private static BufferedReader fileReader;
	private static String filePath, currVer;
	private static ArrayList<Entity> entities;
	private static OS os;
	private static ArrayList<String> mcVersions;
	
	public MCVersionSwap() throws IOException {
		File[] files;
		entities = new ArrayList<Entity>();
		currVer = this.readCurrentVersion();
		mcVersions = readVersions();
		
		if(System.getProperty("os.name").equals("Mac OS X")) {
			os=OS.mac;
			fileBrowser = new File("/Users/kyleschattler/Library/Application Support/minecraft/bin/versions");
		}
		else if(System.getProperty("os.name").equals("Windows 7")) {
			os=OS.windows;
			fileBrowser = new File(System.getenv("APPDATA") + "/.minecraft/bin/versions");
			System.out.println(fileBrowser.getPath());
		}
		
		filePath = fileBrowser.getPath();
		files = fileBrowser.listFiles();
		
		for(int i=0; i<files.length; i++) {
			if(files[i].isDirectory()) {
				fileReader = new BufferedReader(new FileReader(files[i]+"/entity.txt"));
				String[] temp = fileReader.readLine().split(",");
				entities.add(new Entity(temp[0], temp[1], new Directory(files[i].getPath(), files[i].getName())));
			}
		}
	}
	
	public Object[] getDirectories() {
		return entities.toArray();
	}
	
	public String[][] get2DArray() {
		String[][] temp = new String[entities.size()][2];
		for(int i=0; i<entities.size(); i++) {
			for(int x=0; x<2; x++) {
				if(x==0) {
					temp[i][x]=entities.get(i).getName();
				}
				else {
					temp[i][x]=entities.get(i).getVersion();
				}
			}
		}
		
		return temp;
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
	public void updateCurrentVersion(String ver) throws IOException {
		fileWriter=null;
		try {
			fileWriter = new BufferedWriter(new FileWriter(this.getClass().getResource("currentVer.txt").getPath()));
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
	
	/*
	 * returns a new ArrayList with the values in mcVersions
	 */
	public static ArrayList<String> getMCVersions() {
		return new ArrayList<String>(mcVersions);
	}
	
	public static void main(String[] args) {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Minecraft Swap");
		new GUI();
	}
}