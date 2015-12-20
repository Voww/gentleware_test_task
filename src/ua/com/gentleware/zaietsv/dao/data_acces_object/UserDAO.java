package ua.com.gentleware.zaietsv.dao.data_acces_object;

import zaietsv.complextask.mvc.entity.instance.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO extends AbstractDAO<User> {
	
	/**
	 * Constructs an empty data access object for `user` table
	 *//*
	public UserDAO() {
		super();
	}*/

	/**
	 * Constructs a data access object for `user` table using connection parameter
	 * @param connection - an entity of Connection class
	 */
	public UserDAO(Connection connection) {
		super(connection);
	}

	@Override
	public int insert(User user) {
		int rows = 0;
		String sql = "INSERT INTO user (login, password, email, reg_date) VALUES (?, ?, ?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.setDate(4, user.getReg_date());

			rows = ps.executeUpdate();
		} catch (SQLException ignored) {
			rows = -1;
		}
		return rows;
	}

	@Override
	public User read(long id) {
		String sql = "SELECT * FROM user WHERE id = ?";
		User user = new User();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					user.setId(rs.getLong("id"));
					user.setLogin(rs.getString("login"));
					user.setPassword(rs.getString("password"));
					user.setEmail(rs.getString("email"));
					user.setReg_date(rs.getDate("reg_date"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}



	/**
	 * Reads entity's database id number searching the entity by its essential parameters.
	 * Sets derived fields values into the source entity
	 *
	 * @param instance - an entity to be read
	 * @return entity's database id number
	 */
	@Override
	public long read(User instance) {
		String sql = "SELECT id, reg_date FROM `user` WHERE `login` = ? AND `password` = ? AND `email` = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, instance.getLogin());
			ps.setString(2, instance.getPassword());
			ps.setString(3, instance.getEmail());

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					instance.setId(rs.getLong("id"));
					instance.setReg_date(rs.getDate("reg_date"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return instance.getId();
	}

	public long readByLoginAndPassword(User instance) {
		String sql = "SELECT id, email, reg_date FROM `user` WHERE `login` = ? AND `password` = ?";
		Long user_id = 0L;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, instance.getLogin());
			ps.setString(2, instance.getPassword());

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					user_id = rs.getLong("id");
					instance.setId(rs.getLong("id"));
					instance.setEmail(rs.getString("email"));
					instance.setReg_date(rs.getDate("reg_date"));
				}
			} catch (SQLException e) {
				user_id = -1L;
			}
		} catch (SQLException e) {
			user_id = -1L;
		}
		return user_id;
	}

	@Override
	public int update(User user) {
		String sql = "UPDATE user SET `login` = ?, `password` = ?, `email` = ?  WHERE `id` = ?";
		int rows = -1;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, user.getLogin());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.setLong(4, user.getId());

			rows = ps.executeUpdate();
		} catch (SQLException ignored) {
		}
		return rows;
	}

	@Override
	public boolean delete(long id) {
		String sql = " DELETE FROM `user` WHERE id = ? ";
		boolean res = false;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			res = ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Checks if exists a record having specified database identification number
	 *
	 * @param id - an instance's ID number
	 * @return true if exists false otherwise
	 */
	@Override
	public boolean exists(long id) {
		boolean exists = false;
		String sql = "SELECT COUNT(*) `count` FROM `user` WHERE id = ?";
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
	 * @param instance - an instance to be verified
	 * @return true if exists false otherwise
	 */
	@Override
	public boolean exists(User instance) {
		boolean exists = false;
		String sql = "SELECT COUNT(*) `count` FROM `user` WHERE `login` = ? AND `password` = ? AND `email` = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, instance.getLogin());
			ps.setString(2, instance.getPassword());
			ps.setString(3, instance.getEmail());
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

	/* (non-Javadoc)
	 * @see zaietsv.complextask.mvc.dao.data_acces_object.DataAccessObject#readAll()
	 */
	@Override
	public ArrayList<User> readAll() {
		String sql = "SELECT * FROM user WHERE 1";
		ArrayList<User> users = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					User user = new User();
					user.setId(rs.getLong("id"));
					user.setLogin(rs.getString("login"));
					user.setPassword(rs.getString("password"));
					user.setEmail(rs.getString("email"));
					user.setReg_date(rs.getDate("reg_date"));
					users.add(user);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
}
