package Benchmark;

public class DbColumnInfo {
    private String property;
    private String dbColumn;
    private boolean dbPrimaryKey;
    private boolean dbAutoIncrement;
    private boolean dbForeignKey;
    private DbType dbType;
    private int dbSize;
    private String referencedTableName;
    private String referencedColumn;

    /**
     * Gets or sets a name of a class property.
     */
    public String Property()
    {
        return property; 
    }
    
    public void Property(String value)
    {
        property = value;
        if (dbColumn == null) {
            dbColumn = property;
        }
    }

    /**
     * Gets or sets the database attribute.
     */
    public String DbColumn()
    {
        return dbColumn;
    }
    
    public void DbColumn(String value)
    {
        dbColumn = value;
    }

    /**
     * Gets or sets the database data type.
     */
    public DbType DbType() {
        return dbType;
    }

    public DbType DbType(DbType value) {
        dbType = value;
    }

    /**
     * Gets or sets a size of the attribute in the database.
     */
    public int DbSize() {
        return dbSize;
    }

    public void DbSize(int value) {
        dbSize = value;
    }

    /**
     * Gets or sests whether the column is a primary key.
    */
    public boolean DbPrimaryKey() {
        return dbPrimaryKey;
    }

    public void DbPrimaryKey(boolean value) {
        dbPrimaryKey = value;
    }

    public boolean DbAutoIncrement() {
        return dbAutoIncrement;
    }

    public void DbAutoIncrement(boolean value) {
        dbAutoIncrement = value;
    }

    public DbColumnInfo() {

    }

    public DbColumnInfo(String property, String dbColumn, DbType dbType, int dbSize) {
        this.property = property;
        this.dbColumn = dbColumn;
        this.dbType = dbType;
        this.dbSize = dbSize;
    }

    public DbColumnInfo(String property, String dbColumn, DbType dbType, boolean dbPrimaryKey) {
        this.property = property;
        this.dbColumn = dbColumn;
        this.dbType = dbType;
        this.dbPrimaryKey = dbPrimaryKey;
    }

    public DbColumnInfo(String dbColumn, boolean dbPrimaryKey, boolean dbAutoIncrement) {
        this.dbColumn = dbColumn;
        this.dbType = DbType.Int32;
        this.dbPrimaryKey = dbPrimaryKey;
        this.dbAutoIncrement = dbAutoIncrement;
    }

    public DbColumnInfo(String property, String dbColumn, DbType dbType, boolean dbForeignKey, String referencedTableName, String referencedColumn) {
        this.property = property;
        this.dbColumn = dbColumn;
        this.dbType = dbType;
        this.dbForeignKey = dbForeignKey;
        this.referencedTableName = referencedTableName;
        this.referencedColumn = referencedColumn;
    }

    public DbColumnInfo(String property, String dbColumn, DbType dbType, boolean dbPrimaryKey, boolean dbForeignKey, String referencedTableName, String referencedColumn) {
        this.property = property;
        this.dbColumn = dbColumn;
        this.dbType = dbType;
        this.dbPrimaryKey = dbPrimaryKey;
        this.dbForeignKey = dbForeignKey;
        this.referencedTableName = referencedTableName;
        this.referencedColumn = referencedColumn;
    }

    public DbColumnInfo(String property, String dbColumn, DbType dbType) {
        this.property = property;
        this.dbColumn = dbColumn;
        this.dbType = dbType;
    }
}