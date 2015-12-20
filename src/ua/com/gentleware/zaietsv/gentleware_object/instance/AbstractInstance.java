package ua.com.gentleware.zaietsv.gentleware_object.instance;

/**
 * An abstract superclass for Instance instances
 * Created by Voww on 18.12.2015.
 */
public abstract class AbstractInstance implements Instance {

    /** an gentleware_object database ID */
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean equalsIgnoreID(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractInstance that = (AbstractInstance) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "AbstractInstance{" +
                "id=" + id +
                '}';
    }
}
