package ua.com.gentleware.zaietsv.dao.data_acces_object;

import zaietsv.complextask.mvc.entity.instance.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoleDAO extends AbstractDAO<Role> {

	/**
	 * Constructs an empty data access object for `role` table
	 *//*
	public RoleDAO() {
		super();
	}*/

	/**
	 * Constructs a data access object for `role` table using connection parameter
	 * @param connection - an entity of Connection class
	 */
	public RoleDAO(Connection connection) {
		super(connection);
	}

	@Override
	public int insert(Role role) {
		int rows;
		String sql = "INSERT INTO role (name) VALUE (?)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, role.getName());

			rows = ps.executeUpdate();
		} catch (SQLException ignored) {
			rows = -1;
		}
		return rows;
	}

	/* (non-Javadoc)
	 * @see zaietsv.complextask.mvc.dao.data_acces_object.DataAccessObject#read(long)
	 */
	@Override
	public Role read(long id) {
		String sql = "SELECT * FROM role WHERE id = ?";
		Role role = new Role();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					role.setId(rs.getLong("id"));
					role.setName(rs.getString("name"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return role;
	}

	@Override
	public long read(Role instance) {
		String sql = "SELECT id FROM role WHERE name = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, instance.getName());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					instance.setId(rs.getLong("id"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return instance.getId();
	}
	
	/* (non-Javadoc)
	 * @see zaietsv.complextask.mvc.dao.data_acces_object.DataAccessObject#update(zaietsv.complextask.mvc.entity.data_acces_object.InstanceDetail)
	 */
	@Override
	public int update(Role role) {
		String sql = "UPDATE role SET `name` = ?  WHERE `id` = ?";
		int rows;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, role.getName());
			ps.setLong(2, role.getId());

			rows = ps.executeUpdate();
		} catch (SQLException ignored) {
			rows = -1;
		}
		return rows;
	}

	/* (non-Javadoc)
	 * @see zaietsv.complextask.mvc.dao.data_acces_object.DataAccessObject#delete(long)
	 */
	@Override
	public boolean delete(long id) {
		String sql = " DELETE FROM `role` WHERE id = ? ";
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
		String sql = "SELECT COUNT(*) `count` FROM `role` WHERE id = ?";
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
	public boolean exists(Role instance) {
		boolean exists = false;
		String sql = "SELECT COUNT(*) `count` FROM `role` WHERE name = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, instance.getName());
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
	public ArrayList<Role> readAll() {
		String sql = "SELECT * FROM role WHERE 1";
		ArrayList<Role> roles = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Role role = new Role();
					role.setId(rs.getLong("id"));
					role.setName(rs.getString("name"));
					roles.add(role);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roles;
	}
}
