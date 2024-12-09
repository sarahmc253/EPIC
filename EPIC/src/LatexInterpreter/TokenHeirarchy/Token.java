package LatexInterpreter.TokenHeirarchy;

import LatexInterpreter.TokenIdentifier;

public class Token {
    public TokenIdentifier.TokenType tokenType;

    public Token(TokenIdentifier.TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public TokenIdentifier.TokenType getTokenType() {
        return this.tokenType;
    }
}
