package keyWordsSearcher;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import DAO.File;
import DAO.Neo4j;
import DAO.Paths;
import entity.results.SimpleResults;
import exception.DatabaseConnectionException;
import exception.ErrorFileException;

public class Engine {
	@SuppressWarnings("unused")
	private int hitsPerPage;
	private IndexReader reader;
	private IndexSearcher searcher;
	private TopScoreDocCollector collectorTitle;
	private TopScoreDocCollector collectorBody;
	public static final Integer DEFAULT_MAX_RESULTS = 1000;
	
	public Engine(int hitsPerPage, String pathToIndex) throws IOException {
		Directory index = FSDirectory.open(FileSystems.getDefault().getPath(pathToIndex));
		this.hitsPerPage = hitsPerPage;
		reader = DirectoryReader.open(index);
		searcher = new IndexSearcher(reader);
		collectorTitle = TopScoreDocCollector.create(hitsPerPage);
		collectorBody = TopScoreDocCollector.create(hitsPerPage);
	}
	
	public Engine(int hitsPerPage) throws Exception {
		Directory index = FSDirectory.open(FileSystems.getDefault().getPath( Paths.REPOSITORY.toString() + "index") );
		this.hitsPerPage = hitsPerPage;
		reader = DirectoryReader.open(index);
		searcher = new IndexSearcher(reader);
		collectorTitle = TopScoreDocCollector.create(hitsPerPage);
		collectorBody = TopScoreDocCollector.create(hitsPerPage);
	}
	
	public void searcherBy(String queryString) throws IOException, ParseException {
		Query queryTitle = SearchFeatures.queryParser("title", queryString.replaceAll("~", "") + "~");
		searcher.search(queryTitle, collectorTitle);
		
		Query queryBody = SearchFeatures.queryParser("body", queryString.replaceAll("~", "") + "~");
		searcher.search(queryBody, collectorBody);
	}
	
	public ArrayList<SimpleResults> showResults() throws IOException, ErrorFileException, DatabaseConnectionException {
		
		ArrayList<SimpleResults> resultsTitleList = new ArrayList<>();
		
		// Resultados nos titulos
		ScoreDoc[] hitsTitle = collectorTitle.topDocs().scoreDocs;
//		System.out.println("Found " + hitsTitle.length + " hits.");
		for (int i = 0; i < hitsTitle.length; ++i) {
			int docId = hitsTitle[i].doc;
			Document d = searcher.doc(docId);
			SimpleResults result = new SimpleResults(d.get("title"), hitsTitle[i].score + 0.5f);
			resultsTitleList.add(result);
//			System.out.println((i + 1) + ". " + d.get("title") + "\t Score: " + (hitsTitle[i].score + 0.5f));
		}
		
		// Resultados no corpo dos textos
		ArrayList<SimpleResults> resultsBodyList = new ArrayList<>();
		ScoreDoc[] hitsBody = collectorBody.topDocs().scoreDocs;
//		System.out.println("Found " + hitsBody.length + " hits.");
		for (int i = 0; i < hitsBody.length; ++i) {
			int docId = hitsBody[i].doc;
			Document d = searcher.doc(docId);
			SimpleResults result = new SimpleResults(d.get("title"), hitsBody[i].score);
			resultsBodyList.add(result);
//			System.out.println((i + 1) + ". " + d.get("title") + "\t Score: " + hitsBody[i].score);
		}
//		System.out.println(resultsBodyList.size());
		
		// Resultados juntados, filtrados e ranqueados pelo score
		HashMap<String, SimpleResults> filtredResults = new HashMap<>();
		
		for (SimpleResults r : resultsTitleList) {
			filtredResults.put(r.getTitle(), buildResult(r));
		}
		
		for (SimpleResults r : resultsBodyList) {
			if (filtredResults.containsKey(r.getTitle())) {
				SimpleResults old = filtredResults.get(r.getTitle());
				old.setScore(old.getScore() + r.getScore());
				filtredResults.put(r.getTitle(), old);
			} else {
				filtredResults.put(r.getTitle(), buildResult(r));
			}
		}

		ArrayList<SimpleResults> orderedResults = new ArrayList<>();
		
		orderedResults.addAll(filtredResults.values());
		orderedResults.sort((o1, o2) -> o2.getScore().compareTo(o1.getScore()));
		
//		for (SimpleResults r : orderedResults) {
//			System.out.println(r.getTitle() + "\t Score: " + r.getScore());
//		}
		
		return orderedResults;
	}
	
