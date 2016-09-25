package util;

import java.util.ArrayList;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import entity.Position;
import entity.results.CypherResults;
import exception.InvalidQueryException;

/**
 * Possui alguns métodos de processamento de dados úteis para o projeto.
 * 
 * @author Geovani Celebrim
 */
public class DataProcessing {

	/**
	 * Dados dois {@link ArrayList}s de {@link Position} s, esse método faz um
	 * cruzamento desses dados afim de determinar as entidades as quais essas
	 * posições pertencem estão em um mesmo contexto. É considerado que as
	 * entidades estão no mesmo contexto se elas possuem uma distância menor ou
	 * irual a 500 caracteres.
	 * 
	 * @param position1
	 *            é um {@link ArrayList} de {@link Position} de uma entidade.
	 * @param position2
	 *            é um {@link ArrayList} de {@link Position} de uma entidade.
	 * @return <b>ArrayList</b> de {@link Position} com as posições
	 *         correlacionadas.
	 */
	public static ArrayList<Position> crossData(ArrayList<Position> position1,
			ArrayList<Position> position2) {

		ArrayList<Position> crossData = new ArrayList<>();

		for (int i = 0; i < position1.size(); i++) {
			for (int j = 0; j < position2.size(); j++) {

				if (Math.abs(position1.get(i).getBegin()
						- position2.get(j).getEnd()) < 500) {

					boolean repeated = false;

					for (int k = 0; k < crossData.size(); k++) {
						if (Math.abs(crossData.get(k).getBegin()
								- position2.get(j).getEnd()) < 500) {
							repeated = true;
							break;
						}
					}
					if (!repeated) {
						crossData.add(new Position(position1.get(i).getBegin(),
								position2.get(j).getEnd()));
					}
				}
			}
		}

		return crossData;
	}

	/**
	 * Obtém os resultados e constroi um objeto com o retorno do banco.
	 * 
	 * @param cypherQuery
	 *            String com a consulta em Cypher.
	 * @param results
	 *            {@link StatementResult} com o resultado do banco.
	 * @return <b>String</b> com os dados extraídos.
	 * @throws InvalidQueryException
	 *             caso a query esteja incorreta.
	 */
	public static String statementToString(String cypherQuery,
			StatementResult results) throws InvalidQueryException {

		String parameters[] = cypherQuery.split("return")[1].split(",");

		String returned = "";

		while (results.hasNext()) {

			Record record = results.next();

			for (int i = 0; i < parameters.length; i++) {
				try {
					if (parameters[i].split("as ")[0].contains("trecho")) {
						returned += record.get(parameters[i].split("as ")[1])
								+ " ";
					}
				} catch (Exception e) {
					throw new InvalidQueryException();
				}
			}

			returned += "\n";
		}

		return returned;
	}

	// TODO esse método é o responsável por gerar os dados que alimenta a tabela
	// da interface gráfica. Deve ser corrigido para que possa entrar em
	// produção.
	/**
	 * Realiza uma busca das características das entidades passadas como
	 * argumento.
	 * 
	 * @param cypherQuery
	 *            String no formato Cypher.
	 * @param result
	 *            {@link StatementResult} com os resultados do banco.
	 * @return {@link CypherResults} contendo os resultados.
	 */
	public static ArrayList<CypherResults> statementToCypher(
			String cypherQuery, StatementResult result) {

		String parameters[] = cypherQuery.split("return")[1].split(",");

		ArrayList<CypherResults> returned = new ArrayList<>();
		CypherResults cyperResult = null;

		while (result.hasNext()) {
			
			Record record = result.next();
			String document = "", slice = "", citations = "", relations = "";
			for (int i = 0; i < parameters.length; i++) {
				if (parameters[i].split("as ")[0].contains("trecho")) {
					slice = record.get(parameters[i].split("as ")[1]).asString();
					
				} else if (parameters[i].split("as ")[0].contains("numeroCitacoes")) {
					citations = record.get(parameters[i].split("as ")[1]).toString();
					
				} else if (parameters[i].split("as ")[0].contains("numeroRelacoes")) {
					relations = record.get(parameters[i].split("as ")[1]).toString();
					
				} else if (parameters[i].split("as ")[0].contains("nome")) {
					document = record.get(parameters[i].split("as ")[1]).asString();
				
				}
				if (!slice.equals("") && !citations.equals("") && !relations.equals("") && !document.equals("")){
					cyperResult = new CypherResults(slice, citations, relations, document);
					returned.add(cyperResult);
					document = slice = citations = relations = "";
				}				
			}
		}
		return returned;
	}
}
