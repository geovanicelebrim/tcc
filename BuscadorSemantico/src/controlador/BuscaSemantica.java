package controlador;

import java.io.IOException;
import java.util.ArrayList;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import util.DadosDocumentos;
import util.TratamentoDeDados;
import DAO.Documentos;
import DAO.Neo4j;
import DAO.Neo4j_Rest;
import entidade.Grafo;
import entidade.Posicao;
import entidade.resultados.ResultadoCypher;
import entidade.resultados.ResultadoDocumento;
import entidade.resultados.ResultadoGrafo;
import excessao.ErroArquivoException;

public class BuscaSemantica {

	// Busca que mostra apenas o retorno do banco
	public static ArrayList<ResultadoCypher> buscaCypherBolt(String cypherQuery)
			throws Exception {
		//Conexão BOLT
		Neo4j neo4j = new Neo4j();

		// TODO buscar uma maneira eficiente de obter o tempo de execução da
		// query
		// TODO verificar necessidade do tempo da busca, visto que ocorre 
		// mais de uma busca na verdade.
//		Long tempoInicio = System.currentTimeMillis();
		StatementResult retorno = neo4j.getSession().run(cypherQuery);
//		Long tempoTotal = System.currentTimeMillis() - tempoInicio;

		ArrayList<ResultadoCypher> res = null;
		try {
			res = TratamentoDeDados.statementToCypher(cypherQuery, retorno);
			
			
		} catch (Exception e) {
			throw e;
		}

		neo4j.desconectar();

		return res;
	}
	
	public static Grafo buscaCypherRest(String query) {
		
		//Conexão REST
		String q = (query.split("return")[0] + "return " + query.split("return")[0].
				replaceAll("(\" \")*match(\" \")*", "").replaceAll("(\" \")*where(.)*", "")
				.replaceAll("[^A-z+0-9*\":\"A-z+0-9*]+", "")
				.replaceAll("\\[", ",").replaceAll("\\]", ",")
				.replaceAll(":[A-z]*", ""));
		
		Grafo grafo = null;
		
		try {
			grafo = Neo4j_Rest.getGrafo(q);

		} catch (ErroArquivoException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return grafo;
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

				for (int i = 0; i < p1.length; i++) {posicao1.add(new Posicao(p1[i].split(", ")[0], p1[i].split(", ")[1]));}

				String p2[] = record.get("posicao2").asString().split("; ");

				for (int i = 0; i < p2.length; i++) {posicao2.add(new Posicao(p2[i].split(", ")[0], p2[i].split(", ")[1]));}

				ArrayList<Posicao> dadosCruzados = TratamentoDeDados.cruzarDados(posicao1, posicao2);

				String caminhoDocumento = record.get("caminho").asString();

				for (int i = 0; i < dadosCruzados.size(); i++) {

					String texto = Documentos.lerArquivo(caminhoDocumento);
					int inicio, fim;

					inicio = DadosDocumentos.aproximarIndiceInicial(texto, dadosCruzados.get(i).getInicio());
					fim = DadosDocumentos.indiceFinal(texto, inicio + 250);

					ResultadoDocumento resultadoDocumento = new ResultadoDocumento(
							caminhoDocumento, dadosCruzados.get(i),
							texto.substring(inicio, fim), DadosDocumentos.buscarAutorEFonte(
									caminhoDocumento).getAutor(),
									DadosDocumentos.buscarAutorEFonte(caminhoDocumento).getFonte());

					resultadosDocumentos.add(resultadoDocumento);
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

	// Busca que obtém o grafo do banco
	public static ArrayList<ResultadoGrafo> buscaGrafo(String cypherQuery) {
		// TODO Criar uma maneira de recuperar um json do banco.
		/*match (d:Documento)-[r1]-(p:Pessoa)-[r2]-(e:Evento)-[r3]-(d:Documento) where e.trecho =~ "(?i).*guerra.*" return ID(startNode(r1)), ID(endNode(r1)), ID(startNode(r2)), ID(endNode(r2)), ID(startNode(r3)), ID(endNode(r3))
		 * */

		ArrayList<ResultadoGrafo> nos = new ArrayList<>();
		
		return nos;
	}
}