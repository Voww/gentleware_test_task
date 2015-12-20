package ua.com.gentleware.zaietsv.dao;

import zaietsv.complextask.mvc.dao.data_acces_object.AddressDAO;
import zaietsv.complextask.mvc.dao.data_acces_object.MusicDAO;
import zaietsv.complextask.mvc.dao.data_acces_object.RoleDAO;
import zaietsv.complextask.mvc.dao.data_acces_object.UserDAO;
import zaietsv.complextask.mvc.dao.data_access_link_object_detail.UserAddressLinkDAO;
import zaietsv.complextask.mvc.dao.data_access_link_object_detail.UserMusicLinkDAO;
import zaietsv.complextask.mvc.dao.data_access_link_object_detail.UserRoleLinkDAO;
import zaietsv.complextask.mvc.entity.Entity;
import zaietsv.complextask.mvc.entity.UserAddressRoleMusics;
import zaietsv.complextask.mvc.entity.instance.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAddressRoleMusicsDAO extends AbstractDAO<Entity> implements EntityDAO<Entity> {

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
	public UserAddressRoleMusicsDAO(Connection connection) {
		super(connection);
	}

	public int insert(UserAddressRoleMusics uarm) {
		int rows = 0;
		if (insertUser(uarm.getUser()) >= 0) {
			if (insertAddress(uarm.getAddress()) >= 0) {
				UserAddressLinkDAO uald = new UserAddressLinkDAO(connection);
				rows =+ uald.insertLink(uarm.getUser().getId(), uarm.getAddress().getId());
			}
			if (insertRole(uarm.getRole()) >= 0) {
				UserRoleLinkDAO urld = new UserRoleLinkDAO(connection);
				rows =+ urld.insertLink(uarm.getUser().getId(), uarm.getRole().getId());
			}
			if (insertMusics(uarm.getMusics()) >= 0) {
				UserMusicLinkDAO umld = new UserMusicLinkDAO(connection);
				for (Music music : uarm.getMusics().getInstances()) {
					rows =+ umld.insertLink(uarm.getUser().getId(), music.getId());
				}
			}
		}
		return rows;
	}

	private int insertUser(User user) {
		int rows;
		UserDAO udao = new UserDAO(connection);
		if (user.getId() == 0 && !udao.exists(user)) {
			rows = udao.insert(user);
			udao.read(user);
		} else if (udao.exists(user.getId()) && !udao.exists(user)) {
			rows = udao.update(user);
		} else {
			rows = 0;
		}
		return rows;
	}

	private int insertAddress(Address address) {
		int rows;
		AddressDAO adao = new AddressDAO(connection);
		if (address.getId() == 0 && !adao.exists(address)) {
			rows = adao.insert(address);
			adao.read(address);
		} else if (adao.exists(address.getId()) && !adao.exists(address)) {
			rows = adao.update(address);
		} else {
			rows = 0;
		}
		return rows;
	}

	private int insertRole(Role role) {
		int rows;
		RoleDAO rdao = new RoleDAO(connection);
		if (role.getId() == 0 && !rdao.exists(role)) {
			rows = rdao.insert(role);
			rdao.read(role);
		} else if (rdao.exists(role.getId()) && !rdao.exists(role)) {
			rows = rdao.update(role);
		} else {
			rows = 0;
		}
		return rows;
	}

	private int insertMusics(Musics musics) {
		int rows = 0;
		MusicDAO mdao = new MusicDAO(connection);
		for (Music music : musics.getInstances()) {
			if (music.getId() == 0 && !mdao.exists(music)) {
				rows =+ mdao.insert(music);
				mdao.read(music);
			} else if (mdao.exists(music.getId()) && !mdao.exists(music)) {
				rows =+ mdao.update(music);
			}
		}
		return rows;
	}

/*
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
	}*/

	public UserAddressRoleMusics read(long user_id) {
		String sql = "SELECT * FROM `user` u LEFT OUTER JOIN user_address ua ON u.id = ua.user_id LEFT OUTER JOIN address a ON ua.address_id = a.id " +
				"LEFT OUTER JOIN user_role ur ON u.id = ur.user_id LEFT OUTER JOIN role r ON ur.role_id = r.id " +
				"LEFT OUTER JOIN user_music um ON u.id = um.user_id LEFT OUTER JOIN music m ON um.music_id = m.id " +
				"HAVING  u.id = ? ";
		UserAddressRoleMusics uarm = new UserAddressRoleMusics();
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setLong(1, user_id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {

					if (rs.getLong("u.id") != 0) {
						User user = new User();
						user.setId(rs.getLong("u.id"));
						user.setLogin(rs.getString("login"));
						user.setPassword(rs.getString("password"));
						user.setEmail(rs.getString("email"));
						user.setReg_date(rs.getDate("reg_date"));
						uarm.setUser(user);
					}

					if (rs.getLong("a.id") != 0) {
						Address address = new Address();
						address.setId(rs.getLong("a.id"));
						address.setPostcode(rs.getInt("postcode"));
						address.setCity(rs.getString("city"));
						address.setStreet(rs.getString("street"));
						address.setHouse(rs.getInt("house"));
						address.setFlat(rs.getInt("flat"));
						uarm.setAddress(address);
						}

					if (rs.getLong("r.id") != 0) {
						Role role = new Role();
						role.setId(rs.getLong("r.id"));
						role.setName(rs.getString("name"));
						uarm.setRole(role);
					}
				}
				Musics musics = new Musics();
				do {
					if (rs.getLong("m.id") != 0) {
						Music music = new Music();
						music.setId(rs.getLong("m.id"));
						music.setName(rs.getString("m.name"));
						music.setRating(rs.getInt("rating"));
						musics.getInstances().add(music);
					}
				} while (rs.next());
				uarm.setMusics(musics);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return uarm;
	}
}
