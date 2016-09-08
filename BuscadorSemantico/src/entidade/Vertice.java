package entidade;

public class Vertice {
	private String label;
	private String id;
	private String trecho;
	
	public Vertice(String label, String id, String trecho) {
		this.label = label;
		this.id = id;
		this.trecho = trecho;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public String getID() {
		return this.id;
	}
	
	public String getTrecho() {
		return this.trecho;
	}
	
	@Override
	public String toString() {
		return this.id + ", " + this.label + ", " + this.trecho;
	}
}
