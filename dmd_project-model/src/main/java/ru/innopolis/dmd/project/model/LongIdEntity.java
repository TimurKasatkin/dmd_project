package ru.innopolis.dmd.project.model;

import java.io.Serializable;

/**
 * Created by timur on 15.10.15.
 */
public abstract class LongIdEntity implements Serializable {

    protected Long id;

    public LongIdEntity() {
    }

    public LongIdEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LongIdEntity that = (LongIdEntity) o;
        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : System.identityHashCode(this);
    }
}
