package entity.results;

/**
 * Representa o resultado de uma busca simples, por palavras chave.
 * 
 * @author Geovani Celebrim
 * 
 */
public class SimpleResults {
	
	private String pathDocument;
	private String title;
	private String slice;
	private String author;
	private String source;
	private Float score;

	public SimpleResults(String pathDocument, String slice, String author,
			String source, Float score) {
		this.pathDocument = pathDocument;
		this.title = pathDocument.replaceAll(".txt", "");
		this.slice = slice;
		this.author = author;
		this.source = source;
		this.score = score;
	}
	
	public SimpleResults(String pathDocument, Float score) {
		this.pathDocument = pathDocument;
		this.title = pathDocument.replaceAll(".txt", "");
		this.score = score;
	}

	public SimpleResults(String pathDocument) {
		this.pathDocument = pathDocument;
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
		return this.pathDocument;
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
	
	public String getTitle() {
		return this.title;
	}
	
	public Float getScore() {
		return this.score;
	}
	
	public void setScore(Float score) {
		this.score = score;
	}
}
