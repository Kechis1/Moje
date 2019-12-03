package Benchmark;
    public class Script extends BenchmarkObject
    {
        private IBenchmarkObject parentObject;

        private StatementList defaultStatementList;
        private ObservableCollection<SpecificStatementList> specificStatementLists;
        
        @Override
        public IBenchmarkObject ParentObject() {
            return parentObject;
        }

        public IEnumerable<IBenchmarkObject> ChildObjects()
        {
            yield return defaultStatementList;
            for (SpecificStatementList specificStatementList : specificStatementLists)
            {
                yield return specificStatementList;
            }
        }

        public StatementList DefaultStatementList()
        {
            return defaultStatementList;
        }

        public ObservableCollection<SpecificStatementList> SpecificStatementLists()
        {
            return specificStatementLists;
        }

        public Script(IBenchmarkObject parentObject)
        {
            this.parentObject = parentObject;
            this.defaultStatementList = new StatementList(this);
            this.specificStatementLists = new ObservableCollection<SpecificStatementList>();
        }

        public StatementList GetStatementList(String providerName)
        {
            for (SpecificStatementList specificStatementList : specificStatementLists)
            {
                if (specificStatementList.ProviderName() == providerName)
                {
                    return specificStatementList;
                }
            }

            return defaultStatementList;
        }

        public boolean HasSpecificStatementList(String providerName)
        {
            for (SpecificStatementList specificStatementList : specificStatementLists)
            {
                if (specificStatementList.ProviderName() == providerName)
                {
                    return true;
                }
            }
            return false;
        }

        @Override
        public void LoadFromXml(BenchmarkXmlSerializer serializer)
        {
            // Zpetna kompatibilita.
            if (!serializer.ReadObject("default_statement_list", defaultStatementList))
            {
                defaultStatementList.LoadFromXml(serializer);
            }
            serializer.ReadCollection<SpecificStatementList>("specific_statement_lists", "specific_statement_list", specificStatementLists,
                delegate () { return new SpecificStatementList(this); });
        }

        @Override
        public void SaveToXml(BenchmarkXmlSerializer serializer)
        {
            serializer.WriteObject("default_statement_list", defaultStatementList);
            serializer.WriteCollection<SpecificStatementList>("specific_statement_lists", "specific_statement_list", specificStatementLists);
        }

        @Override
        public DbTableInfo GetTableInfo()
        {
            DbTableInfo ret = super.GetTableInfo();

            ret.TableName("Script");

            return ret;
        }
    }
