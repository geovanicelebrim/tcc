package util;

import java.util.Date;

import exception.ErrorFileException;

public class Log {
	private static String path = DAO.Paths.REPOSITORY.toString() + "log.txt";
	public static void write(String log) {
		
		String old = "";
		try {
			old = DAO.File.readFile(path);
		} catch (ErrorFileException e) {
			
		}
		
		old += old != "" ? "\n" + new Date() + "\t" + log : new Date() + "\t" + log;
		
		try {
			DAO.File.writeFile(path, old.replaceAll("[\n]+", "\n"));
		} catch (ErrorFileException e) {
			
		}
	}
}