	private SimpleResults buildResult(SimpleResults r) throws ErrorFileException, DatabaseConnectionException {
		String slice = File.readPrefixedFile(r.getDocumentName());
		slice = slice.length() > 500 ? slice.substring(0, 500) : slice.substring(0, slice.length());
		
		String author = searchAuthor(r.getDocumentName());
		String source = searchSource(r.getDocumentName());
		
		return new SimpleResults(r.getDocumentName(), slice, author, source, r.getScore());
	}
	
	/**
	 * Busca um autor de um documento passado por parâmetro no banco.
	 * 
	 * @param documentName
	 *            String contendo o nome do documento.
	 * @return <b>String</b> contendo o nome do autor.
	 * @throws DatabaseConnectionException
	 *             ocorre quando há uma falha na conexão com o banco de dados.
	 */
	private static String searchAuthor(String documentName) throws DatabaseConnectionException {
		Neo4j neo4j = new Neo4j();
		String cypherQuery = "match (d:Documento) where d.nome = \"" + documentName.replace(".txt", "")
				+ "\" return d.autor as autor";

		StatementResult retorned = neo4j.getSession().run(cypherQuery);

		String author = null;

		while (retorned.hasNext()) {

			Record record = retorned.next();

			author = record.get("autor").asString();

		}

		neo4j.disconnect();
		return author;
	}
	
	/**
	 * Busca a fonte de um documento passado por parâmetro no banco.
	 * 
	 * @param documentName
	 *            String contendo o nome do documento.
	 * @return <b>String</b> contendo o nome da fonte.
	 * @throws DatabaseConnectionException
	 */
	private static String searchSource(String documentName) throws DatabaseConnectionException {

		Neo4j neo4j = new Neo4j();
		String cypherQuery = "match (d:Documento) where d.nome = \"" + documentName.replace(".txt", "")
				+ "\" return d.fonte as fonte";

		StatementResult retorned = neo4j.getSession().run(cypherQuery);

		String source = null;

		while (retorned.hasNext()) {

			Record record = retorned.next();

			source = record.get("fonte").asString();

		}

		neo4j.disconnect();
		return source;
	}
	
	public String[] getSuggestions( String wordForSuggestions, int suggestionsNumber, String pathToDictionary) throws IOException {

		if ( wordForSuggestions.split("\\b").length > 1 ) { return null; }
		BufferedReader br = new BufferedReader(new FileReader(pathToDictionary + "suggestion.txt"));
		try {
			String line = br.readLine();

			while (line != null) {
				if (line.equals(wordForSuggestions)) { return null; }
				line = br.readLine();
			}
		} finally {
			br.close();
		}
		
		Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(pathToDictionary + "index"));
		
		@SuppressWarnings("resource")
		SpellChecker spellChecker = new SpellChecker(directory);
		
		return spellChecker.suggestSimilar(wordForSuggestions, suggestionsNumber);
		
	}
	
	public String[] getSuggestions( String wordForSuggestions, int suggestionsNumber) throws Exception {

		return getSuggestions(wordForSuggestions, suggestionsNumber, Paths.REPOSITORY.toString() + "dictionary/");
	}
	
	public String getSuggestions( String wordForSuggestions) throws IOException {
		String[] suggerstions = getSuggestions(wordForSuggestions, 1, Paths.REPOSITORY.toString() + "dictionary/");
		
		return suggerstions != null && suggerstions.length > 0 ? suggerstions[0] : null;
	}
}
