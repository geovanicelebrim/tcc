package entidade;

public class Documento {
	private String nome;
	private String texto;
	
	public Documento(String nome, String texto) {
		this.nome = nome;
		this.texto = texto;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getTexto() {
		return this.texto;
	}
}
