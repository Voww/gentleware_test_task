package ua.com.gentleware.zaietsv.dao.data_access_link_object_detail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Voww on 11.10.2015.
 */
public class UserMusicLinkDAO extends AbstractLinkDAO {

    /**
     * Constructs a data access object using connection parameter
     *
     * @param connection - an entity of Connection class
     */
    public UserMusicLinkDAO(Connection connection) {
        super(connection);
    }

    @Override
    public int insertLink(Long user_id, Long music_id) {
        int rows;
        String sql = "INSERT INTO user_music (user_id, music_id)  VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, user_id);
            ps.setLong(2, music_id);

            rows = ps.executeUpdate();
        } catch (SQLException e) {
            rows = -1;
        }
        return rows;
    }

    @Override
    public ArrayList<Long> readLink(Long user_id) {
        ArrayList<Long> links;
        String sql = "SELECT * FROM user_music WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, user_id);
            try (ResultSet rs = ps.executeQuery()) {
                links = new ArrayList<>();
                while (rs.next()) {
                    links.add(rs.getLong("music_id"));
                }
            }
        } catch (SQLException e) {
            links = null;
        }
        return links;
    }

    @Override
    public int updateLink(Long user_id, Long music_id) {
        int rows;
        String sql = "UPDATE user_music SET music_id=?  WHERE user_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, music_id);
            ps.setLong(2, user_id);

            rows = ps.executeUpdate();
        } catch (SQLException e) {
            rows = -1;
        }
        return rows;
    }

    public int deleteLink(Long user_id, ArrayList<Long> music_list) {
        int rows = 0;
        String sql = "DELETE FROM user_music WHERE user_id = ? AND music_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Long music_id : music_list) {
                ps.setLong(1, user_id);
                ps.setLong(2, music_id);

                rows += ps.executeUpdate();
            }
        } catch (SQLException e) {
            rows = -1;
        }
        return rows;
    }

    public int deleteLink(Long user_id, Long music_id) {
        int rows;
        String sql = "DELETE FROM user_music WHERE user_id = ? AND music_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, user_id);
            ps.setLong(2, music_id);

            rows = ps.executeUpdate();
        } catch (SQLException e) {
            rows = -1;
        }
        return rows;
    }

    public int deleteLink(Long user_id) {
        int rows;
        String sql = "DELETE FROM user_music WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, user_id);
            rows = ps.executeUpdate();
        } catch (SQLException e) {
            rows = -1;
        }
        return rows;
    }
}
