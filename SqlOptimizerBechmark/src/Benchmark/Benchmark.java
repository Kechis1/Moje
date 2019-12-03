package Benchmark;

    public class Benchmark extends BenchmarkObject implements INamedBenchmarkObject, IDescribedBenchmarkObject
    {
        private String name = "";
        private String author = "";
        private String description = "";
        private Script initScript;
        private Script cleanUpScript;
        private ObservableCollection<TestGroup> testGroups = new ObservableCollection<TestGroup>();
        private ConnectionSettings connectionSettings;
        private ObservableCollection<TestRun> testRuns = new ObservableCollection<TestRun>();
        private ObservableCollection<Annotation> annotations = new ObservableCollection<Annotation>();
        private TestRunSettings testRunSettings;

        private int lastId = 0;

        public Benchmark Owner() {
            return this;
        }

        public IBenchmarkObject ParentObject() {
            return null;
        }

        public IEnumerable<IBenchmarkObject> ChildObjects()
        {
            yield return initScript;
            yield return cleanUpScript;
            foreach (TestGroup testGroup in testGroups)
            {
                yield return testGroup;
            }
            yield return connectionSettings;
            foreach (TestRun testRun in testRuns)
            {
                yield return testRun;
            }
            foreach (Annotation annotation in annotations)
            {
                yield return annotation;
            }
        }

        public String Name()
        {
            return name;
        }

        public String Name(String value)
        {
            if (name != value)
            {
                name = value;
                OnPropertyChanged("Name");
            }
        }

        public String Author() {
            return author;
        }

        public void Author(String value)
        {
            if (author != value)
            {
                author = value;
                OnPropertyChanged("Author");
            }
        }

        public String Description()
        {
            return description;
        }

        public void Description(String value)
        {
            if (description != value)
            {
                description = value;
                OnPropertyChanged("Description");
            }
        }

        public Script InitScript()
        {
            return initScript;
        }

        public Script CleanUpScript()
        {
            return cleanUpScript;
        }

        public ObservableCollection<TestGroup> TestGroups()
        {
           return testGroups;
        }

        public ConnectionSettings ConnectionSettings()
        {
            return connectionSettings;
        }

        public TestRunSettings TestRunSettings()
        {
            return testRunSettings;
        }

        public ObservableCollection<TestRun> TestRuns()
        {
            return testRuns;
        }

        public ObservableCollection<Annotation> Annotations()
        {
            return annotations;
        }

        public Benchmark()
        {
            initScript = new Script(this);
            cleanUpScript = new Script(this);
            connectionSettings = new ConnectionSettings(this);
            testRunSettings = new TestRunSettings(this);
        }

        public void SaveToXml(BenchmarkXmlSerializer serializer)
        {
            serializer.WriteString("name", name);
            serializer.WriteString("author", author);
            serializer.WriteString("description", description);
            serializer.WriteObject("init_script", initScript);
            serializer.WriteObject("clean_up_script", cleanUpScript);
            serializer.WriteCollection<TestGroup>("test_groups", "test_group", testGroups);
            serializer.WriteObject("connection_settings", connectionSettings);
            serializer.WriteCollection<TestRun>("test_runs", "test_run", testRuns);
            serializer.WriteCollection<Annotation>("annotations", "annotation", annotations);
            serializer.WriteObject("test_run_settings", testRunSettings);
            serializer.WriteInt("last_id", lastId);
        }

        public void LoadFromXml(BenchmarkXmlSerializer serializer)
        {
            serializer.ReadString("name", ref name);
            serializer.ReadString("author", ref author);
            serializer.ReadString("description", ref description);
            serializer.ReadObject("init_script", initScript);
            serializer.ReadObject("clean_up_script", cleanUpScript);
            serializer.ReadCollection<TestGroup>("test_groups", "test_group", testGroups,
                delegate () { return new TestGroup(this); });
            serializer.ReadObject("connection_settings", connectionSettings);
            serializer.ReadCollection<TestRun>("test_runs", "test_run", testRuns,
                delegate () { return new TestRun(this); });
            serializer.ReadCollection<Annotation>("annotations", "annotation", annotations,
                delegate () { return new Annotation(this); });
            serializer.ReadObject("test_run_settings", testRunSettings);
            serializer.ReadInt("last_id", ref lastId);
        }

        public void Save(String fileName)
        {
            BenchmarkXmlSerializer serializer = new BenchmarkXmlSerializer();
            serializer.SaveBenchmark(this, fileName);
        }

        public void Load(String fileName)
        {
            BenchmarkXmlSerializer serializer = new BenchmarkXmlSerializer();
            serializer.LoadBenchmark(this, fileName);
        }

        public IIdentifiedBenchmarkObject FindObjectById(int id)
        {
            Stack<IBenchmarkObject> stack = new Stack<IBenchmarkObject>();
            stack.Push(this);
            while (stack.Count > 0)
            {
                IBenchmarkObject obj = stack.Pop();
                if (obj is IIdentifiedBenchmarkObject identifiedBenchmarkObject &&
                    identifiedBenchmarkObject.Id == id)
                {
                    return identifiedBenchmarkObject;
                }
                foreach (IBenchmarkObject child in obj.ChildObjects)
                {
                    stack.Push(child);
                }
            }
            return null;
        }

        public int GenerateId()
        {
            return ++lastId;
        }

        public DbTableInfo GetTableInfo()
        {
            DbTableInfo ret = new DbTableInfo("Benchmark");

            ret.DbColumns.Add(new DbColumnInfo("Id", "benchmark_id", System.Data.DbType.Int32, true));
            ret.DbColumns.Add(new DbColumnInfo("Name", "name", System.Data.DbType.String, 50));
            ret.DbColumns.Add(new DbColumnInfo("Author", "author", System.Data.DbType.String, 200));
            ret.DbColumns.Add(new DbColumnInfo("Description", "description", System.Data.DbType.String, 1000));

            ret.DbDependentTables.Add(new DbDependentTableInfo("TestGroups", "TestGroup", "benchmark_id"));
            ret.DbDependentTables.Add(new DbDependentTableInfo("TestRuns", "TestRun", "benchmark_id"));
            ret.DbDependentTables.Add(new DbDependentTableInfo("Annotations", "Annotation", "benchmark_id"));

            return ret;
        }
    }