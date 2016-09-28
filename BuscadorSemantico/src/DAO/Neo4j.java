package DAO;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

import exception.DatabaseConnectionException;

/**
 * Responsável por realizar as conexções com o banco através do <i>bolt</i>.
 * 
 * @author Geovani Celebrim
 * 
 */
public class Neo4j {

	private Driver driver;

	private Session session;

	/**
	 * O construtor inicializa uma conexão com o banco, para que seja realizada
	 * uma consulta.
	 * 
	 * @throws DatabaseConnectionException
	 *             ocorre quando existe uma falha na conexão com o banco de
	 *             dados.
	 */
	public Neo4j() throws DatabaseConnectionException {
		this.driver = GraphDatabase.driver("bolt://localhost", AuthTokens
				.basic(Authentication.USER.toString(),
						Authentication.PASSWORD.toString()));
		try {
			this.session = this.driver.session();
		} catch (Exception e) {
			throw new DatabaseConnectionException();
		}

	}

	/**
	 * Obtém a instância da sessão atual do banco.
	 * 
	 * @return <b>Session.</b>
	 */
	public Session getSession() {
		return this.session;
	}

	/**
	 * Finaliza a conexção com o banco.
	 */
	public void disconnect() {
		session.close();
		driver.close();
	}
}