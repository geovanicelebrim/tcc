package util;

import java.util.ArrayList;
import java.util.Date;

import exception.ErrorFileException;

public class Log {
	private static Log instance;
	private static String path = DAO.Paths.REPOSITORY.toString() + "log.txt";
	private static ArrayList<String> logBuffer = new ArrayList<>();
	private static boolean block = false;
	
	private Log () {
		Thread t = new Thread(new Runnable() {
			public void run() {
				while(true) {
					
					if(logBuffer.size() > 20) {
						while (block) {};
						block = true;
						freeLogBuffer();
						block = false;
					}
					
					try {
						Thread.sleep(15000);
					} catch (InterruptedException e) {
					}
				}
				
			}
		});
		
		t.start();
	}
	
	public static synchronized Log getInstance() {
		if (instance == null) {
			instance = new Log();
		}
		return instance;
	}
	
	private static void freeLogBuffer() {
		String log = "";
		
		for (String string : logBuffer) {
			log += string + "\n";
		}
		write(log);
		logBuffer.clear();
	}
	
	private static void write(String log) {
		try {
			DAO.File.writeFile(path, log, true);
		} catch (ErrorFileException e) {
			
		}
	}
	
	public static ArrayList<String> getLogSystem() {
		while (block) {};
		block = true;
		if(logBuffer.size() > 0) {
			freeLogBuffer();
		}
		
		ArrayList<String> logSystem = DAO.File.readLinesFile(path);
		block = false;
		
		return logSystem;
	}

	public static void addEntry(String entry) {
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (block) {};
				block = true;
				logBuffer.add(new Date() + "\t" + entry);
				block = false;
			}
		});
		
		t.start();
	}
}
