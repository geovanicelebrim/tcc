package entity;

/**
 * Representa um documento.
 * 
 * @author Geovani Celebrim
 * 
 */
public class Document {
	private String name;
	private String text;

	public Document(String name, String text) {
		this.name = name;
		this.text = text;
	}

	/**
	 * Obtém o nome do documento.
	 * 
	 * @return {@link String}.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Obtém o conteúdo do documento.
	 * 
	 * @return {@link String}.
	 */
	public String getText() {
		return this.text;
	}
}
