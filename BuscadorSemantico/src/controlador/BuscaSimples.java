package controlador;

import java.io.File;
import java.util.ArrayList;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import DAO.Documentos;
import DAO.Neo4j;
import entidade.Documento;
import entidade.resultados.ResultadoSimples;
import excessao.ErroArquivoException;

public class BuscaSimples {
	
	// TODO melhorar a técnica de obter informação do documento para a busca, para não depender de uma substring
	// Realiza uma busca simples por ocorrencia de palavras chave
	public static ArrayList<ResultadoSimples> buscaSimples(String palavrasChave)
			throws ErroArquivoException {

		ArrayList<Documento> documentos = new ArrayList<>();
		File[] nomesDocumentos = Documentos.listarArquivos();
		ArrayList<ResultadoSimples> resultados = new ArrayList<>();

		for (int i = 0; i < nomesDocumentos.length; i++) {
			documentos.add(new Documento(nomesDocumentos[i].getName(),
					Documentos.lerArquivo("/CEDIM-II-GUERRA/" + nomesDocumentos[i].getName())));
		}

		for (int i = 0; i < documentos.size(); i++) {

			if (documentos.get(i).getTexto().toLowerCase().contains(palavrasChave.toLowerCase())) {
				resultados.add(new ResultadoSimples(
						"/CEDIM-II-GUERRA/" + documentos.get(i).getNome(), documentos.get(i)
								.getTexto().substring(0, 500),
						buscarAutor(documentos.get(i).getNome()),
						buscarFonte(documentos.get(i).getNome())));
			}
		}
		
		return resultados;
	}

	private static String buscarAutor(String nomeDocumento) {

		Neo4j neo4j = new Neo4j();
		String cypherQuery = "match (d:Documento) where d.nome = \""
				+ nomeDocumento + "\" return d.autor as autor";

		StatementResult retorno = neo4j.getSession().run(cypherQuery);

		String autor = null;

		while (retorno.hasNext()) {

			Record record = retorno.next();

			autor = record.get("autor").asString();

		}

		neo4j.desconectar();
		return autor;
	}

	private static String buscarFonte(String nomeDocumento) {

		Neo4j neo4j = new Neo4j();
		String cypherQuery = "match (d:Documento) where d.nome = \""
				+ nomeDocumento + "\" return d.fonte as fonte";

		StatementResult retorno = neo4j.getSession().run(cypherQuery);

		String fonte = null;

		while (retorno.hasNext()) {

			Record record = retorno.next();

			fonte = record.get("fonte").asString();

		}

		neo4j.desconectar();
		return fonte;
	}
	
}
