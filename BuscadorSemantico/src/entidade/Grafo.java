package entidade;

import java.util.ArrayList;

public class Grafo {
	ArrayList<Vertice> vertices;
	ArrayList<Aresta> arestas;
	
	public Grafo( ) {
		this.vertices = new ArrayList<>();
		this.arestas = new ArrayList<>();
	}
	
	public void adicionarVertice(Vertice vertice) {
		this.vertices.add(vertice);
	}
	
	public void adicionarAresta(Aresta aresta) {
		this.arestas.add(aresta);
	}
	
	public ArrayList<Vertice> getVertices() {
		return this.vertices;
	}
	
	public ArrayList<Aresta> getArestas() {
		return this.arestas;
	}
}
