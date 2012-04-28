package com.mcvs.core;

import java.io.*;
import java.nio.channels.*;

public class FileLockManager {
	private FileChannel lockChannel = null;
	private FileLock lock = null;
	private File lFile = null;
	
	public FileLockManager()  { }
	
	public FileLockManager(File file) {
		this.lockFile(file);
	}
	
	public void lockFile(File file) {
		lFile = file;
		try {
			if(file.exists()) {
				lFile.delete();
			}
			
			lockChannel = new RandomAccessFile(lFile, "rw").getChannel();
			lock = lockChannel.tryLock();
			
			if(lock == null) {
				lockChannel.close();
				System.exit(1);
				//throw new RuntimeException("An instance of MCVC is already running.");
			}
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					unlockFile();
				}
			});
		}
		catch(IOException ex) {
			throw new RuntimeException("Could not start process.", ex);
		}
	}
	
	public void unlockFile() {
		try {
			if(lock != null) {
				lock.release();
				lockChannel.close();
				lFile.delete();
			}
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
