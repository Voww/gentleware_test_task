package ua.com.gentleware.zaietsv.gentleware_object.instance;

import ua.com.gentleware.zaietsv.gentleware_object.GentlewareObject;

/**
 * An interface for gentleware test task instances
 * Created by Voww on 18.12.2015.
 */
public interface Instance extends GentlewareObject {

    long getId();

    void setId(long id);

    boolean equalsIgnoreID(Object o);
}
