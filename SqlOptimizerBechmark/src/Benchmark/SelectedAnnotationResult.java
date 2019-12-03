package Benchmark;
    public class SelectedAnnotationResult extends BenchmarkObject
    {
        private BenchmarkObject parentObject;
        private int annotationId;
        private boolean isTemplateAnnotation = false;

        @Override
        public IBenchmarkObject ParentObject () {
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

        public boolean IsTemplateAnnotation()
        {
            return isTemplateAnnotation; 
        }

        public void IsTemplateAnnotation(boolean value)
        {
            if (isTemplateAnnotation != value)
            {
                isTemplateAnnotation = value;
                OnPropertyChanged("IsTemplateAnnotation");
            }
        }

        public SelectedAnnotationResult(BenchmarkObject parentObject)
        {
            this.parentObject = parentObject;
        }

        @Override
        public void SaveToXml(BenchmarkXmlSerializer serializer)
        {
            serializer.WriteInt("annotation_id", annotationId);
            serializer.WriteBool("is_template_annotation", isTemplateAnnotation);
        }

        @Override
        public void LoadFromXml(BenchmarkXmlSerializer serializer)
        {
            serializer.ReadInt("annotation_id", ref annotationId);
            serializer.ReadBool("is_template_annotation", ref isTemplateAnnotation);
        }

        @Override
        public DbTableInfo GetTableInfo()
        {
            DbTableInfo ret = super.GetTableInfo();

            ret.TableName("SelectedAnnotationResult");

            ret.DbColumns().add(new DbColumnInfo("selected_annotation_result_id", true, true)); // PK
            ret.DbColumns().add(new DbColumnInfo(null, "test_run_id", System.Data.DbType.Int32, true, "TestRun", "test_run_id")); // FK
            ret.DbColumns().add(new DbColumnInfo(null, "test_result_id", System.Data.DbType.Int32, true, "TestResult", "test_result_id")); // FK
            ret.DbColumns().add(new DbColumnInfo(null, "query_variant_result_id", System.Data.DbType.Int32, true, "QueryVariantResult", "query_variant_result_id")); // FK
            ret.DbColumns().add(new DbColumnInfo(null, "template_result_id", System.Data.DbType.Int32, true, "TemplateResult", "template_result_id")); // FK
            ret.DbColumns().add(new DbColumnInfo("AnnotationId", "annotation_id", System.Data.DbType.Int32, true, "Annotation", "annotation_id")); // FK
            ret.DbColumns().add(new DbColumnInfo("IsTemplateAnnotation", "is_template_annotation", System.Data.DbType.Boolean));

            return ret;
        }
    }
