package Benchmark;

public interface IBenchmarkObject implements INotifyPropertyChanged
{
    Benchmark Owner = null;
    IBenchmarkObject ParentObject = null;
    Iterable<IBenchmarkObject> ChildObjects = null;
    boolean Contains(IBenchmarkObject benchmarkObject);
    void LoadFromXml(BenchmarkXmlSerializer serializer);
    void SaveToXml(BenchmarkXmlSerializer serializer);
    void NotifyChange();

    DbTableInfo GetTableInfo();
}