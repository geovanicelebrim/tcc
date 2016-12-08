package util;

import java.util.ArrayList;
import java.util.Date;

import exception.ErrorFileException;

public class Log {
	private static Log instance;
	private static String pathLogSystem = DAO.Paths.REPOSITORY.toString() + "logSystem.txt";
	private static String pathLogManagement = DAO.Paths.REPOSITORY.toString() + "logManagement.txt";
	private static String pathAccessCount = DAO.Paths.REPOSITORY.toString() + "accessCount.txt";
	private static ArrayList<String> logSystemBuffer = new ArrayList<>();
	private static ArrayList<String> logManagementBuffer = new ArrayList<>();
	private static boolean blockSystem = false;
	private static boolean blockManagement = false;
	private static boolean blockAccessCount = false;
	
	private static boolean systemBoot = false; // Foi inicializado?
	private static boolean index = false; // Está criado?
	private static boolean dictionary = false; // Está criado?
	private static boolean database = false; // Está atualizado e com conexão?
	private static boolean keyWordEngine = false; // Está operando normalmente?
	private static boolean semanticEngine = false; // Está operando normalmente?
	private static boolean management = false; // Está operando normalmente?
	
	private static Integer accessCount = 0; // Contador de acessos
	
	private Log () {
		Thread t = new Thread(new Runnable() {
			@SuppressWarnings("deprecation")
			public void run() {
				while(true) {
					
					if(logSystemBuffer.size() > 20) {
						while (blockSystem) {};
						blockSystem = true;
						freeLogSystemBuffer();
						blockSystem = false;
					}
					
					if(logManagementBuffer.size() > 20) {
						while (blockManagement) {};
						blockManagement = true;
						freeLogManagementBuffer();
						blockManagement = false;
					}
					
					Date now = new Date();
					Date scheduled = new Date();
					scheduled.setHours(23); scheduled.setMinutes(59); scheduled.setSeconds(59);
					
					if(now.after(scheduled)) {
						while (blockAccessCount) {};
						blockAccessCount = true;
						String text = scheduled + "\t" + accessCount + "\n";
						write(pathAccessCount, text);
						accessCount = 0;
						blockAccessCount = false;
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
	
	private static void freeLogSystemBuffer() {
		String log = "";
		
		for (String string : logSystemBuffer) {
			log += string + "\n";
		}
		write(pathLogSystem, log);
		logSystemBuffer.clear();
	}
	
	private static void freeLogManagementBuffer() {
		String log = "";
		
		for (String string : logSystemBuffer) {
			log += string + "\n";
		}
		write(pathLogManagement, log);
		logSystemBuffer.clear();
	}
	
	private static void write(String p, String log) {
		try {
			DAO.File.writeFile(p, log, true);
		} catch (ErrorFileException e) {
			
		}
	}
	
	public static ArrayList<String> getLogSystem() {
		while (blockSystem) {};
		blockSystem = true;
		if(logSystemBuffer.size() > 0) {
			freeLogSystemBuffer();
		}
		
		ArrayList<String> logSystem = DAO.File.readLinesFile(pathLogSystem);
		blockSystem = false;
		
		return logSystem;
	}
	
	public static ArrayList<String> getLogManagement() {
		while (blockManagement) {};
		blockManagement = true;
		if(logManagementBuffer.size() > 0) {
			freeLogManagementBuffer();
		}
		
		ArrayList<String> logManagement = DAO.File.readLinesFile(pathLogManagement);
		blockManagement = false;
		
		return logManagement;
	}

	public static void addSystemEntry(String ip, String entry) {
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (blockSystem) {};
				blockSystem = true;
				logSystemBuffer.add(new Date() + "\t" + ip + "\t" + entry);
				blockSystem = false;
			}
		});
		
		t.start();
	}
	
	public static void addManagementEntry(String ip, String email, String entry) {
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (blockManagement) {};
				blockManagement = true;
				logManagementBuffer.add(new Date() + "\t" + ip + "\t" + email + "\t" + entry);
				blockManagement = false;
			}
		});
		
		t.start();
	}
	
	
	public boolean getSystemBoot() {
		return systemBoot;	
	}
	public boolean getIndex() {
		return index;	
	}
	public boolean getDictionary() {
		return dictionary;	
	}
	public boolean getDatabase() {
		return database;	
	}
	public boolean getKeyWordEngine() {
		return keyWordEngine;	
	}
	public boolean getSemanticEngine() {
		return semanticEngine;	
	}
	public boolean getManagement() {
		return management;	
	}


	public void setSystemBoot(boolean state) {
		systemBoot = state;
	}
	public void setIndex(boolean state) {
		index = state;
	}
	public void setDictionary(boolean state) {
		dictionary = state;
	}
	public void setDatabase(boolean state) {
		database = state;
	}
	public void setKeyWordEngine(boolean state) {
		keyWordEngine = state;
	}
	public void setSemanticEngine(boolean state) {
		semanticEngine = state;
	}
	public void setManagement(boolean state) {
		management = state;
	}
	
	
	public void addAccess() {
		while (blockAccessCount) {};
		blockAccessCount = true;
		accessCount += 1;
		blockAccessCount = false;
	}
	
	public static ArrayList<String> getAccessList() {
		while (blockAccessCount) {};
		blockAccessCount = true;
		
		ArrayList<String> accessCountList = DAO.File.readLinesFile(pathAccessCount);
		blockAccessCount = false;
		
		return accessCountList;
	}
}
