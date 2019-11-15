package Benchmark;

public interface IBenchmarkObject implements INotifyPropertyChanged
{
    Benchmark Owner;
    IBenchmarkObject ParentObject;
    IEnumerable<IBenchmarkObject> ChildObjects;
    bool Contains(IBenchmarkObject benchmarkObject);
    void LoadFromXml(BenchmarkXmlSerializer serializer);
    void SaveToXml(BenchmarkXmlSerializer serializer);
    void NotifyChange();

    DbTableInfo GetTableInfo();
}