package classifiers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import util.Sys;

public class Rer {
	
	public static void extractRelations (String txtFile, String annFile) {
		String csvFile = extractFeatures (txtFile, annFile);
		
		ProcessBuilder builder = null;
		Process process = null;
		
		builder = new ProcessBuilder(
					"python3.5", "relation_predictor.py", 
					"svm", csvFile, annFile);
		
		builder.directory(new File("/home/geovani/git/tcc/BuscadorSemantico/extrator/extrator_relacoes/prediction/"));
		
		builder.redirectErrorStream(true);
			
		try {
				
			process = builder.start();
				
			InputStream stdout = process.getInputStream ();

			BufferedReader reader = new BufferedReader (new InputStreamReader(stdout));

			String line;
				
			while ((line = reader.readLine ()) != null)
		   		System.out.println ("Rer: " + line);
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			Sys.command("rm " + csvFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String extractFeatures (String txtFile, String annFile) {
		
		ProcessBuilder builder = null;
		Process process = null;
		String fileOut = txtFile + ".temp.csv";
		
		builder = new ProcessBuilder(
					"python3.5", "csv.py", 
					annFile, txtFile, fileOut);
		
		//Diretorio com o script ner.py
		builder.directory(new File("/home/geovani/git/tcc/BuscadorSemantico/extrator/extrator_relacoes/"));
		
		builder.redirectErrorStream(true);
			
		try {
				
			process = builder.start();
				
			InputStream stdout = process.getInputStream ();

			BufferedReader reader = new BufferedReader (new InputStreamReader(stdout));

			String line;
				
			while ((line = reader.readLine ()) != null)
		   		System.out.println ("Fer: " + line);
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileOut;
	}
}
