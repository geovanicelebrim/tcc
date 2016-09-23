package entity.results;

import entity.Position;

/**
 * Representa o resultado de uma busca semântica, buscando por documentos.
 * 
 * @author Geovani Celebrim
 * 
 */
public class DocumentResult {
	private String documentName;
	private Position position;
	private String slice;
	private String author;
	private String source;

	public DocumentResult(String documentName, Position position, String slice,
			String author, String source) {
		this.documentName = documentName;
		this.position = position;
		this.slice = slice;
		this.author = author;
		this.source = source;
	}

	public DocumentResult(String documentName, Position position) {
		this.documentName = documentName;
		this.position = position;
	}

	public DocumentResult(String author, String source) {
		this.author = author;
		this.source = source;
	}

	/**
	 * Obtém o nome do documento.
	 * 
	 * @return {@link String}.
	 */
	public String getDocumentName() {
		return this.documentName;
	}

	/**
	 * Obtém a posição {@link Position} a ser mostrada do documento.
	 * 
	 * @return {@link Position}.
	 */
	public Position getPosition() {
		return this.position;
	}

	/**
	 * Obtém um trecho de um documento.
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
		return "Nome: " + this.documentName + "\nPosicao: "
				+ this.position.toString() + "\nAutor: " + this.author
				+ "\nFonte: " + this.source;
	}
}
