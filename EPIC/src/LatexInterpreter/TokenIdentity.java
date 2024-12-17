/*
 * Cian McNamara, 2024
 */

package LatexInterpreter;

/// <summary>
/// Streamlines returning token identities in 'LatexInterpreter\TokenIdentifier.java' by returning the information in an expected format.
/// </summary>
public class TokenIdentity {
    public TokenIdentifier.TokenType tokenType;
    public int jump;

    public TokenIdentity(TokenIdentifier.TokenType tokenType, int jump) {
        this.tokenType = tokenType;
        this.jump = jump;
    }
}
