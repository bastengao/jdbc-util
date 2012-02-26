package com.gaohui.jdbc.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Administrator
 * Date: 12-1-2
 * Time: 下午5:06
 *
 * @author Basten Gao
 */
public class TableMetaData {
    private String name;
    private List<ForeignKeyMetaData> foreignKeys;
    private List<ColumnMetaData> columns;

    public TableMetaData(String name, List<ForeignKeyMetaData> foreignKeys, List<ColumnMetaData> columns) {
        this.name = name;
        this.foreignKeys = foreignKeys;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public List<ForeignKeyMetaData> getForeignKeys() {
        return new ArrayList<ForeignKeyMetaData>(foreignKeys);
    }

    public List<ColumnMetaData> getColumns() {
        return new ArrayList<ColumnMetaData>(columns);
    }

    @Override
    public String toString() {
        return "TableMetaData{" +
                "name='" + name + '\'' +
                '}';
    }
}
