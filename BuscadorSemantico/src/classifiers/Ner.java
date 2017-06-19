package classifiers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javafx.util.Pair;

public class Ner {
	
	public static void extractEntities (ArrayList<Pair<String, String>> files) {
		
		if (files.isEmpty())
			return;
		
		ProcessBuilder builder = null;
		Process process = null;
		
		for (Pair<String, String> file : files) {
			
			builder = new ProcessBuilder(
					"python3.5", "ner.py", 
					"-nlp", "/home/geovani/git/tcc/BuscadorSemantico/extrator/opennlp-1.7.2/bin/opennlp", 
					"-m", "/home/geovani/git/tcc/BuscadorSemantico/extrator/final_model/final.model", 
					"-f", file.getKey(),
					"-o", file.getValue());
			
			//Diretorio com o script ner.py
			builder.directory(new File("/home/geovani/git/tcc/BuscadorSemantico/extrator/"));
			
			builder.redirectErrorStream(true);
			
			try {
				
				process = builder.start();
				
				InputStream stdout = process.getInputStream ();

				BufferedReader reader = new BufferedReader (new InputStreamReader(stdout));

				String line;
				
				while ((line = reader.readLine ()) != null)
		    		System.out.println ("Ner: " + line);
				
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
	
	public static void extractEntities (String file, String fileOut) {
		
		if (file.isEmpty())
			return;
		
		ProcessBuilder builder = null;
		Process process = null;
		
		builder = new ProcessBuilder(
					"python3.5", "ner.py", 
					"-nlp", "/home/geovani/git/tcc/BuscadorSemantico/extrator/opennlp-1.7.2/bin/opennlp", 
					"-m", "/home/geovani/git/tcc/BuscadorSemantico/extrator/final_model/final.model", 
					"-f", file,
					"-o", fileOut);
		
		//Diretorio com o script ner.py
		builder.directory(new File("/home/geovani/git/tcc/BuscadorSemantico/extrator/"));
			
		builder.redirectErrorStream(true);
			
		try {
				
			process = builder.start();
				
			InputStream stdout = process.getInputStream ();

			BufferedReader reader = new BufferedReader (new InputStreamReader(stdout));

			String line;
				
			while ((line = reader.readLine ()) != null)
		   		System.out.println ("Ner: " + line);
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
