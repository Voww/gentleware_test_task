package ua.com.gentleware.zaietsv.gentleware_dao.relation_dao;

import ua.com.gentleware.zaietsv.gentleware_dao.instance_dao.AccountInstanceDAO;
import ua.com.gentleware.zaietsv.gentleware_dao.instance_dao.ClientInstanceDAO;
import ua.com.gentleware.zaietsv.gentleware_object.instance.Account;
import ua.com.gentleware.zaietsv.gentleware_object.instance.Client;
import ua.com.gentleware.zaietsv.gentleware_object.relation.ClientToAccountRelation;
import ua.com.gentleware.zaietsv.service.ClientService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientToAccountRelationDAO extends AbstractRelationDAO<ClientToAccountRelation>  implements ClientService {

	private ClientInstanceDAO clientDAO;
	private AccountInstanceDAO accountDAO;

	/**
	 * Constructs a data access gentleware_object for `client` table using connection parameter
	 * @param connection - an entity of Connection class
	 */
	public ClientToAccountRelationDAO(Connection connection) {
		super(connection);
		clientDAO = new ClientInstanceDAO(connection);
		accountDAO = new AccountInstanceDAO(connection);
	}

	@Override
	public int insert(ClientToAccountRelation relation) {
		int rows = -1;
		long baseId = relation.getBase().getId();
		Client client = clientDAO.read(baseId);
		if (client != null) {
			String sql = "INSERT INTO client_account (client_id, account_id) VALUES (?, ?)";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				List<Account> accounts = relation.getRelated();
				int counter = 0;
				for (Account account : accounts) {
					if (accountDAO.insert(account) > 0) {
						accountDAO.read(account);
						ps.setLong(1, baseId);
						ps.setLong(2, account.getId());
						counter += ps.executeUpdate();
					}
				}
				rows = counter;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rows;
	}

	@Override
	public ClientToAccountRelation read(long baseId) {
		ClientToAccountRelation relation = null;
		Client client = clientDAO.read(baseId);
		if (client != null) {
			relation = new ClientToAccountRelation();
			relation.setBase(client);
			List<Account> accounts = new ArrayList<>();

			String sql = "SELECT * FROM client_account WHERE client_id = ?";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setLong(1, baseId);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						long relatedId = rs.getLong("account_id");
						Account account = accountDAO.read(relatedId);
						if (account != null) {
							accounts.add(account);
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			relation.setRelated(accounts);
		}
		return relation;
	}

	@Override
	public int delete(ClientToAccountRelation relation) {
		int rows = -1;
		long baseId = relation.getBase().getId();
		Client client = clientDAO.read(baseId);
		if (client != null) {
			String sql = "DELETE FROM client_account WHERE client_id = ? AND account_id = ?";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				List<Account> accounts = relation.getRelated();
				int counter = 0;
				for (Account account : accounts) {
					long relatedId = account.getId();
					Account acc = accountDAO.read(relatedId);
					if (acc != null) {
						ps.setLong(1, baseId);
						ps.setLong(2, account.getId());
						ps.execute();
						counter ++;
					}
				}
				rows = counter;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rows;
	}

	@Override
	public double getClientBalance(Client client, List<Account> accounts) {
		double balance = 0.0;
		String sql = "SELECT SUM(amount) balance FROM client JOIN client_account ON client.id = client_account.client_id JOIN account ON account.id = client_account.account_id WHERE client_id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, client.getId());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					balance = rs.getDouble("balance");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balance;
	}

	/**
	 * Selects client with maximal balance
	 * @return client with maximal balance
	 */
	@Override
	public Client getClientWithMaxBalance(List<Account> accounts) {
		Client client = null;
		String sql = "SELECT * FROM (SELECT client.*, SUM(amount) balance FROM client JOIN client_account ON client.id = client_account.client_id JOIN account ON account.id = client_account.account_id GROUP BY client_id) t " +
				"WHERE t.balance = (SELECT MAX(balance) FROM(SELECT SUM(amount) balance FROM client_account JOIN account ON account.id = client_account.account_id GROUP BY client_id) b)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
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
	 * Selects clients with minimal balance
	 * @return clients with minimal balance
	 */
	public List<Client> getClientWithMinBalance() {
		List<Client> clients = null;
		String sql = "SELECT * FROM (SELECT client.*, SUM(amount) balance FROM client JOIN client_account ON client.id = client_account.client_id JOIN account ON account.id = client_account.account_id GROUP BY client_id) t " +
				"WHERE t.balance = (SELECT MIN(balance) FROM(SELECT SUM(amount) balance FROM client_account JOIN account ON account.id = client_account.account_id GROUP BY client_id) b)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				clients = new ArrayList<>();
				while (rs.next()) {
					Client client = new Client();
					client.setId(rs.getLong("id"));
					client.setEmail(rs.getString("email"));
					client.setFirstName(rs.getString("first_name"));
					client.setLastName(rs.getString("last_name"));
					clients.add(client);
				}
			} catch (SQLException e) {
			}
		} catch (SQLException e) {
		}
		return clients;
	}

	/**
	 * Selects last name of client with minimal balance
	 * @return last name of client with minimal balance
	 */
	public List<String> getClientWithMaxBalanceLastName() {
		List<String> names = null;
		String sql = "SELECT last_name FROM (SELECT last_name, SUM(amount) balance FROM client JOIN client_account ON client.id = client_account.client_id JOIN account ON account.id = client_account.account_id GROUP BY client_id) t " +
				"WHERE t.balance = (SELECT MAX(balance) FROM(SELECT SUM(amount) balance FROM client_account JOIN account ON account.id = client_account.account_id GROUP BY client_id) b)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				names = new ArrayList<>();
				while (rs.next()) {
					names.add(rs.getString("last_name"));
				}
			} catch (SQLException e) {
			}
		} catch (SQLException e) {
		}
		return names;
	}

	/**
	 * Selects last name of client with minimal balance
	 * @return last name of client with minimal balance
	 */
	public List<String> getClientWithMinBalanceLastName() {
		List<String> names = null;
		String sql = "SELECT last_name FROM (SELECT last_name, SUM(amount) balance FROM client JOIN client_account ON client.id = client_account.client_id JOIN account ON account.id = client_account.account_id GROUP BY client_id) t " +
				"WHERE t.balance = (SELECT MIN(balance) FROM(SELECT SUM(amount) balance FROM client_account JOIN account ON account.id = client_account.account_id GROUP BY client_id) b)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				names = new ArrayList<>();
				while (rs.next()) {
					names.add(rs.getString("last_name"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return names;
	}
}
