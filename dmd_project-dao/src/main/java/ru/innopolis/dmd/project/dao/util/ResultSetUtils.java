package ru.innopolis.dmd.project.dao.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by timur on 15.10.15.
 */
public class ResultSetUtils {

    public static boolean contains(ResultSet resultSet, String colName) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i < columnCount + 1; i++) {
            if (metaData.getColumnLabel(i).equals(colName))
                return true;
        }
        return false;
    }

    public static <T> T extractIfContains(Class<T> clazz, String column, ResultSet rs) throws SQLException {
        if (contains(rs, column)) {
            switch (clazz.getSimpleName()) {
                case "Long":
                    return (T) Long.valueOf(rs.getLong(column));
                case "Boolean":
                    return (T) Boolean.valueOf(rs.getBoolean(column));
                case "Integer":
                    return (T) Integer.valueOf(rs.getInt(column));
                case "Double":
                    return (T) Double.valueOf(rs.getDouble(column));
                case "Date":
                    return (T) rs.getDate(column);
                case "Time":
                    return (T) rs.getTime(column);
                case "URL":
                    return (T) rs.getURL(column);
                case "String":
                    return (T) rs.getString(column);
            }
            return rs.getObject(column, clazz);
        } else return null;
    }

}
