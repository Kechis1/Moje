package Benchmark;

public class TestRunSettings extends BenchmarkObject {
    private Benchmark benchmark;

    @Override
    public IBenchmarkObject ParentObject() {
        return benchmark;
    }

    private boolean runInitScript = true;
    private boolean runCleanUpScript = true;
    private boolean checkResultSizes = true;
    private boolean compareResults = true;
    private ObservableCollection<SelectedAnnotation> ignoreAnnotations = new ObservableCollection<SelectedAnnotation>();
    private int queryRuns = 1;
    private int testLoops = 1;
    private boolean closeOnComplete = false;

    public boolean RunInitScript() {
        return runInitScript;
    }

    public void RunInitScript(boolean value) {
        if (runInitScript != value) {
            runInitScript = value;
            OnPropertyChanged("RunInitScript");
        }
    }

    public boolean RunCleanUpScript() {
        return runCleanUpScript;
    }

    public void RunCleanUpScript(boolean value) {
        if (runCleanUpScript != value) {
            runCleanUpScript = value;
            OnPropertyChanged("RunCleanUpScript");
        }
    }

    public boolean CheckResultSizes() {
        return checkResultSizes;
    }

    public void CheckResultSizes(boolean value) {
        if (checkResultSizes != value) {
            checkResultSizes = value;
            OnPropertyChanged("CheckResultSizes");
        }
    }

    public boolean CompareResults() {
        return compareResults;
    }

    public void CompareResults(boolean value) {
        if (compareResults != value) {
            compareResults = value;
            OnPropertyChanged("CompareResults");
        }
    }

    public int QueryRuns()
    {
        return queryRuns; 
    }

    public void QueryRuns(int value)
    {
            if (queryRuns != value) {
                    queryRuns = value;
                    OnPropertyChanged("QueryRuns");
            }
    }

    public int TestLoops()
    {
        return testLoops; 
    }

    public void TestLoops(int value)
    {
            if (testLoops != value) {
                    testLoops = value;
                    OnPropertyChanged("TestLoops");
            }
    }

    public boolean CloseOnComplete() {
        return closeOnComplete; 
    }

    public void CloseOnComplete(boolean value) {
            if (closeOnComplete != value) {
                    closeOnComplete = value;
                    OnPropertyChanged("CloseOnComplete");
            }
    }

    public ObservableCollection<SelectedAnnotation> IgnoreAnnotations()
    {
        return ignoreAnnotations;
    }

    public TestRunSettings(Benchmark benchmark) {
        this.benchmark = benchmark;
    }

    @Override
    public void LoadFromXml(BenchmarkXmlSerializer serializer) {
        serializer.ReadBool("run_init_script", ref runInitScript);
        serializer.ReadBool("run_clean_up_script", ref runCleanUpScript);
        serializer.ReadBool("check_result_sizes", ref checkResultSizes);
        serializer.ReadBool("compare_results", ref compareResults);
        serializer.ReadInt("query_runs", ref queryRuns);
        serializer.ReadInt("test_loops", ref testLoops);
        serializer.ReadBool("close_on_complete", ref closeOnComplete);
        serializer.ReadCollection<SelectedAnnotation> ("ignore_annotations", "ignore_annotation", ignoreAnnotations,
                delegate() {
            return new SelectedAnnotation(this);
        });
    }

    @Override
    public void SaveToXml(BenchmarkXmlSerializer serializer) {
        serializer.WriteBool("run_init_script", runInitScript);
        serializer.WriteBool("run_clean_up_script", runCleanUpScript);
        serializer.WriteBool("check_result_sizes", checkResultSizes);
        serializer.WriteBool("compare_results", compareResults);
        serializer.WriteInt("query_runs", queryRuns);
        serializer.WriteInt("test_loops", testLoops);
        serializer.WriteBool("close_on_complete", closeOnComplete);
        serializer.WriteCollection<SelectedAnnotation> ("ignore_annotations", "ignore_annotation", ignoreAnnotations);
    }

    @Override
    public DbTableInfo GetTableInfo() {
        DbTableInfo ret = super.GetTableInfo();

        ret.TableName("TestRunSettings");

        ret.DbColumns().add(new DbColumnInfo("RunInitScript", "run_init_script", System.Data.DbType.Boolean));
        ret.DbColumns().add(new DbColumnInfo("RunCleanUpScript", "run_clean_up_script", System.Data.DbType.Boolean));
        ret.DbColumns().add(new DbColumnInfo("CheckResultSizes", "check_result_sizes", System.Data.DbType.Boolean));
        ret.DbColumns().add(new DbColumnInfo("CompareResults", "compare_results", System.Data.DbType.Boolean));
        ret.DbColumns().add(new DbColumnInfo("QueryRuns", "query_runs", System.Data.DbType.Int32));
        ret.DbColumns().add(new DbColumnInfo("TestLoops", "test_loops", System.Data.DbType.Int32));
        ret.DbColumns().add(new DbColumnInfo("CloseOnComplete", "close_on_complete", System.Data.DbType.Boolean));

        return ret;
    }
}