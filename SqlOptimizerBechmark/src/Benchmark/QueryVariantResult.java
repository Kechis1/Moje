package Benchmark;

public class QueryVariantResult extends BenchmarkObject {
    private PlanEquivalenceTestResult planEquivalenceTestResult;
    private String query = "";
    private int tokenCount = 0;
    private int queryVariantId = 0;
    private String queryVariantNumber = "";
    private String queryVariantName = "";
    private TimeSpan queryProcessingTime = TimeSpan.Zero;
    private int expectedResultSize = 0;
    private int resultSize = 0;
    private DbProviders.QueryPlan queryPlan = null;
    private boolean started = false;
    private boolean completed = false;
    private String errorMessage = "";
    private ObservableCollection<SelectedAnnotationResult> selectedAnnotationResults = new ObservableCollection<SelectedAnnotationResult>();

    @Override
    public IBenchmarkObject ParentObject() {
        return planEquivalenceTestResult;
    }

    public String Query() {
        return query;
    }

    public void Query(String value) {
        if (query != value) {
            query = value;
            OnPropertyChanged("Query");
        }
    }

    public int TokenCount() {
        return tokenCount;
    }

    public void TokenCount(int value) {
        if (tokenCount != value) {
            tokenCount = value;
            OnPropertyChanged("TokenCount");
        }
    }

    public int QueryVariantId() {
        return queryVariantId;
    }

    public void QueryVariantId(int value) {
        if (queryVariantId != value) {
            queryVariantId = value;
            OnPropertyChanged("QueryVariantId");
        }
    }

    public String QueryVariantNumber() {
        return queryVariantNumber;
    }

    public void QueryVariantNumber(String value) {
        if (queryVariantNumber != value) {
            queryVariantNumber = value;
            OnPropertyChanged("QueryVariantNumber");
        }
    }

    public String QueryVariantName() {
        return queryVariantName;
    }

    public void QueryVariantName(String value) {
        if (queryVariantName != value) {
            queryVariantName = value;
            OnPropertyChanged("QueryVariantName");
        }
    }

    public TimeSpan QueryProcessingTime() {
        return queryProcessingTime;
    }

