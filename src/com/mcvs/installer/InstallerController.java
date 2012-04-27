package com.mcvs.installer;

import java.awt.event.*;

public class InstallerController {
	private static InstallerController INSTANCE = null;
	private Installer model;
	private InstallerGUI view;
	
	private InstallerController(Installer m, InstallerGUI v) {
		model = m;
		view = v;
		
		view.addNextButtonActionListener(new NextButtonActionListener());
		view.addBackButtonActionListener(new BackButtonActionListener());
		
		view.setVisible(true);
	}
	
	public static InstallerController getInstance(Installer model, InstallerGUI view) {
		if(INSTANCE == null) {
			INSTANCE = new InstallerController(model, view);
		}
		
		return INSTANCE;
	}
	
	class NextButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch(model.nextState(false).getIndex()) {
			case 0: 
				System.out.println("Index: " + model.nextState(false).getIndex());
				System.out.println("Next State: " + model.nextState(false));
				System.out.println("Last State: " + model.lastState(false));
				model.nextState(true);
				break;
			case 1: 
				view.enableBackButton(true);
				System.out.println("Index: " + model.nextState(false).getIndex());
				System.out.println("Next State: " + model.nextState(false));
				System.out.println("Last State: " + model.lastState(false));
				model.nextState(true);
				break;
			case 2: 
				System.out.println("Index: " + model.nextState(false).getIndex());
				System.out.println("Next State: " + model.nextState(false));
				System.out.println("Last State: " + model.lastState(false));
				model.nextState(true);
				break;
			case 3: 
				System.out.println("Index: " + model.nextState(false).getIndex());
				System.out.println("Next State: " + model.nextState(false));
				System.out.println("Last State: " + model.lastState(false));
				model.nextState(true);
				break;
			case 4: 
				System.out.println("Index: " + model.nextState(false).getIndex());
				System.out.println("Next State: " + model.nextState(false));
				System.out.println("Last State: " + model.lastState(false));
				model.nextState(true);
				break;
			case 5: 
				System.out.println("Index: " + model.nextState(false).getIndex());
				System.out.println("Next State: " + model.nextState(false));
				System.out.println("Last State: " + model.lastState(false));
				model.nextState(true);
				break;
			case 6: 
				System.out.println("Index: " + model.nextState(false).getIndex());
				System.out.println("Next State: " + model.nextState(false));
				System.out.println("Last State: " + model.lastState(false));
				model.nextState(true);
				break;
			default:
				System.out.println("default");
			}
		}
	}
	
	class BackButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch(model.lastState(false).getIndex()) {
			case 0: 
				System.out.println("Index: " + model.lastState(false).getIndex());
				System.out.println("Next State: " + model.nextState(false));
				System.out.println("Last State: " + model.lastState(false));
				model.lastState(true);
				break;
			case 1: 
				view.enableBackButton(false);
				System.out.println("Index: " + model.lastState(false).getIndex());
				System.out.println("Next State: " + model.nextState(false));
				System.out.println("Last State: " + model.lastState(false));
				model.lastState(true);
				break;
			case 2: 
				System.out.println("Index: " + model.lastState(false).getIndex());
				System.out.println("Next State: " + model.nextState(false));
				System.out.println("Last State: " + model.lastState(false));
				model.lastState(true);
				break;
			case 3: 
				System.out.println("Index: " + model.lastState(false).getIndex());
				System.out.println("Next State: " + model.nextState(false));
				System.out.println("Last State: " + model.lastState(false));
				model.lastState(true);
				break;
			case 4: 
				System.out.println("Index: " + model.lastState(false).getIndex());
				System.out.println("Next State: " + model.nextState(false));
				System.out.println("Last State: " + model.lastState(false));
				model.lastState(true);
				break;
			case 5: 
				System.out.println("Index: " + model.lastState(false).getIndex());
				System.out.println("Next State: " + model.nextState(false));
				System.out.println("Last State: " + model.lastState(false));
				model.lastState(true);
				break;
			case 6: 
				System.out.println("Index: " + model.lastState(false).getIndex());
				System.out.println("Next State: " + model.nextState(false));
				System.out.println("Last State: " + model.lastState(false));
				model.lastState(true);
				break;
			default:
				System.out.println("default");
			}
		}
	}
}
