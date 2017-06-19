package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.lucene.index.IndexWriterConfig.OpenMode;

import DAO.Paths;
import exception.DatabaseConnectionException;
import management.RunTasks;
import management.addition.Importation;
import management.addition.Indexer;
import management.entity.TaskType;

public class ManagementAddNewFile {
	
	public static void indexerData(OpenMode openMode) throws Exception {
		Indexer indexer = new Indexer(Paths.REPOSITORY.toString());
		indexer.createIndexWriter(openMode);
		indexer.indexData();
	}

	public static void indexerData() throws Exception {
		indexerData(OpenMode.CREATE);
	}

	public static void buildDictionary() throws Exception {
		Indexer indexer = new Indexer(Paths.REPOSITORY.toString());
		indexer.buildDictionary(Paths.REPOSITORY.toString() + "dictionary");
	}

	public static void importAnn() throws Exception, IOException, DatabaseConnectionException {
		Importation.importOf(Paths.REPOSITORY.toString());
	}

	public static String addFile(final String fileName, final String path, final Part filePart, final String type) throws Exception {

		OutputStream out = null;
		InputStream filecontent = null;
		
		if(!fileName.contains(type)) {
			throw new Exception("The file you entered is not valid.");
		}
		try {
			out = new FileOutputStream(new File(path + File.separator + fileName));
			filecontent = filePart.getInputStream();

			int read = 0;
			final byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
		} catch (FileNotFoundException fne) {
			throw new Exception("You either did not specify a file to upload or are "
					+ "trying to upload a file to a protected or nonexistent location.");
		} finally {
			if (out != null) {
				out.close();
			}
			if (filecontent != null) {
				filecontent.close();
			}
		}
		return path + "/" + fileName;
	}

	public static void createMetaFile(final String fileName, Part textFilePart, String title, String author, String year, String source) throws Exception {
		String text = "Name\t" + fileName.replace(".meta", "") + "\nTitle\t" + title + "\nAuthor\t" + author + 
				"\nYear\t" + year + "\nSource\t" + source + 
				"\nPath\t" + Paths.PATH_DATA.toString() +
				"\nModification\t" + new Date().toString();
		
		DAO.File.writeFile(Paths.REPOSITORY.toString() + "meta/" + fileName, text);
	}
	
	@SuppressWarnings("deprecation")
	public static void scheduleIndex(String schedule) throws Exception {
		
		Date now = new Date();
		Date scheduleDate = new Date(schedule);
		scheduleDate.setHours(23);
		scheduleDate.setMinutes(59);
		scheduleDate.setSeconds(59);
		
		if (now.after(scheduleDate)) {
			throw new Exception("The date is in the past. Indexing will occur along with the next scheduled indexing.");
		}
		RunTasks tasks = RunTasks.getInstance();
		tasks.addTask(TaskType.TYPE_INDEX, scheduleDate);
		
	}
	
	@SuppressWarnings("deprecation")
	public static void scheduleImport(String schedule) throws Exception {

		Date now = new Date();
		Date scheduleDate = new Date(schedule);
		scheduleDate.setHours(23);
		scheduleDate.setMinutes(59);
		scheduleDate.setSeconds(59);
		
		if (now.after(scheduleDate)) {
			throw new Exception("The date is in the past. Import will occur along with the next scheduled import.");
		}
		RunTasks tasks = RunTasks.getInstance();
		
		tasks.addTask(TaskType.TYPE_IMPORT, scheduleDate);
		
	}
	
	public static String generateName(HttpServletRequest request) {
		java.util.Date currentTime = new java.util.Date();
		
		return request.getSession().getId() + "-" + 
				(new java.sql.Timestamp(
						currentTime.getTime()).toString().
						replaceAll(" ", "-").
						replaceAll(":", "-").
						replaceAll("\\.", "-")) + ".";
	}
}
