package Benchmark;

public class Configuration extends BenchmarkObject implements IIdentifiedBenchmarkObject, INumberedBenchmarkObject, INamedBenchmarkObject, IDescribedBenchmarkObject {
    private TestGroup testGroup;
    private int id = 0;
    private String number = "";
    private String name = "";
    private String description = "";
    private Script initScript;
    private Script cleanUpScript;

    @Override
    public IBenchmarkObject ParentObject() {
        return testGroup;
    }

    @Override
    public IEnumerable<IBenchmarkObject>ChildObjects()
    {
        yield return initScript;
        yield return cleanUpScript;
    }

    public TestGroup TestGroup ()
    {
        return testGroup;
    }

    public int Id(){
        return id;
    }

    public String Number()
    {
        return number;
    }

    public void Number(String value)
    {
        if (number != value) {
            number = value;
            OnPropertyChanged("Number");
        }
    }

    public String Name() {
        return name;
    }

    public void Name(String value) {
        if (name != value) {
            name = value;
            OnPropertyChanged("Name");
        }
    }

    public String Description() {
        return description;
    }

    public void Description(String value) {
        if (description != value) {
            description = value;
            OnPropertyChanged("Description");
        }
    }

    public Script InitScript() {
        return initScript;
    }

    public Script CleanUpScript() {
        return cleanUpScript;
    }

    public Configuration(TestGroup testGroup) {
        this.id = testGroup.Owner.GenerateId();
        this.testGroup = testGroup;
        initScript = new Script(this);
        cleanUpScript = new Script(this);
    }

    @Override
    public void SaveToXml(BenchmarkXmlSerializer serializer) {
        serializer.WriteInt("id", id);
        serializer.WriteString("number", number);
        serializer.WriteString("name", name);
        serializer.WriteString("description", description);
        serializer.WriteObject("init_script", initScript);
        serializer.WriteObject("clean_up_script", cleanUpScript);
    }

    @Override
    public void LoadFromXml(BenchmarkXmlSerializer serializer) {
        if (!serializer.ReadInt("id", ref id)) {
            id = testGroup.Benchmark.GenerateId();
        }
        serializer.ReadString("name", ref name);
        serializer.ReadString("number", ref number);
        serializer.ReadString("description", ref description);
        serializer.ReadObject("init_script", initScript);
        serializer.ReadObject("clean_up_script", cleanUpScript);
    }

    @Override
    public DbTableInfo GetTableInfo() {
        DbTableInfo ret = super.GetTableInfo();

        ret.TableName = "Configuration";

        ret.DbColumns.Add(new DbColumnInfo("Id", "configuration_id", System.Data.DbType.Int32, true));
        ret.DbColumns.Add(new DbColumnInfo("Number", "number", System.Data.DbType.String, 20));
        ret.DbColumns.Add(new DbColumnInfo("Name", "name", System.Data.DbType.String, 50));
        ret.DbColumns.Add(new DbColumnInfo("Description", "description", System.Data.DbType.String, 1000));

        return ret;
    }
}
