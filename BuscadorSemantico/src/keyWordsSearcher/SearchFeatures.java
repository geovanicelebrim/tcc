package keyWordsSearcher;
import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

public class SearchFeatures {

	public static Query termQuery(String field, String term){

		Term t = new Term(field,term);
		return new TermQuery(t);
	}
	
	// TODO Considerar multiplos parâmetros
//	private void booleanQueryExample(){
//		System.out.println("BooleanQuery: Search mails that have both 'java' " +
//				"and 'bangalore' in the subject field ");
//		Query query1 = new TermQuery(new Term("subject","java"));
//		Query query2 = new TermQuery(new Term("subject","bangalore"));
//		BooleanQuery query = new BooleanQuery();
//		query.add(query1,BooleanClause.Occur.MUST);
//		query.add(query2,BooleanClause.Occur.MUST);
//		showSearchResults(query);
//	}
	
//	private void phraseQueryExample(){
//		System.out.println("PhraseQuery example: Search mails that have phrase " +
//				"'job opening j2ee' in the subject field.");
//		PhraseQuery query = new PhraseQuery();
//		query.setSlop(1);
//		//Add terms of the phrases.
//		query.add(new Term("subject","job"));
//		query.add(new Term("subject","opening"));
//		query.add(new Term("subject","j2ee"));
//		
//		showSearchResults(query);
//	}
	
//	private void wildCardQueryExample(){
//		System.out.println("WildcardQuery: Search for 'arch*' to find emails that " +
//				"have word 'architect' in subject field.");
//		Query query = new WildcardQuery(new Term("subject","arch*"));
//		showSearchResults(query);
//	}
	
	public static Query fuzzyQuery(String field, String term) {
		return new FuzzyQuery(new Term(field, term));
	}
	
	//Apresenta ótimos resultados
	public static Query queryParser(String field, String query) throws ParseException {

		QueryParser queryParser = new QueryParser(field,new BrazilianAnalyzer());
		
		return queryParser.parse(query);
	}
}
