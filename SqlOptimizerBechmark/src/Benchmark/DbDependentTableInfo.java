package Benchmark;

public class DbDependentTableInfo {
    private String property;
    private String dbTableName;
    private String dbFkColumn;
    private boolean oneToOne;

    public String Property() {
        return property;
    }

    public void Property(String value) {
        property = value;
    }

    public String DbTableName() {
        return dbTableName;
    }

    public void DbTableName(String value) {
        dbTableName = value;
    }

    public String DbFkColumn() {
        return dbFkColumn;
    }

    public void DbFkColumn(String value) {
        dbFkColumn = value;
    }

    public boolean OneToOne() {
        return oneToOne;
    }

    public void OneToOne(boolean value) {
        oneToOne = value;
    }

    public DbDependentTableInfo(String property, String dbTableName, String dbFkColumn, boolean oneToOne) {
        this.property = property;
        this.dbTableName = dbTableName;
        this.dbFkColumn = dbFkColumn;
        this.oneToOne = oneToOne;
    }

    public DbDependentTableInfo(String property, String dbTableName, String dbFkColumn) {
        this.property = property;
        this.dbTableName = dbTableName;
        this.dbFkColumn = dbFkColumn;
        this.oneToOne = false;
    }
}
