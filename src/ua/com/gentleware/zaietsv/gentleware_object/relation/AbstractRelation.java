package ua.com.gentleware.zaietsv.gentleware_object.relation;

import ua.com.gentleware.zaietsv.gentleware_object.instance.AbstractInstance;

import java.util.List;

/**
 * An abstract superclass for Relation instances
 * Created by Voww on 18.12.2015.
 */
public abstract class AbstractRelation<B extends AbstractInstance, R extends AbstractInstance> implements Relation<B, R> {



    /** A basic object (left) */
    private B base;

    /** A related object (right) */
    private List<R> related;

    public AbstractRelation() {
    }

    public AbstractRelation(B base, List<R> related) {
        this.base = base;
        this.related = related;
    }

    public B getBase() {
        return base;
    }

    public void setBase(B base) {
        this.base = base;
    }

    public List<R> getRelated() {
        return related;
    }

    public void setRelated(List<R> related) {
        this.related = related;
    }

    @Override
    public String toString() {
        return "AbstractRelation{" +
                "base=" + base +
                ", related=" + related +
                '}';
    }
}
