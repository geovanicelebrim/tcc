package DAO;

/**
 * Esta classe tem como finalidade informar o usuário e senha do banco para o
 * sistema. O principal objetivo é centralizar as alterações, para que, caso
 * necessário, essas informações sejam alteradas em apenas um local.
 * 
 * @author Geovani Celebrim
 */
public enum Authentication {
	/**
	 * Enum que representa o usuário do banco.
	 */
	USER("neo4j"),

	/**
	 * Enum que representa a senha do banco.
	 */
	PASSWORD("cedim");

	private final String text;

	private Authentication(final String text) {
		this.text = text;
	}

	/**
	 * Pega o valor relacionado ao Enum em questão.
	 * 
	 * @return <b>String</b> contendo o valor associado ao Enum.
	 * 
	 * @see java.lang.Enum#toString()
	 * @see Enum
	 */
	@Override
	public String toString() {
		return text;
	}
}
