package LatexInterpreter;

public class TokenIdentity {
    public TokenIdentifier.TokenType tokenType;
    public int jump;

    public TokenIdentity(TokenIdentifier.TokenType tokenType, int jump) {
        this.tokenType = tokenType;
        this.jump = jump;
    }
}
