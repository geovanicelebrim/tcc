package util;

import java.util.ArrayList;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import entidade.Posicao;
import entidade.resultados.ResultadoCypher;
import excessao.QueryInvalidaException;

public class TratamentoDeDados {
	//TODO Pensar em uma maneira de poder cruzar mais dados
	public static ArrayList<Posicao> cruzarDados(ArrayList<Posicao> posicao1,
			ArrayList<Posicao> posicao2) {

		ArrayList<Posicao> dadosCruzados = new ArrayList<>();

		for (int i = 0; i < posicao1.size(); i++) {
			for (int j = 0; j < posicao2.size(); j++) {

				if (Math.abs(posicao1.get(i).getInicio()
						- posicao2.get(j).getFim()) < 500) {

					boolean repetido = false;

					for (int k = 0; k < dadosCruzados.size(); k++) {
						if (Math.abs(dadosCruzados.get(k).getInicio()
								- posicao2.get(j).getFim()) < 500) {
							repetido = true;
							break;
						}
					}
					if (!repetido) {
						dadosCruzados.add(new Posicao(posicao1.get(i)
								.getInicio(), posicao2.get(j).getFim()));
					}
				}
			}
		}

		return dadosCruzados;
	}

	public static String statementToString(String cypherQuery,
			StatementResult resultado) throws QueryInvalidaException {

		String parametros[] = cypherQuery.split("return")[1].split(",");

		String retorno = "";

		while (resultado.hasNext()) {

			Record record = resultado.next();

			for (int i = 0; i < parametros.length; i++) {
				try {
					if (parametros[i].split("as ")[0].contains("trecho")) {
						retorno += record.get(parametros[i].split("as ")[1])
								+ " ";
					}
				} catch (Exception e) {
					throw new QueryInvalidaException();
				}
			}

			retorno += "\n";
		}

		return retorno;
	}
	
	public static ArrayList<ResultadoCypher> statementToCypher(String cypherQuery,
			StatementResult resultado) throws QueryInvalidaException {

		String parametros[] = cypherQuery.split("return")[1].split(",");

		ArrayList<ResultadoCypher> retorno = new ArrayList<>();

		while (resultado.hasNext()) {
			
			String entidade1 = null, 
			entidade2 = null,
			citacoes_entidade1 = null,
			citacoes_entidade2 = null,
			relacoes_entidade1 = null,
			relacoes_entidade2 = null,
			nome_documento = null;
			
			Record record = resultado.next();

			for (int i = 0; i < parametros.length; i++) {
				try {
						
					if (parametros[i].split("as ")[0].contains("trecho")) {
						
						if(entidade1 == null){
							entidade1 = record.get(parametros[i].split("as ")[1])
									+ " ";
						} else {
							entidade2 = record.get(parametros[i].split("as ")[1])
									+ " ";
						}
						
					} else if (parametros[i].split("as ")[0].contains("numeroCitacoes")) {

						if(citacoes_entidade1 == null){
							citacoes_entidade1 = record.get(parametros[i].split("as ")[1])
									+ " ";
						} else {
							citacoes_entidade2 = record.get(parametros[i].split("as ")[1])
									+ " ";
						}
						
					} else if (parametros[i].split("as ")[0].contains("numeroRelacoes")) {

						if(relacoes_entidade1 == null){
							relacoes_entidade1 = record.get(parametros[i].split("as ")[1])
									+ " ";
						} else {
							relacoes_entidade2 = record.get(parametros[i].split("as ")[1])
									+ " ";
						}
						
					} else if (parametros[i].split("as ")[0].contains("nome")) {

							nome_documento = record.get(parametros[i].split("as ")[1])
									+ " ";
					}
					
					
				} catch (Exception e) {
					throw new QueryInvalidaException();
				}
			}

			retorno.add(new ResultadoCypher(entidade1, entidade2, citacoes_entidade1, citacoes_entidade2, relacoes_entidade1, relacoes_entidade2, nome_documento));
		}
		
		
		return retorno;
	}

}
