package Benchmark;

import javax.swing.text.html.HTMLDocument;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Stack;

public abstract class BenchmarkObject implements IBenchmarkObject {
    public IBenchmarkObject ParentObject;

    public Benchmark Owner() {
        return ParentObject.Owner;
    }

    public Iterable<IBenchmarkObject>ChildObjects() {
        return new Iterable<IBenchmarkObject>();
    }

    public event PropertyChangedEventHandler PropertyChanged;

    protected virtual void OnPropertyChanged(String propertyName) {
        if (PropertyChanged != null) {
            PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
        }

        NotifyChange();
    }

    private void NotifyPropertyChanged([CallerMemberName]String propertyName="") {
        if (PropertyChanged != null) {
            PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
        }
    }


    public event EventHandler Changed;

    protected virtual void OnChanged() {
        if (Changed != null) {
            Changed(this, EventArgs.Empty);
        }
    }

    public abstract void SaveToXml(BenchmarkXmlSerializer serializer);

    public abstract void LoadFromXml(BenchmarkXmlSerializer serializer);

    public void NotifyChange() {
        OnChanged();
        if (ParentObject != null) {
            ParentObject.NotifyChange();
        }
    }

    public boolean Contains(IBenchmarkObject benchmarkObject) {
        Stack<IBenchmarkObject> stack = new Stack<IBenchmarkObject>();
        stack.push(this);
        while (stack.size() > 0) {
            IBenchmarkObject obj = stack.pop();
            if (obj == benchmarkObject) {
                return true;
            }
            for (IBenchmarkObject child : obj.ChildObjects)
            {
                stack.push(child);
            }
        }
        return false;
    }

    public DbTableInfo GetTableInfo() {
        return new DbTableInfo();
    }
}
