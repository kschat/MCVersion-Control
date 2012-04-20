package com.mcvs.tests;

import com.mcvs.core.*;
import java.io.*;

public class tester {
	/*
	public static void main(String[] args) {
		FileManager fm = FileManager.getInstance();
		File file = new File(System.getProperty("user.home")+"/Desktop/test.txt");
		File dir = new File(System.getProperty("user.home")+"/Desktop/testDir/");
		//try {
		
		FileManager.deleteDirectory(dir, true);
		FileManager.createDirectory(dir);
		
			for(int i=0; i<1000; i++) {
				try {
					FileManager.createFile(new File(dir.getPath()+"/File"+i+".txt"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			FileManager.deleteDirectory(dir, true);
			
			
			if(FileManager.createFile(file)) {
				FileManager.writeToFile(file, "ASDFASDFASDF");
				System.out.println("File created");
			}
			else {
				System.out.println("File already exists.");
			}
			
			if(FileManager.deleteFile(file)) {
				System.out.println("File deleted");
			}
			else {
					System.out.println("File was not deleted.");
			}
			
			if(FileManager.createDirectory(dir)) {
				System.out.println("Directory created");
			}
			else {
				System.out.println("Directory already exists");
			}
			
			if(FileManager.deleteDirectory(dir, false)) {
				System.out.println("Directory deleted");
			}
			
			if(FileManager.createDirectory(dir)) {
				System.out.println("Directory created");
				FileManager.createFile(new File(dir.getPath()+"/File1"));
				FileManager.createFile(new File(dir.getPath()+"/File2"));
				System.out.println("Filled directory created");
			}
			else {
				System.out.println("Directory already exists");
			}
			
			if(FileManager.deleteDirectory(dir, false)) {
				System.out.println("Filled directory deleted");
			}
			else {
				System.out.println("Filled directory wasn't deleted");
			}
			
			if(FileManager.deleteDirectory(dir, true)) {
				System.out.println("Filled directory deleted");
			}
			else {
				System.out.println("Filled directory wasn't deleted");
			}
			
		//}
		//catch (IOException e) {
			//e.printStackTrace();
		//}
	}*/
}
