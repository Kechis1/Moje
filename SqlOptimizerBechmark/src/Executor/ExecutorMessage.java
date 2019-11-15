package Executor;

import java.sql.Date;

public class ExecutorMessage
{
    private Date dateTime = Date.Now;
    private ExecutorMessageType messageType;
    private String message;
    private String statement;

    public DateTime DateTime
    {
        get => dateTime;
        set => dateTime = value;
    }

    public ExecutorMessageType MessageType
    {
        get => messageType;
        set => messageType = value;
    }

    public string Message
    {
        get => message;
        set => message = value;
    }

    public string Statement
    {
        get => statement;
        set => statement = value;
    }
}
