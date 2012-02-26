package com.gaohui.jdbc.meta;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * User: Administrator
 * Date: 12-1-2
 * Time: 下午5:53
 *
 * @author Basten Gao
 */
public class DatabaseAnalyzerTest {
    @Test
    public void test() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/disweb", "disweb", "disweb");
        List<String> catalogs = DatabaseAnalyzer.getCatalogs(connection.getMetaData());
        System.out.println(catalogs);
        connection.close();
    }

    @Test
    public void testGetTables() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/disweb", "disweb", "disweb");
        List<TableMetaData> tables = DatabaseAnalyzer.getTables(connection, "disweb");
        for (TableMetaData table : tables) {
            System.out.println(table);
        }
        connection.close();
    }

    @Test
    public void testGetColumns() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/disweb", "disweb", "disweb");
        List<ColumnMetaData> columns = DatabaseAnalyzer.getColumns(connection.getMetaData(), "disweb", "website");
        System.out.println(columns);
        connection.close();
    }

    @Test
    public void testGetForeignKeys() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/disweb", "disweb", "disweb");
        List<ForeignKeyMetaData> foreignKeys = DatabaseAnalyzer.getForeignKeys(connection.getMetaData(), "disweb", "website");
        System.out.println(foreignKeys);
        connection.close();
    }

    @Test
    public void testDropForeignKeys() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/disweb", "disweb", "disweb");
        List<ForeignKeyMetaData> foreignKeys = DatabaseAnalyzer.getForeignKeys(connection.getMetaData(), "disweb", "website");
        for (ForeignKeyMetaData foreignKey : foreignKeys) {
            DatabaseAnalyzer.dropForeignKey(connection, foreignKey);
        }
        for (ForeignKeyMetaData foreignKey : foreignKeys) {
            DatabaseAnalyzer.addForeignKey(connection, foreignKey);
        }
        connection.close();
    }


    @Test
    public void testCleanDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/disweb", "disweb", "disweb");
        DatabaseAnalyzer.cleanDatabase(connection);
        connection.close();
    }

}
