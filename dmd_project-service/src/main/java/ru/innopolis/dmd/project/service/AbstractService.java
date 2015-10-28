package ru.innopolis.dmd.project.service;

import ru.innopolis.dmd.project.model.IdentifiedEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Timur Kasatkin
 * @date 28.10.15.
 * @email aronwest001@gmail.com
 */
public interface AbstractService<E extends IdentifiedEntity, I extends Serializable> {

    List<E> findAllSortedBy(String field, boolean isAsc);

    List<E> findAllSortedBy(String field, boolean isAsc, Integer offset, Integer limit);

    List<E> findBy(String field, Object value);

    List<E> findLike(String field, Object value);

    List<E> findLike(String field, Object value, Integer offset, Integer limit);

    I save(E entity);

    E findById(I id);

    long count();

}
