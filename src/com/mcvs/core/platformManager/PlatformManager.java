package com.mcvs.core.platformManager;

public abstract class PlatformManager {
	protected String homeDirectory;
	protected String minecraftDirectory;
	protected String minecraftRunDirectory;
	
	/*
	 * Abstract class used to help MCVSModel complete platform specific 
	 * operations without the need for a bunch of switch statements.
	 * Class is static and is used to return the proper platform manager
	 * for the system.
	 */
	protected PlatformManager() { 
		homeDirectory = System.getProperty("user.home");
	}
	
	public static PlatformManager getInstance() {
		if(System.getProperty("os.name").equals("Mac OS X")) {
			return MacManager.getInstance();
		}
		if(System.getProperty("os.name").equals("Windows 7")) {
			return WindowsManager.getInstance();
		}
		//TODO: Detect Linux
		
		return null;
	}
	
	abstract public String getHomeDirectory();
	abstract public String getMinecraftDirectory();
	abstract public String getMinecraftRunDirectory();
}
