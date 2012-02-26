package com.gaohui.jdbc.meta;

import java.util.List;

/**
 * User: Administrator
 * Date: 12-1-2
 * Time: 下午5:08
 *
 * @author Basten Gao
 */
public class ForeignKeyMetaData {
    private String pkTable;
    private String pkColumn;
    private String fkTable;
    private String fkColumn;
    private String fkName;

    public ForeignKeyMetaData(String pkTable, String pkColumn, String fkTable, String fkColumn, String fkName) {
        this.pkTable = pkTable;
        this.pkColumn = pkColumn;
        this.fkTable = fkTable;
        this.fkColumn = fkColumn;
        this.fkName = fkName;
    }

    public String getPkTable() {
        return pkTable;
    }

    public String getPkColumn() {
        return pkColumn;
    }

    public String getFkTable() {
        return fkTable;
    }

    public String getFkColumn() {
        return fkColumn;
    }

    public String getFkName() {
        return fkName;
    }

    @Override
    public String toString() {
        return "ForeignKeyMetaData{" +
                "pkTable='" + pkTable + '\'' +
                ", pkColumn='" + pkColumn + '\'' +
                ", fkTable='" + fkTable + '\'' +
                ", fkColumn='" + fkColumn + '\'' +
                ", fkName='" + fkName + '\'' +
                '}';
    }
}
