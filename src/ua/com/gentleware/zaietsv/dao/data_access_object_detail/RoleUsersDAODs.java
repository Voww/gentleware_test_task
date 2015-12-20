package ua.com.gentleware.zaietsv.dao.data_access_object_detail;

import zaietsv.complextask.mvc.dao.data_acces_object.RoleDAO;
import zaietsv.complextask.mvc.dao.data_acces_object.UserDAO;
import zaietsv.complextask.mvc.entity.instance.Role;
import zaietsv.complextask.mvc.entity.instance.User;
import zaietsv.complextask.mvc.entity.instance.Users;
import zaietsv.complextask.mvc.entity.instance_detail.RoleUsers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleUsersDAODs extends AbstractDAODs<RoleUsers> {

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
	public RoleUsersDAODs(Connection connection) {
		super(connection);
	}

	/* (non-Javadoc)
	 * @see zaietsv.complextask.mvc.dao.data_acces_object.DataAccessObject#insert(zaietsv.complextask.mvc.entity.data_acces_object.InstanceDetail)
	 */
	@Override
	public int insert(RoleUsers roleUsers) {

		RoleDAO rdao = new RoleDAO(connection);
		rdao.insert(roleUsers.getInstance());
		rdao.read(roleUsers.getInstance());

		UserDAO udao = new UserDAO(connection);

		int rows = 0;
		String sql = "INSERT INTO user_role (role_id, user_id)  VALUES (?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {

			for (User user : roleUsers.getDetails().getInstances()) {
				udao.insert(user);
				udao.read(user);

				ps.setLong(1, roleUsers.getInstance().getId());
				ps.setLong(2, user.getId());
				rows = ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows;
	}

	@Override
	public int insert(long role_id, Users users) {
		int rows = 0;
		if (role_id != 0 && users != null) {
			UserDAO udao = new UserDAO(connection);

			String sql = "INSERT INTO user_role (role_id, user_id)  VALUES (?, ?)";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				for (User user : users.getInstances()) {
					udao.insert(user);
					udao.read(user);
					ps.setLong(1, role_id);
					ps.setLong(2, user.getId());
					rows = ps.executeUpdate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rows;
	}

	@Override
	public RoleUsers read(long id) {
		String sql = "SELECT * FROM role r LEFT OUTER JOIN user_role ur ON r.id = ur.role_id LEFT OUTER JOIN `user` u ON ur.user_id = u.id HAVING  r.id = ? ";
		RoleUsers roleUsers = new RoleUsers();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Long aID = rs.getLong("r.id");
					if (aID != 0) {
						Role role = new Role();
						role.setId(rs.getLong("r.id"));
						role.setName(rs.getString("name"));
						roleUsers.setInstance(role);
					}
				}

				Users users = new Users();
				do {
					Long uID = rs.getLong("u.id");
					if ( uID != 0) {
						User user = new User();
						user.setId(rs.getLong("u.id"));
						user.setLogin(rs.getString("login"));
						user.setPassword(rs.getString("password"));
						user.setEmail(rs.getString("email"));
						user.setReg_date(rs.getDate("reg_date"));
						users.getInstances().add(user);
					}
				} while (rs.next());
				roleUsers.setDetails(users);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roleUsers;
	}

	@Override
	public long read(RoleUsers roleUsers) {
		String sql = "SELECT r.id, u.id, reg_date FROM role r LEFT OUTER JOIN user_role ur ON r.id = ur.role_id LEFT OUTER JOIN `user` u " +
				"ON ur.user_id = u.id HAVING `name` = ? AND login = ? AND  u.password = ? AND email =?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, roleUsers.getInstance().getName());

			for (User user : roleUsers.getDetails().getInstances()) {
				ps.setString(2, user.getLogin());
				ps.setString(3, user.getPassword());
				ps.setString(4, user.getEmail());
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						long aID = rs.getLong("r.id");
						if (aID != 0) {
							roleUsers.getInstance().setId(rs.getLong("r.id"));
						}
					}
					do {
						long uID = rs.getLong("u.id");
						if (uID != 0) {
							user.setId(rs.getLong("u.id"));
							user.setReg_date(rs.getDate("reg_date"));
						}
					} while (rs.next());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roleUsers.getInstance().getId();
	}

	@Override
	public int update(RoleUsers roleUsers) {
		//String sql = "UPDATE user_role SET user_id = ?, `city` = ?, `street` = ?, `house` = ?, `flat` = ?  WHERE `id` = ?";
		int rows = 0;
		if (roleUsers.getInstance() != null) {
			RoleDAO rdao = new RoleDAO(connection);
			rows += rdao.update(roleUsers.getInstance());
		}
		if (roleUsers.getDetails() != null) {
			UserDAO udao = new UserDAO(connection);
			for (User user : roleUsers.getDetails().getInstances()) {
				rows += udao.update(user);
			}
		}
		return rows;
	}

	@Override
	public boolean unlink(long role_id) {
		boolean res = false;
		if (role_id != 0) {
			String sql = "DELETE FROM `user_role` WHERE role_id = ?";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setLong(1, role_id);
				res = ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	@Override
	public boolean unlink(long role_id, long user_id) {
		boolean res = false;
		if (role_id != 0) {
			String sql = "DELETE FROM `user_role` WHERE role_id = ? AND user_id = ?";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setLong(1, role_id);
				ps.setLong(2, user_id);
				res = ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	@Override
	public boolean delete(long role_id, long user_id) {
		boolean res = false;

		if (role_id != 0 && user_id != 0) {
			unlink(role_id, user_id);
			UserDAO udao = new UserDAO(connection);
			res = udao.delete(user_id);
		} else if (role_id != 0) {
			unlink(role_id);
			RoleDAO rdao = new RoleDAO(connection);
			res = rdao.delete(role_id);
		}
		return res;
	}
}
