package DAO;

public enum Autenticacao {
	USER("neo4j"), PASSWORD("cedim");

	private final String texto;

	private Autenticacao(final String texto) {
		this.texto = texto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return texto;
	}
}
