package Executor;

public class ExecutorMessageEventArgs extends EventArgs
{
    private ExecutorMessage message;

    public ExecutorMessage Message()
    {
        return message;
    }
    public void Message(ExecutorMessage value)
    {
        message = value;
    }

    public ExecutorMessageEventArgs(ExecutorMessage message)
    {
        this.message = message;
    }
}