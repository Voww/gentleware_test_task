package ua.com.gentleware.zaietsv.connector;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Database connections abstract superclass
 * Created by Voww on 18.12.2015.
 */
public abstract class AbstractConnector implements Connector {

	private static boolean isDriverRegistered = false;

	private static Connection connection;
	private String url;
	private String user;
	private String password;

	static {
		try {
			DriverManager.registerDriver(new Driver());
			isDriverRegistered = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean isDriverRegistered() {
		return isDriverRegistered;
	}

	public AbstractConnector(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}

	public AbstractConnector(String propertyFileName) {

		ResourceBundle resourceBundle = ResourceBundle.getBundle(propertyFileName);
		this.url = resourceBundle.getString("url");
		this.user = resourceBundle.getString("user");
		this.password = resourceBundle.getString("password");
	}
	/**
	 * @return the connection
	 */
	@Override
	public Connection getConnection() throws ConnectorException {
		if (!isDriverRegistered()) {
			throw new ConnectorException("A database driver is not registered! getConnection() failed.");
		}

		if (connection == null) {
			try {
				Properties properties = new Properties();
				properties.put("user", user);
				properties.put("password", password);
				connection = DriverManager.getConnection(url, properties);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ConnectorException("Unable to connector to a database '" + url + "' under user name '" + user + "'! getConnection() failed.", e);
			}
		}
		return connection;
	}

	@Override
	public boolean ping() {
		boolean ping = false;
		try {
			if (connection != null) {
				ping = connection.isValid(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ping;
	}
	
	@Override
	public boolean closeConnection() {
		boolean success = false;
		try {
			connection.close();
			success = true;
			System.out.println("A database connection closed succesfully");
		} catch (SQLException e) {
			System.out.println("A database connection failed");
			e.printStackTrace();
		}

		return success;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
}
