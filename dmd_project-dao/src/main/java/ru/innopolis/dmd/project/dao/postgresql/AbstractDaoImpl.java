package ru.innopolis.dmd.project.dao.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.innopolis.dmd.project.dao.AbstractDao;
import ru.innopolis.dmd.project.dao.util.Constants;
import ru.innopolis.dmd.project.dao.util.EntityMapper;
import ru.innopolis.dmd.project.dao.util.FetchUtils;
import ru.innopolis.dmd.project.model.IdentifiedEntity;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.List;

import static java.text.MessageFormat.format;
import static ru.innopolis.dmd.project.dao.util.EntityMapper.extractEntity;
import static ru.innopolis.dmd.project.dao.util.SQLUtils.alias;
import static ru.innopolis.dmd.project.dao.util.SQLUtils.fieldsStr;

/**
 * Created by timur on 15.10.15.
 */
public abstract class AbstractDaoImpl<E extends IdentifiedEntity, I extends Serializable> implements AbstractDao<E, I> {

    /**
     * JUST FOR TESTS IN MAIN METHOD!!!
     */
    protected static DataSource testDataSource = new DriverManagerDataSource(Constants.POSTGRESQL_URL,
            Constants.POSTGRESQL_LOGIN, Constants.POSTGRESQL_PASSWORD);

    protected Class<E> entityClass;

    @Autowired
    protected DataSource dataSource;

    protected JdbcTemplate jdbcTemplate;

    protected String alias;

    protected String tableName;

    protected String tableFieldsStr;

    public AbstractDaoImpl(Class<E> entityClass, DataSource dataSource) {
        this(Constants.ENTITY_TABLE_NAME.get(entityClass), entityClass, dataSource);
    }

    public AbstractDaoImpl(String tableName, Class<E> entityClass, DataSource dataSource) {
        this.tableName = tableName;
        this.alias = alias(tableName);
        this.entityClass = entityClass;
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource, true);
        this.tableFieldsStr = fieldsStr(entityClass);
    }

    @Override
    public List<E> findBy(String field, Object value) {
        return proxy(jdbcTemplate.query(format("SELECT {0} FROM {1} {2} WHERE {3}=?;",
                tableFieldsStr, tableName, alias(tableName), field), rowMapper(), value));
    }

    @Override
    public List<E> findBy(String field, Object value, Integer limit, Integer offset) {
        return proxy(jdbcTemplate.query(format("SELECT {0} FROM {1} {2} WHERE {3}=? LIMIT {4} OFFSET {5};",
                        tableFieldsStr, tableName, alias, field, limit.toString(), offset.toString()),
                (rs, rowNum) -> extractEntity(entityClass, rs), value));
    }

    @Override
    public List<E> findLike(String field, Object similarValue, Integer limit, Integer offset) {
        return proxy(jdbcTemplate.query(format("SELECT {0} FROM {1} {2} WHERE {3} ~* '{4}' LIMIT {5} OFFSET {5};",
                tableFieldsStr, tableName, alias, field, similarValue, limit.toString(), offset.toString()), rowMapper()));
    }

    @Override
    public E findById(I id) {
        String alias = alias(tableName);
        return proxy(jdbcTemplate.queryForObject(format("SELECT {0} FROM {1} {2} WHERE {2}.id=?;",
                tableFieldsStr, tableName, alias), rowMapper(), id));
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(format("SELECT count(*) AS {0}_count FROM {1};",
                entityClass.getSimpleName().toLowerCase(), tableName), Long.class);
    }

    @Override
    public void delete(I id) {
        jdbcTemplate.update(format("DELETE FROM {0} WHERE id=?;", tableName), id);
    }

    @Override
    public List<E> findAll() {
        return proxy(jdbcTemplate.query("SELECT " + tableFieldsStr + " "
                + "FROM " + tableName + " " + alias + ";", rowMapper()));
    }

    @Override
    public List<E> findAllAndSortBy(String columnName, boolean isAsc) {
        return proxy(jdbcTemplate.query(
                format("SELECT {0} FROM {1} {2} ORDER BY {3} {4}",
                        tableFieldsStr, tableName, alias, columnName, isAsc ? "ASC" : "DESC"),
                rowMapper()));
    }

    @Override
    public List<E> findAllAndSortBy(String columnName, boolean isAsc, Integer offset, Integer limit) {
        return proxy(jdbcTemplate.query(
                format("SELECT {0} FROM {1} {2} ORDER BY {3} {4} LIMIT {5} OFFSET {6};",
                        tableFieldsStr, tableName, alias, columnName, isAsc ? "ASC" : "DESC", limit.toString(), offset.toString()),
                rowMapper()));
    }

    protected RowMapper<E> rowMapper() {
        return (rs, rowNum) -> EntityMapper.extractEntity(entityClass, rs);
    }

    protected ResultSetExtractor<E> resExtractor() {
        return rs -> rs.next() ? EntityMapper.extractEntity(entityClass, rs) : null;
    }

    protected E proxy(E entity) {
        FetchUtils.proxy(entityClass, entity, jdbcTemplate);
        return entity;
    }

    protected <ET extends IdentifiedEntity> List<ET> proxy(List<ET> entities) {
        entities.forEach(entity ->
                FetchUtils.proxy((Class<ET>) entity.getClass(), entity, jdbcTemplate));
        return entities;
    }
}
