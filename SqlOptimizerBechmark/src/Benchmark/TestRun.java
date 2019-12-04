package Benchmark;

import javafx.collections.ObservableList;

import java.util.Date;

public class TestRun extends BenchmarkObject implements IIdentifiedBenchmarkObject, INamedBenchmarkObject
    {
        private Benchmark benchmark;
        private int id;
        private String name = "";
        private Date startDate = Date.MinValue;
        private Date endDate = Date.MinValue;
        private String settingsInfo = "";
        private String executorInfo = "";

        private ObservableList<TestGroupResult> testGroupResults = new ObservableList<TestGroupResult>();
        private ObservableList<ConfigurationResult> configurationResults = new ObservableList<ConfigurationResult>();
        private ObservableList<AnnotationResult> annotationResults = new ObservableList<AnnotationResult>();
        private ObservableList<TestResult> testResults = new ObservableList<TestResult>();

        public IBenchmarkObject ParentObject() {
            return benchmark;
        }

        public IEnumerable<IBenchmarkObject> ChildObjects()
        {
            for (TestGroupResult testGroupResult : testGroupResults)
            {
                yield return testGroupResult;
            }

            for (ConfigurationResult configurationResult : configurationResults)
            {
                yield return configurationResult;
            }

            for (AnnotationResult annotationResult : annotationResults)
            {
                yield return annotationResult;
            }

            for (TestResult testResult : testResults)
            {
                yield return testResult;
            }
        }

        public int Id() {
            return id;
        }

        public Benchmark Benchmark() {
            return benchmark;
        }

        public String Name()
        {
            return name;        
        }
        public void Name(String value)
        {
            if (name != value)
            {
                name = value;
                OnPropertyChanged("Name");
            }     
        }

        public Date StartDate()
        {
            return startDate; 
        }

        public void StartDate(Date value)
        {
            if (startDate != value)
            {
                startDate = value;
                OnPropertyChanged("StartDate");
            }
        }

        public Date EndDate()
        {
            return endDate; 
        }

        public void EndDate(Date value)
        {
            if (endDate != value)
            {
                endDate = value;
                OnPropertyChanged("EndDate");
            }
        }

        public String SettingsInfo()
        {
            return settingsInfo; 
        }

        public void SettingsInfo(String value)
        {
            if (settingsInfo != value)
            {
                settingsInfo = value;
                OnPropertyChanged("SettingsInfo");
            }
        }

        public String ExecutorInfo()
        {
            return executorInfo; 
        }

        public void ExecutorInfo(String value)
        {
            if (executorInfo != value)
            {
                executorInfo = value;
                OnPropertyChanged("ExecutorInfo");
            }
        }

        public ObservableList<TestGroupResult> TestGroupResults => testGroupResults;

        public ObservableList<ConfigurationResult> ConfigurationResults => configurationResults;

        public ObservableList<AnnotationResult> AnnotationResults => annotationResults;

        public ObservableList<TestResult> TestResults => testResults;


        public TestRun(Benchmark benchmark)
        {
            this.id = benchmark.GenerateId();
            this.benchmark = benchmark;
        }

        public TestGroupResult GetTestGroupResult(int testGroupId)
        {
            return testGroupResults.Where(t => t.TestGroupId == testGroupId).FirstOrDefault();
        }

        public ConfigurationResult GetConfigurationResult(int configurationId)
        {
            return configurationResults.Where(c => c.ConfigurationId == configurationId).FirstOrDefault();
        }

        public AnnotationResult GetAnnotationResult(int annotationId)
        {
            return annotationResults.Where(a => a.AnnotationId == annotationId).FirstOrDefault();
        }

        public void LoadFromXml(BenchmarkXmlSerializer serializer)
        {
            serializer.ReadInt("id", ref id);
            serializer.ReadString("name", ref name);
            serializer.ReadDate("start_date", ref startDate);
            serializer.ReadDate("end_date", ref endDate);
            serializer.ReadString("settings_info", ref settingsInfo);
            serializer.ReadString("executor_info", ref executorInfo);

            serializer.ReadCollection<ConfigurationResult>("configuration_results", "configuration_result", configurationResults,
                delegate () { return new ConfigurationResult(this); });

            serializer.ReadCollection<TestGroupResult>("test_group_results", "test_group_result", testGroupResults,
                delegate () { return new TestGroupResult(this); });

            serializer.ReadCollection<AnnotationResult>("annotation_results", "annotation_result", annotationResults,
                delegate () { return new AnnotationResult(this); });

            serializer.ReadCollection<TestResult>("test_results", "test_result", testResults, // TODO - various test types
                delegate () { return new PlanEquivalenceTestResult(this); });
        }

        public void SaveToXml(BenchmarkXmlSerializer serializer)
        {
            serializer.WriteInt("id", id);
            serializer.WriteString("name", name);
            serializer.WriteDate("start_date", startDate);
            serializer.WriteDate("end_date", endDate);
            serializer.WriteString("settings_info", settingsInfo);
            serializer.WriteString("executor_info", executorInfo);
            serializer.WriteCollection<ConfigurationResult>("configuration_results", "configuration_result", configurationResults);
            serializer.WriteCollection<TestGroupResult>("test_group_results", "test_group_result", testGroupResults);
            serializer.WriteCollection<AnnotationResult>("annotation_results", "annotation_result", annotationResults);
            serializer.WriteCollection<TestResult>("test_results", "test_result", testResults);
        }

        public static String GetCsvStr(String str)
        {
            return String.Format("\"{0}\"", str.Replace("\"", "\"\""));
        }

        public void ExportToCsv(StreamWriter writer, CsvExportOptions exportOptions)
        {
            if ((exportOptions & CsvExportOptions.ExportDistinctPlans) > 0)
            {
                writer.WriteLine("code;group;configuration;test;test annotations;distinct plans;completed variants");
            }

            if ((exportOptions & CsvExportOptions.ExportQueryVariants) > 0)
            {
                writer.WriteLine("code;group;configuration;test;test annotations;variant;variant annotations;result size;processing time;query plan;root cost;root estimated rows;root actual rows");
            }

            for (TestResult testResult : testResults)
            {
                testResult.ExportToCsv(writer, exportOptions);
            }
        }

        public void ExportToCsv(String fileName, CsvExportOptions exportOptions)
        {
            StreamWriter writer = new StreamWriter(fileName);
            ExportToCsv(writer, exportOptions);
            writer.Close();
        }

        public DbTableInfo GetTableInfo()
        {
            DbTableInfo ret = super.GetTableInfo();

            ret.TableName("TestRun");

            ret.DbColumns().add(new DbColumnInfo("Id", "test_run_id", System.Data.DbType.Int32, true)); // PK
            ret.DbColumns().add(new DbColumnInfo(null, "benchmark_id", System.Data.DbType.Int32, true, "Benchmark", "benchmark_id")); // FK

            ret.DbColumns().add(new DbColumnInfo("Name", "name", System.Data.DbType.String, 50));
            ret.DbColumns().add(new DbColumnInfo("StartDate", "start_date", System.Data.DbType.Date));
            ret.DbColumns().add(new DbColumnInfo("EndDate", "end_date", System.Data.DbType.Date));
            ret.DbColumns().add(new DbColumnInfo("SettingsInfo", "settings_info", System.Data.DbType.String, 300));
            ret.DbColumns().add(new DbColumnInfo("ExecutorInfo", "executor_info", System.Data.DbType.String, 300));

            ret.DbDependentTables().add(new DbDependentTableInfo("ConfigurationResults", "ConfigurationResult", "test_run_id"));
            ret.DbDependentTables().add(new DbDependentTableInfo("TestGroupResults", "TestGroupResult", "test_run_id"));
            ret.DbDependentTables().add(new DbDependentTableInfo("AnnotationResults", "AnnotationResult", "test_run_id"));
            ret.DbDependentTables().add(new DbDependentTableInfo("TestResults", "TestResult", "test_run_id"));

            return ret;
        }
    }
