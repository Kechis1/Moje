package Benchmark;
    public class SelectedAnnotation extends BenchmarkObject
    {
        private BenchmarkObject parentObject;
        private int annotationId;

        public IBenchmarkObject ParentObject() {
            return parentObject;
        }

        public int AnnotationId
        {
            get => annotationId;
            set
            {
                if (annotationId != value)
                {
                    annotationId = value;
                    OnPropertyChanged("AnnotationId");
                }
            }
        }

        public SelectedAnnotation(BenchmarkObject parentObject)
        {
            this.parentObject = parentObject;
        }

        public override void LoadFromXml(BenchmarkXmlSerializer serializer)
        {
            serializer.ReadInt("annotation_id", ref annotationId);
        }

        public override void SaveToXml(BenchmarkXmlSerializer serializer)
        {
            serializer.WriteInt("annotation_id", annotationId);
        }

        public override DbTableInfo GetTableInfo()
        {
            DbTableInfo ret = base.GetTableInfo();

            ret.TableName = "SelectedAnnotation";

            ret.DbColumns.Add(new DbColumnInfo("AnnotationId", "annotation_id", System.Data.DbType.Int32, true, "Annotation", "annotation_id"));

            return ret;
        }
    }
