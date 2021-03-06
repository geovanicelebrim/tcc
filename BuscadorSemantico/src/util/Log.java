package util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import DAO.Neo4j;
import control.SemanticSearch;
import control.SimpleSearch;
import exception.DatabaseConnectionException;
import exception.ErrorFileException;
import exception.InvalidQueryException;

public class Log {
	public static final String ERROR_TYPE = "ERROR";
	public static final String ACTIVITY_TYPE = "ACTIVITY";
	private static Log instance;
	private static String pathLogSystem = DAO.Paths.REPOSITORY.toString() + "logSystem.txt";
	private static String pathLogManagement = DAO.Paths.REPOSITORY.toString() + "logManagement.txt";
	private static String pathAccessCount = DAO.Paths.REPOSITORY.toString() + "accessCount.txt";
	private static ArrayList<String> logSystemBuffer = new ArrayList<>();
	private static ArrayList<String> logManagementBuffer = new ArrayList<>();
	private static boolean blockSystem = false;
	private static boolean blockManagement = false;
	private static boolean blockAccessCount = false;
	
	private static boolean writeAccess = false;
	
	private static Integer accessCount = 0;
	
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

					if(now.getHours() == 0 && now.getMinutes() < 5 && !writeAccess) {
						while (blockAccessCount) {};
						blockAccessCount = true;
						Date date = new Date(); 
						String newstring = new SimpleDateFormat("yyyy-MM-dd").format(date);
						String text = newstring + "\t" + accessCount + "\n";
						write(pathAccessCount, text);
						accessCount = 0;
						blockAccessCount = false;
						writeAccess = true;
					}

					if(now.getHours() >= 0 && now.getMinutes() >= 5 && writeAccess) {
						writeAccess = false;
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
		
		for (String string : logManagementBuffer) {
			log += string + "\n";
		}
		write(pathLogManagement, log);
		logManagementBuffer.clear();
	}
	
	private static void write(String p, String log) {
		try {
			DAO.File.writeFile(p, log, true);
		} catch (ErrorFileException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getLogSystem() {
		while (blockSystem) {};
		blockSystem = true;
		if(logSystemBuffer.size() > 0) {
			freeLogSystemBuffer();
		}
		
		ArrayList<String> logSystem = null;
		
		try {
			logSystem = DAO.File.readLinesFile(pathLogSystem);
		} catch (IOException e) {
			logSystem = new ArrayList<>();
		}
		
		blockSystem = false;
		
		return logSystem;
	}
	
	public ArrayList<String> getLogManagement() {
		while (blockManagement) {};
		blockManagement = true;
		if(logManagementBuffer.size() > 0) {
			freeLogManagementBuffer();
		}
		
		ArrayList<String> logManagement = null;
		try {
			logManagement = DAO.File.readLinesFile(pathLogManagement);
		} catch (IOException e) {
			logManagement = new ArrayList<>();
		}
		blockManagement = false;
		
		return logManagement;
	}

	public void addSystemEntry(String ip, String entry) {
		
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
	
	public void addManagementEntry(String logType, String ip, String email, String entry) {
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (blockManagement) {};
				blockManagement = true;
				logManagementBuffer.add(logType + "\t" + new Date() + "\t" + ip + "\t" + email + "\t" + entry);
				blockManagement = false;
			}
		});
		
		t.start();
	}
	
	public String getSystemBoot() {
		File[] files = DAO.File.listFilesOfType(DAO.Paths.REPOSITORY.toString() + "dictionary/", ".txt");
		if(files != null)
			return files.length > 0 ? "Was executed." : "error:\tIt was not executed.";
		else
			return "error:\tIt was not executed.";
	}
	
	public String getIndex() {
		File[] files = DAO.File.listFilesOfType(DAO.Paths.REPOSITORY.toString() + "index/", "");
		if(files != null)
			return files.length > 0 ? "Is created." : "error:\tNot created.";
		else
			return "error:\tNot created.";
	}
	
	public String getDictionary() {
		File[] files = DAO.File.listFilesOfType(DAO.Paths.REPOSITORY.toString() + "dictionary/index/", "");
		if (files != null)
			return files.length > 0 ? "Is created." : "error:\tNot created.";
		else
			return "error:\tNot created.";
	}
	
	public String getDatabase() {
		Neo4j neo4jTest;
		try {
			neo4jTest = new Neo4j();
			neo4jTest.disconnect();
		} catch (DatabaseConnectionException e) {
			return "error:\t" + e.getMessage();
		}
		
		File[] files = DAO.File.listFilesOfType(DAO.Paths.REPOSITORY.toString() + "ann/", ".ann");
		if(files != null)
			return files.length > 0 ? "error:\tOutdated database." : "Database updated.";
		else
			return "Database updated.";

	}
	
	public String getKeyWordEngine() {
		try {
			SimpleSearch.simpleSearch("test");
		} catch (Exception e) {
			return "error:\tKeyword search is not operating.";
		}
		return "Keyword search is operating.";
	}
	
	public String getSemanticEngine() {
		String newQuery = null;
		try {
			newQuery = Syntactic.translateToCypherQuery("Pessoa");
		} catch (InvalidQueryException e) {
		}
		
		try {
			if(SemanticSearch.cypherSearchBolt(newQuery).size() == 0) { return "error:\tThe database is empty."; }
			SemanticSearch.documentSearch(newQuery);
			SemanticSearch.buscaCypherRest(newQuery);
		} catch (Exception e) {
			return "error:\tSemantic search is not operating.";
		}
		return "Semantic search is operating.";
	}

	
	public void addAccess() {
		while (blockAccessCount) {};
		blockAccessCount = true;
		accessCount += 1;
		blockAccessCount = false;
	}
	
	public ArrayList<String> getAccessList() {
		while (blockAccessCount) {};
		blockAccessCount = true;
		
		ArrayList<String> accessCountList = null;
		try {
			accessCountList = DAO.File.readLinesFile(pathAccessCount);
		} catch (IOException e) {
			accessCountList = new ArrayList<>();
		}
		blockAccessCount = false;
		
		return accessCountList;
	}
}
