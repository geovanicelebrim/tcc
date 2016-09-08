package entidade.resultados;

public class ResultadoCypher {
	
	private String resultado;
	private Long tempo;
	
	public ResultadoCypher(String resultado, Long tempo) {
		this.resultado = resultado;
		this.tempo = tempo;
	}
	
	public Long getTempo() {
		return this.tempo;
	}
	
	@Override
	public String toString() {
		return this.resultado;
	}
}
