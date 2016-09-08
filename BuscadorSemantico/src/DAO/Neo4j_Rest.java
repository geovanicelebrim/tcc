package DAO;

import java.io.IOException;

import util.Arquivo;
import util.Sistema;
import entidade.Aresta;
import entidade.Grafo;
import entidade.Vertice;
import excessao.ErroArquivoException;

public class Neo4j_Rest {

	public static String rest_query(String query) throws ErroArquivoException,
			IOException {
		String usuario = Autenticacao.USER.toString();
		String senha = Autenticacao.PASSWORD.toString();

		String template = Arquivo
				.lerArquivo("./src/DAO/rest_query_template.py");

		Arquivo.escreverArquivo(
				"./src/DAO/rest_query.py",
				template.replace("#user", "\"" + usuario + "\"")
						.replace("#password", "\"" + senha + "\"")
						.replace("#query",
								"\"" + query.replace("\"", "\\\"") + "\""));
		String retorno = Sistema.comando("python ./src/DAO/rest_query.py");
		Sistema.comando("rm ./src/DAO/rest_query.py");
		
		return retorno;
	}

	public static Grafo construirGrafo(String response) {
		Grafo grafo = new Grafo();
		String linhas[] = response.split("\n");

		for (int i = 0; i < linhas.length; i++) {
			String linha[] = linhas[i].split(", ");

			if (linha[0].equals("node")) {

				Vertice vertice = new Vertice(linha[1].split(": ")[1],
						linha[2].split(": ")[1], linha[3].split(": ")[1]);
				
				grafo.adicionarVertice(vertice);
				
				
			} else if (linha[0].equals("edge")) {
				
				Aresta aresta = null;
				try {
					aresta = new Aresta(linha[1].split(": ")[1], linha[2].split(": ")[1], linha[3].split(": ")[1]);
				} catch (Exception e) {
					aresta = new Aresta("", linha[2].split(": ")[1], linha[3].split(": ")[1]);
				}
				
				grafo.adicionarAresta(aresta);
			}
		}

		return grafo;
	}
	
	// TODO remover o main que foi utilizado para teste
	public static void main(String[] args) throws ErroArquivoException,
			IOException {

		String query = "match (p:Pessoa)-[r1]-(e:Evento)-[r2]-(l:Local) where e.trecho =~ \"(?i).*guer.*\" return p,e,l,r1,r2";

		Grafo grafo = construirGrafo(rest_query(query));
		
		for (int i = 0; i < grafo.getVertices().size(); i++) {
			System.out.println(grafo.getVertices().get(i));
		}
		
		for (int i = 0; i < grafo.getArestas().size(); i++) {
			System.out.println(grafo.getArestas().get(i));
		}
	}
}
