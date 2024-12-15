/*
 * Cian McNamara, 2024
 */

package LatexInterpreter.TokenHeirarchy;

import LatexInterpreter.TokenIdentifier;
import LatexInterpreter.TokenStringReader;

public class IdentifierToken extends Token {
    public String identifier;

    public IdentifierToken(String identifier) {
        super(TokenIdentifier.TokenType.Identifier);
        this.identifier = identifier;
    }

    private static boolean validFirstCharacter(int character) {
        int lowerNumberBound = 48;
        int upperNumberBound = 57;

        int lowerUpperCaseBound = 65;
        int upperUpperCaseBound = 90;

        int lowerLowerCaseBound = 97;
        int upperLowerCaseBound = 122;

        return
                !(lowerNumberBound <= character && character <= upperNumberBound) &&
                        (character == '_' || character == '\\' || (lowerUpperCaseBound <= character && character <= upperUpperCaseBound) ||
                                (lowerLowerCaseBound <= character && character <= upperLowerCaseBound));
    }

    private static boolean validNameCharacter(int character) {
        int lowerNumberBound = 48;
        int upperNumberBound = 57;

        int lowerUpperCaseBound = 65;
        int upperUpperCaseBound = 90;

        int lowerLowerCaseBound = 97;
        int upperLowerCaseBound = 122;

        return (lowerNumberBound <= character && character <= upperNumberBound) ||
                character == '_' ||
                (lowerUpperCaseBound <= character && character <= upperUpperCaseBound) ||
                (lowerLowerCaseBound <= character && character <= upperLowerCaseBound);
    }

    public static IdentifierToken Parse(TokenStringReader tokenStringReader) {
        String identifier = "";
        int character;

        if (tokenStringReader.hasNext()) {
            character = tokenStringReader.peekForward();

            if (!(validFirstCharacter(character))) {
                throw new RuntimeException("Name token not valid at " + tokenStringReader.getPosition());
            }

            identifier = identifier + (char)character;
            tokenStringReader.forward();
        } else {
            throw new RuntimeException("Unexpected end of data.");
        }

        while (tokenStringReader.hasNext()) {
            character = tokenStringReader.peekForward();

            if (!validNameCharacter(character)) {
                break;
            }

            identifier = identifier + (char)character;
            tokenStringReader.forward();
        }

        return new IdentifierToken(identifier);
    }

    @Override
    public String toString() {
        return identifier;
    }
}
