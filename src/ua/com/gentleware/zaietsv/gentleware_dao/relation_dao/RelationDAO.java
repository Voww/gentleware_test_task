package ua.com.gentleware.zaietsv.gentleware_dao.relation_dao;

import ua.com.gentleware.zaietsv.gentleware_dao.GentlewareDAO;
import ua.com.gentleware.zaietsv.gentleware_object.relation.Relation;

/**
 * An interface for gentleware test task DataAccess Object instances
 * Created by Voww on 18.12.2015.
 */
public interface RelationDAO<R extends Relation> extends GentlewareDAO<R> {

    int insert(R relation);

    R read(long baseId);

    //long read(R relation);

    //int update(R relation);

    //int delete(long id);

    int delete(R relation);

    //List<R> readAll();

}
