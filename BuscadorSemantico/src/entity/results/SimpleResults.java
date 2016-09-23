package entity.results;

/**
 * Representa o resultado de uma busca simples, por palavras chave.
 * 
 * @author Geovani Celebrim
 * 
 */
public class SimpleResults {
	private String documentResult;
	private String slice;
	private String author;
	private String source;

	public SimpleResults(String documentResults, String slice, String author,
			String fonte) {
		this.documentResult = documentResults;
		this.slice = slice;
		this.author = author;
		this.source = fonte;
	}

	public SimpleResults(String documentName) {
		this.documentResult = documentName;
	}

	public SimpleResults(String author, String source) {
		this.author = author;
		this.source = source;
	}

	/**
	 * Obtém o nome do documento.
	 * 
	 * @return {@link String}.
	 */
	public String getDocumentName() {
		return this.documentResult;
	}

	/**
	 * Obtém o trecho do documento.
	 * 
	 * @return {@link String}.
	 */
	public String getSlice() {
		return this.slice;
	}

	/**
	 * Obtém o nome do autor do documento.
	 * 
	 * @return {@link String}.
	 */
	public String getAuthor() {
		return this.author;
	}

	/**
	 * Obtém a fonte do documento.
	 * 
	 * @return {@link String}.
	 */
	public String getSource() {
		return this.source;
	}

	@Override
	public String toString() {
		return "Nome: " + this.documentResult + "\nPosicao: " + "\nAutor: "
				+ this.author + "\nFonte: " + this.source;
	}
}
