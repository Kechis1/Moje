package Benchmark;

public class ConfigurationResult extends BenchmarkObject
{
    private TestRun testRun;

    private int configurationId = 0;
    private String configurationNumber ="";
    private String configurationName ="";
    private boolean initScriptStarted = false;
    private boolean initScriptCompleted = false;
    private String initScriptErrorMessage ="";
    private boolean cleanUpScriptStarted = false;
    private boolean cleanUpScriptCompleted = false;
    private String cleanUpScriptErrorMessage ="";


    public IBenchmarkObject ParentObject() {
        return testRun;
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

    public String ConfigurationNumber()
    {
        return configurationNumber;
    }

    public void ConfigurationNumber(String value)
    {
        if (configurationNumber != value)
        {
            configurationNumber = value;
            OnPropertyChanged("ConfigurationNumber");
        }
    }

    public String ConfigurationName()
    {
        return configurationName;
    }


    public void ConfigurationName(String value)
    {
        if (configurationName != value)
        {
            configurationName = value;
            OnPropertyChanged("ConfigurationName");
        }
    }

    public boolean InitScriptStarted()
    {
        return initScriptStarted;
    }

    public void InitScriptStarted(boolean value)
    {
        if (initScriptStarted != value)
        {
            initScriptStarted = value;
            OnPropertyChanged("InitScriptStarted");
        }
    }

    public boolean InitScriptCompleted()
    {
        return initScriptCompleted;
    }


    public void InitScriptCompleted(boolean value)
    {
        if (initScriptCompleted != value)
        {
            initScriptCompleted = value;
            OnPropertyChanged("InitScriptCompleted");
        }
    }

    public String InitScriptErrorMessage()
    {
        return initScriptErrorMessage;
    }

    public void InitScriptErrorMessage(String value)
    {
        if (initScriptErrorMessage != value)
        {
            initScriptErrorMessage = value;
            OnPropertyChanged("InitScriptErrorMessage");
        }
    }

    public boolean CleanUpScriptStarted()
    {
        return cleanUpScriptStarted;
    }


    public boolean CleanUpScriptStarted(boolean value)
    {
        if (cleanUpScriptStarted != value)
        {
            cleanUpScriptStarted = value;
            OnPropertyChanged("CleanUpScriptStarted");
        }
    }

    public boolean CleanUpScriptCompleted()
    {
        return cleanUpScriptCompleted;
    }

    public boolean CleanUpScriptCompleted(boolean value)
    {
        if (cleanUpScriptCompleted != value)
        {
            cleanUpScriptCompleted = value;
            OnPropertyChanged("CleanUpScriptCompleted");
        }
    }

    public String CleanUpScriptErrorMessage()
    {
        return cleanUpScriptErrorMessage;
    }

    public void CleanUpScriptErrorMessage(String value)
    {
        if (cleanUpScriptErrorMessage != value)
        {
            cleanUpScriptErrorMessage = value;
            OnPropertyChanged("CleanUpScriptErrorMessage");
        }
    }

    public ConfigurationResult(TestRun testRun)
    {
        this.testRun = testRun;
    }

    @Override
    public void LoadFromXml(BenchmarkXmlSerializer serializer)
    {
        serializer.ReadInt("configuration_id", ref configurationId);
        serializer.ReadString("configuration_number", ref configurationNumber);
        serializer.ReadString("configuration_name", ref configurationName);

        serializer.ReadBool("init_script_started", ref initScriptStarted);
        serializer.ReadBool("init_script_completed", ref initScriptCompleted);
        serializer.ReadString("init_error_message", ref initScriptErrorMessage);
        serializer.ReadBool("clean_up_script_started", ref cleanUpScriptStarted);
        serializer.ReadBool("clean_up_script_completed", ref cleanUpScriptCompleted);
        serializer.ReadString("clean_up_error_message", ref cleanUpScriptErrorMessage);
    }
    @Override
    public void SaveToXml(BenchmarkXmlSerializer serializer)
    {
        serializer.WriteInt("configuration_id", configurationId);
        serializer.WriteString("configuration_number", configurationNumber);
        serializer.WriteString("configuration_name", configurationName);

        serializer.WriteBool("init_script_started", initScriptStarted);
        serializer.WriteBool("init_script_completed", initScriptCompleted);
        serializer.WriteString("init_error_message", initScriptErrorMessage);
        serializer.WriteBool("clean_up_script_started", cleanUpScriptStarted);
        serializer.WriteBool("clean_up_script_completed", cleanUpScriptCompleted);
        serializer.WriteString("clean_up_error_message", cleanUpScriptErrorMessage);
    }

    @Override
    public DbTableInfo GetTableInfo()
    {
        DbTableInfo ret = super.GetTableInfo();

        ret.TableName("ConfigurationResult");

        ret.DbColumns().add(new DbColumnInfo(null, "test_run_id", System.Data.DbType.Int32, true, true, "TestRun", "test_run_id")); // PK, FK
        ret.DbColumns().add(new DbColumnInfo("ConfigurationId", "configuration_id", System.Data.DbType.Int32, true, true, "Configuration", "configuration_id")); // PK, FK

        ret.DbColumns().add(new DbColumnInfo("ConfigurationNumber", "configuration_number", System.Data.DbType.String, 20));
        ret.DbColumns().add(new DbColumnInfo("ConfigurationName", "configuration_name", System.Data.DbType.String, 50));
        ret.DbColumns().add(new DbColumnInfo("InitScriptStarted", "init_script_started", System.Data.DbType.Boolean));
        ret.DbColumns().add(new DbColumnInfo("InitScriptCompleted", "init_script_completed", System.Data.DbType.Boolean));
        ret.DbColumns().add(new DbColumnInfo("InitErrorMessage", "init_error_message", System.Data.DbType.String, 1000));
        ret.DbColumns().add(new DbColumnInfo("CleanUpScriptStarted", "clean_up_script_started", System.Data.DbType.Boolean));
        ret.DbColumns().add(new DbColumnInfo("CleanUpScriptCompleted", "clean_up_script_completed", System.Data.DbType.Boolean));
        ret.DbColumns().add(new DbColumnInfo("CleanUpErrorMessage", "clean_up_error_message", System.Data.DbType.String, 1000));

        return ret;
    }
}
