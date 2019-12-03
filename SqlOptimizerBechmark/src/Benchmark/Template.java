package Benchmark;

public class Template extends BenchmarkObject implements IIdentifiedBenchmarkObject, INumberedBenchmarkObject {
    private PlanEquivalenceTest planEquivalenceTest;
    private ObservableCollection<SelectedAnnotation> selectedAnnotations = new ObservableCollection<SelectedAnnotation>();

    private int id = 0;
    private String number = "";
    private int expectedResultSize = 0;

    @Override
    public IBenchmarkObject ParentObject() {
        return planEquivalenceTest;
    }

    public PlanEquivalenceTest PlanEquivalenceTest()
    {
        return planEquivalenceTest;
    }

    @Override
    public IEnumerable<IBenchmarkObject>ChildObjects()
    {
        for (SelectedAnnotation selectedAnnotation : selectedAnnotations)
        {
            yield return selectedAnnotation;
        }
    }

    public int Id()
    {
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

    public int ExpectedResultSize() {
        return expectedResultSize;
    }

    public void ExpectedResultSize(int value) {
        if (expectedResultSize != value) {
            expectedResultSize = value;
            OnPropertyChanged("ExpectedResultSize");
        }
    }

    public ObservableCollection<SelectedAnnotation> SelectedAnnotations() {
        return selectedAnnotations;
    }

    public Template(PlanEquivalenceTest planEquivalenceTest) {
        this.id = planEquivalenceTest.Owner.GenerateId();
        this.planEquivalenceTest = planEquivalenceTest;
    }


    @Override
    public void LoadFromXml(BenchmarkXmlSerializer serializer) {
        if (!serializer.ReadInt("id", ref id)) {
            id = planEquivalenceTest.Owner.GenerateId();
        }
        serializer.ReadString("number", ref number);
        serializer.ReadInt("expected_result_size", ref expectedResultSize);
        serializer.ReadCollection<SelectedAnnotation>
        ("selected_annotations", "selected_annotation", selectedAnnotations,
                delegate() {
            return new SelectedAnnotation(this);
        });
    }

    @Override
    public void SaveToXml(BenchmarkXmlSerializer serializer) {
        serializer.WriteInt("id", id);
        serializer.WriteString("number", number);
        serializer.WriteInt("expected_result_size", expectedResultSize);
        serializer.WriteCollection<SelectedAnnotation>
        ("selected_annotations", "selected_annotation", selectedAnnotations);
    }

    @Override
    public DbTableInfo GetTableInfo() {
        DbTableInfo ret = super.GetTableInfo();

        ret.TableName("Template");

        ret.DbColumns().add(new DbColumnInfo("Id", "template_id", System.Data.DbType.Int32, true));
        ret.DbColumns().add(new DbColumnInfo("Number", "number", System.Data.DbType.String, 50));
        ret.DbColumns().add(new DbColumnInfo("ExpectedResultSize", "expected_result_size", System.Data.DbType.Int32));

        return ret;
    }
}