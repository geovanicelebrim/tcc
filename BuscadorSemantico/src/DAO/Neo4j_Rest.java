package DAO;

import java.io.IOException;

import util.Sys;
import entity.Edge;
import entity.Graph;
import entity.Vertex;
import exception.ErrorFileException;
/**
 * Responsável por realizar as conexções com o banco usando <i>REST</i>.
 * 
 * @author Geovani Celebrim
 * 
 */
public class Neo4j_Rest {
	/**
	 * Sobrescreve um template em Python e realiza uma conexão com o banco para obter o <i>JSON.</i>
	 * @param query que é a consulta em Cypher a ser realizada.
	 * @return <b>String</b> com o <i>JSON</i> que representa um grafo.
	 * @throws ErrorFileException caso ocorra algum erro na sobrescrita.
	 * @throws IOException caso ocorra algum erro na sobrescrita.
	 */
	private static String rest_query(String query) throws ErrorFileException {
		//TODO colocar o arquivo com um nome temporário.
		String user = Authentication.USER.toString();
		String password = Authentication.PASSWORD.toString();
		String template = null;
		try {
			template = File
					.readFile(Paths.REST.toString() + "rest_query_template.py");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String retorned = null;
		
		try {
			File.writeFile( Paths.REST.toString() + "rest_query.py",
					template.replace("#user", "\"" + user + "\"")
							.replace("#password", "\"" + password + "\"")
							.replace("#query",
									"\"" + query.replace("\"", "\\\"") + "\""));
			retorned = Sys.command("python " + Paths.REST.toString() + "rest_query.py");
			Sys.command("rm " + Paths.REST.toString() + "rest_query.py");
		} catch (IOException e) {
			throw new ErrorFileException("write");
		}
		
		return retorned;
	}

	/**
	 * Constroi um grafo com o <i>JSON</i> retornado do banco.
	 * @param response que é o que o banco retorna através do método rest_query da classe {@link Neo4j_Rest}.
	 * @return <b>Graph</b> construido a partir do <b>response</b>.
	 */
	private static Graph builderGraph(String response) {
		Graph graph = new Graph();
		String lines[] = response.split("\n");

		for (int i = 0; i < lines.length; i++) {
			String line[] = lines[i].split(", ");

			if (line[0].equals("node")) {

				Vertex vertex = new Vertex(line[1].split(": ")[1],
						line[2].split(": ")[1], line[3].split(": ")[1]);
				
				graph.addVertex(vertex);
				
				
			} else if (line[0].equals("edge")) {
				Edge edge = new Edge(line[1].split(": ")[1], line[2].split(": ")[1]);
				
				graph.addEdge(edge);
			}
		}

		return graph;
	}
	
	/**
	 * Retorna um grafo, dada uma query em Cypher.
	 * @param query String no formato Cypher.
	 * @return <b>Graph</b>.
	 * @throws ErrorFileException caso ocorra erro nos métodos dependentes.
	 * @throws IOException caso ocorra erro nos métodos dependentes.
	 */
	public static Graph getGraph(String query) throws ErrorFileException {
		return builderGraph(rest_query(query));
	}
}
