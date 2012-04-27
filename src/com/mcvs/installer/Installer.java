package com.mcvs.installer;

import java.io.*;
import java.util.*;
import com.mcvs.core.*;
import com.mcvs.core.platformManager.*;

public class Installer {
	private static Installer INSTANCE = null;
	private final static String INSTALL_DIR = "MCVersion-Control/";
	private FileManager fileManager;
	private PlatformManager platformManager;
	private String installDir;
	private Stack<State> currentState;
	private Stack<State> backStack;
	
	private Installer() throws IOException {
		platformManager = PlatformManager.getInstance();
		fileManager = FileManager.getInstance();
		currentState = new Stack<State>();
		backStack = new Stack<State>();
		
		for(int i=State.summary.getIndex(); i>0; i--) {
			currentState.push(State.getByIndex(i));
		}
	}
	
	public static Installer getInstance() throws IOException {
		if(INSTANCE == null) {
			INSTANCE = new Installer();
		}
		return INSTANCE;
	}
	
	public String checkMCDirectory() throws IOException {
		String message = null;
		fileManager.setFileBrowser(new File(platformManager.getMinecraftDirectory()));
		
		if(fileManager.getFileBrowser().exists()) {
			message = "Minecraft is installed";
		}
		else {
			message = "Minecraft not installed";
		}
		
		return message;
	}
	
	public String getDefaultInstallDir() {
		return installDir;
	}
	
	public String setInstallDirectory(String dir) throws IOException {
		String message = "Install directory was not set";
		fileManager.setFileBrowser(new File(dir));
		if(fileManager.getFileBrowser().exists()) {
			installDir = new File(dir+INSTALL_DIR).getPath();
			message = "Install directory set";
		}
		
		System.out.println(dir+INSTALL_DIR);
		return message;
	}
	
	public int getState() {
		return currentState.peek().getIndex();
	}
	
	public State nextState(boolean pop) {
		if(currentState.size() == 0) {
			return null;
		}
		
		if(pop) {
			backStack.push(currentState.peek());
			return currentState.pop();
		}
		
		return currentState.peek();
	}
	
	public State lastState(boolean pop) {
		if(backStack.size() == 0) {
			return null;
		}
		
		if(pop) {
			currentState.push(backStack.peek());
			return backStack.pop();
		}
		
		return backStack.peek();
	}
}
