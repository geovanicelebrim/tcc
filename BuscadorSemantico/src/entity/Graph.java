package entity;

import java.util.ArrayList;

/**
 * Representa um grafo.
 * 
 * @author Geovani Celebrim
 * 
 */
public class Graph {
	private ArrayList<Vertex> vertices;
	private ArrayList<Edge> edges;

	public Graph() {
		this.vertices = new ArrayList<>();
		this.edges = new ArrayList<>();
	}

	/**
	 * Adiciona um novo vértice no grafo.
	 * 
	 * @param vertice
	 *            .
	 */
	public void addVertex(Vertex vertice) {
		this.vertices.add(vertice);
	}

	/**
	 * Adiciona uma nova aresta no grafo.
	 * 
	 * @param edge
	 */
	public void addEdge(Edge edge) {
		this.edges.add(edge);
	}

	/**
	 * Obtém os vértices de um grafo.
	 * 
	 * @return {@link ArrayList} de {@link Vertex}.
	 */
	public ArrayList<Vertex> getVertices() {
		return this.vertices;
	}

	/**
	 * Obtém as arestas de um grafo.
	 * 
	 * @return {@link ArrayList} de {@link Edge}.
	 */
	public ArrayList<Edge> getEdges() {
		return this.edges;
	}
	
	/* Tem que testar */
	public String getJsonEdges() {
		String json = "[{";
		
		for (int i = 0; i < this.getEdges().size(); i++) {
			json += "from: " + this.getEdges().get(i).getFrom() + ",";
			json += "to: " + this.getEdges().get(i).getTo();
			
			if( i + 1 != this.getEdges().size()) {
				json += "}, {";
			}
		}
		
		json += "}]";
		return json;
	}
	
	/* Tem que testar */
	public String getJsonNodes() {
		String json = "[{";
		
		for (int i = 0; i < this.getVertices().size(); i++) {
			json += "id: " + this.getVertices().get(i).getID() + ",";
			json += "label: '" + this.getVertices().get(i).getSlice() + "',";
			json += "group: '" + this.getVertices().get(i).getLabel() + "',";
			
			if ( i + 1 != this.getVertices().size()) {
				json += "}, {";
			}
		}
		
		json += "}]";
		return json;
	}
}
