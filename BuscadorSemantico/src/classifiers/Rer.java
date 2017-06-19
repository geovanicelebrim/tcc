package classifiers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Rer {
	
	public static void extractRelations (String txtFile, String annFile) {
		extractFeatures (txtFile, annFile);
	}
	
	public static void extractFeatures (String txtFile, String annFile) {
		
		ProcessBuilder builder = null;
		Process process = null;
		
		builder = new ProcessBuilder(
					"python3.5", "csv.py", 
					annFile, txtFile, txtFile + ".temp.csv");
		
		//Diretorio com o script ner.py
		builder.directory(new File("/home/geovani/git/tcc/BuscadorSemantico/extrator/extrator_relacoes/"));
		
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
	}
}
