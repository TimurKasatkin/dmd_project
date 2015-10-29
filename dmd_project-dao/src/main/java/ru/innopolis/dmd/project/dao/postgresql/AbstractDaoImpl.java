package ru.innopolis.dmd.project.dao.postgresql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.innopolis.dmd.project.dao.AbstractDao;
import ru.innopolis.dmd.project.dao.util.Constants;
import ru.innopolis.dmd.project.dao.util.EntityMapper;
import ru.innopolis.dmd.project.model.IdentifiedEntity;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.List;

import static java.text.MessageFormat.format;
import static ru.innopolis.dmd.project.dao.util.Constants.TABLE_FIELDS;
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

    protected String[] tableFields;

    public AbstractDaoImpl(Class<E> entityClass, DataSource dataSource) {
        this(Constants.ENTITY_TABLE_NAME.get(entityClass), entityClass, dataSource);
    }

    public AbstractDaoImpl(String tableName, Class<E> entityClass, DataSource dataSource) {
        this.tableName = tableName;
        this.alias = alias(tableName);
        this.entityClass = entityClass;
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource, true);
        this.tableFields = TABLE_FIELDS.get(tableName);
        this.tableFieldsStr = fieldsStr(entityClass);
    }

    @Override
    public List<E> findBy(String field, Object value) {
        return jdbcTemplate.query(format("SELECT {0} FROM {1} {2} WHERE {3}=?;",
                tableFieldsStr, tableName, alias(tableName), field), rowMapper(), value);
    }

    @Override
    public List<E> findBy(String field, Object value, Integer limit, Integer offset) {
        return jdbcTemplate.query(format("SELECT {0} FROM {1} {2} WHERE {3}=? LIMIT {4} OFFSET {5};",
                        tableFieldsStr, tableName, alias(tableName), field, limit, offset),
                (rs, rowNum) -> extractEntity(entityClass, rs), value);
    }

    @Override
    public E findById(I id) {
        String alias = alias(tableName);
        return jdbcTemplate.queryForObject(format("SELECT {0} FROM {1} {2} WHERE {2}.id=?;",
                tableFieldsStr, tableName, alias), rowMapper(), id);
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
        return jdbcTemplate.query("SELECT " + tableFieldsStr + " "
                + "FROM " + tableName + ";", rowMapper());
    }

    @Override
    public List<E> findAllAndSortBy(String columnName, boolean isAsc) {
        return jdbcTemplate.query(
                format("SELECT {0} FROM {1} ORDER BY {2} {3}",
                        tableFieldsStr, tableName, columnName, isAsc ? "ASC" : "DESC"),
                rowMapper());
    }

    @Override
    public List<E> findAllAndSortBy(String columnName, boolean isAsc, Integer offset, Integer limit) {
        return jdbcTemplate.query(
                format("SELECT {0} FROM {1} {2} ORDER BY {3} {4} LIMIT {5} OFFSET {6};",
                        tableFieldsStr, tableName, alias, columnName, isAsc ? "ASC" : "DESC", limit, offset),
                rowMapper());
    }

    protected RowMapper<E> rowMapper() {
        return (rs, rowNum) -> EntityMapper.extractEntity(entityClass, rs);
    }
}
