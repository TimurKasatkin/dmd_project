package ru.innopolis.dmd.project.dao.postgresql.utils;

import ru.innopolis.dmd.project.model.IdentifiedEntity;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.text.MessageFormat.format;
import static ru.innopolis.dmd.project.dao.util.Constants.*;

/**
 * @author Timur Kasatkin
 * @date 28.10.15.
 * @email aronwest001@gmail.com
 */
public class SQLUtils {

    public static String fieldsStr(String entityName, String alias, String... fields) {
        return Stream.of(fields)
                .map(field -> format("{0}.{1} AS {2}_{1}", alias, field, entityName))
                .collect(Collectors.joining(","));
    }

    public static String fieldsByTable(String entityName, String tableName, String... fields) {
        return fieldsStr(entityName, alias(tableName), fields);
    }

    public static String fieldsStr(Class<? extends IdentifiedEntity> entityClass) {
        String tableName = ENTITY_TABLE_NAME.get(entityClass);
        return fieldsByTable(entityClass.getSimpleName().toLowerCase(), tableName, TABLE_FIELDS.get(tableName));
    }

    public static String tableName(Class<? extends IdentifiedEntity> entityClass) {
        return ENTITY_TABLE_NAME.get(entityClass);
    }

    public static String alias(Class<? extends IdentifiedEntity> entityClass) {
        return alias(tableName(entityClass));
    }

    public static String alias(String tableName) {
        return DEFAULT_TABLE_ALIAS.get(tableName);
    }

}
