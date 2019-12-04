package Executor;

enum ExecutorMessageType {
    Info(0),
    Warning(1),
    Error(2);

    private int value;

    ExecutorMessageType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
