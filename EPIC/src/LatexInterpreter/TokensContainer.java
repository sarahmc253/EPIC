package LatexInterpreter;

import LatexInterpreter.TokenHeirarchy.Token;

import java.util.ArrayList;
import java.util.List;

public class TokensContainer {
    /// <summary>
    /// Get the list of tokens.
    /// </summary>
    private List<Token> tokens;

    /// <summary>
    /// Get the current position in the list.
    /// </summary>
    private int i;

    public List<Token> getTokens() {
        return this.tokens;
    }

    public int getPosition() {
        return  this.i;
    }

    /// <summary>
    /// Constructor for the TokensContainer class.
    /// </summary>
    public TokensContainer() {
        this.tokens = new ArrayList<Token>();
    }

    /// <summary>
    /// Add a token to the container.
    /// </summary>
    /// <param name="token"></param>
    public void addToken(Token token) {
        this.tokens.add(token);
        rewind();
    }

    /// <summary>
    /// Reset the current position to zero.
    /// </summary>
    public void rewind() {
        this.i = 0;
    }

    /// <summary>
    /// Return the next token and increase the current position by one.
    /// </summary>
    /// <returns></returns>
    public Token forward() {
        return tokens.get(this.i++);
    }

    /// <summary>
    /// Check the next token without increasing the position.
    /// </summary>
    /// <returns></returns>
    public Token peekForward() {
        return this.tokens.get(this.i);
    }

    /// <summary>
    /// Check the token that is a defined number of poisitions forward from the current position.
    /// </summary>
    /// <param name="offset"></param>
    /// <returns></returns>
    public Token peekForward(int offset) {
        return this.tokens.get(this.i + offset);
    }

    /// <summary>
    /// Checks if there is a token after the current position.
    /// </summary>
    /// <returns></returns>
    public boolean hasNext() {
        return this.i < this.tokens.size();
    }

    /// <summary>
    /// Checks if the token after the current position plus an offset can be read.
    /// </summary>
    /// <param name="offset"></param>
    /// <returns></returns>
    public boolean hasNext(int offset) {
        return this.i + (offset - 1) < this.tokens.size();
    }
}
