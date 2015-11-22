package ru.innopolis.dmd.project.dao.innodb;

import ru.innopolis.dmd.project.dao.AbstractDao;
import ru.innopolis.dmd.project.dao.util.Constants;
import ru.innopolis.dmd.project.innodb.relalgrebra.Scan;
import ru.innopolis.dmd.project.model.IdentifiedEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author Timur Kasatkin
 * @date 18.11.15.
 * @email aronwest001@gmail.com
 */
public class AbstractDaoImpl<E extends IdentifiedEntity, I extends Serializable> implements AbstractDao<E, I> {


    protected Class<E> entityClass;

    protected String tableName;

    public AbstractDaoImpl(Class<E> entityClass) {
        this(Constants.ENTITY_TABLE_NAME.get(entityClass), entityClass);
    }

    public AbstractDaoImpl(String tableName, Class<E> entityClass) {
        this.tableName = tableName;
        this.entityClass = entityClass;
    }


    @Override
    public E findById(I id) {
//        Row row = new Select(new Scan(Cache.getTable("articles")),
//                new RowPredicate(r -> r.v("id").compareTo(id) == 0,
//                        new Condition("id", EQUALS, (Comparable) id))
//        ).loadAll().get(0);
        return null;
    }

    @Override
    public I save(E entity) {
        return null;
    }

    @Override
    public void update(E entity) {

    }

    @Override
    public long count() {
        return new Scan(tableName).loadAll().size();
    }

    @Override
    public void delete(I id) {

    }

    @Override
    public List<E> findAllAndSortBy(String columnName, boolean isAsc, Integer offset, Integer limit) {
        return null;
    }

    @Override
    public List<E> findBySomeFieldLike(String value) {
        return null;
    }
}
