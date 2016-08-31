package entidade;

public class ResultadoDocumento {
	private String nomeDocumento;
	private Posicao posicao;
	private String trecho;
	private String autor;
	private String fonte;

	public ResultadoDocumento(String nomeDocumento, Posicao posicao,
			String trecho, String autor, String fonte) {
		this.nomeDocumento = nomeDocumento;
		this.posicao = posicao;
		this.trecho = trecho;
		this.autor = autor;
		this.fonte = fonte;
	}

	public ResultadoDocumento(String nomeDocumento, Posicao posicao) {
		this.nomeDocumento = nomeDocumento;
		this.posicao = posicao;
	}

	public ResultadoDocumento(String autor, String fonte) {
		this.autor = autor;
		this.fonte = fonte;
	}

	public String getNomeDocumento() {
		return this.nomeDocumento;
	}

	public Posicao getPosicao() {
		return this.posicao;
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
		return "Nome: " + this.nomeDocumento + "\nPosicao: "
				+ this.posicao.toString() + "\nAutor: " + this.autor
				+ "\nFonte: " + this.fonte;
	}
}
