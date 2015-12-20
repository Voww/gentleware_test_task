package ua.com.gentleware.zaietsv.dao.data_access_object_detail;

import zaietsv.complextask.mvc.entity.instance_detail.AbstractInstanceDetails;

import java.sql.Connection;

public abstract class AbstractDAODs<I extends AbstractInstanceDetails> implements DataAccessObjectDetails<I> {

	protected Connection connection;

	/**
	 * Constructs an empty data access object
	 *//*
	public AbstractDAO() {

	}*/

	/**
	 * Constructs a data access object using connection parameter
	 * @param connection - an entity of Connection class
	 */
	public AbstractDAODs(Connection connection) {
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
