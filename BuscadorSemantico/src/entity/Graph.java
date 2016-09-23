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
}
