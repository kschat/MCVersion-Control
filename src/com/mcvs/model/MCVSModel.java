package com.mcvs.model;

import java.io.IOException;
import com.mcvs.core.*;
import com.mcvs.core.platformManager.*;

public class MCVSModel {
	private static MCVSModel INSTANCE = null;
	private FileManager fileManager;
	private PlatformManager platformManager;
	
	private MCVSModel() {
		platformManager = PlatformManager.getInstance();
		fileManager = FileManager.getInstance(platformManager.getHomeDirectory());
		System.out.println(platformManager.getHomeDirectory());
	}
	
	public static MCVSModel getInstance() {
		if(INSTANCE==null) {
			INSTANCE = new MCVSModel();
		}
		
		return INSTANCE;
	}
	
	public void launchMinecraft() {
		try {
			Runtime.getRuntime().exec(new String[] {"open", platformManager.getMinecraftRunDirectory()});
		} 
		catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	public void closeMCVS() {
		System.exit(0);
	}
}
