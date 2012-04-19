package com.mcvs.model;
import com.mcvs.core.*;

public class MCVSModel implements Singleton {
	private static MCVSModel INSTANCE = null;
	
	private MCVSModel() {
		
	}
	
	public MCVSModel getInstance() {
		if(INSTANCE==null) {
			INSTANCE = new MCVSModel();
		}
		
		return INSTANCE;
	}
}
