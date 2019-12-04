package Executor;

import java.sql.Date;

public class ExecutorMessage
{
    private Date dateTime = Date.valueOf("now");
    private ExecutorMessageType messageType;
    private String message;
    private String statement;

    public Date DateTime()
    {
        return dateTime;
    }
    
    public void DateTime(Date value)
    {
        dateTime = value;
    }

    public ExecutorMessageType MessageType()
    {
        return messageType;
    }

    public void MessageType(ExecutorMessageType value)
    {
        messageType = value;
    }

    public String Message()
    {
        return message;
    }

    public void Message(String value)
    {
        message = value;
    }

    public String Statement()
    {
        return statement;
    }

    public void Statement(String value)
    {
        statement = value;
    }
}
