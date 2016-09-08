package DAO;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

public class Neo4j {

	private Driver driver;

	private Session session;

	public Neo4j() {
		this.driver = GraphDatabase.driver("bolt://localhost", AuthTokens
				.basic(Autenticacao.USER.toString(),
						Autenticacao.PASSWORD.toString()));
		this.session = this.driver.session();
	}

	public Session getSession() {
		return this.session;
	}

	public void desconectar() {
		session.close();
		driver.close();
	}
}