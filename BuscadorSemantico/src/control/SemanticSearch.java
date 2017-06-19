package control;

import java.util.ArrayList;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import util.DataProcessing;
import util.DocumentsMetaData;
import DAO.File;
import DAO.Neo4j;
import DAO.Neo4j_Rest;
import entity.Graph;
import entity.Position;
import entity.results.CypherResults;
import entity.results.DocumentResult;
import exception.DatabaseConnectionException;
import exception.ErrorFileException;

/**
 * Controlador responsável por mediar as buscas semânticas no banco e no
 * repositório de arquivos.
 * 
 * @author Geovani Celebrim
 * 
 */
public class SemanticSearch {

	/**
	 * Busca no banco Neo4j, dada uma String no formato Cypher, e retorna um
	 * {@link ArrayList} de {@link CypherResults}.
	 * 
	 * @param cypherQuery
	 *            String no formato Cypher.
	 * @return <b>ArrayList</b> de {@link CypherResults} contendo os resultados
	 *         encontrados.
	 * @throws DatabaseConnectionException ocorre quando há uma falha na conexão com o banco de dados. 
	 */
	public static ArrayList<CypherResults> cypherSearchBolt(String cypherQuery) throws DatabaseConnectionException {
		Neo4j neo4j = new Neo4j();

		StatementResult retorned = neo4j.getSession().run(cypherQuery);

		ArrayList<CypherResults> res = null;

		res = DataProcessing.statementToCypher(cypherQuery, retorned);

		neo4j.disconnect();

		return res;
	}

	/**
	 * Realiza uma busca no banco Neo4j, usando o módulo em Python, dada uma
	 * String no formato Cypher, e retorna um {@link Graph}.
	 * 
	 * @param query
	 *            String em formato Cypher.
	 * @return <b>Graph</b>
	 * @throws ErrorFileException
	 */
	public static Graph buscaCypherRest(String query) throws ErrorFileException {

		String q = (query.split("return")[0] + "return " + query
				.split("return")[0].replaceAll("(\" \")*match(\" \")*", "")
				.replaceAll("(\" \")*where(.)*", "")
				.replaceAll("[^A-z+0-9*\":\"A-z+0-9*]+", "")
				.replaceAll("\\[", ",").replaceAll("\\]", ",")
				.replaceAll(":[A-z]*", ""));

		Graph graph = Neo4j_Rest.getGraph(q);

		return graph;
	}

	/**
	 * Realiza uma busca no banco Neo4j, dada uma String no formato Cypher, e
	 * retorna um {@link ArrayList} de {@link DocumentResult}. Essa busca cruza
	 * os dados do banco para obter o trecho onde a informação se encontra no
	 * documento.
	 * 
	 * @param cypherQuery
	 *            String no formado Cypher.
	 * @return <b>ArrayList</b> de {@link DocumentResult}.
	 * @throws ErrorFileException caso ocorra algum erro na extração dos dados.
	 * @throws DatabaseConnectionException ocorre quando há uma falha na conexão com o banco de dados.
	 */
	public static ArrayList<DocumentResult> documentSearch(String cypherQuery) throws ErrorFileException, DatabaseConnectionException {

		ArrayList<DocumentResult> documentResults = new ArrayList<>();

		Neo4j neo4j = new Neo4j();

		StatementResult retorned = neo4j.getSession().run(cypherQuery);

		ArrayList<Position> position1 = new ArrayList<>();
		ArrayList<Position> position2 = new ArrayList<>();

		while (retorned.hasNext()) {
			Record record = retorned.next();
			String p1[] = record.get("posicao1").asString().split("; ");

			for (int i = 0; i < p1.length; i++) {
				position1.add(new Position(p1[i].split(", ")[0], p1[i]
						.split(", ")[1]));
			}

			String p2[] = record.get("posicao2").asString().split("; ");

			for (int i = 0; i < p2.length; i++) {
				position2.add(new Position(p2[i].split(", ")[0], p2[i]
						.split(", ")[1]));
			}

			ArrayList<Position> crusadersData = DataProcessing.crossData(
					position1, position2);

			String documentPath = record.get("caminho").asString();
			String documentName = record.get("nome").asString() + ".txt";
			String title = record.get("titulo").asString();
			for (int i = 0; i < crusadersData.size(); i++) {

				String text = File.readFile(documentPath + "/" + documentName);
				int begin, end;

				begin = DocumentsMetaData.approachingInitialIndex(text,
						crusadersData.get(i).getBegin());
				end = (begin + 250) >= text.length() ? DocumentsMetaData.endIndex(text, text.length() - 1) : DocumentsMetaData.endIndex(text, begin + 250);
				
				DocumentResult documentResult = new DocumentResult( title,
						documentName, crusadersData.get(i), text.substring(
								begin, end), begin, end, DocumentsMetaData
								.searchAuthorAndSource(documentPath)
								.getAuthor(), DocumentsMetaData
								.searchAuthorAndSource(documentPath)
								.getSource());
				
				if (documentResults.contains(documentResult)) {
					int index = documentResults.indexOf(documentResult);
					
					documentResults.get(index).addChildren(documentResult);
				} else {
					documentResults.add(documentResult);
				}
			}

			position1.clear();
			position2.clear();
		}

		neo4j.disconnect();

		return documentResults;
	}

}