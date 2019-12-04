package Benchmark;

public class Statement extends BenchmarkObject {
    private BenchmarkObject parentObject;
    private String commandText = "";

    @Override
    public IBenchmarkObject ParentObject() {
        return parentObject;
    }

    public String CommandText() {
        return commandText;
    }

    public void CommandText(String value) {
        if (commandText != value) {
            commandText = value;
            OnPropertyChanged("CommandText");
        }
    }

    public Statement(BenchmarkObject parentObject) {
        this.parentObject = parentObject;
    }

    @Override
    public void SaveToXml(BenchmarkXmlSerializer serializer) {
        serializer.WriteString("command_text", commandText);
    }

    @Override
    public void LoadFromXml(BenchmarkXmlSerializer serializer) {
        serializer.ReadString("command_text", commandText);
    }

    @Override
    public DbTableInfo GetTableInfo() {
        DbTableInfo ret = super.GetTableInfo();

        ret.TableName("Statement");

        ret.DbColumns().add(new DbColumnInfo("CommandText", "command_text", System.Data.DbType.String, 1000));

        return ret;
    }
}
