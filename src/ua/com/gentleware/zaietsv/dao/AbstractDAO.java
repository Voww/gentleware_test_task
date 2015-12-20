package ua.com.gentleware.zaietsv.dao;

import zaietsv.complextask.mvc.entity.Entity;

import java.sql.Connection;

public abstract class AbstractDAO<I extends Entity> implements EntityDAO<I> {

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
	public AbstractDAO(Connection connection) {
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
