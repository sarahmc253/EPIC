/*
 * Cian McNamara, 2024
 */

package LatexInterpreter;

public class TokenIdentifier {
    public enum TokenType {
        Number,
        Identifier,
        ParameterSeparator,
        LeftBracket,
        RightBracket,
        LeftCurlyBracket,
        RightCurlyBracket,
        AdditionOperator,
        SubtractionOperator,
        MultiplicationOperator,
        DivisionOperator,
        PowerOperator,
        FactorialOperator,
        Unknown,
    }

    /// <summary>
    /// Outputs the token type and an integer of how far forward we need to jump in the count via a TokenIdentity class.
    /// <param name="tokenStringReader"></param>
    /// <returns></returns>
    public static TokenIdentity identify(TokenStringReader tokenStringReader) {
        TokenType tokenType = TokenType.Unknown;
        int jump = 1;

        if (isNumber(tokenStringReader)) {
            tokenType = TokenType.Number;
            jump = 0;
        } else if (isLeftBracket(tokenStringReader)) {
            tokenType = TokenType.LeftBracket;
        } else if (isRightBracket(tokenStringReader)) {
            tokenType = TokenType.RightBracket;
        } else if (isLeftCurlyBracket(tokenStringReader)) {
            tokenType = TokenType.LeftCurlyBracket;
        } else if (isRightCurlyBracket(tokenStringReader)) {
            tokenType = TokenType.RightCurlyBracket;
        } else if (isParameterSeparator(tokenStringReader)) {
            tokenType = TokenType.ParameterSeparator;
        } else if (isAdditionOperator(tokenStringReader)) {
            tokenType = TokenType.AdditionOperator;
        } else if (isSubtractionOperator(tokenStringReader)) {
            tokenType = TokenType.SubtractionOperator;
        } else if (isMultiplicationOperator(tokenStringReader)) {
            tokenType = TokenType.MultiplicationOperator;
            jump = getMultiplicationJump(tokenStringReader);
        } else if (isDivisionOperator(tokenStringReader)) {
            tokenType = TokenType.DivisionOperator;
            jump = 4;
        } else if (isPowerOperator(tokenStringReader)) {
            tokenType = TokenType.PowerOperator;
        } else if (isFactorialOperator(tokenStringReader)) {
            tokenType = TokenType.FactorialOperator;
        } else if (isIdentifier(tokenStringReader)) {
            tokenType = TokenType.Identifier;
            jump = 0;
        }

        return new TokenIdentity(tokenType, jump);
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is a number.
    /// </summary>
    private static boolean isNumber(TokenStringReader tokenStringReader) {
        if (!tokenStringReader.hasNext()) {
            return false;
        }

        int nextCharacter = tokenStringReader.peekForward();
        int lowerBound = '0';
        int upperBound = '9';

        return lowerBound <= nextCharacter && nextCharacter <= upperBound;
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is a left bracket '('.
    /// </summary>
    private static boolean isLeftBracket(TokenStringReader tokenStringReader) {
        return tokenStringReader.hasNext() && tokenStringReader.peekForward() == '(';
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is a right bracket ')'.
    /// </summary>
    private static boolean isRightBracket(TokenStringReader tokenStringReader) {
        return tokenStringReader.hasNext() && tokenStringReader.peekForward() == ')';
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is a left curly bracket '{'.
    /// </summary>
    private static boolean isLeftCurlyBracket(TokenStringReader tokenStringReader) {
        return tokenStringReader.hasNext() && tokenStringReader.peekForward() == '{';
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is a right curly bracket '}'
    /// </summary>
    private static boolean isRightCurlyBracket(TokenStringReader tokenStringReader) {
        return tokenStringReader.hasNext() && tokenStringReader.peekForward() == '}';
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is an identifier (variables, functions, latex functions).
    /// </summary>
    private static boolean isIdentifier(TokenStringReader tokenStringReader) {
        if (!tokenStringReader.hasNext()) {
            return false;
        }

        int lowerNumberBound = 48;
        int upperNumberBound = 57;
        int lowerUpperCaseBound = 65;
        int upperUpperCaseBound = 90;
        int lowerLowerCaseBound = 97;
        int upperLowerCaseBound = 122;


        int nextCharacter = tokenStringReader.peekForward();
        return
                !(lowerNumberBound <= nextCharacter && nextCharacter <= upperNumberBound) &&
                        (nextCharacter == '_' || nextCharacter == '\\' ||
                                (lowerLowerCaseBound <= nextCharacter && nextCharacter <= upperLowerCaseBound) ||
                                (lowerUpperCaseBound <= nextCharacter && nextCharacter <= upperUpperCaseBound));
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is a ','.
    /// </summary>
    private static boolean isParameterSeparator(TokenStringReader reader) {
        return reader.hasNext() && reader.peekForward() == ',';
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is an addition operator '+'.
    /// </summary>
    private static boolean isAdditionOperator(TokenStringReader tokenStringReader) {
        return tokenStringReader.hasNext() && tokenStringReader.peekForward() == '+';
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is a subtraction operator '-'.
    /// </summary>
    private static boolean isSubtractionOperator(TokenStringReader tokenStringReader) {
        return tokenStringReader.hasNext() && tokenStringReader.peekForward() == '-';
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is the start of a multiplication operator.
    /// "\times" or "\cdot".
    /// </summary>
    private static boolean isMultiplicationOperator(TokenStringReader tokenStringReader) {
        if (tokenStringReader.hasNext(6)) {
            String checkString = "";

            for (int i = 0; i < 6; i++) {
                checkString = checkString + tokenStringReader.peekForward(i);
            }

            if (checkString.equals("\\times")) {
                return true;
            }
        } else if (tokenStringReader.hasNext(5)) {
            String checkString = "";

            for (int i = 0; i < 5; i++) {
                checkString = checkString + tokenStringReader.peekForward(i);
            }

            if (checkString.equals("\\cdot")) {
                return true;
            }
        }
        return false;
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is the start of a division operator "\div".
    /// </summary>
    private static boolean isDivisionOperator(TokenStringReader tokenStringReader) {
        if (tokenStringReader.hasNext(4)) {
            String checkString = "";

            for (int i = 0; i < 4; i++) {
                checkString = checkString + tokenStringReader.peekForward(i);
            }

            if (checkString.equals("\\div")) {
                return true;
            }
        }

        return false;
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is a power operator '^'.
    /// </summary>
    private static boolean isPowerOperator(TokenStringReader tokenStringReader) {
        return tokenStringReader.hasNext() && tokenStringReader.peekForward() == '^';
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is a factorial operator '!'.
    /// </summary>
    private static boolean isFactorialOperator(TokenStringReader tokenStringReader) {
        return tokenStringReader.hasNext() && tokenStringReader.peekForward() == '!';
    }

    /// <summary>
    /// Gets the jump size a certain multiplication operation will need.
    /// </summary>
    private static int getMultiplicationJump(TokenStringReader tokenStringReader) {
        if (tokenStringReader.hasNext(6)) {
            String checkString = "";

            for (int i = 0; i < 6; i++) {
                checkString = checkString + tokenStringReader.peekForward(i);
            }

            if (checkString.equals("\\times")) {
                return 6;
            }
        } else if (tokenStringReader.hasNext(5)) {
            String checkString = "";

            for (int i = 0; i < 5; i++) {
                checkString = checkString + tokenStringReader.peekForward(i);
            }

            if (checkString.equals("\\cdot")) {
                return 5;
            }
        }

        return 0;
    }
}
