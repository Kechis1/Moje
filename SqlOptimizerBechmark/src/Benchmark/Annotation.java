package Benchmark;

public class Annotation extends BenchmarkObject implements IIdentifiedBenchmarkObject, INumberedBenchmarkObject, INamedBenchmarkObject, IDescribedBenchmarkObject {
    private Benchmark benchmark;
    private int id = 0;
    private String number = "";
    private String name = "";
    private String description = "";

    public IBenchmarkObject ParentObject() {
        return benchmark;
    }

    public Benchmark Benchmark() {
        return benchmark;
    }

    public int Id() {
        return id;
    }

    public String Number() {
        return number;
    }
    
    public void Number(String value) {
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

    public Annotation(Benchmark benchmark) {
        this.id = benchmark.GenerateId();
        this.benchmark = benchmark;
    }

    public void SaveToXml(BenchmarkXmlSerializer serializer) {
        serializer.WriteInt("id", id);
        serializer.WriteString("number", number);
        serializer.WriteString("name", name);
        serializer.WriteString("description", description);
    }

    public void LoadFromXml(BenchmarkXmlSerializer serializer) {
        if (!serializer.ReadInt("id", id)) {
            id = benchmark.GenerateId();
        }
        serializer.ReadString("number", number);
        serializer.ReadString("name", name);
        serializer.ReadString("description", description);
    }

    public DbTableInfo GetTableInfo() {
        DbTableInfo ret = super.GetTableInfo();

        ret.TableName("Annotation");

        ret.DbColumns().add(new DbColumnInfo("Id", "annotation_id", System.Data.DbType.Int32, true)); // PK
        ret.DbColumns().add(new DbColumnInfo(null, "benchmark_id", System.Data.DbType.Int32, true, "Benchmark", "benchmark_id")); // FK

        ret.DbColumns().add(new DbColumnInfo("Number", "number", System.Data.DbType.String, 20));
        ret.DbColumns().add(new DbColumnInfo("Name", "name", System.Data.DbType.String, 50));
        ret.DbColumns().add(new DbColumnInfo("Description", "description", System.Data.DbType.String, 1000));

        return ret;
    }
}
