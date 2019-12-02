package Classes;

enum SqlTokenType
{
    IDENTIFIER_OR_KEYWORD,
    COMMA,
    DOT,
    LEFT_BRACKET,
    RIGHT_BRACKET,
    ASTERISK,
    PLUS,
    MINUS,
    SLASH,
    PERCENT,
    STRING_CONSTANT,
    NUMERIC_CONSTANT,
    NOT_EQUAL,
    EQUAL,
    MORE_THAN,
    LESS_THAN,
    MORE_OR_EQUALS,
    LESS_OR_EQUALS,
    EOF,
    UNDEFINED
}

public class SqlToken
{
    private SqlTokenType type;
    private String value;
    private int startCharIndex;
    private int endCharIndex;
    private Boolean quoted = false;

    public SqlTokenType Type()
    {
       return type;
    }

    public String Value()
    {
        return value;
    }

    public int StartCharIndex()
    {
        return startCharIndex;
    }

    public void StartCharIndex(int value)
    {
        startCharIndex = value;
    }

    public int EndCharIndex()
    {
        return endCharIndex;
    }

    public void EndCharIndex(int value)
    {
        endCharIndex = value;
    }

    public Boolean Quoted()
    {
        return quoted;
    }

    public void Quoted(Boolean value)
    {
        quoted = value;
    }

    public boolean equals(Object obj)
    {
        if (obj instanceof SqlToken)
            return (((SqlToken)obj).value.compareToIgnoreCase(this.value)) == 0 && ((SqlToken)obj).type == this.type;
        else
            return super.equals(obj);
    }

    public Boolean Equals(SqlTokenType type, String value)
    {
        return this.value.compareToIgnoreCase(value) == 0 && this.type == type;
    }

    public int GetHashCode()
    {
        return type.hashCode() ^ value.toUpperCase().hashCode();
    }

    public SqlToken(SqlTokenType type, String value, int startCharIndex, int endCharIndex)
    {
        this.type = type;
        this.value = value;
        this.startCharIndex = startCharIndex;
        this.endCharIndex = endCharIndex;
    }

    public SqlToken(SqlTokenType type, String value)
    {
        this.type = type;
        this.value = value;
        startCharIndex = 0;
        endCharIndex = 0;
    }
}
