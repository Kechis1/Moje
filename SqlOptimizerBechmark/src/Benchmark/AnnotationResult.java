package Benchmark;

public class AnnotationResult extends BenchmarkObject {
    private TestRun testRun;
    private int annotationId = 0;
    private String annotationNumber = "";
    private String annotationName = "";

    public IBenchmarkObject ParentObject() {
        return testRun;
    }

    public void AnnotationId(int value) {
        if (annotationId != value) {
            annotationId = value;
            OnPropertyChanged("AnnotationId");
        }
    }

    public int AnnotationId() {
        return annotationId;
    }

    public String AnnotationNumber() {
        return annotationNumber;
    }

    public void AnnotationNumber(String value) {
        if (annotationNumber != value) {
            annotationNumber = value;
            OnPropertyChanged("AnnotationNumber");
        }

    }

    public String AnnotationName() {
        return annotationName;
    }

    public void AnnotationName(String value) {
        if (annotationName != value) {
            annotationName = value;
            OnPropertyChanged("AnnotationName");
        }
    }

    public AnnotationResult(TestRun testRun) {
        this.testRun = testRun;
    }

    @Override
    public void LoadFromXml(BenchmarkXmlSerializer serializer) {
        serializer.ReadInt("anntation_id", ref annotationId);
        serializer.ReadString("anntation_number", ref annotationNumber);
        serializer.ReadString("anntation_name", ref annotationName);
    }

    @Override
    public void SaveToXml(BenchmarkXmlSerializer serializer) {
        serializer.WriteInt("anntation_id", annotationId);
        serializer.WriteString("anntation_number", annotationNumber);
        serializer.WriteString("anntation_name", annotationName);
    }

    @Override
    public DbTableInfo GetTableInfo() {
        DbTableInfo ret = super.GetTableInfo();

        ret.TableName("AnnotationResult");

        ret.DbColumns().add(new DbColumnInfo(null, "test_run_id", System.Data.DbType.Int32, true, true, "TestRun", "test_run_id")); // PK, FK
        ret.DbColumns().add(new DbColumnInfo("AnnotationId", "annotation_id", System.Data.DbType.Int32, true, true, "Annotation", "annotation_id")); // PK, FK
        ret.DbColumns().add(new DbColumnInfo("AnnotationNumber", "annotation_number", System.Data.DbType.String, 20));
        ret.DbColumns().add(new DbColumnInfo("AnnotationName", "annotation_name", System.Data.DbType.String, 50));

        return ret;
    }
}