    public void QueryProcessingTime(Timespan value) {
        if (queryProcessingTime != value) {
            queryProcessingTime = value;
            OnPropertyChanged("QueryProcessingTime");
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

    public int ResultSize() {
        return resultSize;
    }

    public void ResultSize(int value) {
        if (resultSize != value) {
            resultSize = value;
            OnPropertyChanged("ResultSize");
        }
    }

    public DbProviders.QueryPlan QueryPlan() {
        return queryPlan;
    }

    public void QueryPlan(DbProviders.QueryPlan value) {
        if (queryPlan != value) {
            queryPlan = value;
            OnPropertyChanged("QueryPlan");
        }
    }

    public String QueryPlanWoElapsedTime() {
        if (queryPlan != null) {
            return queryPlan.ToString(false);
        } else {
            return null;
        }
    }

    public boolean Started() {
        return started;
    }

    public void Started(boolean value) {
        if (started != value) {
            started = value;
            OnPropertyChanged("Started");
        }
    }

    public boolean Completed() {
        return completed;
    }

    public void Completed(boolean value) {
        if (completed != value) {
            completed = value;
            OnPropertyChanged("Completed");
        }
    }

    public String ErrorMessage() {
        return errorMessage;
    }

    public void ErrorMessage(String value) {
        if (errorMessage != value) {
            errorMessage = value;
            OnPropertyChanged("ErrorMessage");
        }
    }

    public ObservableCollection<SelectedAnnotationResult> SelectedAnnotationResults() {
        return selectedAnnotationResults;
    }

    public QueryVariantResult(PlanEquivalenceTestResult planEquivalenceTestResult) {
        this.planEquivalenceTestResult = planEquivalenceTestResult;
    }

    @Override
    public void LoadFromXml(BenchmarkXmlSerializer serializer) {
        serializer.ReadString("query", ref query);
        serializer.ReadInt("token_count", ref tokenCount);
        serializer.ReadInt("query_variant_id", ref queryVariantId);
        serializer.ReadString("query_variant_number", ref queryVariantNumber);
        serializer.ReadString("query_variant_name", ref queryVariantName);
        serializer.ReadTimeSpan("query_processing_time", ref queryProcessingTime);
        serializer.ReadInt("expected_result_size", ref expectedResultSize);
        serializer.ReadInt("result_size", ref resultSize);
        serializer.ReadBool("started", ref completed);
        serializer.ReadBool("completed", ref completed);
        serializer.ReadString("error_message", ref errorMessage);

        serializer.ReadCollection<SelectedAnnotationResult>
        ("selected_annotation_results", "selected_annotation_result", selectedAnnotationResults,
                delegate() {
            return new SelectedAnnotationResult(this);
        });


        XElement eQueryPlan = serializer.ReadXml("query_plan");
        if (eQueryPlan != null) {
            queryPlan = new DbProviders.QueryPlan();
            queryPlan.LoadFromXml(eQueryPlan);
        } else {
            queryPlan = null;
        }
    }

    @Override
    public void SaveToXml(BenchmarkXmlSerializer serializer) {
        serializer.WriteString("query", query);
        serializer.WriteInt("token_count", tokenCount);
        serializer.WriteInt("query_variant_id", queryVariantId);
        serializer.WriteString("query_variant_number", queryVariantNumber);
        serializer.WriteString("query_variant_name", queryVariantName);
        serializer.WriteTimeSpan("query_processing_time", queryProcessingTime);
        serializer.WriteInt("expected_result_size", expectedResultSize);
        serializer.WriteInt("result_size", resultSize);
        serializer.WriteBool("started", completed);
        serializer.WriteBool("completed", completed);
        serializer.WriteString("error_message", errorMessage);

        serializer.WriteCollection<SelectedAnnotationResult>
        ("selected_annotation_results", "selected_annotation_result",
                selectedAnnotationResults);

        if (queryPlan != null) {
            XElement eQueryPlan = new XElement("query_plan");
            queryPlan.SaveToXml(eQueryPlan);
            serializer.WriteXml(eQueryPlan);
        }
    }

    /// <summary>
    /// Returns an unique code of the query variant including variant number, test number, group number and template number.
    /// </summary>
    /// <returns></returns>
    public String GetCode() {
        TestGroupResult testGroupResult = planEquivalenceTestResult.TestRun.GetTestGroupResult(planEquivalenceTestResult.TestGroupId);
        ConfigurationResult configurationResult = planEquivalenceTestResult.TestRun.GetConfigurationResult(planEquivalenceTestResult.ConfigurationId);

        String testNumber = planEquivalenceTestResult.TestNumber;
        if (!String.IsNullOrEmpty(planEquivalenceTestResult.TemplateNumber)) {
            testNumber += "/" + planEquivalenceTestResult.TemplateNumber;
        }

        String code = String.Format("{0}-{1}-{2}-{3}",
                testGroupResult.TestGroupNumber, configurationResult.ConfigurationNumber,
                testNumber, queryVariantNumber);

        return code;
    }

    public void ExportToCsv(StreamWriter writer, CsvExportOptions exportOptions) {
        if ((exportOptions & CsvExportOptions.ExportQueryVariants) > 0) {
            TestGroupResult testGroupResult = planEquivalenceTestResult.TestRun.GetTestGroupResult(planEquivalenceTestResult.TestGroupId);
            ConfigurationResult configurationResult = planEquivalenceTestResult.TestRun.GetConfigurationResult(planEquivalenceTestResult.ConfigurationId);

            String testAnnotationsStr = "";
            for (int annotationId : planEquivalenceTestResult.
                    SelectedAnnotationResults.Select(ar = > ar.AnnotationId))
            {
                AnnotationResult annotationResult = planEquivalenceTestResult.TestRun.GetAnnotationResult(annotationId);
                String annotationStr = annotationResult.AnnotationNumber;
                if (!String.IsNullOrEmpty(testAnnotationsStr)) {
                    testAnnotationsStr += ",";
                }
                testAnnotationsStr += annotationStr;
            }

            String variantAnnotationsStr = "";
            for (int annotationId : this.SelectedAnnotationResults.Select(ar = > ar.AnnotationId))
            {
                AnnotationResult annotationResult = planEquivalenceTestResult.TestRun.GetAnnotationResult(annotationId);
                String annotationStr = annotationResult.AnnotationNumber;
                if (!String.IsNullOrEmpty(variantAnnotationsStr)) {
                    variantAnnotationsStr += ",";
                }
                variantAnnotationsStr += annotationStr;
            }

            String code = GetCode();

            writer.WriteLine("{0};{1};{2};{3};{4};{5};{6};{7};{8};{9};{10};{11};{12}",
                    TestRun.GetCsvStr(code),
                    TestRun.GetCsvStr(testGroupResult.TestGroupName),
                    TestRun.GetCsvStr(configurationResult.ConfigurationName),
                    TestRun.GetCsvStr(planEquivalenceTestResult.TestName),
                    TestRun.GetCsvStr(testAnnotationsStr),
                    TestRun.GetCsvStr(this.QueryVariantName),
                    TestRun.GetCsvStr(variantAnnotationsStr),
                    TestRun.GetCsvStr(Convert.ToString(this.resultSize)),
                    TestRun.GetCsvStr(Convert.ToString(this.queryProcessingTime)),
                    TestRun.GetCsvStr(Convert.ToString(this.queryPlan)),
                    queryPlan != null && queryPlan.Root != null ? Convert.ToString(queryPlan.Root.EstimatedCost) : "",
                    queryPlan != null && queryPlan.Root != null ? Convert.ToString(queryPlan.Root.EstimatedRows) : "",
                    queryPlan != null && queryPlan.Root != null ? Convert.ToString(queryPlan.Root.ActualRows) : ""
            );
        }
    }

    @Override
    public DbTableInfo GetTableInfo() {
        DbTableInfo ret = super.GetTableInfo();

        ret.TableName("QueryVariantResult");

        ret.DbColumns().add(new DbColumnInfo("query_variant_result_id", true, true)); // PK
        ret.DbColumns().add(new DbColumnInfo(null, "test_result_id", System.Data.DbType.Int32, true, "TestResult", "test_result_id")); // FK

        ret.DbColumns().add(new DbColumnInfo("Query", "query", System.Data.DbType.String, 1000));
        ret.DbColumns().add(new DbColumnInfo("TokenCount", "token_count", System.Data.DbType.Int32));
        ret.DbColumns().add(new DbColumnInfo("QueryVariantNumber", "query_variant_number", System.Data.DbType.String, 20));
        ret.DbColumns().add(new DbColumnInfo("QueryVariantName", "query_variant_name", System.Data.DbType.String, 50));
        ret.DbColumns().add(new DbColumnInfo("QueryProcessingTime", "query_processing_time", System.Data.DbType.Double));
        ret.DbColumns().add(new DbColumnInfo("ExpectedResultSize", "expected_result_size", System.Data.DbType.Int32));
        ret.DbColumns().add(new DbColumnInfo("ResultSize", "result_size", System.Data.DbType.Int32));
        ret.DbColumns().add(new DbColumnInfo("Started", "started", System.Data.DbType.Boolean));
        ret.DbColumns().add(new DbColumnInfo("Completed", "completed", System.Data.DbType.Boolean));
        ret.DbColumns().add(new DbColumnInfo("ErrorMessage", "error_message", System.Data.DbType.String, 1000));
        ret.DbColumns().add(new DbColumnInfo("QueryVariantId", "query_variant_id", System.Data.DbType.Int32, true, "QueryVariant", "query_variant_id")); // FK
        ret.DbColumns().add(new DbColumnInfo("QueryPlanWoElapsedTime", "query_plan", System.Data.DbType.String, 1000));

        ret.DbDependentTables().add(new DbDependentTableInfo("SelectedAnnotationResults", "SelectedAnnotationResult", "query_variant_result_id"));

        return ret;
    }
}