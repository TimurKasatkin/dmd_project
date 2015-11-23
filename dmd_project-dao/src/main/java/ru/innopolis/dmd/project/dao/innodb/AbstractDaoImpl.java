package ru.innopolis.dmd.project.dao.innodb;

import ru.innopolis.dmd.project.dao.AbstractDao;
import ru.innopolis.dmd.project.dao.innodb.util.InnoDBEntityMappingUtils;
import ru.innopolis.dmd.project.dao.util.Constants;
import ru.innopolis.dmd.project.innodb.Row;
import ru.innopolis.dmd.project.innodb.logic.Condition;
import ru.innopolis.dmd.project.innodb.logic.ConditionType;
import ru.innopolis.dmd.project.innodb.relalgrebra.Scan;
import ru.innopolis.dmd.project.innodb.relalgrebra.Select;
import ru.innopolis.dmd.project.innodb.relalgrebra.Sort;
import ru.innopolis.dmd.project.innodb.utils.RowUtils;
import ru.innopolis.dmd.project.model.IdentifiedEntity;
import ru.innopolis.dmd.project.model.User;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import static ru.innopolis.dmd.project.innodb.utils.CollectionUtils.stream;

/**
 * @author Timur Kasatkin
 * @date 18.11.15.
 * @email aronwest001@gmail.com
 */
public abstract class AbstractDaoImpl<E extends IdentifiedEntity, I extends Serializable> implements AbstractDao<E, I> {


    protected Class<E> entityClass;

    protected String tableName;

    public AbstractDaoImpl(Class<E> entityClass) {
        this(Constants.ENTITY_TABLE_NAME.get(entityClass), entityClass);
    }

    public AbstractDaoImpl(String tableName, Class<E> entityClass) {
        this.tableName = tableName;
        this.entityClass = entityClass;
    }

    public static void main(String[] args) {
        List<User> id = new UserDaoImpl().findAllAndSortBy("id", true, 0, 50);
    }

    @Override
    public E findById(I id) {
        List<Row> rows = new Select(tableName, new Condition("id", ConditionType.EQUALS, (Comparable) id)).loadAll()
                .stream().map(row -> RowUtils.addTablePrefix(tableName, row)).collect(Collectors.toList());
        return rows.size() > 0 ? InnoDBEntityMappingUtils.extractEntity(entityClass, rows.get(0)) : null;
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
        List<Row> collection = RowUtils.addTablePrefix(tableName, new Sort(columnName, isAsc ? Sort.SortType.ASC : Sort.SortType.DESC, new Scan(tableName), offset, limit).loadAll());
        List collect = stream(collection)
                .map(row -> InnoDBEntityMappingUtils.extractEntity(entityClass, row))
                .collect(Collectors.toList());
        return collect;
    }
}
