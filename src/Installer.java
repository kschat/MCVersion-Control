import java.io.*;

public class Installer {
	private static final String WIN7_MC_DIR = System.getenv("APPDATA") + "/.minecraft/bin/";
	private static final String OSX_MC_DIR = "/Users/kyleschattler/Library/Application Support/minecraft/bin/";
	private static final String LINUX_MC_DIR = "";
	private static Installer INSTANCE = null;
	private String MC_DIR = "";
	private String install_dir;
	private File fileBrowser;
	private OS os;
	private MCVersionSwap mcvs;
	
	private Installer(OS os) throws IOException {
		mcvs = MCVersionSwap.getInstance();
		this.os = os;
		
		switch(os) {
		case windows:
			MC_DIR = WIN7_MC_DIR;
			install_dir = System.getenv("ProgramFiles");
			break;
		case mac:
			MC_DIR = OSX_MC_DIR;
			break;
		case linux:
			MC_DIR = LINUX_MC_DIR;
			break;
		default:
			System.out.println("OS not supported.");
		}
	}
	
	public static Installer getInstance(OS os) throws IOException {
		if(INSTANCE == null) {
			INSTANCE = new Installer(os);
		}
		return INSTANCE;
	}
	
	public boolean checkMCDirectory() throws IOException {
		fileBrowser = new File(MC_DIR);
		if(fileBrowser.exists()) {
			fileBrowser = new File(MC_DIR+"/versions");
			if(fileBrowser.createNewFile()) {
				return true;
			}
		}
		else {
			System.out.println("Minecraft not installed.");
		}
		return false;
	}
	
	public String getDefaultInstallDir() {
		return install_dir;
	}
	
	public boolean setInstallDirectory(String dir) throws IOException {
		fileBrowser = new File(dir);
		if(fileBrowser.exists()) {
			fileBrowser = new File(dir+"/MCVersionSwap");
			if(fileBrowser.createNewFile()) {
				install_dir = fileBrowser.getPath();
				return true;
			}
		}
		return false;
	}
	
	public boolean writeNeededFiles() throws IOException {
		if(install_dir!=null) {
			fileBrowser = new File(install_dir+"/data");
			if(fileBrowser.createNewFile()) {
				return true;
			}
		}
		
		return false;
	}
}
