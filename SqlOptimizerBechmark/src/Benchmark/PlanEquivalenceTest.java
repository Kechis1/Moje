package Benchmark;

public class PlanEquivalenceTest extends Test {
    private ObservableCollection<QueryVariant> variants = new ObservableCollection<QueryVariant>();
    private ObservableCollection<SelectedAnnotation> selectedAnnotations = new ObservableCollection<SelectedAnnotation>();
    private ObservableCollection<Parameter> parameters = new ObservableCollection<Parameter>();
    private ObservableCollection<Template> templates = new ObservableCollection<Template>();
    private ObservableCollection<ParameterValue> parameterValues = new ObservableCollection<ParameterValue>();

    private int expectedResultSize = 0;
    private boolean parametrized;

    @Override
    public IEnumerable<IBenchmarkObject>ChildObjects()
    {
        for(QueryVariant variant : variants)
        {
            yield return variant;
        }
        for(SelectedAnnotation selectedAnnotation : selectedAnnotations)
        {
            yield return selectedAnnotation;
        }
    }

    @Override
    public TestType TestType() {
        return TestType.PlanEquivalence;
    }

    public ObservableCollection<QueryVariant> Variants()
    {
        return variants;
    }

    public ObservableCollection<SelectedAnnotation> SelectedAnnotations()

    {
        return selectedAnnotations;
    }

    public ObservableCollection<Parameter> Parameters()

    {
        return parameters;
    }

    public ObservableCollection<Template> Templates()

    {
        return templates;
    }

    public ObservableCollection<ParameterValue> ParameterValues()

    {
        return parameterValues;
    }

    public int ExpectedResultSize()

    {
        return expectedResultSize;
    }

    public void ExpectedResultSize(int value)

    {
        if (value != expectedResultSize) {
            expectedResultSize = value;
            OnPropertyChanged("ExpectedResultSize");
        }
    }

    public boolean Parametrized()

    {
        return parametrized;
    }

    public void Parametrized(boolean value)

    {
        if (value != parametrized) {
            parametrized = value;
            OnPropertyChanged("Parametrized");
        }
    }

    public PlanEquivalenceTest(TestGroup testGroup) : super(testGroup) {
        variants.CollectionChanged += Variants_CollectionChanged;
    }

    private void Variants_CollectionChanged(object sender, System.Collections.Specialized.NotifyCollectionChangedEventArgs e) {
        NotifyChange();
    }

    @Override
    public void SaveToXml(BenchmarkXmlSerializer serializer) {
        base.SaveToXml(serializer);
        serializer.WriteCollection<QueryVariant> ("variants", "variant", variants);
        serializer.WriteCollection<SelectedAnnotation>
        ("selected_annotations", "selected_annotation", selectedAnnotations);
        serializer.WriteInt("expected_result_size", expectedResultSize);
        serializer.WriteBool("parametrized", parametrized);
        if (parametrized) {
            serializer.WriteCollection<Parameter> ("parameters", "parameter", parameters);
            serializer.WriteCollection<Template> ("templates", "template", templates);
            serializer.WriteCollection<ParameterValue> ("parameter_values", "parameter_value", parameterValues);
        }
    }

    @Override
    public void LoadFromXml(BenchmarkXmlSerializer serializer) {
        super.LoadFromXml(serializer);
        serializer.ReadCollection<QueryVariant> ("variants", "variant", variants,
                delegate() {
            return new QueryVariant(this);
        });
        serializer.ReadCollection<SelectedAnnotation>
        ("selected_annotations", "selected_annotation", selectedAnnotations,
                delegate() {
            return new SelectedAnnotation(this);
        });
        serializer.ReadInt("expected_result_size", ref expectedResultSize);
        serializer.ReadBool("parametrized", ref parametrized);
        if (parametrized) {
            serializer.ReadCollection<Parameter> ("parameters", "parameter", parameters,
                    delegate() {
                return new Parameter(this);
            });
            serializer.ReadCollection<Template> ("templates", "template", templates,
                    delegate() {
                return new Template(this);
            });
            serializer.ReadCollection<ParameterValue> ("parameter_values", "parameter_value", parameterValues,
                    delegate() {
                return new ParameterValue(this);
            });
        }
    }

    @Override
    public DbTableInfo GetTableInfo() {
        DbTableInfo ret = super.GetTableInfo();

        ret.DbColumns().add(new DbColumnInfo("ExpectedResultSize", "expected_result_size", System.Data.DbType.Int32));
        ret.DbColumns().add(new DbColumnInfo("Parametrized", "parametrized", System.Data.DbType.Boolean));

        return ret;
    }
}
