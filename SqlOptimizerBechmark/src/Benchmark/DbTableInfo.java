package Benchmark;

public class DbTableInfo {
    private String tableName;
    private IList<DbColumnInfo> dbColumns = new List<DbColumnInfo>();
    private IList<DbDependentTableInfo> dbDependentTables = new List<DbDependentTableInfo>();

    public String TableName

    {
        get =>tableName;
        set =>tableName = value;
    }

    public IList<DbColumnInfo> DbColumns

    {
        get =>dbColumns;
    }

    public IList<DbDependentTableInfo> DbDependentTables

    {
        get =>dbDependentTables;
    }

    public DbTableInfo() {

    }

    public DbTableInfo(String tableName) {
        this.tableName = tableName;
    }
}