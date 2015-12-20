package ua.com.gentleware.zaietsv.gentleware_dao.relation_dao;

import ua.com.gentleware.zaietsv.gentleware_dao.instance_dao.AccountInstanceDAO;
import ua.com.gentleware.zaietsv.gentleware_object.instance.Account;
import ua.com.gentleware.zaietsv.gentleware_object.relation.PaymentRelation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentRelationDAO extends AbstractRelationDAO<PaymentRelation> {

	private AccountInstanceDAO accountDAO;

	public PaymentRelationDAO(Connection connection) {
		super(connection);
		accountDAO = new AccountInstanceDAO(connection);
	}

	@Override
	public int insert(PaymentRelation relation) {
		int rows = -1;
		long baseId = relation.getBase().getId();
		Account accountBase = relation.getBase();
		if (accountDAO.updateAmount(accountBase) > 0) {
			String sql = "INSERT INTO payment (amount, from_account_id, to_account_id, comment) VALUES (?, ?, ?, ?)";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				List<Account> accounts = relation.getRelated();
				int counter = 0;
				for (Account accountRelated : accounts) {
					long relatedId = accountRelated.getId();
					if (accountDAO.updateAmount(accountRelated) > 0) {
						ps.setDouble(1, relation.getAmount());
						ps.setLong(2, baseId);
						ps.setLong(3, relatedId);
						ps.setString(4, relation.getComment());
						counter += ps.executeUpdate();
					}
				}
				read(relation);
				rows = counter;
			} catch (SQLException e) {
			}
		}
		return rows;
	}

	@Override
	public PaymentRelation read(long paymentId) {
		PaymentRelation relation = null;
			String sql = "SELECT * FROM payment WHERE id = ?";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setLong(1, paymentId);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						long baseId = rs.getLong("from_account_id");
						Account accountBase = accountDAO.read(baseId);
					if (accountBase != null) {
						relation = new PaymentRelation();
						relation.setBase(accountBase);
						relation.setId(rs.getLong("id"));
						relation.setDate(rs.getDate("date"));
						relation.setAmount(rs.getDouble("amount"));
						relation.setComment(rs.getString("comment"));

						List<Account> accounts = new ArrayList<>();
						long relatedId = rs.getLong("to_account_id");
						Account accountRelated = accountDAO.read(relatedId);
						if (accountRelated != null) {
							accounts.add(accountRelated);
						}
						relation.setRelated(accounts);
					}
				}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return relation;
	}

	public long read(PaymentRelation payment) {
		String sql = "SELECT id, date FROM payment WHERE amount = ? AND from_account_id = ? AND to_account_id = ? AND comment = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setDouble(1, payment.getAmount());
			ps.setLong(2, payment.getBase().getId());
			ps.setLong(3, payment.getRelated().get(0).getId());
			ps.setString(4, payment.getComment());

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					payment.setId(rs.getLong("id"));
					payment.setDate(rs.getDate("date"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return payment.getId();
	}

	@Override
	public int delete(PaymentRelation relation) {
		int rows = -1;
		long baseId = relation.getBase().getId();
		Account accountBase = accountDAO.read(baseId);
		if (accountBase != null) {
			String sql = "DELETE FROM payment WHERE from_account_id = ? AND to_account_id = ?";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				List<Account> accounts = relation.getRelated();
				int counter = 0;
				for (Account accountRelated : accounts) {
					long relatedId = accountRelated.getId();
					Account acc = accountDAO.read(relatedId);
					if (acc != null) {
						ps.setLong(1, baseId);
						ps.setLong(2, relatedId);
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
}
