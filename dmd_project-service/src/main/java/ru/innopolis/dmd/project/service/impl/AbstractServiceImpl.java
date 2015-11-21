package ru.innopolis.dmd.project.service.impl;

import ru.innopolis.dmd.project.dao.AbstractDao;
import ru.innopolis.dmd.project.model.IdentifiedEntity;
import ru.innopolis.dmd.project.service.AbstractService;

import java.io.Serializable;
import java.util.List;

/**
 * @author Timur Kasatkin
 * @date 28.10.15.
 * @email aronwest001@gmail.com
 */
public abstract class AbstractServiceImpl<E extends IdentifiedEntity, I extends Serializable> implements AbstractService<E, I> {


    protected AbstractDao<E, I> abstractDao;

    public AbstractServiceImpl(AbstractDao<E, I> abstractDao) {
        this.abstractDao = abstractDao;
    }

    @Override
    public List<E> findAllSortedBy(String field, boolean isAsc, Integer offset, Integer limit) {
        return abstractDao.findAllAndSortBy(field, isAsc, offset, limit);
    }

    @Override
    public List<E> findBySomeFieldLike(String value) {
        return abstractDao.findBySomeFieldLike(value);
    }

    @Override
    public I save(E entity) {
        return abstractDao.save(entity);
    }

    @Override
    public E findById(I id) {
        return abstractDao.findById(id);
    }

    @Override
    public long count() {
        return abstractDao.count();
    }
}
