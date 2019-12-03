package Benchmark;
    public abstract class Test extends BenchmarkObject implements IIdentifiedBenchmarkObject, INumberedBenchmarkObject, INamedBenchmarkObject, IDescribedBenchmarkObject
    {
        private TestGroup testGroup;
        private int id = 0;
        private String number = "";
        private String name = "";
        private String description = "";
        private boolean active = true;

        @Override
        public IBenchmarkObject ParentObject() {
            return testGroup;
        }

        public TestGroup TestGroup()
        {
            return testGroup;
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

        public boolean Active()
        {
            return active;
        }

        public boolean Active(boolean value)
        {

            if (active != value)
            {
                active = value;
                OnPropertyChanged("Active");
            }
        }

        public abstract TestType TestType()
        {
            return;
        }

        public Test(TestGroup testGroup)
        {
            this.id = testGroup.Owner.GenerateId();
            this.testGroup = testGroup;
        }

        @Override
        public void SaveToXml(BenchmarkXmlSerializer serializer)
        {
            serializer.WriteInt("id", id);
            serializer.WriteString("number", number);
            serializer.WriteString("name", name);
            serializer.WriteString("description", description);
            serializer.WriteBool("active", active);            
        }
        
        @Override
        public void LoadFromXml(BenchmarkXmlSerializer serializer)
        {
            if (!serializer.ReadInt("id", ref id))
            {
                id = testGroup.Benchmark.GenerateId();
            }
            serializer.ReadString("number", ref number);
            serializer.ReadString("name", ref name);
            serializer.ReadString("description", ref description);
            serializer.ReadBool("active", ref active);
        }

        @Override
        public DbTableInfo GetTableInfo()
        {
            DbTableInfo ret = super.GetTableInfo();

            ret.TableName("Test");

            ret.DbColumns().add(new DbColumnInfo("Id", "test_id", System.Data.DbType.Int32, true));
            ret.DbColumns().add(new DbColumnInfo("Number", "number", System.Data.DbType.String, 20));
            ret.DbColumns().add(new DbColumnInfo("Name", "name", System.Data.DbType.String, 50));
            ret.DbColumns().add(new DbColumnInfo("Description", "description", System.Data.DbType.String, 1000));
            ret.DbColumns().add(new DbColumnInfo("Active", "active", System.Data.DbType.Boolean));

            return ret;
        }
    }
