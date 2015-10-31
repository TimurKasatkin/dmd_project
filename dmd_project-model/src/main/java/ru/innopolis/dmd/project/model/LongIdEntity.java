package ru.innopolis.dmd.project.model;

/**
 * Created by timur on 15.10.15.
 */
public abstract class LongIdEntity implements IdentifiedEntity<Long> {

    protected Long id;

    public LongIdEntity() {
    }

    public LongIdEntity(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
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
