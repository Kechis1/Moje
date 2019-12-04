package Benchmark;

public class TestGroupResult extends BenchmarkObject {
    private TestRun testRun;
    private int testGroupId = 0;
    private String testGroupNumber = "";
    private String testGroupName = "";

    @Override
    public IBenchmarkObject ParentObject() {
        return testRun;
    }

    public int TestGroupId() {
        return testGroupId;
    }

    public int TestGroupId(int value) {
        if (testGroupId != value) {
            testGroupId = value;
            OnPropertyChanged("TestGroupId");
        }
    }

    public String TestGroupNumber() {
        return testGroupNumber;
    }

    public void TestGroupNumber(String value) {
        if (testGroupNumber != value) {
            testGroupNumber = value;
            OnPropertyChanged("TestGroupNumber");
        }
    }

    public String TestGroupName() {
        return testGroupName;
    }

    public void TestGroupName(String value) {
        if (testGroupName != value) {
            testGroupName = value;
            OnPropertyChanged("TestGroupName");
        }
    }

    public TestGroupResult(TestRun testRun) {
        this.testRun = testRun;
    }

    @Override
    public void LoadFromXml(BenchmarkXmlSerializer serializer) {
        serializer.ReadInt("test_group_id", testGroupId);
        serializer.ReadString("test_group_number", testGroupNumber);
        serializer.ReadString("test_group_name", testGroupName);
    }

    @Override
    public void SaveToXml(BenchmarkXmlSerializer serializer) {
        serializer.WriteInt("test_group_id", testGroupId);
        serializer.WriteString("test_group_number", testGroupNumber);
        serializer.WriteString("test_group_name", testGroupName);
    }

    @Override
    public DbTableInfo GetTableInfo() {
        DbTableInfo ret = super.GetTableInfo();

        ret.TableName("TestGroupResult");

        ret.DbColumns().add(new DbColumnInfo(null, "test_run_id", System.Data.DbType.Int32, true, true, "TestRun", "test_run_id")); // PK, FK
        ret.DbColumns().add(new DbColumnInfo("TestGroupId", "test_group_id", System.Data.DbType.Int32, true, true, "TestGroup", "test_group_id")); // PK, FK

        ret.DbColumns().add(new DbColumnInfo("TestGroupNumber", "test_group_number", System.Data.DbType.String, 20));
        ret.DbColumns().add(new DbColumnInfo("TestGroupName", "test_group_name", System.Data.DbType.String, 50));

        return ret;
    }
}
