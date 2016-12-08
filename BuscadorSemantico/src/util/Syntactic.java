package util;

import exception.InvalidQueryException;

/**
 * 
 * @author Geovani Celebrim
 * @version 1.1.3
 * 
 *          Analizador léxico e sentático para a linguagem de consulta semântica
 *          definida no projeto CEDIM. A estrutura da linguagem é dada da
 *          seguinte maneira:
 * 
 *          (Entidade1:"filtro2")--(Entidade2:"filtro2)
 * 
 *          Onde Entidade[1-2] representa uma entidade nomeada a ser buscada,
 *          podendo também ser omitida na consulta e "filtro[1-2]" são
 *          especificações daquela entidade, e.g (Pessoa:"Geovani Celebrim).
 * 
 */
public class Syntactic {

	/**
	 * Este método é responsável por realizar a análise léxica e análise
	 * sintática da query de entrada. Seu retorno é verdadeiro, caso a query
	 * passe no teste. Caso não passe, é lançada uma excessão.
	 * 
	 * @param query
	 *            do tipo String, contendo a consulta semântica.
	 * @return <b>true</b> informando se a query está léxica e sintáticamente
	 *         correta.
	 * @throws InvalidQueryException informando que a query não passou no teste léxico/sintático.
	 */
	public static boolean checkQuery(String query) throws InvalidQueryException {
		String in = query.replaceAll("( )+", " ").replaceAll("- -", "--")
				.replaceAll(" -- ", "--").replaceAll("'", "\"");
		String tokens[] = in.split("--");

		for (int i = 0; i < tokens.length; i++) {

			if (tokens[i].matches("( )*[A-z]+( )*:( )*\".*\"( )*")) {
				// System.out.println("Ok (Entidade:Where)");
			} else if (tokens[i].matches("( )*\".*\"( )*")) {
				// System.out.println("Ok (Where)");
			} else if (tokens[i].matches("( )*[A-z]+( )*")) {
				// System.out.println("Ok (Entidade)");
			} else {
				throw new InvalidQueryException(tokens[i]);
			}
		}
		return true;
	}

