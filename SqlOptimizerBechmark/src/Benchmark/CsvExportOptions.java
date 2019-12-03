package Benchmark;

enum CsvExportOptions {
    ExportDistinctPlans(1),
    ExportQueryVariants(2);

    private int value;

    CsvExportOptions(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
