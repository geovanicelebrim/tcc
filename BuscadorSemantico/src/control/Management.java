package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.Part;

import org.apache.lucene.index.IndexWriterConfig.OpenMode;

import DAO.Paths;
import management.addition.Importation;
import management.addition.Indexer;

public class Management {

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

	public static void addFile(final String path, final Part filePart) throws Exception {

		final String fileName = getFileName(filePart);
		OutputStream out = null;
		InputStream filecontent = null;

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

	private static String getFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}
