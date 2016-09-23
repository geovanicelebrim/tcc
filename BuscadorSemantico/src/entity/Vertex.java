package entity;

/**
 * Representa um vértice de um grafo.
 * 
 * @author Geovani Celebrim
 * 
 */
public class Vertex {
	private String label;
	private String id;
	private String slice;

	public Vertex(String label, String id, String slice) {
		this.label = label;
		this.id = id;
		this.slice = slice;
	}

	/**
	 * Obtém o label de um vértice.
	 * 
	 * @return {@link String}.
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * Obtém o ID de um vértice.
	 * 
	 * @return {@link String}.
	 */
	public String getID() {
		return this.id;
	}

	/**
	 * Obtém o trecho de um vértice.
	 * 
	 * @return {@link String}.
	 */
	public String getSlice() {
		return this.slice;
	}

	@Override
	public String toString() {
		return this.id + ", " + this.label + ", " + this.slice;
	}
}
