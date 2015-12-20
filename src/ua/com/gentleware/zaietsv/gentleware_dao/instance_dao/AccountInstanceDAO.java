package ua.com.gentleware.zaietsv.gentleware_dao.instance_dao;

import ua.com.gentleware.zaietsv.gentleware_object.instance.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountInstanceDAO extends AbstractInstanceDAO<Account> {

	/**
	 * Constructs an empty data access gentleware_object for `account` table
	 *//*
	public AccountInstanceDAO() {
		super();
	}*/

	/**
	 * Constructs a data access gentleware_object for `account` table using connection parameter
	 * @param connection - an entity of Connection class
	 */
	public AccountInstanceDAO(Connection connection) {
		super(connection);
	}

	@Override
	public int insert(Account account) {

		String sql = "INSERT INTO account (code, alias, amount, description) VALUES (?, ?, ?, ?)";
		int rows = -1;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, account.getCode());
			ps.setString(2, account.getAlias());
			ps.setDouble(3, account.getAmount());
			ps.setString(4, account.getDescription());

			rows = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows;
	}

	@Override
	public Account read(long id) {
		String sql = "SELECT * FROM account WHERE id = ?";
		Account account = null;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					account = new Account();
					account.setId(rs.getLong("id"));
					account.setCode(rs.getString("code"));
					account.setAlias(rs.getString("alias"));
					account.setAmount(rs.getDouble("amount"));
					account.setDescription(rs.getString("description"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}

	/**
	 * Reads entity's database id number searching the entity by its essential parameters.
	 * Sets derived fields values into the source entity
	 *
	 * @param account - an entity to be read
	 * @return entity's database id number
	 */
	@Override
	public long read(Account account) {
		String sql = "SELECT id FROM `account` WHERE `code` = ? AND `alias` = ? AND `amount` = ? AND `description` = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, account.getCode());
			ps.setString(2, account.getAlias());
			ps.setDouble(3, account.getAmount());
			ps.setString(4, account.getDescription());

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					account.setId(rs.getLong("id"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account.getId();
	}

	@Override
	public int update(Account account) {
		String sql = "UPDATE account SET `code` = ?, `alias` = ?, `amount` = ?, `description` = ?  WHERE `id` = ?";
		int rows = -1;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, account.getCode());
			ps.setString(2, account.getAlias());
			ps.setDouble(3, account.getAmount());
			ps.setString(4, account.getDescription());
			ps.setLong(5, account.getId());

			rows = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows;
	}

	public int updateAmount(Account account) {
		String sql = "UPDATE account SET amount = ? WHERE id = ?";
		int rows = -1;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setDouble(1, account.getAmount());
			ps.setLong(2, account.getId());

			rows = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows;
	}

	@Override
	public boolean delete(long id) {
		String sql = "DELETE FROM `account` WHERE id = ?";
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
	public boolean delete(Account account) {
		String sql = "DELETE FROM `account` WHERE `code` = ? AND `alias` = ? AND `amount` = ? AND `description` = ?";
		boolean res = false;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, account.getCode());
			ps.setString(2, account.getAlias());
			ps.setDouble(3, account.getAmount());
			ps.setString(4, account.getDescription());

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
	 * @param id - an account's ID number
	 * @return true if exists false otherwise
	 */
	@Override
	public boolean exists(long id) {
		boolean exists = false;
		String sql = "SELECT COUNT(*) `count` FROM `account` WHERE id = ?";
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
	 * @param account - an account to be verified
	 * @return true if exists false otherwise
	 */
	@Override
	public boolean exists(Account account) {
		boolean exists = false;
		String sql = "SELECT COUNT(*) `count` FROM `account` WHERE `code` = ? AND `alias` = ? AND `amount` = ? AND `description` = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, account.getCode());
			ps.setString(2, account.getAlias());
			ps.setDouble(3, account.getAmount());
			ps.setString(4, account.getDescription());
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
	public List<Account> readAll() {
		String sql = "SELECT * FROM account WHERE 1";
		List<Account> accounts = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Account account = new Account();
					account.setId(rs.getLong("id"));
					account.setCode(rs.getString("code"));
					account.setAlias(rs.getString("alias"));
					account.setAmount(rs.getLong("amount"));
					account.setDescription(rs.getString("description"));
					accounts.add(account);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}

	public List<Account> readClientAccounts(long clientID) {
		String sql = "SELECT * FROM client_account JOIN  account  ON account.id = account_id WHERE client_id = ?";
		List<Account> accounts = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, clientID);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Account account = new Account();
					account.setId(rs.getLong("id"));
					account.setCode(rs.getString("code"));
					account.setAlias(rs.getString("alias"));
					account.setAmount(rs.getLong("amount"));
					account.setDescription(rs.getString("description"));
					accounts.add(account);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accounts;
	}
}
