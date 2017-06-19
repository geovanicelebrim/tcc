package entity.results;

/**
 * Representa o resultado de uma busca por tabela no banco, Essa entidade tem
 * como principal objetivo representar algumas estatísticas de uma busca por
 * tabela.
 * 
 * @author Geovani Celebrim
 * 
 */
public class CypherResults {

	private String label, slice, citations, relations, document, title;

	public CypherResults(String title, String label, String slice, String citations, String relations,
			String document) {
		this.label = label;
		this.slice = slice;
		this.citations = citations;
		this.relations = relations;
		this.document = document;
		this.title = title;
	}

	/**
	 * Obtém qual é o tipo da entidade.
	 * 
	 * @return {@link String}.
	 */
	public String getLabel() {
		return this.label;
	}
	
	/**
	 * Obtém o techo guardado no banco que representa aquela entidade.
	 * 
	 * @return {@link String}.
	 */
	public String getSlice() {
		return this.slice;
	}

	/**
	 * Obtém o número de vezes que uma entidade foi citada em determinado documento.
	 * 
	 * @return {@link String}.
	 */
	public String getCitations() {
		return this.citations;
	}

	/**
	 * Obtém o número de relações que uma entidade possui em um determinado documento.
	 * 
	 * @return {@link String}.
	 */
	public String getRelations() {
		return this.relations;
	}

	/**
	 * Obtém o nome do documento ao qual aquela entidade pertence.
	 * 
	 * @return {@link String}.
	 */
	public String getDocument() {
		return this.document;
	}
	
	public String getTitle() {
		return this.title;
	}
}
