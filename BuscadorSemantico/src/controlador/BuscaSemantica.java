package controlador;

import java.util.ArrayList;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import DAO.Documentos;
import DAO.Neo4j;
import entidade.Posicao;
import entidade.ResultadoCypher;
import entidade.ResultadoDocumento;
import excessao.QueryInvalidaException;

public class BuscaSemantica {

	// Busca que mostra apenas o retorno do banco
	public static ResultadoCypher buscaCypher(String cypherQuery)
			throws Exception {
		Neo4j neo4j = new Neo4j();

		// TODO buscar uma maneira eficiente de obter o tempo de execução da
		// query
		Long tempoInicio = System.currentTimeMillis();
		StatementResult retorno = neo4j.getSession().run(cypherQuery);
		Long tempoTotal = System.currentTimeMillis() - tempoInicio;

		String resultado;
		try {
			resultado = statementToString(cypherQuery, retorno);
		} catch (Exception e) {
			throw e;
		}

		neo4j.desconectar();

		return new ResultadoCypher(resultado, tempoTotal);
	}

	// Busca que cruza os dados do banco para obter o trecho onde a informação
	// se encontra no documento
	public static ArrayList<ResultadoDocumento> buscaDocumento(
			String cypherQuery) throws Exception {

		ArrayList<ResultadoDocumento> resultadosDocumentos = new ArrayList<>();

		Neo4j neo4j = new Neo4j();

		StatementResult retorno = neo4j.getSession().run(cypherQuery);

		ArrayList<Posicao> posicao1 = new ArrayList<>();
		ArrayList<Posicao> posicao2 = new ArrayList<>();

		try {
			while (retorno.hasNext()) {
				Record record = retorno.next();
				String p1[] = record.get("posicao1").asString().split("; ");

				for (int i = 0; i < p1.length; i++) {
					posicao1.add(new Posicao(p1[i].split(", ")[0], p1[i]
							.split(", ")[1]));
				}

				String p2[] = record.get("posicao2").asString().split("; ");

				for (int i = 0; i < p2.length; i++) {
					posicao2.add(new Posicao(p2[i].split(", ")[0], p2[i]
							.split(", ")[1]));
				}

				ArrayList<Posicao> dadosCruzados = cruzarDados(posicao1,
						posicao2);

				String nomeDocumento = record.get("nome").asString();

				for (int i = 0; i < dadosCruzados.size(); i++) {
					String texto = Documentos.lerArquivo(nomeDocumento);
					int inicio, fim;

					if (dadosCruzados.get(i).getInicio() < dadosCruzados.get(i)
							.getFim()) {

						if (dadosCruzados.get(i).getInicio() < 200) {
							inicio = 0;
						} else {
							inicio = dadosCruzados.get(i).getInicio() - 200;
						}

						if ((texto.length() - dadosCruzados.get(i).getFim()) < 200) {
							fim = texto.length();
						} else {
							fim = dadosCruzados.get(i).getFim() + 200;
						}
						// TODO Inserir parametros (autor e fonte) na instância
						// do novo objeto
						ResultadoDocumento resultadoDocumento = new ResultadoDocumento(
								nomeDocumento, dadosCruzados.get(i),
								texto.substring(inicio, fim),
								buscarAutorEFonte(nomeDocumento).getAutor(),
								buscarAutorEFonte(nomeDocumento).getFonte());
						
						resultadosDocumentos.add(resultadoDocumento);
					} else {

						if (dadosCruzados.get(i).getFim() < 200) {
							inicio = 0;
						} else {
							inicio = dadosCruzados.get(i).getFim() - 200;
						}

						if ((texto.length() - dadosCruzados.get(i).getInicio()) < 200) {
							fim = texto.length();
						} else {
							fim = dadosCruzados.get(i).getInicio() + 200;
						}

						ResultadoDocumento resultadoDocumento = new ResultadoDocumento(
								nomeDocumento, dadosCruzados.get(i),
								texto.substring(inicio, fim),
								buscarAutorEFonte(nomeDocumento).getAutor(),
								buscarAutorEFonte(nomeDocumento).getFonte());

						resultadosDocumentos.add(resultadoDocumento);
					}
				}

				posicao1.clear();
				posicao2.clear();
			}
		} catch (Exception e) {
			throw e;
		}

		neo4j.desconectar();

		return resultadosDocumentos;
	}

	private static ResultadoDocumento buscarAutorEFonte(String nomeDocumento) {

		Neo4j neo4j = new Neo4j();
		String cypherQuery = "match (d:Documento) where d.nome = \""
				+ nomeDocumento + "\" return d.autor as autor, d.fonte as fonte";

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

	private static ArrayList<Posicao> cruzarDados(ArrayList<Posicao> posicao1,
			ArrayList<Posicao> posicao2) {
		// TODO pensar em poder cruzar mais parametros para uma pesquisa mais
		// completa
		ArrayList<Posicao> dadosCruzados = new ArrayList<>();

		for (int i = 0; i < posicao1.size(); i++) {
			for (int j = 0; j < posicao2.size(); j++) {

				if (Math.abs(posicao1.get(i).getInicio()
						- posicao2.get(j).getFim()) < 500) {

					boolean repetido = false;

					for (int k = 0; k < dadosCruzados.size(); k++) {
						if (Math.abs(dadosCruzados.get(k).getInicio()
								- posicao2.get(j).getFim()) < 500) {
							repetido = true;
							break;
						}
					}
					if (!repetido) {
						dadosCruzados.add(new Posicao(posicao1.get(i)
								.getInicio(), posicao2.get(j).getFim()));
					}
				}
			}
		}

		return dadosCruzados;
	}

	private static String statementToString(String cypherQuery,
			StatementResult resultado) throws QueryInvalidaException {

		String parametros[] = cypherQuery.split("return")[1].split(",");

		String retorno = "";

		while (resultado.hasNext()) {

			Record record = resultado.next();

			for (int i = 0; i < parametros.length; i++) {
				try {
					if (parametros[i].split("as ")[0].contains("trecho")) {
						retorno += record.get(parametros[i].split("as ")[1])
								+ " ";
					}
				} catch (Exception e) {
					throw new QueryInvalidaException();
				}
			}

			retorno += "\n";
		}

		return retorno;
	}
}
