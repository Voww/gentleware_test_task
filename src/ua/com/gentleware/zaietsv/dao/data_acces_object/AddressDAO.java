package ua.com.gentleware.zaietsv.dao.data_acces_object;

import zaietsv.complextask.mvc.entity.instance.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddressDAO extends AbstractDAO<Address> {
	
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
	public AddressDAO(Connection connection) {
		super(connection);
	}

	@Override
	public int insert(Address address) {
		int rows;
		String sql = "INSERT INTO address (postcode, city, street, house, flat) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, address.getPostcode());
			ps.setString(2, address.getCity());
			ps.setString(3, address.getStreet());
			ps.setInt(4, address.getHouse());
			ps.setInt(5, address.getFlat());

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
	public Address read(long id) {
		String sql = "SELECT * FROM address WHERE id = ?";
		Address address = new Address();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {				
					address.setId(rs.getLong("id"));
					address.setPostcode(rs.getInt("postcode"));
					address.setCity(rs.getString("city"));
					address.setStreet(rs.getString("street"));
					address.setHouse(rs.getInt("house"));
					address.setFlat(rs.getInt("flat"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return address;
	}

	/**
	 * Reads entity's database id number searching the entity by its essential fields parameters.
	 * Sets id and other derived fields values into the source entity.
	 * @param instance - an entity to be read
	 * @return entity's database id number
	 */
	@Override
	public long read(Address instance) {
		String sql = "SELECT id FROM address WHERE postcode = ? AND city = ? AND street = ? AND house = ? AND flat = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, instance.getPostcode());
			ps.setString(2, instance.getCity());
			ps.setString(3, instance.getStreet());
			ps.setInt(4, instance.getHouse());
			ps.setInt(5, instance.getFlat());
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
	public int update(Address address) {
		String sql = "UPDATE address SET `postcode` = ?, `city` = ?, `street` = ?, `house` = ?, `flat` = ?  WHERE `id` = ?";
		int rows;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, address.getPostcode());
			ps.setString(2, address.getCity());
			ps.setString(3, address.getStreet());
			ps.setInt(4, address.getHouse());
			ps.setInt(5, address.getFlat());
			ps.setLong(6, address.getId());
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
		String sql = " DELETE FROM `address` WHERE id = ? ";
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
		String sql = "SELECT COUNT(*) `count` FROM `address` WHERE id = ?";
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
	 * Checks if exists a database record having specified field (excluding immutable fields)
	 *
	 * @param instance - an instance to be verified
	 * @return true if exists false otherwise
	 */
	@Override
	public boolean exists(Address instance) {
		boolean exists = false;
		String sql = "SELECT COUNT(*) `count` FROM `address` WHERE postcode = ? AND city = ? AND street = ? AND house = ? AND flat = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, instance.getPostcode());
			ps.setString(2, instance.getCity());
			ps.setString(3, instance.getStreet());
			ps.setInt(4, instance.getHouse());
			ps.setInt(5, instance.getFlat());
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
	public ArrayList<Address> readAll() {
		String sql = "SELECT * FROM address WHERE 1";
		ArrayList<Address> addresses = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Address address = new Address();
					address.setId(rs.getLong("id"));
					address.setPostcode(rs.getInt("postcode"));
					address.setCity(rs.getString("city"));
					address.setStreet(rs.getString("street"));
					address.setHouse(rs.getInt("house"));
					address.setFlat(rs.getInt("flat"));
					addresses.add(address);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addresses;
	}
}
