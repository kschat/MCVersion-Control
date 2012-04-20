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
		view.addExitListener(new ExitListener());
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
			model.launchMinecraft();
		}
	}
	
	class ExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}
}
