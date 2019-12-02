package Classes;

public class SqlScannerException extends SqlException {
    public static final int CODE_UNTERMINATED_STRING = 1;
    public static final int CODE_INVALID_NUMERIC_SYMBOL = 2;
    public static final int CODE_INVALID_IDENTIFIER_SYMBOL = 3;
    public static final int CODE_INVALID_SYMBOL = 4;

    public SqlScannerException(int code, String message, int startCharIndex, int endCharIndex) {
        super(code, message, startCharIndex, endCharIndex);
    }
}
