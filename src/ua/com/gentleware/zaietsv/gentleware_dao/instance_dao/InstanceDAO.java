package ua.com.gentleware.zaietsv.gentleware_dao.instance_dao;

import ua.com.gentleware.zaietsv.gentleware_dao.GentlewareDAO;
import ua.com.gentleware.zaietsv.gentleware_object.instance.Instance;

import java.util.List;

/**
 * An interface for gentleware test task DataAccess Object instances
 * Created by Voww on 18.12.2015.
 */
public interface InstanceDAO<I extends Instance> extends GentlewareDAO<I> {

    int insert(I instance);

    I read(long id);

    long read(I instance);

    int update(I instance);

    boolean delete(long id);

    boolean delete(I instance);

    boolean exists(long id);

    public boolean exists(I instance);

    List<I> readAll();

}
