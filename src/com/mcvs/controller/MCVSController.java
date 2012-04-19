package com.mcvs.controller;
import java.awt.event.*;
import java.io.IOException;

import com.mcvs.view.*;
import com.mcvs.core.*;
import com.mcvs.model.*;

public class MCVSController {
	private static MCVSController INSTANCE = null;
	private MCVSView view;
	private MCVSModel model;
	
	private MCVSController(MCVSView v, MCVSModel m) {
		view = v;
		model = m;
		
		view.addLaunchButtonListener(new LaunchButtonListener());
	}
	
	public static MCVSController getInstance(MCVSView v, MCVSModel m) {
		if(INSTANCE==null) {
			INSTANCE = new MCVSController(v, m);
		}
		
		return INSTANCE;
	}
	
	class LaunchButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
				try {
					Runtime.getRuntime().exec(new String[] {"C:\\Users\\Kyle\\Desktop\\Games\\Minecraft.exe"});
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		
	}
}
