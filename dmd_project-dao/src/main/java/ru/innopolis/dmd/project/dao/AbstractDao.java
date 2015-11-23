package ru.innopolis.dmd.project.dao;

import ru.innopolis.dmd.project.model.IdentifiedEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by timur on 15.10.15.
 */
public interface AbstractDao<E extends IdentifiedEntity, I extends Serializable> {

    E findById(I id);

    I save(E entity);

    /**
     * @param entity - entity to rewrite old one
     */
    void update(E entity);

    long count();

    default void delete(E entity) {
        delete((I) entity.getId());
    }

    /**
     * Delete entity by id
     */
    void delete(I id);

    /**
     * Find all rows and sort them
     *
     * @param columnName ORDER BY columnName
     * @param isAsc      true -> ASC, false -> DESC
     * @param limit
     * @param offset
     * @return rows sorted by columnName
     */
    List<E> findAllAndSortBy(String columnName, boolean isAsc, Integer offset, Integer limit);

    List<E> findBySomeFieldLike(String value);
}
