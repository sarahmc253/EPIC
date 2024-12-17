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

        // Checks what type of token something is and then sets the tokenType to that TokenType and jumps forward the correct amount.
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
        // Seeing if the tokenStringReader is blank
        if (!tokenStringReader.hasNext()) {
            return false;
        }

        // Defining our lower and upper bound for characters. (Characters are stored as integers)
        int nextCharacter = tokenStringReader.peekForward();
        int lowerBound = '0';
        int upperBound = '9';

        // Returns wether the character that was found is a number by checking if its integer is between '0' (48) and
        // '9' (57) and returns the answer as true or false.
        return lowerBound <= nextCharacter && nextCharacter <= upperBound;
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is a left bracket '('.
    /// </summary>
    private static boolean isLeftBracket(TokenStringReader tokenStringReader) {
        // Checks if the next character exists and if that character is '(' then it returns true otherwise returns false.
        return tokenStringReader.hasNext() && tokenStringReader.peekForward() == '(';
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is a right bracket ')'.
    /// </summary>
    private static boolean isRightBracket(TokenStringReader tokenStringReader) {
        // Checks if the next character exists and if that character is ')' then it returns true otherwise returns false.
        return tokenStringReader.hasNext() && tokenStringReader.peekForward() == ')';
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is a left curly bracket '{'.
    /// </summary>
    private static boolean isLeftCurlyBracket(TokenStringReader tokenStringReader) {
        // Checks if the next character exists and if that character is '{' then it returns true otherwise returns false.
        return tokenStringReader.hasNext() && tokenStringReader.peekForward() == '{';
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is a right curly bracket '}'
    /// </summary>
    private static boolean isRightCurlyBracket(TokenStringReader tokenStringReader) {
        // Checks if the next character exists and if that character is '}' then it returns true otherwise returns false.
        return tokenStringReader.hasNext() && tokenStringReader.peekForward() == '}';
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is an identifier (variables, functions, latex functions).
    /// </summary>
    private static boolean isIdentifier(TokenStringReader tokenStringReader) {
        // Check if character exists.
        if (!tokenStringReader.hasNext()) {
            // Return false if no character exists.
            return false;
        }

        // Define our upper and lower character boundaries.
        int lowerNumberBound = 48;
        int upperNumberBound = 57;
        int lowerUpperCaseBound = 65;
        int upperUpperCaseBound = 90;
        int lowerLowerCaseBound = 97;
        int upperLowerCaseBound = 122;

        // If the first character is not a number and the character is '_' '\' or some letter or number aslong as it is
        // not first then return true.
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
        // Check if the next character exists and if it is a ',' if so return true else return false.
        return reader.hasNext() && reader.peekForward() == ',';
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is an addition operator '+'.
    /// </summary>
    private static boolean isAdditionOperator(TokenStringReader tokenStringReader) {
        // Check if the next character exists and if it is a '+' if so return true else return false.
        return tokenStringReader.hasNext() && tokenStringReader.peekForward() == '+';
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is a subtraction operator '-'.
    /// </summary>
    private static boolean isSubtractionOperator(TokenStringReader tokenStringReader) {
        // Check if the next character exists and if it is a '-' if so return true else return false.
        return tokenStringReader.hasNext() && tokenStringReader.peekForward() == '-';
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is the start of a multiplication operator.
    /// "\times" or "\cdot".
    /// </summary>
    private static boolean isMultiplicationOperator(TokenStringReader tokenStringReader) {
        // Declare blank strings which will be filled in by the next 2 while loops.
        String cdotString = "";
        String timesString = "";

        // If there are 5 characters left in the tokenStringReader then add them all into cdotString using a for loop.
        if (tokenStringReader.hasNext(5)) {
            for (int i = 0; i < 5; i++) {
                cdotString = cdotString + tokenStringReader.peekForward(i);
            }
        }

        // If there are 6 characters left in the tokenStringReader then add them all into timesString using a for loop.
        if (tokenStringReader.hasNext(6)) {
            for (int i = 0; i < 6; i++) {
                timesString = timesString + tokenStringReader.peekForward(i);
            }
        }

        // If cdotString is "\cdot" or timeString is "\times" then return true.
        if (cdotString.equals("\\cdot") || timesString.equals("\\times")) {
            return true;
        }

        // If everything fails return false.
        return false;
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is the start of a division operator "\div".
    /// </summary>
    private static boolean isDivisionOperator(TokenStringReader tokenStringReader) {
        // Checks if there are 4 or more characters left in the tokenStringReader
        if (tokenStringReader.hasNext(4)) {
            // Declaring a blank string to be filled in later.
            String checkString = "";

            // Set checkString to the next 4 characters.
            for (int i = 0; i < 4; i++) {
                checkString = checkString + tokenStringReader.peekForward(i);
            }

            // See if checkString is equal to "\div" and if it is return true.
            if (checkString.equals("\\div")) {
                return true;
            }
        }

        // If all else fails return false.
        return false;
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is a power operator '^'.
    /// </summary>
    private static boolean isPowerOperator(TokenStringReader tokenStringReader) {
        // Check if the next character exists and if it is a '^' if so return true else return false.
        return tokenStringReader.hasNext() && tokenStringReader.peekForward() == '^';
    }

    /// <summary>
    /// Checks if the next character in some TokenStringReader is a factorial operator '!'.
    /// </summary>
    private static boolean isFactorialOperator(TokenStringReader tokenStringReader) {
        // Check if the next character exists and if it is a '!' if so return true else return false.
        return tokenStringReader.hasNext() && tokenStringReader.peekForward() == '!';
    }

    /// <summary>
    /// Gets the jump size a certain multiplication operation will need.
    /// </summary>
    private static int getMultiplicationJump(TokenStringReader tokenStringReader) {
        // Declare blank strings which will be filled in by the next 2 while loops.
        String cdotString = "";
        String timesString = "";

        // If there are 5 characters left in the tokenStringReader then add them all into cdotString using a for loop.
        if (tokenStringReader.hasNext(5)) {
            for (int i = 0; i < 5; i++) {
                cdotString = cdotString + tokenStringReader.peekForward(i);
            }
        }

        // If there are 6 characters left in the tokenStringReader then add them all into timesString using a for loop.
        if (tokenStringReader.hasNext(6)) {
            for (int i = 0; i < 6; i++) {
                timesString = timesString + tokenStringReader.peekForward(i);
            }
        }

        // If cdotString is "\cdot" or timeString is "\times" then return true.
        if (cdotString.equals("\\cdot")) {
            return 5;
        } else if (timesString.equals("\\times")) {
            return 6;
        }

        // If everything fails return false.
        return 0;
    }
}
