/*
 * Cian McNamara, 2024
 */

package LatexInterpreter.TokenHeirarchy;

import LatexInterpreter.TokenIdentifier;

public class Token {
    public TokenIdentifier.TokenType tokenType;

    public Token(TokenIdentifier.TokenType tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return tokenType.toString();
    }
}
