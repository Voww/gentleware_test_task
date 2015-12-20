package ua.com.gentleware.zaietsv.gentleware_dao.relation_dao;

import ua.com.gentleware.zaietsv.gentleware_object.relation.Relation;

import java.sql.Connection;

/**
 * An abstract superclass for RelationDAO instances
 * Created by Voww on 18.12.2015.
 */
public abstract class AbstractRelationDAO<R extends Relation> implements RelationDAO<R> {

	protected Connection connection;

	/**
	 * Constructs a data access gentleware_object using connection parameter
	 * @param connection - an entity of Connection class
	 */
	public AbstractRelationDAO(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Returns a connection
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Sets a connection
	 * @param connection the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
