package LatexInterpreter.TokenHeirarchy;

public class Token {
    public TokenIdentifier.TokenType tokenType;

    public Token(TokenIdentifier.TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public getTokenType() {
        return this.tokenType;
    }
}
