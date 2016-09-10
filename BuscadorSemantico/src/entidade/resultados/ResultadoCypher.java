package entidade.resultados;

public class ResultadoCypher {
	
	private String entidade1, 
			entidade2,
			citacoes_entidade1,
			citacoes_entidade2,
			relacoes_entidade1,
			relacoes_entidade2,
			nome_documento;
			
	/*
	 * match (p:Pessoa)-[]-(e:Evento)-[]-(d:Documento) where e.trecho =~ "(?i).*guerra.*" return p.trecho as ptrecho, e.trecho as etrecho, p.posicao as posicao1, e.posicao as posicao2, d.caminho as caminho, p.numeroRelacoes as prelacoes, e.numeroRelacoes as rrelacoes, p.numeroCitacoes as pcitacoes, e.numeroCitacoes as ecitacoes, d.nome as nome
	 */
	
	// TODO remover isso e atualizar para os outros atributos afim de construir uma tabela
	private String resultado;
	
	private Long tempo;
	
	public ResultadoCypher(String entidade1, String entidade2, String citacoes_entidade1, String citacoes_entidade2,
							String relacoes_entidade1,String relacoes_entidade2,String nome_documento) {
		this.entidade1 = entidade1;
		this.entidade2 = entidade2;
		this.citacoes_entidade1 = citacoes_entidade1;
		this.citacoes_entidade2 = citacoes_entidade2;
		this.relacoes_entidade1 = relacoes_entidade1;
		this.relacoes_entidade2 = relacoes_entidade2;
		this.nome_documento = nome_documento;
	}
	
	public ResultadoCypher(String resultado, Long tempo) {
		this.resultado = resultado;
		this.tempo = tempo;
	}
	
	public Long getTempo() {
		return this.tempo;
	}
	
	public String getResultado() {
		return this.resultado;
	}
	
	public String getEntidade1() {
		return this.entidade1;
	}
	
	public String getEntidade2() {
		return this.entidade2;
	}
	
	public String getCitacoesEntidade1() {
		return this.citacoes_entidade1;
	}
	
	public String getCitacoesEntidade2() {
		return this.citacoes_entidade2;
	}
	
	public String getRelacoesEntidade1() {
		return this.relacoes_entidade1;
	}
	
	public String getRelacoesEntidade2() {
		return this.relacoes_entidade2;
	}
	
	public String getNomeDocumento() {
		return this.nome_documento;
	}
	
	@Override
	public String toString() {
		return this.entidade1 + ", " + 
				this.entidade2 + ", " +
				this.citacoes_entidade1 + ", " +
				this.citacoes_entidade2 + ", " +
				this.relacoes_entidade1 + ", " +
				this.relacoes_entidade2 + ", " +
				this.nome_documento;
	}
}
