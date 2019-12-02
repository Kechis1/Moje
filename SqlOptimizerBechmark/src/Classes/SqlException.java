package Classes;

public class SqlException extends Exception
{
    private int code;
    private int startCharIndex;
    private int endCharIndex;

    public int Code()
    {
        return code;
    }

    public int StartCharIndex()
    {
        return startCharIndex;
    }

    public int EndCharIndex()
    {
        return endCharIndex;
    }

    public SqlException(int code, String message, int startCharIndex, int endCharIndex)
    {
        super(message);
        this.code = code;
        this.startCharIndex = startCharIndex;
        this.endCharIndex = endCharIndex;
    }
}

