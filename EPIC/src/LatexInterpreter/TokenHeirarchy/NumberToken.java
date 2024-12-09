package LatexInterpreter.TokenHeirarchy;

import LatexInterpreter.TokenIdentifier;
import LatexInterpreter.TokenStringReader;

public class NumberToken extends Token {
    public double value;

    public NumberToken(double value) {
        super(TokenIdentifier.TokenType.Number);
        this.value = value;
    }

    private static boolean isDigit(int character) {
        return '0' <= character && character <= '9';
    }

    public static NumberToken Parse(TokenStringReader tokenStringReader) {
        String number = "";
        int character;
        boolean isDecimal = false;

        if (tokenStringReader.hasNext()) {
            character = tokenStringReader.peekForward();

            if (!isDigit(character)) {
                throw new RuntimeException("Number token not valid at " +  tokenStringReader.getPosition());
            }

            number = number + (char)character;
            tokenStringReader.forward();
        }

        while (tokenStringReader.hasNext()) {
            character = tokenStringReader.peekForward();

            if (character != '.' && !isDigit(character)) {
                break;
            }

            if (character == '.') {
                if (isDecimal) {
                    throw new RuntimeException("Number token not valid at " + tokenStringReader.getPosition());
                }

                isDecimal = true;
            }

            number = number + (char)character;
            tokenStringReader.forward();
        }

        return new NumberToken(Double.parseDouble(number));
    }
}
