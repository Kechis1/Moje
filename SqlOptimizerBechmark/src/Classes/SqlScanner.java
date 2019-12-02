package Classes;

import java.util.ArrayList;
import java.util.List;

public class SqlScanner
    {
        private static char[] splitters = { ' ', '\t', '\r', '\n', ')', '(', ')', '+', '-', '*', '/', '.', ',', '=', '>', '<', '!', '%' };
        private static char[] whiteChars = { ' ', '\t', '\r', '\n' };
        private static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        private static char[] identifierChars = { '_', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'á', 'ä', 'č', 'ď', 'é', 'ě', 'í', 'ĺ', 'ľ', 'ň', 'ó', 'ô', 'ŕ', 'ř', 'š', 'ť', 'ú', 'ů', 'ý', 'ž',
            'Á', 'Ä', 'Č', 'Ď', 'É', 'Ě', 'Í', 'Ĺ', 'Ľ', 'Ň', 'Ó', 'Ô', 'Ŕ', 'Ř', 'Š', 'Ť', 'Ú', 'Ů', 'Ý', 'Ž'};
        private static char[] firstIdentifierChars = { '_',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'á', 'ä', 'č', 'ď', 'é', 'ě', 'í', 'ĺ', 'ľ', 'ň', 'ó', 'ô', 'ŕ', 'ř', 'š', 'ť', 'ú', 'ů', 'ý', 'ž',
            'Á', 'Ä', 'Č', 'Ď', 'É', 'Ě', 'Í', 'Ĺ', 'Ľ', 'Ň', 'Ó', 'Ô', 'Ŕ', 'Ř', 'Š', 'Ť', 'Ú', 'Ů', 'Ý', 'Ž'};

        private static char decimalSeparator = '.';
        private static char stringChar = '\'';
        private static char identifierChar1 = '\"';
        private static char identifierStartChar2 = '[';
        private static char identifierEndChar2 = ']';

        private String querySql;
        private List<SqlToken> tokens;

        public String QuerySql()
        {
            return querySql;
        }

        public void QuerySql(String value)
        {
            querySql = value;
        }

        public List<SqlToken> Tokens()
        {
            return tokens;
        }

        private boolean IsInArray(char c, char[] array)
        {
            for (char c1 : array) {
                if (c1 == c) return true;
            }
            return false;
        }

        private boolean IsSplitter(char c) { return IsInArray(c, splitters); }
        private boolean IsWhiteChar(char c) { return IsInArray(c, whiteChars); }
        private boolean IsDigit(char c) { return IsInArray(c, digits); }
        private boolean IsIdentifierChar(char c) { return IsInArray(c, identifierChars); }
        private boolean IsFirstIdentifierChar(char c) { return IsInArray(c, firstIdentifierChars); }

        private SqlToken CreateToken(String strSymbol, int startCharIndex, int endCharIndex) throws SqlScannerException {

            switch (strSymbol)
            {
                case "(": return new SqlToken(SqlTokenType.LEFT_BRACKET, "(", startCharIndex, endCharIndex);
                case ")": return new SqlToken(SqlTokenType.RIGHT_BRACKET, ")", startCharIndex, endCharIndex);
                case ".": return new SqlToken(SqlTokenType.DOT, ".", startCharIndex, endCharIndex);
                case ",": return new SqlToken(SqlTokenType.COMMA, ",", startCharIndex, endCharIndex);
                case "+": return new SqlToken(SqlTokenType.PLUS, "+", startCharIndex, endCharIndex);
                case "-": return new SqlToken(SqlTokenType.MINUS, "-", startCharIndex, endCharIndex);
                case "*": return new SqlToken(SqlTokenType.ASTERISK, "*", startCharIndex, endCharIndex);
                case "/": return new SqlToken(SqlTokenType.SLASH, "/", startCharIndex, endCharIndex);
                case "%": return new SqlToken(SqlTokenType.PERCENT, "%", startCharIndex, endCharIndex);
                case "=": return new SqlToken(SqlTokenType.EQUAL, "=", startCharIndex, endCharIndex);
                case ">": return new SqlToken(SqlTokenType.MORE_THAN, ">", startCharIndex, endCharIndex);
                case "<": return new SqlToken(SqlTokenType.LESS_THAN, "<", startCharIndex, endCharIndex);
                case ">=": return new SqlToken(SqlTokenType.MORE_OR_EQUALS, ">=", startCharIndex, endCharIndex);
                case "<=": return new SqlToken(SqlTokenType.LESS_OR_EQUALS, "<=", startCharIndex, endCharIndex);
                case "<>": return new SqlToken(SqlTokenType.NOT_EQUAL, "<>", startCharIndex, endCharIndex);
                case "!=": return new SqlToken(SqlTokenType.NOT_EQUAL, "!=", startCharIndex, endCharIndex);
                default:
                    if (strSymbol.charAt(0) == stringChar)
                        return new SqlToken(SqlTokenType.STRING_CONSTANT, strSymbol.substring(1, strSymbol.length() - 2).replace("''", "'"), startCharIndex, endCharIndex);

                    //strSymbol = strSymbol.ToUpper();

                    if (IsDigit(strSymbol.charAt(0)) || (strSymbol.charAt(0) == decimalSeparator))
                    {
                        for (char c : strSymbol.toCharArray())
                            if (!IsDigit(c) && (c != decimalSeparator))
                                throw new SqlScannerException(SqlScannerException.CODE_INVALID_NUMERIC_SYMBOL, "Invalid numeric symbol '" + strSymbol + "'", startCharIndex, endCharIndex);
                        return new SqlToken(SqlTokenType.NUMERIC_CONSTANT, strSymbol, startCharIndex, endCharIndex);
                    }

                    if (strSymbol.charAt(0) == identifierChar1)
                        return new SqlToken(SqlTokenType.IDENTIFIER_OR_KEYWORD, strSymbol.substring(1, strSymbol.length() - 2).replace("\"\"", "\""), startCharIndex, endCharIndex);

                    if (strSymbol.charAt(0) == identifierStartChar2)
                        return new SqlToken(SqlTokenType.IDENTIFIER_OR_KEYWORD, strSymbol.substring(1, strSymbol.length() - 2).replace("]]", "]"), startCharIndex, endCharIndex);

                    if (IsFirstIdentifierChar(strSymbol.charAt(0)))
                    {
                        for (char c : strSymbol.toCharArray())
                            if (!IsIdentifierChar(c))
                                throw new SqlScannerException(SqlScannerException.CODE_INVALID_IDENTIFIER_SYMBOL, "Invalid identifier symbol '" + strSymbol + "'", startCharIndex, endCharIndex);

                        // Specialni test... nektera klicova slova mohou byt taky funkce (LEFT, RIGHT)
                        boolean functionFollows = false;
                        int index = endCharIndex + 1;
                        while (index < querySql.length() && IsWhiteChar(querySql.charAt(index)))
                        {
                            index++;
                        }
                        if (index < querySql.length() && querySql.charAt(index) == '(')
                        {
                            functionFollows = true;
                        }

                        return new SqlToken(SqlTokenType.IDENTIFIER_OR_KEYWORD, strSymbol, startCharIndex, endCharIndex);
                    }

                    throw new SqlScannerException(SqlScannerException.CODE_INVALID_SYMBOL, "Invalid symbol '" + strSymbol + "'", startCharIndex, endCharIndex);
            }
        }

        private List<SqlToken> Tokenize() throws SqlScannerException {
            List<SqlToken> result = new ArrayList<SqlToken>();

            String token = "";
            boolean readingString = false;         // Priznak - cte se string
            boolean readingIdentifier1 = false;    // Priznak - cte se identifikator v uvozovkach
            boolean readingIdentifier2 = false;    // Priznak - cte se identifikator v hranatych zavorkach
            boolean readingComment1 = false;       // Priznak - cte se jednoradkovy komentar uvozeny --
            boolean readingComment2 = false;       // Priznak - cte se viceradkovy komentar mezi /*  */
            char nextChar;
            char currChar;
            int startCharIndex = 0;
            int index = 0;

            while (index < querySql.length())
            {
                currChar = querySql.charAt(index);

                if (index + 1 < querySql.length())
                    nextChar = querySql.charAt(index+1);
                else
                    nextChar = (char)0;

                // Zacatek unikodoveho textoveho retezce
                if (currChar == 'N' && nextChar == stringChar && !readingString && !readingIdentifier1 && !readingIdentifier2 && !readingComment1 && !readingComment2)
                {
                    if (token != "") result.add(CreateToken(token, startCharIndex, index - 1));
                    readingString = true;
                    index++;
                }
                // Textove retezce
                else if (currChar == stringChar && !readingIdentifier1 && !readingIdentifier2 && !readingComment1 && !readingComment2)
                {
                    if (readingString)
                    {
                        if (nextChar == stringChar)
                        {
                            index++;
                            token += stringChar + String.valueOf(stringChar);
                        }
                        else
                        {
                            result.add(CreateToken(stringChar + token + stringChar, startCharIndex, index));
                            readingString = false;
                            token = "";
                        }
                    }
                    else
                    {
                        if (token != "") result.add(CreateToken(token, startCharIndex, index - 1));
                        readingString = true;
                    }
                }
                // Identifikatory v uvozovkach "..."
                else if (currChar == identifierChar1 && !readingString && !readingIdentifier2 && !readingComment1 && !readingComment2)
                {
                    if (readingIdentifier1)
                    {
                        if (nextChar == identifierChar1)
                        {
                            index++;
                            token += String.valueOf(identifierChar1) + identifierChar1;
                        }
                        else
                        {
                            SqlToken sqlToken = CreateToken(identifierChar1 + token + identifierChar1, startCharIndex, index);
                            sqlToken.Quoted(true);
                            result.add(sqlToken);

                            readingIdentifier1 = false;
                            token = "";
                        }
                    }
                    else
                    {
                        if (token != "") result.add(CreateToken(token, startCharIndex, index - 1));
                        readingIdentifier1 = true;
                    }
                }
                // Identifikatory v hranatych zavorkach [...]
                else if (currChar == identifierStartChar2 && !readingString && !readingIdentifier1 && !readingIdentifier2 && !readingComment1 && !readingComment2)
                {
                    if (token != "") result.add(CreateToken(token, startCharIndex, index - 1));
                    readingIdentifier2 = true;
                }
                else if (currChar == identifierEndChar2 && readingIdentifier2)
                {
                    if (nextChar == identifierEndChar2)
                    {
                        index++;
                        token += identifierEndChar2 + identifierEndChar2;
                    }
                    else
                    {
                        SqlToken sqlToken = CreateToken(identifierStartChar2 + token + identifierEndChar2, startCharIndex, index);
                        sqlToken.Quoted(true);
                        result.add(sqlToken);

                        readingIdentifier2 = false;
                        token = "";
                    }
                }
                // Jedoradkovy komentar
                else if (currChar == '-' && nextChar == '-' && !readingString && !readingIdentifier1 && !readingIdentifier2 && !readingComment1 && !readingComment2)
                {
                    if (token != "") result.add(CreateToken(token, startCharIndex, index - 1));
                    readingComment1 = true;
                }
                else if (currChar == '\n' && readingComment1)
                {
                    readingComment1 = false;
                    token = "";
                }
                // Viceradkovy komentar
                else if (currChar == '/' && nextChar == '*' && !readingString && !readingIdentifier1 && !readingIdentifier2 && !readingComment1 && !readingComment2)
                {
                    if (token != "") result.add(CreateToken(token, startCharIndex, index - 1));
                    readingComment2 = true;
                }
                else if (currChar == '*' && nextChar == '/' && readingComment2)
                {
                    index++;
                    readingComment2 = false;
                    token = "";
                }
                else
                {
                    boolean extendToken = false;

                    if (!readingString && !readingIdentifier1 && !readingIdentifier2 && !readingComment1 && !readingComment2)
                    {
                        if (IsSplitter(currChar) && !(currChar == decimalSeparator && IsDigit(nextChar) && (token == "" || IsDigit(token.charAt(0)))))
                        {
                            if (token != "")
                                result.add(CreateToken(token, startCharIndex, index - 1));

                            if (!IsWhiteChar(currChar))
                            {
                                if (currChar == '>' && nextChar == '=')
                                {
                                    result.add(CreateToken(">=", index, index + 2));
                                    index++;
                                }
                                else if (currChar == '<' && nextChar == '=')
                                {
                                    result.add(CreateToken("<=", index, index + 2));
                                    index++;
                                }
                                else if (currChar == '<' && nextChar == '>')
                                {
                                    result.add(CreateToken("<>", index, index + 2));
                                    index++;
                                }
                                else if (currChar == '!' && nextChar == '=')
                                {
                                    result.add(CreateToken("!=", index, index + 2));
                                    index++;
                                }
                                else
                                    result.add(CreateToken(String.valueOf(currChar), index, index));

                            }

                            token = "";
                            startCharIndex = index + 1;
                        }
                        else
                        {
                            extendToken = true;
                        }
                    }
                    else
                    {
                        extendToken = true;
                    }

                    if (extendToken)
                    {
                        token += currChar;
                    }
                }
                index++;
            }

            if (readingString)
                throw new SqlScannerException(SqlScannerException.CODE_UNTERMINATED_STRING, "Unterminated string", startCharIndex, index);
            if (token != "") result.add(CreateToken(token, startCharIndex, index - 1));

            result.add(new SqlToken(SqlTokenType.EOF, "", startCharIndex, index));
            return result;
        }

        public void Scan() throws SqlScannerException {
            tokens = Tokenize();
        }

        public SqlScanner(String querySql)
        {
            this.querySql = querySql;
        }
    }