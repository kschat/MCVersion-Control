package com.mcvs.core;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class UpdateManager extends SwingWorker<Void, String> {
	private static int PORT = 3233;
	private Socket clientSocket;
	private BufferedReader input;
	private DataOutputStream output;
	
	public UpdateManager() {
		try {
			InetAddress address = InetAddress.getLocalHost();
			System.out.println(address.getHostAddress());
			clientSocket = new Socket("10.0.2.15", PORT);
		} 
		catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("A");
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("B");
			e.printStackTrace();
		}
		
	}

	@Override
	protected Void doInBackground() throws Exception {
		return null;
	}
	
	public static void main(String[] args) {
		new UpdateManager();
	}
}
