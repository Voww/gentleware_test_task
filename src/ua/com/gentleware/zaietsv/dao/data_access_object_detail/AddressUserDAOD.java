package ua.com.gentleware.zaietsv.dao.data_access_object_detail;

import zaietsv.complextask.mvc.dao.data_acces_object.AddressDAO;
import zaietsv.complextask.mvc.dao.data_acces_object.UserDAO;
import zaietsv.complextask.mvc.entity.instance.Address;
import zaietsv.complextask.mvc.entity.instance.User;
import zaietsv.complextask.mvc.entity.instance_detail.AddressUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressUserDAOD extends AbstractDAOD<AddressUser> {

	/**
	 * Constructs an empty data access object for `address` table
	 *//*
	public AddressDAO() {
		super();
	}*/

	/**
	 * Constructs a data access object for `address` table using connection parameter
	 * @param connection - an entity of Connection class
	 */
	public AddressUserDAOD(Connection connection) {
		super(connection);
	}

	/* (non-Javadoc)
	 * @see zaietsv.complextask.mvc.dao.data_acces_object.DataAccessObject#insert(zaietsv.complextask.mvc.entity.data_acces_object.InstanceDetail)
	 */
	@Override
	public int insert(AddressUser addressUser) {

		AddressDAO adao = new AddressDAO(connection);
		adao.insert(addressUser.getInstance());
		adao.read(addressUser.getInstance());

		UserDAO udao = new UserDAO(connection);
		udao.insert(addressUser.getDetail());
		udao.read(addressUser.getDetail());

		int rows = 0;
		String sql = "INSERT INTO user_address (address_id, user_id)  VALUES (?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setLong(1, addressUser.getInstance().getId());
			ps.setLong(2, addressUser.getDetail().getId());
			rows = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows;
	}

	@Override
	public int insert(long address_id, User user) {
		int rows = 0;
		if (address_id != 0 && user != null) {
			UserDAO udao = new UserDAO(connection);
			udao.insert(user);
			udao.read(user);

			String sql = "INSERT INTO user_address (address_id, user_id)  VALUES (?, ?)";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setLong(1, address_id);
				ps.setLong(2, user.getId());
				rows = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rows;
	}

	@Override
	public AddressUser read(long id) {
		String sql = "SELECT * FROM address a LEFT OUTER JOIN user_address ua ON a.id = ua.address_id LEFT OUTER JOIN `user` u ON ua.user_id = u.id HAVING  a.id = ? ";
		AddressUser addressUser = new AddressUser();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Long aID = rs.getLong("a.id");
					if (aID != 0) {
						Address address = new Address();
						address.setId(rs.getLong("a.id"));
						address.setPostcode(rs.getInt("postcode"));
						address.setCity(rs.getString("city"));
						address.setStreet(rs.getString("street"));
						address.setHouse(rs.getInt("house"));
						address.setFlat(rs.getInt("flat"));
						addressUser.setInstance(address);
					}

					Long uID = rs.getLong("u.id");
/*					String login = rs.getString("login");
					String password = rs.getString("password");
					String email = rs.getString("email");
					Date reg_date = rs.getDate("reg_date");
*/
					if ( uID != 0) {
						User user = new User();
						user.setId(rs.getLong("u.id"));
						user.setLogin(rs.getString("login"));
						user.setPassword(rs.getString("password"));
						user.setEmail(rs.getString("email"));
						user.setReg_date(rs.getDate("reg_date"));
						addressUser.setDetail(user);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressUser;
	}

	@Override
	public long read(AddressUser addressUser) {
		String sql = "SELECT a.id, u.id, reg_date FROM address a LEFT OUTER JOIN user_address ua ON a.id = ua.address_id LEFT OUTER JOIN `user` u " +
				"ON ua.user_id = u.id HAVING postcode = ? AND city = ? AND street = ? AND house = ? AND flat = ? " +
				"AND login = ? AND  u.password = ? AND email =?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, addressUser.getInstance().getPostcode());
			ps.setString(2, addressUser.getInstance().getCity());
			ps.setString(3, addressUser.getInstance().getStreet());
			ps.setInt(4, addressUser.getInstance().getHouse());
			ps.setInt(5, addressUser.getInstance().getFlat());
			ps.setString(6, addressUser.getDetail().getLogin());
			ps.setString(7, addressUser.getDetail().getPassword());
			ps.setString(8, addressUser.getDetail().getEmail());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					long aID = rs.getLong("a.id");
					if (aID != 0) {
						addressUser.getInstance().setId(rs.getLong("a.id"));
					}
					long uID = rs.getLong("u.id");
					if ( uID != 0) {
						addressUser.getDetail().setId(rs.getLong("u.id"));
						addressUser.getDetail().setReg_date(rs.getDate("reg_date"));
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressUser.getInstance().getId();
	}
	

	/* (non-Javadoc)
	 * @see zaietsv.complextask.mvc.dao.data_acces_object.DataAccessObject#update(zaietsv.complextask.mvc.entity.data_acces_object.InstanceDetail)
	 */
	@Override
	public int update(AddressUser addressUser) {
		//String sql = "UPDATE user_address SET user_id = ?, `city` = ?, `street` = ?, `house` = ?, `flat` = ?  WHERE `id` = ?";
		int rows = 0;
		if (addressUser.getInstance() != null) {
			AddressDAO adao = new AddressDAO(connection);
			rows += adao.update(addressUser.getInstance());
		}
		if (addressUser.getDetail() != null) {
			UserDAO udao = new UserDAO(connection);
			rows += udao.update(addressUser.getDetail());
		}
		return rows;
	}

	@Override
	public boolean unlink(long address_id) {
		boolean res = false;
		if (address_id != 0) {
			String sql = "DELETE FROM `user_address` WHERE address_id = ?";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setLong(1, address_id);
				res = ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	@Override
	public boolean unlink(long address_id, long user_id) {
		boolean res = false;
		if (address_id != 0) {
			String sql = "DELETE FROM `user_address` WHERE address_id = ? AND user_id = ?";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setLong(1, address_id);
				ps.setLong(2, user_id);
				res = ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	@Override
	public boolean delete(long address_id, long user_id) {
		boolean res = false;

		if (address_id != 0 && user_id == 0) {
			unlink(address_id);
			AddressDAO adao = new AddressDAO(connection);
			res = adao.delete(address_id);
		} else if (address_id != 0) {
			unlink(address_id);
			UserDAO udao = new UserDAO(connection);
			res = udao.delete(user_id);
		}
		return res;
	}
}
