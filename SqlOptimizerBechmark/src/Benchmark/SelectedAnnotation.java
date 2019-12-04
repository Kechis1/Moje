package Benchmark;
    public class SelectedAnnotation extends BenchmarkObject
    {
        private BenchmarkObject parentObject;
        private int annotationId;

        public IBenchmarkObject ParentObject() {
            return parentObject;
        }

        public int AnnotationId()
        {
            return annotationId;
        }

        public void AnnotationId(int value)
        {
            if (annotationId != value)
            {
                annotationId = value;
                OnPropertyChanged("AnnotationId");
            }
        }

        public SelectedAnnotation(BenchmarkObject parentObject)
        {
            this.parentObject = parentObject;
        }

        @Override
        public void LoadFromXml(BenchmarkXmlSerializer serializer)
        {
            serializer.ReadInt("annotation_id", annotationId);
        }

        @Override
        public void SaveToXml(BenchmarkXmlSerializer serializer)
        {
            serializer.WriteInt("annotation_id", annotationId);
        }

        @Override
        public DbTableInfo GetTableInfo()
        {
            DbTableInfo ret = super.GetTableInfo();

            ret.TableName("SelectedAnnotation");

            ret.DbColumns().add(new DbColumnInfo("AnnotationId", "annotation_id", System.Data.DbType.Int32, true, "Annotation", "annotation_id"));

            return ret;
        }
    }
