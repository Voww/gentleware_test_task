package ua.com.gentleware.zaietsv.dao.data_access_object_detail;

import zaietsv.complextask.mvc.dao.data_acces_object.MusicDAO;
import zaietsv.complextask.mvc.dao.data_acces_object.UserDAO;
import zaietsv.complextask.mvc.entity.instance.Music;
import zaietsv.complextask.mvc.entity.instance.User;
import zaietsv.complextask.mvc.entity.instance.Users;
import zaietsv.complextask.mvc.entity.instance_detail.MusicUsers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MusicUsersDAODs extends AbstractDAODs<MusicUsers> {

	/**
	 * Constructs an empty data access object for `music` table
	 *//*
	public MusicDAO() {
		super();
	}*/

	/**
	 * Constructs a data access object for `music` table using connection parameter
	 * @param connection - an entity of Connection class
	 */
	public MusicUsersDAODs(Connection connection) {
		super(connection);
	}

	/* (non-Javadoc)
	 * @see zaietsv.complextask.mvc.dao.data_acces_object.DataAccessObject#insert(zaietsv.complextask.mvc.entity.data_acces_object.InstanceDetail)
	 */
	@Override
	public int insert(MusicUsers musicUsers) {

		MusicDAO mdao = new MusicDAO(connection);
		mdao.insert(musicUsers.getInstance());
		mdao.read(musicUsers.getInstance());

		UserDAO udao = new UserDAO(connection);

		int rows = 0;
		String sql = "INSERT INTO user_music (music_id, user_id)  VALUES (?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {

			for (User user : musicUsers.getDetails().getInstances()) {
				udao.insert(user);
				udao.read(user);

				ps.setLong(1, musicUsers.getInstance().getId());
				ps.setLong(2, user.getId());
				rows = ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows;
	}

	@Override
	public int insert(long music_id, Users users) {
		int rows = 0;
		if (music_id != 0 && users != null) {
			UserDAO udao = new UserDAO(connection);

			String sql = "INSERT INTO user_music (music_id, user_id)  VALUES (?, ?)";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				for (User user : users.getInstances()) {
					udao.insert(user);
					udao.read(user);
					ps.setLong(1, music_id);
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
	public MusicUsers read(long id) {
		String sql = "SELECT * FROM music m LEFT OUTER JOIN user_music um ON m.id = um.music_id LEFT OUTER JOIN `user` u ON um.user_id = u.id HAVING  m.id = ? ";
		MusicUsers musicUsers = new MusicUsers();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					Long aID = rs.getLong("m.id");
					if (aID != 0) {
						Music music = new Music();
						music.setId(rs.getLong("m.id"));
						music.setName(rs.getString("name"));
						music.setRating(rs.getInt("rating"));
						musicUsers.setInstance(music);
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
				musicUsers.setDetails(users);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return musicUsers;
	}

	@Override
	public long read(MusicUsers musicUsers) {
		String sql = "SELECT m.id, u.id, reg_date FROM music m LEFT OUTER JOIN user_music um ON m.id = um.music_id LEFT OUTER JOIN `user` u " +
				"ON um.user_id = u.id HAVING `name` = ? AND login = ? AND  u.password = ? AND email =?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, musicUsers.getInstance().getName());

			for (User user : musicUsers.getDetails().getInstances()) {
				ps.setString(2, user.getLogin());
				ps.setString(3, user.getPassword());
				ps.setString(4, user.getEmail());
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						long aID = rs.getLong("m.id");
						if (aID != 0) {
							musicUsers.getInstance().setId(rs.getLong("m.id"));
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
		return musicUsers.getInstance().getId();
	}

	@Override
	public int update(MusicUsers musicUsers) {
		//String sql = "UPDATE user_music SET user_id = ?, `city` = ?, `street` = ?, `house` = ?, `flat` = ?  WHERE `id` = ?";
		int rows = 0;
		if (musicUsers.getInstance() != null) {
			MusicDAO mdao = new MusicDAO(connection);
			rows += mdao.update(musicUsers.getInstance());
		}
		if (musicUsers.getDetails() != null) {
			UserDAO udao = new UserDAO(connection);
			for (User user : musicUsers.getDetails().getInstances()) {
				rows += udao.update(user);
			}
		}
		return rows;
	}

	@Override
	public boolean unlink(long music_id) {
		boolean res = false;
		if (music_id != 0) {
			String sql = "DELETE FROM `user_music` WHERE music_id = ?";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setLong(1, music_id);
				res = ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	@Override
	public boolean unlink(long music_id, long user_id) {
		boolean res = false;
		if (music_id != 0) {
			String sql = "DELETE FROM `user_music` WHERE music_id = ? AND user_id = ?";
			try (PreparedStatement ps = connection.prepareStatement(sql)) {
				ps.setLong(1, music_id);
				ps.setLong(2, user_id);
				res = ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	@Override
	public boolean delete(long music_id, long user_id) {
		boolean res = false;

		if (music_id != 0 && user_id != 0) {
			unlink(music_id, user_id);
			UserDAO udao = new UserDAO(connection);
			res = udao.delete(user_id);
		} else if (music_id != 0) {
			unlink(music_id);
			MusicDAO mdao = new MusicDAO(connection);
			res = mdao.delete(music_id);
		}
		return res;
	}
}
