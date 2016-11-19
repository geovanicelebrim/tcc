package keyWordsSearcher;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;

import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {

	private IndexWriter indexWriter;

	/* Diretorio do indice salvo */
	private String indexDirectory;

	/* Diretorio dos dados */
	private String dataDirectory;

	// 1 Especifica o analizador do tokenizador.
	// O mesmo analizador usado para indexar deve ser utilizado para pesquisar.
	private BrazilianAnalyzer brazilianAnalyser = new BrazilianAnalyzer();

	public Indexer(String pathLucene) {
		this.indexDirectory = pathLucene + "index";
		this.dataDirectory = pathLucene + "data";
	}

	/* Verifica se os dados já foram indexados */
	public boolean isIndexed() {
		return indexWriter != null ? true : false;
	}

	/* Cria o indexador de dados */
	void createIndexWriter(OpenMode openMode) throws Exception {
		try {
			Directory index = FSDirectory.open(FileSystems.getDefault().getPath(indexDirectory));

			/* Inicializa a configuração do indice */
			IndexWriterConfig config = new IndexWriterConfig(brazilianAnalyser);
			config.setOpenMode(openMode);
			indexWriter = new IndexWriter(index, config);
		} catch (IOException ie) {
			System.out.println("Error in creating IndexWriter");
			throw new RuntimeException(ie);
		}
	}

	private ArrayList<String> removeStopWords(String body, String pathToStopWords) throws Exception {
		
		BufferedReader br = new BufferedReader(new FileReader(pathToStopWords));
		ArrayList<String> stopWords = new ArrayList<>();
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
				stopWords.add(line);
			}
		} finally {
			br.close();
		}
		
		ArrayList<String> dictionary = new ArrayList<>();
		
		String tokens[] = body.toLowerCase().replaceAll("\n", " ").replaceAll("[^a-zA-Z ]", " ").split("\\b[ ]+");
		
		for (String token : tokens) {
			if (!stopWords.contains(token)) {
				if (token.length() > 2 )
					if (!dictionary.contains(token)) dictionary.add(token);
			}
		}

		return dictionary;
	}
	
	private void createDictionary (String path) throws Exception {
		
		File[] files = getFilesToBeIndxed();

		String body = "";
		for (File file : files) {

			BufferedReader br = new BufferedReader(new FileReader(file));
			try {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine();
				}
				body += sb.toString();
			} finally {
				br.close();
			}

		}
		
		ArrayList<String> dictionary = removeStopWords(body, path + "/stopWordsPt.txt");
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(path + "/suggestion.txt"));
		try {
			
			for ( String token : dictionary) {
				bw.write(token + "\n");
			}
		} finally {
			bw.close();
		}

	}
	
	public void buildDictionary(String path) throws Exception {
		
		createDictionary(path);
		
		Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(path + "/index"));
		
		@SuppressWarnings("resource")
		SpellChecker spellChecker = new SpellChecker(directory);
		//Só quando vai criar o indice
		IndexWriterConfig config = new IndexWriterConfig(new BrazilianAnalyzer());
		config.setOpenMode(OpenMode.CREATE);
		// O que é o ultimo parâmetro?
		spellChecker.indexDictionary(new PlainTextDictionary(FileSystems.getDefault().getPath( path + "/suggestion.txt")), config, true);
	}
	
	public void indexData() throws FileNotFoundException, IOException {

		File[] files = getFilesToBeIndxed();

		for (File file : files) {

			String title = file.getName();
			String body;

			BufferedReader br = new BufferedReader(new FileReader(file));
			try {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine();
				}
				body = sb.toString();
			} finally {
				br.close();
			}

			addDoc(indexWriter, title, body);

		}
		indexWriter.close();
	}

	private static void addDoc(IndexWriter w, String title, String body) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("title", title, Field.Store.YES));
		doc.add(new TextField("body", body, Field.Store.YES));
		
		w.addDocument(doc);
	}

	private File[] getFilesToBeIndxed() {
		File dataDir = new File(dataDirectory);
		if (!dataDir.exists()) {
			throw new RuntimeException(dataDirectory + " does not exist");
		}
		File[] files = dataDir.listFiles();
		return files;
	}
}
