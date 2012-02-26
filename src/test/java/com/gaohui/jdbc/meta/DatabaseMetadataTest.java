package com.gaohui.jdbc.meta;

import org.junit.Test;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * User: Administrator
 * Date: 12-1-2
 * Time: 下午12:17
 *
 * @author Basten Gao
 */
public class DatabaseMetadataTest {
    @Test
    public void test() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/disweb", "disweb", "disweb");
        System.out.println(connection.getCatalog());
        DatabaseMetaData dbMeta = connection.getMetaData();
        ResultSet schemas = dbMeta.getCatalogs();
        printResultSet(schemas);
        ResultSet tableTypes = dbMeta.getTableTypes();
        printResultSet(tableTypes);
        ResultSet tables = dbMeta.getTables(null, null, null, new String[]{"TABLE"});
        printResultSet(tables);

        ResultSet primaryKeys = dbMeta.getPrimaryKeys("disweb", null, "website");
        printResultSet(primaryKeys);

        ResultSet exportedKeys = dbMeta.getExportedKeys("disweb", null, "website");
        printResultSet(exportedKeys);

        ResultSet importedKeys = dbMeta.getImportedKeys("disweb", null, "website");
        printResultSet(importedKeys);

        ResultSet columns = dbMeta.getColumns("disweb", null, "website", null);
        printResultSet(columns);
        connection.close();
    }

    public static void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMeta = resultSet.getMetaData();
        printResultColumnHead(resultSetMeta);
        int columnCount = resultSetMeta.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("|%-15s", resultSet.getString(i));
            }
            System.out.printf("|");
            System.out.printf("%n");
        }
        System.out.println("---------------------------------------------------------\n\n");
    }

    public static void printResultColumnHead(ResultSetMetaData resultSetMetaData) throws SQLException {
        int columnCount = resultSetMetaData.getColumnCount();
        System.out.printf("--------------------------------------------------------%n");
        for (int i = 1; i <= columnCount; i++) {
            System.out.printf("|%-15s", resultSetMetaData.getColumnName(i));
        }
        System.out.printf("|");
        System.out.printf("%n--------------------------------------------------------%n");
    }

    public List<String> getTableNames(DatabaseMetaData databaseMetaData, String dbName) throws SQLException {
        List<String> tableNames = new LinkedList<String>();
        ResultSet resultSet = databaseMetaData.getTables(dbName, null, null, new String[]{"TABLE"});
        while (resultSet.next()) {
            tableNames.add(resultSet.getString("TABLE_NAME"));
        }
        return tableNames;
    }

}
