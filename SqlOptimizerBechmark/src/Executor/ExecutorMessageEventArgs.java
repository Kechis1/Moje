package Executor;

public class ExecutorMessageEventArgs extends EventArgs
{
    private ExecutorMessage message;

    public ExecutorMessage Message
    {
        get => message;
        set => message = value;
    }

    public ExecutorMessageEventArgs(ExecutorMessage message)
    {
        this.message = message;
    }
}