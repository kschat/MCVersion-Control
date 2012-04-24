package com.mcvs.core.platformManager;

public class MacManager extends PlatformManager {
	private static MacManager INSTANCE = null;
	
	private MacManager() { 
		super();
		homeDirectory = System.getProperty("user.home");
		minecraftDirectory = homeDirectory + "/Library/Application Support/minecraft/";
		minecraftRunDirectory = "/Applications/Minecraft/Minecraft.app";
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
}
