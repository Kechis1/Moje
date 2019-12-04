package Benchmark;

enum TestResultStatus {
    Prepared(0),
    Success(1),
    Failed(2),
    Error(3);

    private int value;

    TestResultStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
