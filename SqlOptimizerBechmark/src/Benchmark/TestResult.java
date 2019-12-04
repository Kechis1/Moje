package Benchmark;
    public class TestResult extends BenchmarkObject
    {
        private TestRun testRun;

        private int testId;
        private String testNumber = "";
        private String testName = "";
        private int testGroupId;
        private int configurationId;
        private String errorMessage = "";
        
        @Override
        public IBenchmarkObject ParentObject() {
            return testRun;
        }

        public TestRun TestRun() {
            return testRun;
        }

        public int TestId()
        {
            return testId;
        }

        public void TestId(int value)
        {
            if (testId != value)
            {
                testId = value;
                OnPropertyChanged("TestId");
            }
        }
        
        public String TestNumber()
        {
            return testNumber;
        }

        public void TestNumber(String value)
        {
            if (testNumber != value)
            {
                testNumber = value;
                OnPropertyChanged("TestNumber");
            }
        }

        public String TestName()
        {
            return testName;
        }

        public void TestName(String value)
        {
            if (testName != value)
            {
                testName = value;
                OnPropertyChanged("TestName");
            }
        }

        public int TestGroupId()
        {
            return testGroupId;
        }

        public void TestGroupId(int value)
        {
            if (testGroupId != value)
            {
                testGroupId = value;
                OnPropertyChanged("TestGroupId");
            }
        }

        public int ConfigurationId()
        {
            return configurationId;
        }

        public void ConfigurationId(int value)
        {
            if (configurationId != value)
            {
                configurationId = value;
                OnPropertyChanged("ConfigurationId");
            }
        }

        public String ErrorMessage()
        {
            return errorMessage;
        }

        public void ErrorMessage(String value)
        {
            if (errorMessage != value)
            {
                errorMessage = value;
                OnPropertyChanged("ErrorMessage");
            }
        }

        public TestResult(TestRun testRun)
        {
            this.testRun = testRun;
        }

        @Override
        public void LoadFromXml(BenchmarkXmlSerializer serializer)
        {
            serializer.ReadInt("test_id", testId);
            serializer.ReadString("test_number", testNumber);
            serializer.ReadString("test_name", testName);
            serializer.ReadInt("test_group_id", testGroupId);
            serializer.ReadInt("configuration_id", configurationId);
            serializer.ReadString("error_message", errorMessage);
        }

        @Override
        public void SaveToXml(BenchmarkXmlSerializer serializer)
        {
            serializer.WriteInt("test_id", testId);
            serializer.WriteString("test_number", testNumber);
            serializer.WriteString("test_name", testName);
            serializer.WriteInt("test_group_id", testGroupId);
            serializer.WriteInt("configuration_id", configurationId);
            serializer.WriteString("error_message", errorMessage);
        }

        public void ExportToCsv(StreamWriter writer, CsvExportOptions exportOptions)
        {
        }

        @Override
        public DbTableInfo GetTableInfo()
        {
            DbTableInfo ret = super.GetTableInfo();

            ret.TableName("TestResult");

            ret.DbColumns().add(new DbColumnInfo("test_result_id", true, true)); // PK
            ret.DbColumns().add(new DbColumnInfo(null, "test_run_id", System.Data.DbType.Int32, true, "TestRun", "test_run_id")); // FK

            ret.DbColumns().add(new DbColumnInfo("TestId", "test_id", System.Data.DbType.Int32, true, "Test", "test_id")); // FK
            ret.DbColumns().add(new DbColumnInfo("TestNumber", "test_number", System.Data.DbType.String, 20));
            ret.DbColumns().add(new DbColumnInfo("TestName", "test_name", System.Data.DbType.String, 50));
            ret.DbColumns().add(new DbColumnInfo("ErrorMessage", "error_message", System.Data.DbType.String, 1000));
            ret.DbColumns().add(new DbColumnInfo("TestGroupId", "test_group_id", System.Data.DbType.Int32, true, "TestGroup", "test_group_id")); // FK
            ret.DbColumns().add(new DbColumnInfo("ConfigurationId", "configuration_id", System.Data.DbType.Int32, true, "Configuration", "configuration_id")); // FK

            return ret;
        }
    }