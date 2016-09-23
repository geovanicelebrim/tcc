package util;
/**
 * 
 * @author Geovani Celebrim
 * @version 1.0.0
 * 
 * Analizador léxico e sentático para a linguagem de consulta semântica definida no projeto CEDIM.
 * A estrutura da linguagem é dada da seguinte maneira:
 * 
 * (Entidade1:"filtro2")--(Entidade2:"filtro2)
 * 
 * Onde Entidade[1-2] representa uma entidade nomeada a ser buscada, podendo também ser omitida na consulta e
 * "filtro[1-2]" são especificações daquela entidade, e.g (Pessoa:"Geovani Celebrim).
 *
 */
public class Lex {
	
	/**
	 * Este método é responsável por realizar a análise léxica e análise sintática da query de entrada.
	 * Seu retorno é verdadeiro, caso a query passe no teste. Caso não passe, é lançada uma excessão.
	 * @param query do tipo String, contendo a consulta semântica.
	 * @return <b>true</b> informando se a query está léxica e sintáticamente correta. 
	 * @throws Exception informando que a query não passou no teste léxico/sintático.
	 */
	public static boolean checkQuery (String query) throws Exception {
		String in = query.replaceAll("( )+", " ").replaceAll("- -", "--").replaceAll("\\) -- \\(", "\\)--\\(").replaceAll("'", "\"");
		String tokens[] = in.split("--");
		
		System.out.println("Possui " + tokens.length + " entidades");
		
		for (int i = 0; i < tokens.length; i++) {

			if(tokens[i].matches("\\(( )*[A-z]+( )*:( )*\"[^\\)]*\"( )*\\)")) {		
				System.out.println("Ok (Entidade:Where)");
			}
			else if (tokens[i].matches("\\(( )*\"[^\\)]*\"( )*\\)")) {
				System.out.println("Ok (Where)");
			}
			else if (tokens[i].matches("\\(( )*[A-z]+( )*\\)")) {
				System.out.println("Ok (Entidade)");
			}
			else {
				throw new Exception("A consulta não está correta. Erro no token " + tokens[i]);
			}
		}
		return true;
	}

	/**
	 * Este método é responsável por realizar a "tradução" da query de entrada, utilizada na busca semântica,
	 * para uma query em Cypher, linguagem utilizada no banco de dados de grafos Neo4j.
	 * Se a consulta estiver léxica ou sintáticamente incorreta, é lançada uma excessão.
	 * @see <a href="https://neo4j.com/docs/developer-manual/current/">Neo4j Documentação</a>
	 * Seu retorno é uma String traduzida para Cypher.
	 * @param queryIn do tipo String, contendo a consulta semântica.
	 * @return <b>queryOut</b> do tipo String, contendo a consulta semântica traduzida para Cypher.
	 * @throws Exception informando que a query não passou no teste léxico/sintático.
	 */
	public static String translateToCypherQuery (String queryIn) throws Exception {
		checkQuery(queryIn);
		
		String in = queryIn.replaceAll("( )+", " ").replaceAll("- -", "--").replaceAll("\\) -- \\(", "\\)--\\(").replaceAll("'", "\"");
		String tokens[] = in.split("--");
		
		int cont = 0;
        String queryOut = "match (doc:Documento)-[relDoc]-";
        String where = " where ";
        String returnable = " return doc, relDoc, ";
        
        for (int i = 0; i < tokens.length; i++) {
        	tokens[i] = tokens[i].replaceAll("\\(", "").replaceAll("\\)", "");
        	
        	if(tokens[i].split(":").length > 1) {
	        	if(i + 1 == tokens.length) {
	        		queryOut += "(node" + cont + ":" + tokens[i].split(":")[0] + ")";
	        		where += "node" + cont + ".trecho =~ " + tokens[i].split(":")[1].replaceFirst("\"", "\"\\(?i\\)");
	        		returnable += "node" + cont;
	        		cont++;
	        	} else if(!tokens[i + 1].contains("\"")) {
	        		queryOut += "(node" + cont + ":" + tokens[i].split(":")[0] + ")-[rel" + cont + " ]-";
	        		where += "node" + cont + ".trecho =~ " + tokens[i].split(":")[1].replaceFirst("\"", "\"\\(?i\\)");
	        		returnable += "node" + cont + ", rel" + cont + ", ";
	        		cont++;
	        	} else {
	        		queryOut += "(node" + cont + ":" + tokens[i].split(":")[0] + ")-[rel" + cont + " ]-";
	        		where += "node" + cont + ".trecho =~ " + tokens[i].split(":")[1].replaceFirst("\"", "\"\\(?i\\)") + " and ";
	        		returnable += "node" + cont + ", rel" + cont + ", ";
	        		cont++;
	        	}
        	} else if (tokens[i].contains("\"")){
        		if(i + 1 == tokens.length) {
	        		queryOut += "(node" + cont + ")";
	        		where += "node" + cont + ".trecho =~ " + tokens[i].replaceFirst("\"", "\"\\(?i\\)");
	        		returnable += "node" + cont;
	        		cont++;
	        	} else if(!tokens[i + 1].contains("\"")) {
	        		queryOut += "(node" + cont + ")-[rel" + cont + " ]-";
	        		where += "node" + cont + ".trecho =~ " + tokens[i].replaceFirst("\"", "\"\\(?i\\)");
	        		returnable += "node" + cont + ", rel" + cont + ", ";
	        		cont++;
	        	} else {
	        		queryOut += "(node" + cont + ")-[rel" + cont + " ]-";
	        		where += "node" + cont + ".trecho =~ " + tokens[i].replaceFirst("\"", "\"\\(?i\\)") + " and ";
	        		returnable += "node" + cont + ", rel" + cont + ", ";
	        		cont++;
	        	}
        	} else {
        		if(i + 1 == tokens.length) {
	        		queryOut += "(node" + cont + ":" + tokens[i] + ")";
	        		returnable += "node" + cont;
	        		cont++;
	        	} else {
	        		queryOut += "(node" + cont + ":" + tokens[i] + ")-[rel" + cont + " ]-";
	        		returnable += "node" + cont + ", rel" + cont + ", ";
	        		cont++;
	        	}
        	}
		}
        
        if (where.equals(" where ")){
        	where = "";
        }
        return queryOut + where + returnable;
	}
	
    public static void main(String[] args) {
        /*
			
			(Pessoa:'.*.*Paulo.*.*')--(Evento:'.*guerra.*')--(Data:'.*19.*')
			
			(Pessoa:'.*Paulo.*')--(Evento:'.*guerra.*')--('.*19.*')
			
			(Pessoa:'.*Paulo.*')--('.*guerra.*')--('.*19.*')
			
			(Pessoa:'.*Paulo.*')--(Evento)--(Data)
			
			(Pessoa:'.*Paulo.*')
			
			(Pessoa)
			
		*/
    	
    	//Scanner sc = new Scanner(System.in);
    	//FUNCIONA CARALHOOOO
    	String in = "(Pessoa:'.*Paulo.*')--(Evento:'.*guerra.*')--('.*19.*')";//sc.nextLine();
        
    	try {
    		System.out.println(translateToCypherQuery(in));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}