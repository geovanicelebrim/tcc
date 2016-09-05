package util;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import DAO.Neo4j;
import entidade.ResultadoDocumento;

public class DadosDocumentos {
	public static ResultadoDocumento buscarAutorEFonte(String caminhoDocumento) {

		Neo4j neo4j = new Neo4j();
		String cypherQuery = "match (d:Documento) where d.caminho = \""
				+ caminhoDocumento
				+ "\" return d.autor as autor, d.fonte as fonte";

		StatementResult retorno = neo4j.getSession().run(cypherQuery);

		ResultadoDocumento resultadoDocumento = null;

		while (retorno.hasNext()) {

			Record record = retorno.next();

			resultadoDocumento = new ResultadoDocumento(record.get("autor")
					.asString(), record.get("fonte").asString());

		}

		neo4j.desconectar();
		return resultadoDocumento;
	}
	
	public static int aproximarIndiceInicial(String texto, int posicao) {
		for (int i = posicao; i > 0; i--) {
			if (((texto.charAt(i) == '.') || (texto.charAt(i) == '?') || (texto
					.charAt(i) == '!')) && (texto.charAt(i + 1) == ' ')) {

				return i + 2;
			}
		}
		return 0;
	}

	public static int indiceFinal(String texto, int posicao) {
		for (int i = posicao; i < texto.length(); i++) {
			if (((texto.charAt(i) == '.') || (texto.charAt(i) == '?') || (texto
					.charAt(i) == '!')) && (texto.charAt(i + 1) == ' ')) {

				return i + 2;
			}
		}
		return texto.length() - 1;
	}
}
