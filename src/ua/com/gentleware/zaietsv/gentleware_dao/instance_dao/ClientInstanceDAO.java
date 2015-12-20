package ua.com.gentleware.zaietsv.gentleware_dao.instance_dao;

import ua.com.gentleware.zaietsv.gentleware_object.instance.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientInstanceDAO extends AbstractInstanceDAO<Client> {
	
	/**
	 * Constructs an empty data access gentleware_object for `client` table
	 *//*
	public ClientInstanceDAO() {
		super();
	}*/

	/**
	 * Constructs a data access gentleware_object for `client` table using connection parameter
	 * @param connection - an entity of Connection class
	 */
	public ClientInstanceDAO(Connection connection) {
		super(connection);
	}

	@Override
	public int insert(Client client) {

		String sql = "INSERT INTO client (email, first_name, last_name) VALUES (?, ?, ?)";
		int rows = -1;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, client.getEmail());
			ps.setString(2, client.getFirstName());
			ps.setString(3, client.getLastName());

			rows = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows;
	}

	@Override
	public Client read(long id) {
		String sql = "SELECT * FROM client WHERE id = ?";
		Client client = null;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					client = new Client();
					client.setId(rs.getLong("id"));
					client.setEmail(rs.getString("email"));
					client.setFirstName(rs.getString("first_name"));
					client.setLastName(rs.getString("last_name"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return client;
	}



	/**
	 * Reads entity's database id number searching the entity by its essential parameters.
	 * Sets derived fields values into the source entity
	 *
	 * @param client - an entity to be read
	 * @return entity's database id number
	 */
	@Override
	public long read(Client client) {
		String sql = "SELECT id FROM `client` WHERE `email` = ? AND `first_name` = ? AND `last_name` = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, client.getEmail());
			ps.setString(2, client.getFirstName());
			ps.setString(3, client.getLastName());

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					client.setId(rs.getLong("id"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return client.getId();
	}

	@Override
	public int update(Client client) {
		String sql = "UPDATE client SET `email` = ?, `first_name` = ?, `last_name` = ?  WHERE `id` = ?";
		int rows = -1;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, client.getEmail());
			ps.setString(2, client.getFirstName());
			ps.setString(3, client.getLastName());
			ps.setLong(4, client.getId());

			rows = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows;
	}

	@Override
	public boolean delete(long id) {
		String sql = "DELETE FROM `client` WHERE id = ?";
		boolean res = false;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			res = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean delete(Client client) {
		String sql = "DELETE FROM `client` WHERE email = ? AND first_name = ? AND last_name = ?";
		boolean res = false;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, client.getEmail());
			ps.setString(2, client.getFirstName());
			ps.setString(3, client.getLastName());

			ps.execute();
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Checks if exists a record having specified database identification number
	 *
	 * @param id - an client's ID number
	 * @return true if exists false otherwise
	 */
	@Override
	public boolean exists(long id) {
		boolean exists = false;
		String sql = "SELECT COUNT(*) `count` FROM `client` WHERE id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				rs.next();
				exists = rs.getInt("count") > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exists;
	}

	/**
	 * Checks if exists a database record having specified fields
	 * (excluding immutable fields)
	 *
	 * @param client - an client to be verified
	 * @return true if exists false otherwise
	 */
	@Override
	public boolean exists(Client client) {
		boolean exists = false;
		String sql = "SELECT COUNT(*) `count` FROM `client` WHERE `email` = ? AND `first_name` = ? AND `last_name` = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, client.getEmail());
			ps.setString(2, client.getFirstName());
			ps.setString(3, client.getLastName());
			try (ResultSet rs = ps.executeQuery()) {
				rs.next();
				exists = rs.getInt("count") > 0;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exists;
	}

	@Override
	public List<Client> readAll() {
		String sql = "SELECT * FROM client WHERE 1";
		List<Client> clients = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Client client = new Client();
					client.setId(rs.getLong("id"));
					client.setEmail(rs.getString("email"));
					client.setFirstName(rs.getString("first_name"));
					client.setLastName(rs.getString("last_name"));
					clients.add(client);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clients;
	}
}
