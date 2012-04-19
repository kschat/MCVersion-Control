package com.mcvs.model;
import com.mcvs.core.*;

public class MCVSModel {
	private static MCVSModel INSTANCE = null;
	
	private MCVSModel() {
		
	}
	
	public static MCVSModel getInstance() {
		if(INSTANCE==null) {
			INSTANCE = new MCVSModel();
		}
		
		return INSTANCE;
	}
}
