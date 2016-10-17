package DAO;

/**
 * Esta classe tem como finalidade informar os diretórior de arquivos utilizados
 * no sistema. O principal objetivo é centralizar as alterações, para que, caso
 * necessário, essas informações sejam alteradas em apenas um local.
 * 
 * @author Geovani Celebrim
 */
public enum Paths {
	/**
	 * Enum que representa o diretório de arquivos de texto retornados nas
	 * buscas.
	 */

	 DATA_TEXT("/home/geovani/WebServer/"),

	/**
	 * Enum que representa a pasta dos dados utilizados no protótipo.
	 */

	FOLDER("/CEDIM-II-GUERRA"),
	
	/**
	 * Enum que representa o diretório do módulo em Python utilizado para conexão <i>REST</i> 
	 */
	
	REST("/home/geovani/WebServer/");

	private final String text;

	private Paths(final String text) {
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