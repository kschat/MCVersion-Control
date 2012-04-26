package com.mcvs.core.platformManager;

public class MacManager extends PlatformManager {
	private static MacManager INSTANCE = null;
	
	private MacManager() { 
		super();
		homeDirectory = System.getProperty("user.home");
		minecraftDirectory = homeDirectory + "/Library/Application Support/minecraft/bin/";
		minecraftRunDirectory = "/Applications/Minecraft/Minecraft.app";
		dataDirectory = this.getClass().getResource("/data").getPath()+"/";
		versionsDirectory = dataDirectory + "versions/";
	}
	
	public static MacManager getInstance() {
		if(INSTANCE==null) {
			INSTANCE = new MacManager();
		}
		
		return INSTANCE;
	}
	
	@Override
	public String getHomeDirectory() {
		return homeDirectory;
	}

	@Override
	public String getMinecraftDirectory() {
		return minecraftDirectory;
	}

	@Override
	public String[] getMinecraftRunDirectory() {
		return new String[] {"Open", minecraftRunDirectory};
	}
	
	@Override
	public String getDataDirectory() {
		return dataDirectory;
	}

	@Override
	public String getVersionsDirectory() {
		return versionsDirectory;
	}
}
