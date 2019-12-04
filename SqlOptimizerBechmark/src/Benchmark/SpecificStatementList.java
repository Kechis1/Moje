package Benchmark;

public class SpecificStatementList extends StatementList {
    private String providerName = "";

    public String ProviderName() {
        return providerName;
    }

    public void ProviderName(String value) {
        if (value != providerName) {
            providerName = value;
            OnPropertyChanged("ProviderName");
        }
    }

    public SpecificStatementList(Script script): super(script)
    {
    }

    @Override
    public void SaveToXml(BenchmarkXmlSerializer serializer) {
        serializer.WriteString("provider_name", providerName);
        super.SaveToXml(serializer);
    }

    @Override
    public void LoadFromXml(BenchmarkXmlSerializer serializer) {
        serializer.ReadString("provider_name", providerName);
        super.LoadFromXml(serializer);
    }

    @Override
    public DbTableInfo GetTableInfo() {
        DbTableInfo ret = super.GetTableInfo();

        ret.TableName("SpecificStatementList");

        ret.DbColumns().add(new DbColumnInfo("ProviderName", "provider_name", System.Data.DbType.String, 50));

        return ret;
    }
}
