package ua.com.gentleware.zaietsv.connector;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * A database connection interface
 * Created by Voww on 18.12.2015.
 */
public interface Connector {

	/**
	 * Connects to a database and returns a database connection gentleware_object
	 * @return a database connection gentleware_object
	 * @throws SQLException - if a database access error occurs
	 */
	Connection getConnection() throws ConnectorException;

	/**
	 * Closes a connection to a database
	 * @return true on success false on fault
	 * @throws ConnectorException
	 */
	boolean closeConnection();

	/**
	 * Validates a database connection
	 * @return true on success false on fault
	 */
	boolean ping();
}
