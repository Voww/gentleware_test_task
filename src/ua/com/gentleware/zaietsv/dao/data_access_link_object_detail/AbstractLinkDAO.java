package ua.com.gentleware.zaietsv.dao.data_access_link_object_detail;

import zaietsv.complextask.mvc.dao.AbstractDAO;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by Voww on 11.10.2015.
 */
public abstract class AbstractLinkDAO extends AbstractDAO implements LinkDAO {
    /**
     * Constructs a data access object using connection parameter
     *
     * @param connection - an entity of Connection class
     */
    public AbstractLinkDAO(Connection connection) {
        super(connection);
    }

    @Override
    public abstract int insertLink(Long instance_id, Long detail_id);

    @Override
    public abstract ArrayList<Long> readLink(Long instance_id);

    @Override
    public abstract int updateLink(Long instance_id, Long detail_id);

    @Override
    public abstract int deleteLink(Long instance_id, Long detail_id);
}
