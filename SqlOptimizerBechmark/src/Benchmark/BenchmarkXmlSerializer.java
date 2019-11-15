package Benchmark;

import javax.xml.bind.annotation.XmlElement;

public class BenchmarkXmlSerializer
{
    private final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss.fff";

    private XDocument document = new XDocument();
    private XmlElement currentElement;

    public BenchmarkXmlSerializer()
    {
    }

    public void WriteString(String name, String value)
    {
        XmlElement element = new XElement(name);
        element.Value = value;
        currentElement.Add(element);
    }

    public void WriteInt(String name, int value)
    {
        XElement element = new XElement(name);
        element.Value = Convert.ToString(value);
        currentElement.Add(element);
    }

    public void WriteBool(String name, bool value)
    {
        XElement element = new XElement(name);
        element.Value = Convert.ToString(value);
        currentElement.Add(element);
    }

    public void WriteDouble(String name, double value)
    {
        XElement element = new XElement(name);
        element.Value = value.ToString(System.Globalization.CultureInfo.InvariantCulture);
        currentElement.Add(element);
    }

    public void WriteDateTime(String name, DateTime value)
    {
        XElement element = new XElement(name);
        element.Value = value.ToString(dateTimeFormat);
    }

    public void WriteTimeSpan(String name, TimeSpan value)
    {
        double seconds = value.TotalSeconds;
        WriteDouble(name, seconds);
    }

    public void WriteObject(String name, BenchmarkObject benchmarkObject)
    {
        XElement oldCurrent = currentElement;
        currentElement = new XElement(name);
        benchmarkObject.SaveToXml(this);
        oldCurrent.Add(currentElement);
        currentElement = oldCurrent;
    }

    public void WriteXml(XElement element)
    {
        currentElement.Add(element);
    }

    public void WriteCollection<T>(String collectionName, String itemName, ObservableCollection<T> collection)
        where T : BenchmarkObject
    {
        XElement oldCurrent = currentElement;
        XElement eCollection = new XElement(collectionName);
        foreach (T benchmarkObject in collection)
        {
            XElement eItem = new XElement(itemName);
            currentElement = eItem;
            benchmarkObject.SaveToXml(this);
            eCollection.Add(eItem);
        }
        oldCurrent.Add(eCollection);
        currentElement = oldCurrent;
    }

    public bool ReadString(String name, ref String value)
    {
        XElement element = currentElement.Element(name);
        if (element != null)
        {
            value = element.Value;
            return true;
        }
        else
        {
            return false;
        }
    }

    public bool ReadInt(String name, ref int value)
    {
        XElement element = currentElement.Element(name);
        if (element != null)
        {
            if (int.TryParse(element.Value, out value))
            {
                return true;
            }
        }

        return false;
    }

    public bool ReadBool(String name, ref bool value)
    {
        XElement element = currentElement.Element(name);
        if (element != null)
        {
            if (bool.TryParse(element.Value, out value))
            {
                return true;
            }
        }

        return false;
    }

    public bool ReadDouble(String name, ref double value)
    {
        XElement element = currentElement.Element(name);
        if (element != null)
        {
            if (double.TryParse(element.Value, System.Globalization.NumberStyles.Any, System.Globalization.CultureInfo.InvariantCulture, out value))
            {
                return true;
            }
        }
        return false;
    }

    public bool ReadDateTime(String name, ref DateTime value)
    {
        XElement element = currentElement.Element(name);
        if (element != null)
        {
            if (DateTime.TryParseExact(element.Value, dateTimeFormat, System.Globalization.CultureInfo.InvariantCulture,
                System.Globalization.DateTimeStyles.None, out value))
            {
                return true;
            }
        }

        return false;
    }

    public bool ReadTimeSpan(String name, ref TimeSpan value)
    {
        double seconds = 0;
        if (ReadDouble(name, ref seconds))
        {
            value = TimeSpan.FromSeconds(seconds);
            return true;
        }
        else
        {
            return false;
        }
    }

    public bool ReadObject(String name, BenchmarkObject benchmarkObject)
    {
        if (currentElement.Element(name) != null)
        {
            XElement oldCurrent = currentElement;
            currentElement = currentElement.Element(name);
            benchmarkObject.LoadFromXml(this);
            currentElement = oldCurrent;
            return true;
        }

        return false;
    }

    public bool ReadCollection<T>(String collectionName, String itemName, ObservableCollection<T> collection, InstantiateBenchmarkObjectDelegate<T> instantiateBenchmarkObject)
        where T: BenchmarkObject
    {
        if (currentElement.Element(collectionName) != null)
        {
            collection.Clear();
            XElement eCollection = currentElement.Element(collectionName);
            foreach (XElement eItem in eCollection.Elements(itemName))
            {
                XElement oldCurrent = currentElement;
                currentElement = eItem;
                T obj = instantiateBenchmarkObject();
                obj.LoadFromXml(this);
                collection.Add(obj);
                currentElement = oldCurrent;
            }
            return true;
        }
        return false;
    }

    public void SaveBenchmark(Benchmark benchmark, String fileName)
    {
        XDocument document = new XDocument();
        XElement root = new XElement("sql_benchmark");
        document.Add(root);

        currentElement = root;
        benchmark.SaveToXml(this);

        document.Save(fileName);
    }

    public void LoadBenchmark(Benchmark benchmark, String fileName)
    {
        XDocument document = XDocument.Load(fileName);
        XElement root = document.Root;

        currentElement = root;
        benchmark.LoadFromXml(this);
    }

    public XElement ReadXml(String name)
    {
        return currentElement.Element(name);
    }
}

public delegate T InstantiateBenchmarkObjectDelegate<T>() where T: BenchmarkObject;