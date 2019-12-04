package Benchmark;

import javafx.collections.ObservableList;

public class TestGroup extends BenchmarkObject implements IIdentifiedBenchmarkObject, INumberedBenchmarkObject, INamedBenchmarkObject, IDescribedBenchmarkObject
    {
        private Benchmark benchmark;
        private int id = 0;
        private String number = "";
        private String name = "";
        private String description = "";
        private ObservableList<Test> tests = new ObservableList<Test>();
        private ObservableList<Configuration> configurations = new ObservableList<Configuration>();

        @Override
        public IBenchmarkObject ParentObject() {
            return benchmark;
        }

        @Override
        public IEnumerable<IBenchmarkObject> ChildObjects()
        { 
            for (Test test : tests)
            {
                yield return test;
            }
            foreach (Configuration configuration in configurations)
            {
                yield return configuration;
            } 
        }

        public Benchmark Benchmark()
        {
            return benchmark;
        }               

        public int Id()
        {
            return id;
        }

        public String Number()
        {
            return number;
        }
        public void Number(String value)
        {
            if (number != value)
            {
                number = value;
                OnPropertyChanged("Number");
            }
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

        public ObservableList<Test> Tests()
        {
            return tests;
        }

        public ObservableList<Configuration> Configurations()
        {
            return configurations;
        }

        public TestGroup(Benchmark benchmark)
        {
            this.id = benchmark.GenerateId();
            this.benchmark = benchmark;
            tests.CollectionChanged += Tests_CollectionChanged;
            configurations.CollectionChanged += Configurations_CollectionChanged;
        }

        private void Tests_CollectionChanged(object sender, System.Collections.Specialized.NotifyCollectionChangedEventArgs e)
        {
            NotifyChange();
        }

        private void Configurations_CollectionChanged(object sender, System.Collections.Specialized.NotifyCollectionChangedEventArgs e)
        {
            NotifyChange();
        }

        public boolean Contains(BenchmarkObject benchmarkObject)
        {
            if (benchmarkObject == this)
            {
                return true;
            }

            for (Configuration configuration : configurations)
            {
                if (configuration.Contains(benchmarkObject))
                {
                    return true;
                }
            }

            for (Test test : tests)
            {
                if (test.Contains(benchmarkObject))
                {
                    return true;
                }
            }

            return false;
        }

        @Override
        public void SaveToXml(BenchmarkXmlSerializer serializer)
        {
            serializer.WriteInt("id", id);
            serializer.WriteString("number", number);
            serializer.WriteString("name", name);
            serializer.WriteString("description", description);
            serializer.WriteCollection<Test>("tests", "test", tests);
            serializer.WriteCollection<Configuration>("configurations", "configuration", configurations);
        }

        @Override
        public void LoadFromXml(BenchmarkXmlSerializer serializer)
        {
            if (!serializer.ReadInt("id", ref id))
            {
                id = benchmark.GenerateId();
            }
            serializer.ReadString("number", ref number);
            serializer.ReadString("name", ref name);
            serializer.ReadString("description", ref description);

            serializer.ReadCollection<Test>("tests", "test", tests,
                delegate () { return new PlanEquivalenceTest(this); }); // TODO - distinguish test types.

            serializer.ReadCollection<Configuration>("configurations", "configuration", configurations,
                delegate () { return new Configuration(this); });
        }

        @Override
        public DbTableInfo GetTableInfo()
        {
            DbTableInfo ret = super.GetTableInfo();

            ret.TableName("TestGroup");

            ret.DbColumns().add(new DbColumnInfo("Id", "test_group_id", System.Data.DbType.Int32, true)); // PK
            ret.DbColumns().add(new DbColumnInfo(null, "benchmark_id", System.Data.DbType.Int32, true, "Benchmark", "benchmark_id")); // FK

            ret.DbColumns().add(new DbColumnInfo("Number", "number", System.Data.DbType.String, 20));
            ret.DbColumns().add(new DbColumnInfo("Name", "name", System.Data.DbType.String, 50));
            ret.DbColumns().add(new DbColumnInfo("Description", "description", System.Data.DbType.String, 1000));

            ret.DbDependentTables().add(new DbDependentTableInfo("Tests", "Test", "test_group_id"));
            ret.DbDependentTables().add(new DbDependentTableInfo("Configurations", "Configuration", "test_group_id"));

            return ret;
        }
    }
