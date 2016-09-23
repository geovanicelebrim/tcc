package control;

import java.util.ArrayList;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import DAO.File;
import DAO.Neo4j;
import entity.Document;
import entity.results.SimpleResults;
import exception.ErrorFileException;

/**
 * Controlador responsável por mediar as buscas por palavra chave no banco e no
 * repositório de arquivos.
 * 
 * @author Geovani Celebrim
 * 
 */
public class SimpleSearch {

	/**
	 * Realiza uma busca simples por palavra chave nos documentos do repositório.
	 * @param keyWords String da busca.
	 * @return <b>ArrayList</b> de {@link SimpleResults}.
	 * @throws ErrorFileException caso ocorra erro na leitura dos arquivos.
	 */
	// TODO melhorar a técnica de obter informação do documento para a busca,
	// para não depender de uma substring
	public static ArrayList<SimpleResults> simpleSearch(String keyWords)
			throws ErrorFileException {

		ArrayList<Document> documents = new ArrayList<>();
		java.io.File[] documentsNames = File.listFiles();
		ArrayList<SimpleResults> resuls = new ArrayList<>();

		for (int i = 0; i < documentsNames.length; i++) {
			documents.add(new Document(documentsNames[i].getName(), File
					.readPrefixedFile("/CEDIM-II-GUERRA/"
							+ documentsNames[i].getName())));
		}

		for (int i = 0; i < documents.size(); i++) {

			if (documents.get(i).getText().toLowerCase()
					.contains(keyWords.toLowerCase())) {
				resuls.add(new SimpleResults("/CEDIM-II-GUERRA/"
						+ documents.get(i).getName(), documents.get(i)
						.getText().substring(0, 500), searchAuthor(documents
						.get(i).getName()), searchSource(documents.get(i)
						.getName())));
			}
		}

		return resuls;
	}

	/**
	 * Busca um autor de um documento passado por parâmetro no banco.
	 * @param documentName String contendo o nome do documento.
	 * @return <b>String</b> contendo o nome do autor.
	 */
	private static String searchAuthor(String documentName) {

		Neo4j neo4j = new Neo4j();
		String cypherQuery = "match (d:Documento) where d.nome = \""
				+ documentName + "\" return d.autor as autor";

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
	 * @param documentName String contendo o nome do documento.
	 * @return <b>String</b> contendo o nome da fonte.
	 */
	private static String searchSource(String documentName) {

		Neo4j neo4j = new Neo4j();
		String cypherQuery = "match (d:Documento) where d.nome = \""
				+ documentName + "\" return d.fonte as fonte";

		StatementResult retorned = neo4j.getSession().run(cypherQuery);

		String source = null;

		while (retorned.hasNext()) {

			Record record = retorned.next();

			source = record.get("fonte").asString();

		}

		neo4j.disconnect();
		return source;
	}
}
