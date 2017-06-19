package entity.results;

import java.util.ArrayList;

import entity.Position;

/**
 * Representa o resultado de uma busca semântica, buscando por documentos.
 * 
 * @author Geovani Celebrim
 * 
 */
public class DocumentResult {
	private String title;
	private String documentName;
	private Position position;
	private Integer beginSlice;
	private Integer endSlice;
	private String slice;
	private String author;
	private String source;
	
	private ArrayList<DocumentResult> children;

	public DocumentResult(String title, String documentName, Position position, String slice, Integer beginSlice, Integer endSlice,
			String author, String source) {
		this.title = title;
		this.documentName = documentName;
		this.position = position;
		this.slice = slice;
		this.beginSlice = beginSlice;
		this.endSlice = endSlice;
		this.author = author;
		this.source = source;
		this.children = new ArrayList<>();
	}

	public DocumentResult(String documentName, Position position) {
		this.documentName = documentName;
		this.position = position;
		this.children = new ArrayList<>();
	}

	public DocumentResult(String author, String source) {
		this.author = author;
		this.source = source;
		this.children = new ArrayList<>();
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
	
	public Integer getBeginSlice() {
		return this.beginSlice;
	}
	
	public Integer getEndSlice() {
		return this.endSlice;
	}
	
	public void addChildren(DocumentResult document) {
		this.children.add(document);
	}
	
	public ArrayList<DocumentResult> getChildren() {
		return this.children;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	@Override
	public boolean equals(Object obj) {
		DocumentResult document = (DocumentResult) obj;
		
		if (document.getDocumentName().equals(this.documentName)) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Nome: " + this.documentName + "\nPosicao: "
				+ this.position.toString() + "\nAutor: " + this.author
				+ "\nFonte: " + this.source;
	}
}
