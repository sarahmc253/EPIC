/*
 * Cian McNamara, 2024
 */

package LatexInterpreter;

public class TokenStringReader {
    private String tokenString;
    private int i;

    /// <summary>
    /// Shows the token string readers token string.
    /// </summary>
    /// <returns></returns>
    public String getTokenString() {
        return this.tokenString;
    }

    /// <summary>
    /// Gets the position of the token string reader.
    /// </summary>
    /// <returns></returns>
    public int getPosition() {
        return this.i;
    }

    /// <summary>
    /// TokenStringReader's class constructor.
    /// </summary>
    /// <param name="tokenString"></param>
    public TokenStringReader(String tokenString) {
        this.tokenString = tokenString;
        rewind();
    }

    /// <summary>
    /// Reset's the token string readers position to zero.
    /// </summary>
    public void rewind() {
        this.i = 0;
    }

    /// <summary>
    /// Returns the next character and goes forward.
    /// </summary>
    /// <returns></returns>
    public int forward() {
        return this.tokenString.charAt(i++);
    }

    /// <summary>
    /// Shows the next character that is going to be read.
    /// </summary>
    /// <returns></returns>
    public char peekForward() {
        return this.tokenString.charAt(i);
    }

    /// <summary>
    /// Shows the character that is a select number of places forward.
    ///</summary>
    /// <param name="offset"></param>
    /// <returns></returns>
    public char peekForward(int offset) {
        return this.tokenString.charAt(i + offset);
    }

    /// <summary>
    /// Checks if the string has one or more characters left which it can read.
    /// </summary>
    /// <returns></returns>
    public boolean hasNext() {
        return this.i < this.tokenString.length();
    }

    /// <summary>
    /// Checks if the string has more than a specific number of characters left.
    /// </summary>
    /// <param name="characterCount"></param>
    /// <returns></returns>
    public boolean hasNext(int offset) {
        return this.i + (offset  - 1) < this.tokenString.length();
    }
}
