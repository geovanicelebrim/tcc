package control;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.queryparser.classic.ParseException;

import DAO.Paths;
import entity.results.SimpleResults;
import exception.DatabaseConnectionException;
import exception.ErrorFileException;
import keyWordsSearcher.Engine;

/**
 * Controlador responsável por mediar as buscas por palavra chave no banco e no
 * repositório de arquivos.
 * 
 * @author Geovani Celebrim
 * 
 */
public class SimpleSearch {

	/**
	 * Realiza uma busca simples por palavra chave nos documentos do
	 * repositório.
	 * 
	 * @param keyWords
	 *            String da busca.
	 * @return <b>ArrayList</b> de {@link SimpleResults}.
	 * @throws Exception 
	 * @throws ErrorFileException
	 *             caso ocorra erro na leitura dos arquivos.
	 * @throws DatabaseConnectionException
	 * @throws ParseException 
	 */
	public static ArrayList<SimpleResults> simpleSearch(String keyWords) throws IOException, ErrorFileException, DatabaseConnectionException, ParseException {
		Engine engine = new Engine(Engine.DEFAULT_MAX_RESULTS, Paths.REPOSITORY.toString() + "index");
		engine.searcherBy(keyWords);
		
		return engine.showResults();
	}
	
	public static String getSuggestion(String keyWords) throws IOException {
		Engine engine = new Engine(Engine.DEFAULT_MAX_RESULTS, Paths.REPOSITORY.toString() + "index");
		String suggestions = engine.getSuggestions(keyWords.replaceAll("\"", "").trim());
		return suggestions != null ? suggestions : null;
	}
}
