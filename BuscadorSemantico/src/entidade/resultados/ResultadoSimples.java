package entidade.resultados;

public class ResultadoSimples {
	private String nomeDocumento;
	private String trecho;
	private String autor;
	private String fonte;

	public ResultadoSimples(String nomeDocumento, String trecho, String autor,
			String fonte) {
		this.nomeDocumento = nomeDocumento;
		this.trecho = trecho;
		this.autor = autor;
		this.fonte = fonte;
	}

	public ResultadoSimples(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}

	public ResultadoSimples(String autor, String fonte) {
		this.autor = autor;
		this.fonte = fonte;
	}

	public String getNomeDocumento() {
		return this.nomeDocumento;
	}

	public String getTrecho() {
		return this.trecho;
	}

	public String getAutor() {
		return this.autor;
	}

	public String getFonte() {
		return this.fonte;
	}
	
	@Override
	public String toString() {
		return "Nome: " + this.nomeDocumento + "\nPosicao: " + "\nAutor: "
				+ this.autor + "\nFonte: " + this.fonte;
	}
}
