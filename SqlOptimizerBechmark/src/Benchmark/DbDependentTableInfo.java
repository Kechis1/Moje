package Benchmark;

public class DbDependentTableInfo {
    private String property;
    private String dbTableName;
    private String dbFkColumn;
    private bool oneToOne;

    public String Property

    {
        get =>property;
        set =>property = value;
    }

    public String DbTableName

    {
        get =>dbTableName;
        set =>dbTableName = value;
    }

    public String DbFkColumn

    {
        get =>dbFkColumn;
        set =>dbFkColumn = value;
    }

    public bool OneToOne

    {
        get =>oneToOne;
        set =>oneToOne = value;
    }

    public DbDependentTableInfo(String property, String dbTableName, String dbFkColumn, bool oneToOne =false) {
        this.property = property;
        this.dbTableName = dbTableName;
        this.dbFkColumn = dbFkColumn;
        this.oneToOne = oneToOne;
    }
}
