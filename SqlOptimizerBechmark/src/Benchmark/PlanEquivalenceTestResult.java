package Benchmark;
    public class PlanEquivalenceTestResult extends TestResult
    {
        private int distinctQueryPlans = 0;
        private int successfullyCompletedVariants = 0;
        private ObservableCollection<QueryVariantResult> queryVariantResults = new ObservableCollection<QueryVariantResult>();
        private ObservableCollection<SelectedAnnotationResult> selectedAnnotationResults = new ObservableCollection<SelectedAnnotationResult>();
        private boolean started = false;
        private boolean completed = false;
        private String templateNumber = "";
        
        @Override
        public IEnumerable<IBenchmarkObject> ChildObjects()
        {
            for (QueryVariantResult queryVariantResult in queryVariantResults)
            {
                yield return queryVariantResult;
            }
        }

        public int DistinctQueryPlans()
        {
            return distinctQueryPlans;
        }

        public void DistinctQueryPlans(int value)
        {
            if (distinctQueryPlans != value)
            {
                distinctQueryPlans = value;
                OnPropertyChanged("DistinctQueryPlans");
            }
        }

        public int SuccessfullyCompletedVariants() {
            return successfullyCompletedVariants;
        }

        public void SuccessfullyCompletedVariants(int value) {
            if (successfullyCompletedVariants != value)
            {
                successfullyCompletedVariants = value;
                OnPropertyChanged("SuccessfullyCompletedVariants");
            }
        }

        public String TemplateNumber()
        {
            return templateNumber;
        }

        public void TemplateNumber(String value)
        {
            if (templateNumber != value)
            {
                templateNumber = value;
                OnPropertyChanged("TemplateNumber");
            }
        }

        public boolean Success()
        { 
            return completed && distinctQueryPlans == 1 && successfullyCompletedVariants > 1 && String.IsNullOrEmpty(ErrorMessage);
        }

        public ObservableCollection<QueryVariantResult> QueryVariantResults() {
            return queryVariantResults;
        }

        public ObservableCollection<SelectedAnnotationResult> SelectedAnnotationResults() {
            return selectedAnnotationResults;
        }

        public boolean Started() {
            return started;
        }

        public void Started(boolean value) {
            if (started != value)
            {
                started = value;
                OnPropertyChanged("Started");
            }
        }

        public boolean Completed()
        {
            return completed;
        }

        public void Completed(boolean value)
        {
            if (completed != value)
            {
                completed = value;
                OnPropertyChanged("Completed");
            }
        }

        public PlanEquivalenceTestResult(TestRun testRun) : super(testRun)
        {

        }

        @Override
        public void LoadFromXml(BenchmarkXmlSerializer serializer)
        {
            super.LoadFromXml(serializer);
            serializer.ReadInt("distinct_query_plans", ref distinctQueryPlans);
            serializer.ReadInt("successfully_completed_variants", ref successfullyCompletedVariants);
            serializer.ReadCollection<QueryVariantResult>("query_variant_results", "query_variant_result", queryVariantResults,
                delegate () { return new QueryVariantResult(this); });
            serializer.ReadCollection<SelectedAnnotationResult>("selected_annotation_results", "selected_annotation_result", selectedAnnotationResults,
                delegate () { return new SelectedAnnotationResult(this); });
            serializer.ReadBool("started", ref started);
            serializer.ReadBool("completed", ref completed);
            serializer.ReadString("template_number", ref templateNumber);
        }

        @Override
        public void SaveToXml(BenchmarkXmlSerializer serializer)
        {
            super.SaveToXml(serializer);
            serializer.WriteInt("distinct_query_plans", distinctQueryPlans);
            serializer.WriteInt("successfully_completed_variants", successfullyCompletedVariants);
            serializer.WriteCollection<QueryVariantResult>("query_variant_results", "query_variant_result", queryVariantResults);
            serializer.WriteCollection<SelectedAnnotationResult>("selected_annotation_results", "selected_annotation_result", selectedAnnotationResults);
            serializer.WriteBool("started", started);
            serializer.WriteBool("completed", completed);
            serializer.WriteString("template_number", templateNumber);
        }

        @Override
        public void ExportToCsv(StreamWriter writer, CsvExportOptions exportOptions)
        {
            if (!completed)
            {
                return;
            }

            if ((exportOptions & CsvExportOptions.ExportDistinctPlans.getValue()) > 0)
            {
                TestGroupResult testGroupResult = TestRun.GetTestGroupResult(TestGroupId);
                ConfigurationResult configurationResult = TestRun.GetConfigurationResult(ConfigurationId);

                String annotationsStr = "";
                for (int annotationId in selectedAnnotationResults.Select(ar => ar.AnnotationId))
                {
                    AnnotationResult annotationResult = TestRun.GetAnnotationResult(annotationId);
                    String annotationStr = annotationResult.AnnotationNumber;
                    if (!String.IsNullOrEmpty(annotationsStr))
                    {
                        annotationsStr += ",";
                    }
                    annotationsStr += annotationStr;
                }

                String code = String.Format("{0}-{1}-{2}", testGroupResult.TestGroupNumber,
                    configurationResult.ConfigurationNumber, this.TestNumber);
                if (!String.IsNullOrEmpty(templateNumber))
                {
                    code += "/" + templateNumber;
                }

                writer.WriteLine("{0};{1};{2};{3};{4};{5};{6}",
                    TestRun.GetCsvStr(code),
                    TestRun.GetCsvStr(testGroupResult.TestGroupName),
                    TestRun.GetCsvStr(configurationResult.ConfigurationName),
                    TestRun.GetCsvStr(this.TestName),
                    TestRun.GetCsvStr(annotationsStr),
                    TestRun.GetCsvStr(Convert.ToString(this.distinctQueryPlans)),
                    TestRun.GetCsvStr(Convert.ToString(this.successfullyCompletedVariants)));
            }

            if ((exportOptions & CsvExportOptions.ExportQueryVariants) > 0)
            {
                for (QueryVariantResult variantResult : queryVariantResults)
                {
                    variantResult.ExportToCsv(writer, exportOptions);
                }
            }
        }

        @Override
        public DbTableInfo GetTableInfo()
        {
            DbTableInfo ret = super.GetTableInfo();

            // TODO - nebude fungovat pro vice typu testu.

            ret.DbColumns().add(new DbColumnInfo("DistinctQueryPlans", "distinct_query_plans", System.Data.DbType.Int32));
            ret.DbColumns().add(new DbColumnInfo("SuccessfullyCompletedVariants", "successfully_completed_variants", System.Data.DbType.Int32));
            ret.DbColumns().add(new DbColumnInfo("Started", "started", System.Data.DbType.Boolean));
            ret.DbColumns().add(new DbColumnInfo("Completed", "completed", System.Data.DbType.Boolean));
            ret.DbColumns().add(new DbColumnInfo("TemplateNumber", "template_number", System.Data.DbType.String, 20));

            ret.DbDependentTables().add(new DbDependentTableInfo("QueryVariantResults", "QueryVariantResult", "test_result_id"));
            ret.DbDependentTables().add(new DbDependentTableInfo("SelectedAnnotationResults", "SelectedAnnotationResult", "test_result_id"));

            return ret;
        }
    }
