package org.xseam.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * This class overwrites equals, hashcode, toString based on object Entity ID.
 * Generally this is good to use with legacy system with existent database tables.
 * 
 * @author Rafael Benevides
 *
 */
@MappedSuperclass
public abstract class AbstractBaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    public abstract Object getId();
    
    @Override
    public int hashCode() {
        Object id = getId();
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final AbstractBaseEntity other = (AbstractBaseEntity) obj;
        Object id = getId();
        if (id == null) {
            if (other.getId() != null)
                return false;
        } else if (!id.equals(other.getId())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(getClass().getName()).append("[id = ").append(getId()).append(" ]");
        return s.toString();
    }

}
