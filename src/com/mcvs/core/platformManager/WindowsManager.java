package com.mcvs.core.platformManager;

public class WindowsManager extends PlatformManager {
	private static WindowsManager INSTANCE = null;
	
	private WindowsManager() { 
		super();
		homeDirectory = System.getProperty("user.home");
		minecraftDirectory = System.getenv("APPDATA") + "\\.minecraft\\bin\\";
		minecraftRunDirectory = "C:\\Users\\Kyle\\Desktop\\Games\\Minecraft.exe";
		dataDirectory = this.getClass().getResource("/data").getPath();
	}
	
	public static WindowsManager getInstance() {
		if(INSTANCE==null) {
			INSTANCE = new WindowsManager();
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
		return new String[] {minecraftRunDirectory};
	}

	@Override
	public String getDataDirectory() {
		return dataDirectory;
	}
	
}
