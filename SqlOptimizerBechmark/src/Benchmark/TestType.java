package Benchmark;

enum TestType
{
    PlanEquivalence(0);

    private int value;

    TestType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}