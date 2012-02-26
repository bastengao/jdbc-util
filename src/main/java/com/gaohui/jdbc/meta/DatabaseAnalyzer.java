package com.gaohui.jdbc.meta;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import java.sql.*;
import java.util.*;

/**
 * 目前只支持mysql,其他数据库未进行测试。
 * User: Administrator
 * Date: 12-1-2
 * Time: 下午5:19
 *
 * @author Basten Gao
 */
public class DatabaseAnalyzer {

    public static void cleanDatabase(Connection connection) throws SQLException {
        String dbName = connection.getCatalog();
        List<TableMetaData> tables = getTables(connection, dbName);
        for (TableMetaData table : tables) {
            dropForeignKeys(connection, table);
        }
        for (TableMetaData table : tables) {
            deleteAll(connection, table.getName());
        }
        for (TableMetaData table : tables) {
            addForeignKeys(connection, table);
        }
    }

    public static void dropForeignKeys(Connection connection, TableMetaData tableMetaData) throws SQLException {
        for (ForeignKeyMetaData foreignKey : tableMetaData.getForeignKeys()) {
            dropForeignKey(connection, foreignKey);
        }
    }

    @VisibleForTesting
    public static void dropForeignKey(Connection connection, ForeignKeyMetaData foreignKey) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(String.format("alter table `%s` drop  foreign key `%s` ", foreignKey.getFkTable(), foreignKey.getFkName()));
        stmt.execute();
        stmt.close();
    }

    public static void addForeignKeys(Connection connection, TableMetaData tableMetaData) throws SQLException {
        for (ForeignKeyMetaData foreignKey : tableMetaData.getForeignKeys()) {
            addForeignKey(connection, foreignKey);
        }
    }

    @VisibleForTesting
    public static void addForeignKey(Connection connection, ForeignKeyMetaData foreignKey) throws SQLException {
        String sql = String.format("alter table `%s` add constraint `%s` foreign  key (`%s`) references `%s`(`%s`)", foreignKey.getFkTable(), foreignKey.getFkName(), foreignKey.getFkColumn(), foreignKey.getPkTable(), foreignKey.getPkColumn());
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.execute();
        stmt.close();
    }

    public static void deleteAll(Connection connection, String tableName) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(String.format("delete from %s", tableName));
        stmt.execute();
        stmt.close();
    }

    public static List<TableMetaData> getTables(Connection connection, String dbName) throws SQLException {
        Preconditions.checkArgument(!connection.isClosed(), "connection is closed");
        DatabaseMetaData dbMeta = connection.getMetaData();

        List<TableMetaData> tables = new LinkedList<TableMetaData>();

        ResultSet tablesResultSet = dbMeta.getTables(dbName, null, null, new String[]{"TABLE"});
        while (tablesResultSet.next()) {
            String tableName = tablesResultSet.getString("TABLE_NAME");
            tables.add(new TableMetaData(tableName, getForeignKeys(dbMeta, dbName, tableName), getColumns(dbMeta, dbName, tableName)));
        }
        return tables;
    }

    public static List<ColumnMetaData> getColumns(DatabaseMetaData dbMeta, String dbName, String tableName) throws SQLException {
        List<ColumnMetaData> columns = new LinkedList<ColumnMetaData>();
        ResultSet columnResultSet = dbMeta.getColumns(dbName, null, tableName, null);
        while (columnResultSet.next()) {
            String columnName = columnResultSet.getString("COLUMN_NAME");
            String columnType = columnResultSet.getString("TYPE_NAME");
            columns.add(new ColumnMetaData(columnName, columnType));

        }
        return columns;
    }

    public static List<ForeignKeyMetaData> getForeignKeys(DatabaseMetaData dbMeta, String dbName, String tableName) throws SQLException {
        List<ForeignKeyMetaData> foreignKeys = new LinkedList<ForeignKeyMetaData>();
        ResultSet foreignKeysResultSet = dbMeta.getImportedKeys(dbName, null, tableName);
        while (foreignKeysResultSet.next()) {
            String fkTableName = foreignKeysResultSet.getString("FKTABLE_NAME");
            String fkColumnName = foreignKeysResultSet.getString("FKCOLUMN_NAME");
            String fkName = foreignKeysResultSet.getString("FK_NAME");
            String pkTableName = foreignKeysResultSet.getString("PKTABLE_NAME");
            String pkColumnName = foreignKeysResultSet.getString("PKCOLUMN_NAME");
            foreignKeys.add(new ForeignKeyMetaData(pkTableName, pkColumnName, fkTableName, fkColumnName, fkName));
        }
        return foreignKeys;
    }

    public static List<String> getCatalogs(DatabaseMetaData dbMeta) throws SQLException {
        List<String> catalogs = new LinkedList<String>();
        ResultSet catalogResultSet = dbMeta.getCatalogs();
        while (catalogResultSet.next()) {
            catalogs.add(catalogResultSet.getString("TABLE_CAT"));
        }
        return catalogs;
    }
}
