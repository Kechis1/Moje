package Benchmark;
    public class SpecificStatement extends Statement
    {
        private String providerName = "";
        private boolean notSupported = false;

        public String ProviderName()
        {
            return providerName;
        }
        public void ProviderName(String value)
        {
            if (value != providerName)
            {
                providerName = value;
                OnPropertyChanged("ProviderName");
            }
        }

        public boolean NotSupported()
        {
            return notSupported;
        }

        public void NotSupported(boolean value)
        {
            if (value != notSupported)
            {
                notSupported = value;
                OnPropertyChanged("NotSupported");
            }
        }

        public SpecificStatement(BenchmarkObject parentObject) 
            : super(parentObject)
        {
        }

        @Override
        public void LoadFromXml(BenchmarkXmlSerializer serializer)
        {
            serializer.ReadString("provider_name", providerName);
            serializer.ReadBool("not_supported", notSupported);
            super.LoadFromXml(serializer);
        }

        @Override
        public void SaveToXml(BenchmarkXmlSerializer serializer)
        {
            serializer.WriteString("provider_name", providerName);
            serializer.WriteBool("not_supported", notSupported);
            super.SaveToXml(serializer);
        }

        @Override
        public DbTableInfo GetTableInfo()
        {
            DbTableInfo ret = super.GetTableInfo();

            ret.TableName("SpecificStatement");

            ret.DbColumns().add(new DbColumnInfo("ProviderName", "provider_name", System.Data.DbType.String, 50));
            ret.DbColumns().add(new DbColumnInfo("NotSupported", "not_supported", System.Data.DbType.Boolean));

            return ret;
        }
    }
