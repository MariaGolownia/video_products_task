package by.godel.video.app.entity;
import java.io.Serializable;

/**
 * Entity Model Object.
 *
 *  Serialization is provided in case of a possible expansion of the project to the web project
 *
 * @author Maryia_Halaunia
 * @version 1.0
 */

abstract public class Entity implements Serializable {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if(object != null) {
            if(object != this) {
                if(object.getClass() == getClass() && id != null) {
                    return id.equals(((Entity)object).id);
                }
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return"id=" + id +',';
    }
}
