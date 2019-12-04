package Benchmark;

public class QueryVariant extends BenchmarkObject implements IIdentifiedBenchmarkObject, INumberedBenchmarkObject, INamedBenchmarkObject, IDescribedBenchmarkObject {
    private PlanEquivalenceTest planEquivalenceTest;
    private int id = 0;
    private String number = "";
    private String name = "";
    private String description = "";

    private Statement defaultStatement;
    private ObservableCollection<SpecificStatement> specificStatements;
    private ObservableCollection<SelectedAnnotation> selectedAnnotations = new ObservableCollection<SelectedAnnotation>();

    @Override
    public IBenchmarkObject ParentObject() {
        return planEquivalenceTest;
    }

    @Override
    public IEnumerable<IBenchmarkObject> ChildObjects() {
        yield return defaultStatement;

        for (SpecificStatement specificStatement : specificStatements) {
            yield return specificStatement;
        }
        for (SelectedAnnotation selectedAnnotation : selectedAnnotations) {
            yield return selectedAnnotation;
        }
    }

    public PlanEquivalenceTest PlanEquivalenceTest() {
        return planEquivalenceTest;
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

    public Statement DefaultStatement() {
        return defaultStatement;
    }

    public ObservableCollection<SpecificStatement> SpecificStatements() {
        return specificStatements;
    }

    public ObservableCollection<SelectedAnnotation> SelectedAnnotations() {
        return selectedAnnotations;
    }

    public QueryVariant(PlanEquivalenceTest planEquivalenceTest) {
        this.id = planEquivalenceTest.Owner.GenerateId();
        this.planEquivalenceTest = planEquivalenceTest;
        defaultStatement = new Statement(this);
        specificStatements = new ObservableCollection<SpecificStatement>();
    }

    public Statement GetStatement(String providerName) {
        for (SpecificStatement specificStatement : specificStatements) {
            if (specificStatement.ProviderName == providerName) {
                return specificStatement;
            }
        }

        return defaultStatement;
    }

    public boolean HasSpecificStatement(String providerName) {
        for (SpecificStatement specificStatement : specificStatements) {
            if (specificStatement.ProviderName() == providerName) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void SaveToXml(BenchmarkXmlSerializer serializer) {
        serializer.WriteInt("id", id);
        serializer.WriteString("number", number);
        serializer.WriteString("name", name);
        serializer.WriteString("description", description);
        serializer.WriteObject("default_statement", defaultStatement);
        serializer.WriteCollection<SpecificStatement> ("specific_statements", "specific_statement", specificStatements);
        serializer.WriteCollection<SelectedAnnotation>
        ("selected_annotations", "selected_annotation", selectedAnnotations);
    }

    @Override
    public void LoadFromXml(BenchmarkXmlSerializer serializer) {
        if (!serializer.ReadInt("id", ref id)) {
            id = planEquivalenceTest.TestGroup.Benchmark.GenerateId();
        }
        serializer.ReadString("number", ref number);
        serializer.ReadString("name", ref name);
        serializer.ReadString("description", ref description);
        // Zpetna kompatibilita.
        if (!serializer.ReadObject("default_statement", defaultStatement)) {
            serializer.ReadObject("statement", defaultStatement);
        }
        serializer.ReadCollection<SpecificStatement> ("specific_statements", "specific_statement", specificStatements,
                delegate() {
            return new SpecificStatement(this);
        });
        serializer.ReadCollection<SelectedAnnotation>
        ("selected_annotations", "selected_annotation", selectedAnnotations,
                delegate() {
            return new SelectedAnnotation(this);
        });
    }

    @Override
    public DbTableInfo GetTableInfo() {
        DbTableInfo ret = new DbTableInfo();

        ret.TableName("QueryVariant");

        ret.DbColumns().add(new DbColumnInfo("Id", "query_variant_id", System.Data.DbType.Int32, true));
        ret.DbColumns().add(new DbColumnInfo("Number", "number", System.Data.DbType.String, 20));
        ret.DbColumns().add(new DbColumnInfo("Name", "name", System.Data.DbType.String, 50));
        ret.DbColumns().add(new DbColumnInfo("Description", "description", System.Data.DbType.String, 1000));

        return ret;
    }
}
