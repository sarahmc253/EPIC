/*
 * Cian McNamara, 2024
 */

package LatexInterpreter;

/// <summary>
/// Streamlines returning token identities in 'LatexInterpreter\TokenIdentifier.java'
/// </summary>
class TokenIdentity {
    public TokenIdentifier.TokenType tokenType;
    public int jump;

    public TokenIdentity(TokenIdentifier.TokenType tokenType, int jump) {
        this.tokenType = tokenType;
        this.jump = jump;
    }
}
