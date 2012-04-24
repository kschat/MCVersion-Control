package com.mcvs.tests;

import javax.swing.*;
import com.mcvs.controller.*;
import com.mcvs.model.*;
import com.mcvs.view.*;

public class mainDemo {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MCVSView view = new MCVSView("Minecraft Version Control", 650, 450);
		MCVSModel model = MCVSModel.getInstance();
		MCVSController controller = MCVSController.getInstance(view, model);
	}
}
