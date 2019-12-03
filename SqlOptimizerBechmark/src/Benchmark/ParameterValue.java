package Benchmark;

    public class ParameterValue extends BenchmarkObject
    {
        private PlanEquivalenceTest planEquivalenceTest;
        private int templateId = 0;
        private int parameterId = 0;
        private String value = "";

        public IBenchmarkObject ParentObject() {
            return planEquivalenceTest;
        }

        public PlanEquivalenceTest PlanEquivalenceTest()
        {
            return planEquivalenceTest;
        }

        public int TemplateId()
        {
            return templateId;
        }

        public void TemplateId(int value)
        {
            if (templateId != value)
            {
                templateId = value;
                OnPropertyChanged("TemplateId");
            }
        }

        public int ParameterId()
        {
            return parameterId;
        }

        public void ParameterId(int value)
        {
            if (parameterId != value)
            {
                parameterId = value;
                OnPropertyChanged("ParameterId");
            }
        }

        public String Value()
        {
            return value;
        }

        public void Value(String value)
        {
            if (this.value != value)
            {
                this.value = value;
                OnPropertyChanged("Value");
            }
        }

        public ParameterValue(PlanEquivalenceTest planEquivalenceTest)
        {
            this.planEquivalenceTest = planEquivalenceTest;
        }

        @Override
        public void LoadFromXml(BenchmarkXmlSerializer serializer)
        {
            serializer.ReadInt("template_id", ref templateId);
            serializer.ReadInt("parameter_id", ref parameterId);
            serializer.ReadString("value", ref value);
        }

        @Override
        public void SaveToXml(BenchmarkXmlSerializer serializer)
        {
            serializer.WriteInt("template_id", templateId);
            serializer.WriteInt("parameter_id", parameterId);
            serializer.WriteString("value", value);
        }

        @Override
        public DbTableInfo GetTableInfo()
        {
            DbTableInfo ret = GetTableInfo();

            ret.TableName = "ParameterValue";

            ret.DbColumns.add(new DbColumnInfo("TemplateId", "template_id", System.Data.DbType.Int32, true, "Template", "template_id"));
            ret.DbColumns.add(new DbColumnInfo("ParameterId", "parameter_id", System.Data.DbType.Int32, true, "Parameter", "parameter_id"));
            ret.DbColumns.add(new DbColumnInfo("Value", "value", System.Data.DbType.String, 100));

            return ret;
        }
    }
