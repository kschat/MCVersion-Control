package com.mcvs.core.platformManager;

public class WindowsManager extends PlatformManager {
	private static WindowsManager INSTANCE = null;
	
	private WindowsManager() { 
		super();
		homeDirectory = System.getProperty("user.home");
		minecraftDirectory = System.getenv("APPDATA") + "\\.minecraft\\bin\\";
		minecraftRunDirectory = "C:\\Users\\Kyle\\Desktop\\Games\\Minecraft.exe";
	}
	
	public static WindowsManager getInstance() {
		if(INSTANCE==null) {
			INSTANCE = new WindowsManager();
		}
		
		return INSTANCE;
	}

	@Override
	public String getHomeDirectory() {
		// TODO Auto-generated method stub
		return homeDirectory;
	}

	@Override
	public String getMinecraftDirectory() {
		// TODO Auto-generated method stub
		return minecraftDirectory;
	}

	@Override
	public String[] getMinecraftRunDirectory() {
		// TODO Auto-generated method stub
		return new String[] {minecraftRunDirectory};
	}
	
}
