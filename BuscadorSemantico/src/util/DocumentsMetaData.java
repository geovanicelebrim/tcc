package util;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import DAO.Neo4j;
import entity.results.DocumentResult;
import exception.DatabaseConnectionException;

/**
 * Realiza buscas de metadados dos arquivos.
 * 
 * @author Geovani Celebrim
 */
public class DocumentsMetaData {
	/**
	 * Busca pelo autor e fonte de um dado documento.
	 * 
	 * @param documentPath
	 *            String contendo o caminho do documento.
	 * @return {@link DocumentResult}.
	 * @throws DatabaseConnectionException 
	 */
	public static DocumentResult searchAuthorAndSource(String documentPath) throws DatabaseConnectionException {

		Neo4j neo4j = new Neo4j();
		String cypherQuery = "match (d:Documento) where d.caminho = \""
				+ documentPath + "\" return d.autor as autor, d.fonte as fonte";

		StatementResult returned = neo4j.getSession().run(cypherQuery);

		DocumentResult documentResult = null;

		while (returned.hasNext()) {

			Record record = returned.next();

			documentResult = new DocumentResult(record.get("autor").asString(),
					record.get("fonte").asString());

		}

		neo4j.disconnect();
		return documentResult;
	}

	/**
	 * Busca o melhor índice inicial para um trecho do documento ser carregado
	 * como resposta.
	 * 
	 * @param text
	 *            String com o conteúdo do documento.
	 * @param position
	 *            inteiro encontrado a partir do cruzamento de dados.
	 * @return <b>int</b> com o melhor posicionamento inicial.
	 */
	public static int approachingInitialIndex(String text, int position) {
		for (int i = position; i > 0; i--) {
			if (((text.charAt(i) == '.') || (text.charAt(i) == '?') || (text
					.charAt(i) == '!')) && (text.charAt(i + 1) == ' ')) {

				return i + 2;
			}
		}
		return 0;
	}

	/**
	 * Busca o melhor índice final para um trecho do documento ser carregado
	 * como resposta.
	 * 
	 * @param text
	 *            String com o conteúdo do documento.
	 * @param position
	 *            inteiro encontrado a partir do cruzamento de dados.
	 * @return <b>int</b> com o melhor posicionamento final.
	 */
	public static int endIndex(String text, int position) {
		for (int i = position; i < text.length(); i++) {
			if (((text.charAt(i) == '.') || (text.charAt(i) == '?') || (text
					.charAt(i) == '!')) && (text.charAt(i + 1) == ' ')) {

				return i + 2;
			}
		}
		return text.length() - 1;
	}
}
