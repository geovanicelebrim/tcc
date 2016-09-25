package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

		String tokens[] = cypherQuery.split("return")[1].split(", ");
		String queryDocument = "";
		
		List<String> queryLabel, querySlice, queryCitations, queryRelations;
		queryLabel = new ArrayList<>();
		querySlice = new ArrayList<>();
		queryCitations = new ArrayList<>();
		queryRelations = new ArrayList<>();
		
		for (int i = 0; i < tokens.length; i++) {
			if (tokens[i].contains("nome")) {
				queryDocument = tokens[i].split("as ")[1];
			} else if (tokens[i].contains("node") && !tokens[i].contains("posicao")) {
				if (tokens[i].contains("labels")) {
					queryLabel.add(tokens[i].split("as ")[1]);
				} else if (tokens[i].contains("trecho")) {
					querySlice.add(tokens[i].split("as ")[1]);
				} else if (tokens[i].contains("numeroCitacoes")) {
					queryCitations.add(tokens[i].split("as ")[1]);
				} else if (tokens[i].contains("numeroRelacoes")) {
					queryRelations.add(tokens[i].split("as ")[1]);
				}
			}
		}
		
		ArrayList<CypherResults> returned = new ArrayList<>();
		HashMap<String, CypherResults> mapResult = new HashMap<>();
		CypherResults cypherResult = null;

		while (result.hasNext()) {
			
			Record record = result.next();
			String document = "", label = "", slice = "", citations = "", relations = "";
			
			for (int i = 0; i < querySlice.size(); i++) {
				
				label = record.get(queryLabel.get(i)).asList().get(0).toString();
				slice = record.get(querySlice.get(i)).asString();
				citations = record.get(queryCitations.get(i)).toString();
				relations = record.get(queryRelations.get(i)).toString();
				document = record.get(queryDocument).asString();
				
				cypherResult = new CypherResults(label, slice, citations, relations, document);
				mapResult.put(slice+document, cypherResult);
			}
		}
		
		Set<String> keys = mapResult.keySet();
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();)
		{
			String key = iterator.next();
			if(key != null)
				returned.add(mapResult.get(key));
		}
		
		return returned;
	}
}
