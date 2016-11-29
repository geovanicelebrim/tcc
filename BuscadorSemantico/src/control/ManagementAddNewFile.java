package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.Part;

import org.apache.lucene.index.IndexWriterConfig.OpenMode;

import DAO.Paths;
import management.RunTasks;
import management.addition.Importation;
import management.addition.Indexer;

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

	public static void importAnn() {
		Importation.importOf(Paths.REPOSITORY.toString());
	}

	public static void addFile(final String path, final Part filePart, final String type) throws Exception {

		final String fileName = getFileName(filePart);
		OutputStream out = null;
		InputStream filecontent = null;
		
		if(!fileName.contains(type)) {
			throw new Exception("O arquivo inserido não é válido");
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
	}

	public static void createMetaFile(Part textFilePart, String title, String author, String year, String source) throws Exception {
		String fileName = getFileName(textFilePart).replace(".txt", ".meta");
		
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
			throw new Exception("A data está no passado. A indexação ocorrerá junto com a próxima indexação agendada.");
		}
		RunTasks tasks = RunTasks.getInstance();
		
		tasks.addIndexSchedule(scheduleDate);
		
	}
	
	@SuppressWarnings("deprecation")
	public static void scheduleImport(String schedule) throws Exception {

		Date now = new Date();
		Date scheduleDate = new Date(schedule);
		scheduleDate.setHours(23);
		scheduleDate.setMinutes(59);
		scheduleDate.setSeconds(59);
		
		if (now.after(scheduleDate)) {
			throw new Exception("A data está no passado. A indexação ocorrerá junto com a próxima indexação agendada.");
		}
		RunTasks tasks = RunTasks.getInstance();
		
		tasks.addImportSchedule(scheduleDate);
		
	}
	
	private static String getFileName(final Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}
