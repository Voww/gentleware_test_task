package ua.com.gentleware.zaietsv.gentleware_dao.instance_dao;

import ua.com.gentleware.zaietsv.gentleware_object.instance.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentInstanceDAO extends AbstractInstanceDAO<Payment> {

	/**
	 * Constructs an empty data access gentleware_object for `payment` table
	 *//*
	public PaymentInstanceDAO() {
		super();
	}*/

	/**
	 * Constructs a data access gentleware_object for `payment` table using connection parameter
	 * @param connection - an entity of Connection class
	 */
	public PaymentInstanceDAO(Connection connection) {
		super(connection);
	}

	@Override
	public int insert(Payment payment) {

		String sql = "INSERT INTO payment (amount, from_account_id, to_account_id, comment) VALUES (?, ?, ?, ?)";
		int rows = -1;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setDouble(1, payment.getAmount());
			ps.setLong(2, payment.getFromAccountID());
			ps.setLong(3, payment.getToAccountID());
			ps.setString(4, payment.getComment());

			rows = ps.executeUpdate();
		} catch (SQLException e) {
		}
		return rows;
	}

	@Override
	public Payment read(long id) {
		String sql = "SELECT * FROM payment WHERE id = ?";
		Payment payment = null;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					payment = new Payment();
					payment.setId(rs.getLong("id"));
					payment.setDate(rs.getDate("date"));
					payment.setAmount(rs.getDouble("amount"));
					payment.setFromAccountID(rs.getLong("from_account_id"));
					payment.setToAccountID(rs.getLong("to_account_id"));
					payment.setComment(rs.getString("comment"));
				}
			} catch (SQLException e) {
			}
		} catch (SQLException e) {
		}
		return payment;
	}

	/**
	 * Reads entity's database id number searching the entity by its essential parameters.
	 * Sets derived fields values into the source entity
	 *
	 * @param payment - an entity to be read
	 * @return entity's database id number
	 */
	@Override
	public long read(Payment payment) {
		String sql = "SELECT id, date FROM payment WHERE amount = ? AND from_account_id = ? AND to_account_id = ? AND comment = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setDouble(1, payment.getAmount());
			ps.setLong(2, payment.getFromAccountID());
			ps.setLong(2, payment.getToAccountID());
			ps.setString(4, payment.getComment());

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					payment.setId(rs.getLong("id"));
					payment.setDate(rs.getDate("date"));
				}
			} catch (SQLException e) {
			}
		} catch (SQLException e) {
		}
		return payment.getId();
	}

	@Override
	public int update(Payment payment) {
		String sql = "UPDATE payment SET amount = ?, from_account_id = ?, to_account_id = ?, comment = ?  WHERE id = ?";
		int rows = -1;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setDouble(1, payment.getAmount());
			ps.setLong(2, payment.getFromAccountID());
			ps.setLong(3, payment.getToAccountID());
			ps.setString(4, payment.getComment());
			ps.setLong(5, payment.getId());

			rows = ps.executeUpdate();
		} catch (SQLException e) {
		}
		return rows;
	}

	public int updateAmount(Payment payment) {
		String sql = "UPDATE payment SET amount = ? WHERE id = ?";
		int rows = -1;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setDouble(1, payment.getAmount());
			ps.setLong(2, payment.getId());

			rows = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows;
	}

	@Override
	public boolean delete(long id) {
		String sql = "DELETE FROM payment WHERE id = ?";
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
	public boolean delete(Payment payment) {
		String sql = "DELETE FROM payment WHERE amount = ? AND from_account_id = ? AND to_account_id = ? AND comment = ?";
		boolean res = false;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setDouble(1, payment.getAmount());
			ps.setLong(2, payment.getFromAccountID());
			ps.setLong(3, payment.getToAccountID());
			ps.setString(4, payment.getComment());

			ps.execute();
			res = true;
		} catch (SQLException e) {
		}
		return res;
	}

	/**
	 * Checks if exists a record having specified database identification number
	 *
	 * @param id - an payment's ID number
	 * @return true if exists false otherwise
	 */
	@Override
	public boolean exists(long id) {
		boolean exists = false;
		String sql = "SELECT COUNT(*) `count` FROM `payment` WHERE id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				rs.next();
				exists = rs.getInt("count") > 0;
			} catch (SQLException e) {
			}
		} catch (SQLException e) {
		}
		return exists;
	}

	/**
	 * Checks if exists a database record having specified fields
	 * (excluding immutable fields)
	 *
	 * @param payment - an payment to be verified
	 * @return true if exists false otherwise
	 */
	@Override
	public boolean exists(Payment payment) {
		boolean exists = false;
		String sql = "SELECT COUNT(*) `count` FROM `payment` WHERE amount = ? AND from_account_id = ? AND to_account_id = ? AND comment = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setDouble(1, payment.getAmount());
			ps.setLong(2, payment.getFromAccountID());
			ps.setLong(3, payment.getToAccountID());
			ps.setString(4, payment.getComment());
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
	public List<Payment> readAll() {
		String sql = "SELECT * FROM payment WHERE 1";
		List<Payment> payments = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Payment payment = new Payment();
					payment.setId(rs.getLong("id"));
					payment.setDate(rs.getDate("date"));
					payment.setAmount(rs.getLong("amount"));
					payment.setFromAccountID(rs.getLong("from_account_id"));
					payment.setToAccountID(rs.getLong("to_account_id"));
					payment.setComment(rs.getString("comment"));
					payments.add(payment);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return payments;
	}
}
