package ua.com.gentleware.zaietsv.dao.data_acces_object;

import zaietsv.complextask.mvc.entity.instance.Music;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MusicDAO extends AbstractDAO<Music> {

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
	public MusicDAO(Connection connection) {
		super(connection);
	}

	@Override
	public int insert(Music music) {
		int rows;
		String sql = "INSERT INTO music (name) VALUE (?)";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, music.getName());

			rows = ps.executeUpdate();
		} catch (SQLException ignored) {
			rows = -1;
		}
		return rows;
	}

	@Override
	public Music read(long id) {
		String sql = "SELECT * FROM music WHERE id = ?";
		Music music = new Music();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					music.setId(rs.getLong("id"));
					music.setName(rs.getString("name"));
					music.setRating(rs.getInt("rating"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return music;
	}

	@Override
	public long read(Music instance) {
		String sql = "SELECT id FROM music WHERE name = ?";
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

	@Override
	public int update(Music music) {
		String sql = "UPDATE music SET `name` = ?  WHERE `id` = ?";
		int rows;
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, music.getName());
			ps.setInt(2, music.getRating());

			rows = ps.executeUpdate();
		} catch (SQLException ignored) {
			rows = -1;
		}
		return rows;
	}

	@Override
	public boolean delete(long id) {
		String sql = " DELETE FROM `music` WHERE id = ?";
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
		String sql = "SELECT COUNT(*) `count` FROM `music` WHERE id = ?";
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
	public boolean exists(Music instance) {
		boolean exists = false;
		String sql = "SELECT COUNT(*) `count` FROM `music` WHERE name = ?";
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
	public ArrayList<Music> readAll() {
		String sql = "SELECT * FROM music WHERE 1";
		ArrayList<Music> musics = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Music music = new Music();
					music.setId(rs.getLong("id"));
					music.setName(rs.getString("name"));
					music.setRating(rs.getInt("rating"));
					musics.add(music);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return musics;
	}
}
