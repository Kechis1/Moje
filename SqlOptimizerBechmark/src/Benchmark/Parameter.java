package Benchmark;
    public class Parameter implements BenchmarkObject, IIdentifiedBenchmarkObject, INamedBenchmarkObject
    {
        private PlanEquivalenceTest planEquivalenceTest;
        private int id = 0;
        private String name = "";

        @Override
        public IBenchmarkObject ParentObject() {
            return planEquivalenceTest;
        }

        public PlanEquivalenceTest PlanEquivalenceTest()
        {
            return planEquivalenceTest;
        }
        public int Id()
        {
            return id;
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

        public Parameter(PlanEquivalenceTest planEquivalenceTest)
        {
            this.id = planEquivalenceTest.Owner.GenerateId();
            this.planEquivalenceTest = planEquivalenceTest;
        }

        @Override
        public void LoadFromXml(BenchmarkXmlSerializer serializer)
        {
            if (!serializer.ReadInt("id", ref id))
            {
                id = planEquivalenceTest.Owner.GenerateId();
            }
            serializer.ReadString("name", ref name);
        }

        @Override
        public void SaveToXml(BenchmarkXmlSerializer serializer)
        {
            serializer.WriteInt("id", id);
            serializer.WriteString("name", name);
        }

        @Override
        public DbTableInfo GetTableInfo()
        {
            DbTableInfo ret = super.GetTableInfo();

            ret.TableName("Parameter");
            ret.DbColumns().add(new DbColumnInfo("Id", "parameter_id", System.Data.DbType.Int32, true));
            ret.DbColumns().add(new DbColumnInfo("Name", "name", System.Data.DbType.String, 50));

            return ret;
        }
    }
