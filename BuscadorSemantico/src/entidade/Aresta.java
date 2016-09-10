package entidade;

public class Aresta {

	private String from;
	private String to;
	
	public Aresta(String from, String to) {
		this.from = from;
		this.to = to;
	}
	
	public String getFrom() {
		return this.from;
	}
	
	public String getTo() {
		return this.to;
	}
	
	@Override
	public String toString() {
		return this.from + ", " + this.to;
	}
}
