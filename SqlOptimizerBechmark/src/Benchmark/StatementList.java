package Benchmark;

public class StatementList extends BenchmarkObject {
    private Script script;

    private ObservableCollection<Statement> statements = new ObservableCollection<Statement>();

    @Override
    public IBenchmarkObject ParentObject() {
        return script;
    }

    @Override
    public IEnumerable<IBenchmarkObject> ChildObjects() {
        for (Statement statement : statements) {
            yield return statement;
        }
    }

    public ObservableCollection<Statement> Statements() {
        return statements;
    }

    public StatementList(Script script) {
        this.script = script;
        statements.CollectionChanged += Statements_CollectionChanged;
    }

    private void Statements_CollectionChanged(object sender, System.Collections.Specialized.NotifyCollectionChangedEventArgs e) {
        NotifyChange();
    }

    @Override
    public void SaveToXml(BenchmarkXmlSerializer serializer) {
        serializer.WriteCollection<Statement> ("statements", "statement", statements);
    }

    @Override
    public void LoadFromXml(BenchmarkXmlSerializer serializer) {
        serializer.ReadCollection<Statement> ("statements", "statement", statements,
                delegate() {
            return new Statement(this);
        });
    }

    @Override
    public DbTableInfo GetTableInfo() {
        DbTableInfo ret = super.GetTableInfo();

        ret.TableName("StatementList");

        return ret;
    }
}