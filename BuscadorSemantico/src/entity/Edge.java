package entity;

/**
 * Representa uma aresta de um grafo.
 * 
 * @author Geovani Celebrim
 * 
 */
public class Edge {

	private String from;
	private String to;

	public Edge(String from, String to) {
		this.from = from;
		this.to = to;
	}

	/**
	 * Obtém o ponto de partida da aresta.
	 * 
	 * @return {@link String}.
	 */
	public String getFrom() {
		return this.from;
	}

	/**
	 * Obtém o ponto de chegada da aresta.
	 * 
	 * @return {@link String}.
	 */
	public String getTo() {
		return this.to;
	}

	@Override
	public String toString() {
		return this.from + ", " + this.to;
	}
}
