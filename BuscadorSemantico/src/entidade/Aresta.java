package entidade;

public class Aresta {
	private String note;
	private String from;
	private String to;
	
	public Aresta(String note, String from, String to) {
		this.note = note;
		this.from = from;
		this.to = to;
	}
	
	public String getNote() {
		return this.note;
	}
	
	public String getFrom() {
		return this.from;
	}
	
	public String getTo() {
		return this.to;
	}
	
	@Override
	public String toString() {
		return this.note + ", " + this.from + ", " + this.to;
	}
}
