package Benchmark;

import java.util.ArrayList;
import java.util.List;

public class DbTableInfo {
    private String tableName;
    private List<DbColumnInfo> dbColumns = new ArrayList<DbColumnInfo>();
    private List<DbDependentTableInfo> dbDependentTables = new ArrayList<DbDependentTableInfo>();

    public String TableName() {
        return tableName;
    }

    public void TableName(String value) {
        tableName = value;
    }

    public List<DbColumnInfo> DbColumns() {
        return dbColumns;
    }

    public List<DbDependentTableInfo> DbDependentTables() {
        return dbDependentTables;
    }

    public DbTableInfo() {

    }

    public DbTableInfo(String tableName) {
        this.tableName = tableName;
    }
}