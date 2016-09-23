package control;

import java.io.IOException;
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
import exception.ErrorFileException;

/**
 * Controlador responsável por mediar as buscas semânticas no banco e no
 * repositório de arquivos.
 * 
 * @author Geovani Celebrim
 * 
 */
public class SemanticSearch {

	// TODO adicionar exemplo do formado da query
	/**
	 * Busca no banco Neo4j, dada uma String no formato Cypher, e retorna um
	 * {@link ArrayList} de {@link CypherResults}.
	 * 
	 * @param cypherQuery
	 *            String no formato Cypher.
	 * @return <b>ArrayList</b> de {@link CypherResults} contendo os resultados
	 *         encontrados.
	 * @throws Exception
	 *             caso ocorra uma falha em algum passo da busca.
	 */
	public static ArrayList<CypherResults> cypherSearchBolt(String cypherQuery)
			throws Exception {
		Neo4j neo4j = new Neo4j();

		StatementResult retorned = neo4j.getSession().run(cypherQuery);

		ArrayList<CypherResults> res = null;
		try {
			res = DataProcessing.statementToCypher(cypherQuery, retorned);
		} catch (Exception e) {
			throw e;
		}

		neo4j.disconnect();

		return res;
	}

	// TODO adicionar exemplo do formado da query
	/**
	 * Realiza uma busca no banco Neo4j, usando o módulo em Python, dada uma
	 * String no formato Cypher, e retorna um {@link Graph}.
	 * 
	 * @param query
	 *            String em formato Cypher.
	 * @return <b>Graph</b>
	 */
	public static Graph buscaCypherRest(String query) {

		String q = (query.split("return")[0] + "return " + query
				.split("return")[0].replaceAll("(\" \")*match(\" \")*", "")
				.replaceAll("(\" \")*where(.)*", "")
				.replaceAll("[^A-z+0-9*\":\"A-z+0-9*]+", "")
				.replaceAll("\\[", ",").replaceAll("\\]", ",")
				.replaceAll(":[A-z]*", ""));

		Graph graph = null;

		try {
			graph = Neo4j_Rest.getGraph(q);

		} catch (ErrorFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return graph;
	}

	// TODO adicionar exemplo do formado da query
	/**
	 * Realiza uma busca no banco Neo4j, dada uma String no formato Cypher, e
	 * retorna um {@link ArrayList} de {@link DocumentResult}. Essa busca cruza
	 * os dados do banco para obter o trecho onde a informação se encontra no
	 * documento.
	 * 
	 * @param cypherQuery
	 *            String no formado Cypher.
	 * @return <b>ArrayList</b> de {@link DocumentResult}.
	 * @exception Exception
	 *                caso ocorra algum erro na extração dos dados.
	 */
	public static ArrayList<DocumentResult> documentSearch(String cypherQuery)
			throws Exception {

		ArrayList<DocumentResult> documentResults = new ArrayList<>();

		Neo4j neo4j = new Neo4j();

		StatementResult retorned = neo4j.getSession().run(cypherQuery);

		ArrayList<Position> position1 = new ArrayList<>();
		ArrayList<Position> position2 = new ArrayList<>();

		try {
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

				for (int i = 0; i < crusadersData.size(); i++) {

					String text = File.readPrefixedFile(documentPath);
					int begin, end;

					begin = DocumentsMetaData.approachingInitialIndex(text,
							crusadersData.get(i).getBegin());
					end = DocumentsMetaData.endIndex(text, begin + 250);

					DocumentResult documentResult = new DocumentResult(
							documentPath, crusadersData.get(i), text.substring(
									begin, end), DocumentsMetaData
									.searchAuthorAndSource(documentPath)
									.getAuthor(), DocumentsMetaData
									.searchAuthorAndSource(documentPath)
									.getSource());

					documentResults.add(documentResult);
				}

				position1.clear();
				position2.clear();
			}
		} catch (Exception e) {
			throw e;
		}

		neo4j.disconnect();

		return documentResults;
	}

}