	/**
	 * Este método é responsável por realizar a "tradução" da query de entrada,
	 * utilizada na busca semântica, para uma query em Cypher, linguagem
	 * utilizada no banco de dados de grafos Neo4j. Se a consulta estiver léxica
	 * ou sintáticamente incorreta, é lançada uma excessão. Seu retorno é uma
	 * String traduzida para Cypher.
	 * 
	 * @see <a href="https://neo4j.com/docs/developer-manual/current/">Neo4j
	 *      Documentação</a>
	 * @param queryIn
	 *            do tipo String, contendo a consulta semântica.
	 * @return <b>queryOut</b> do tipo String, contendo a consulta semântica
	 *         traduzida para Cypher.
	 * @throws InvalidQueryException 
	 * @throws Exception
	 *             informando que a query não passou no teste léxico/sintático.
	 */
	public static String translateToCypherQuery(String queryIn) throws InvalidQueryException {
		checkQuery(queryIn);

		String in = queryIn.replaceAll("( )+", " ").replaceAll("- -", "--")
				.replaceAll(" -- ", "--").replaceAll("'", "\"");
		String tokens[] = in.split("--");

		int count = 0;
		String queryOut = "match (doc:Documento)-[relDoc]-";
		String where = " where ";
		String returnable = " return doc.caminho as caminho, doc.nome as nome, relDoc, ";
		
		int nPositions = tokens.length;
		int nPositionsUsed = 0;

		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = tokens[i];

			if (tokens[i].split(":").length > 1) {
				if (i + 1 == tokens.length) {
					queryOut += "(node" + count + ":" + tokens[i].split(":")[0]
							+ ")";
					where += "node"
							+ count
							+ ".trecho =~ "
							+ tokens[i].split(":")[1].replaceAll("\"", ".*\"").replaceFirst("\\.\\*\"",
									"\"\\(?i\\).*");
					if(nPositions == 1){
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".posicao as posicao1, " + node + ".posicao as posicao2, " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count;
					} else if (nPositionsUsed < 2) {
						nPositionsUsed++;
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".posicao as posicao" + nPositionsUsed + ", "  + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count;
					} else {
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count;
					}
					
					count++;
				} else if (!tokens[i + 1].contains("\"")) {
					queryOut += "(node" + count + ":" + tokens[i].split(":")[0]
							+ ")-[rel" + count + "]-";
					where += "node"
							+ count
							+ ".trecho =~ "
							+ tokens[i].split(":")[1].replaceAll("\"", ".*\"").replaceFirst("\\.\\*\"",
									"\"\\(?i\\).*");
					
					if(nPositions == 1){
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".posicao as posicao1, " + node + ".posicao as posicao2, "  + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count + ", rel" + count + ", ";
					} else if (nPositionsUsed < 2) {
						nPositionsUsed++;
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".posicao as posicao" + nPositionsUsed + ", " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count + ", rel" + count + ", ";
					} else {
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count + ", rel" + count + ", ";
					}
					count++;
				} else {
					queryOut += "(node" + count + ":" + tokens[i].split(":")[0]
							+ ")-[rel" + count + "]-";
					where += "node"
							+ count
							+ ".trecho =~ "
							+ tokens[i].split(":")[1].replaceAll("\"", ".*\"").replaceFirst("\\.\\*\"",
									"\"\\(?i\\).*") + " and ";
					
					if(nPositions == 1){
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".posicao as posicao1, " + node + ".posicao as posicao2, " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count + ", rel" + count + ", ";
					} else if (nPositionsUsed < 2) {
						nPositionsUsed++;
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".posicao as posicao" + nPositionsUsed + ", " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count + ", rel" + count + ", ";
					} else {
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count + ", rel" + count + ", ";
					}
					
					count++;
				}
			} else if (tokens[i].contains("\"")) {
				if (i + 1 == tokens.length) {
					queryOut += "(node" + count + ")";
					where += "node" + count + ".trecho =~ "
							+ tokens[i].replaceAll("\"", ".*\"").replaceFirst("\\.\\*\"", "\"\\(?i\\).*");
					
					if(nPositions == 1){
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".posicao as posicao1, " + node + ".posicao as posicao2, " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count;
					} else if (nPositionsUsed < 2) {
						nPositionsUsed++;
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".posicao as posicao" + nPositionsUsed + ", " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count;
					} else {
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count;
					}
					
					count++;
				} else if (!tokens[i + 1].contains("\"")) {
					queryOut += "(node" + count + ")-[rel" + count + "]-";
					where += "node" + count + ".trecho =~ "
							+ tokens[i].replaceAll("\"", ".*\"").replaceFirst("\\.\\*\"", "\"\\(?i\\).*");
					
					if(nPositions == 1){
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".posicao as posicao1, " + node + ".posicao as posicao2, " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count + ", rel" + count + ", ";
					} else if (nPositionsUsed < 2) {
						nPositionsUsed++;
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".posicao as posicao" + nPositionsUsed + ", " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count + ", rel" + count + ", ";
					} else {
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count + ", rel" + count + ", ";
					}
					
					count++;
				} else {
					queryOut += "(node" + count + ")-[rel" + count + "]-";
					where += "node" + count + ".trecho =~ "
							+ tokens[i].replaceAll("\"", ".*\"").replaceFirst("\\.\\*\"", "\"\\(?i\\).*")
							+ " and ";
					
					if(nPositions == 1){
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".posicao as posicao1, " + node + ".posicao as posicao2, " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count + ", rel" + count + ", ";
					} else if (nPositionsUsed < 2) {
						nPositionsUsed++;
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".posicao as posicao" + nPositionsUsed + ", " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count + ", rel" + count + ", ";
					} else {
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count + ", rel" + count + ", ";
					}
					
					count++;
				}
			} else {
				if (i + 1 == tokens.length) {
					queryOut += "(node" + count + ":" + tokens[i] + ")";
					
					if(nPositions == 1){
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".posicao as posicao1, " + node + ".posicao as posicao2, " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count;
					} else if (nPositionsUsed < 2) {
						nPositionsUsed++;
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".posicao as posicao" + nPositionsUsed + ", " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count;
					} else {
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count;
					}
					
					count++;
				} else {
					queryOut += "(node" + count + ":" + tokens[i] + ")-[rel"
							+ count + "]-";
					
					if(nPositions == 1){
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".posicao as posicao1, " + node + ".posicao as posicao2, " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count + ", rel" + count + ", ";
					} else if (nPositionsUsed < 2) {
						nPositionsUsed++;
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".posicao as posicao" + nPositionsUsed + ", " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count + ", rel" + count + ", ";
					} else {
						String node = "node" + count;
						returnable += "labels(" + node + ") as label" + count + ", " + node + ".trecho as " + "trecho" + count + ", " + node + ".numeroCitacoes as c" + count + ", " + node + ".numeroRelacoes as r" + count + ", rel" + count + ", ";
					}
					
					count++;
				}
			}
		}

		if (where.equals(" where ")) {
			where = "";
		}
		return queryOut + where + returnable;
	}
}