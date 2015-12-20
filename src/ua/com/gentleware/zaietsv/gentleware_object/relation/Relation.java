package ua.com.gentleware.zaietsv.gentleware_object.relation;

import ua.com.gentleware.zaietsv.gentleware_object.GentlewareObject;
import ua.com.gentleware.zaietsv.gentleware_object.instance.Instance;

import java.util.List;

/**
 * An interface for gentleware test task relations of instances
 * Created by Voww on 18.12.2015.
 */
public interface Relation<B extends Instance, R extends Instance> extends GentlewareObject {

    B getBase();

    void setBase(B base);

    List<R> getRelated();

    void setRelated(List<R> related);
}
