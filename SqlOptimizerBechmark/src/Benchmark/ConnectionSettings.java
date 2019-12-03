package Benchmark;

    public class ConnectionSettings extends BenchmarkObject
    {
        private Benchmark benchmark;
        private DbProviders.DbProvider dbProvider;

        public DbProviders.DbProvider DbProvider() {
            return dbProvider;
        }

        public void DbProvider(DbProviders.DbProvider value) {
            dbProvider = value;
        }

        @Override
        public IBenchmarkObject ParentObject() {
            return benchmark;
        }

        public ConnectionSettings(Benchmark benchmark)
        {
            this.benchmark = benchmark;
        }
        
        @Override
        public void LoadFromXml(BenchmarkXmlSerializer serializer)
        {
            String providerName = null;
            serializer.ReadString("provider", ref providerName);

            if (providerName != null)
            {
                XElement element = serializer.ReadXml("settings");
                if (element != null)
                {
                    dbProvider = DbProviders.DbProvider.GetProvider(providerName);
                    dbProvider.LoadFromXml(element);
                }
            }
            else
            {
                String currentProvider = null;
                serializer.ReadString("current_provider", ref currentProvider);

                if (currentProvider != null)
                {
                    XElement eProviders = serializer.ReadXml("providers");
                    for (XElement eProvider : eProviders.Elements("provider"))
                    {
                        providerName = eProvider.Attribute("name").Value;
                        DbProviders.DbProvider dbProvider1 = DbProviders.DbProvider.GetProvider(providerName);
                        dbProvider1.LoadFromXml(eProvider);

                        if (currentProvider == providerName)
                        {
                            dbProvider = dbProvider1;
                        }
                    }
                }
            }
        }

        @Override
        public void SaveToXml(BenchmarkXmlSerializer serializer)
        {
            if (dbProvider != null)
            {
                serializer.WriteString("current_provider", dbProvider.Name);

                XElement eProviders = new XElement("providers");

                // Since ver. 1.30, settings of all providers are stored in the XML.
                for (DbProviders.DbProvider provider : DbProviders.DbProvider.Providers)
                {
                    XElement eProvider = new XElement("provider");
                    XAttribute aProviderName = new XAttribute("name", provider.Name);
                    eProvider.Add(aProviderName);
                    provider.SaveToXml(eProvider);
                    eProviders.Add(eProvider);
                }

                serializer.WriteXml(eProviders);
            }
        }
    }
