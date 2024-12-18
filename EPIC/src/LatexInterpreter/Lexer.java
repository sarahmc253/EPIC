/*
 * Cian McNamara, 2024
 */

package LatexInterpreter;

import LatexInterpreter.TokenHeirarchy.*;

/// <summary>
/// The Lexer breaks down the string into a tokenized form.
/// These could be numbers, operators, brackets, curly brackets or whitespace in our case.
/// </summary>
public class Lexer {
    /// <summary>
    /// The token string reader object which will be used in the rest of the class.
    /// </summary>
    TokenStringReader tokenStringReader;

    /// <summary>
    /// The constructor method for the Lexer class.
    /// </summary>
    /// <param name="tokenStringReader"></param>
    public Lexer(TokenStringReader tokenStringReader) {
        this.tokenStringReader = tokenStringReader;
    }

    /// <summary>
    /// Tokenizes all the data into their respective TokenTypes and adds them to a TokensContainer.
    /// </summary>
    /// <returns></returns>
    /// <exception cref="RuntimeException"></exception>
    public TokensContainer tokenize() {
        // Create a new token container
        TokensContainer tokenContainer = new TokensContainer();

        // Keep repeating until you are at the end of the input string
        while (tokenStringReader.hasNext()) {
            TokenIdentity identity = TokenIdentifier.identify(tokenStringReader);
            TokenIdentifier.TokenType tokenType = identity.tokenType;
            int jump = identity.jump; // how far we jump forward in the string after reaching a character

            // skip white space if you see it
            if (tokenStringReader.peekForward() == ' ') {
                tokenStringReader.forward();
            } else {
                // loop through the entire string
                for (int i = 0; i < jump; i++) { tokenStringReader.forward(); }
                // follow the right process for each token type when adding the token to the container
                switch (tokenType) {
                    case TokenIdentifier.TokenType.Number:
                        tokenContainer.addToken(NumberToken.parse(tokenStringReader));
                        break;
                    case TokenIdentifier.TokenType.Identifier:
                        tokenContainer.addToken(IdentifierToken.Parse(tokenStringReader));
                        break;
                    case TokenIdentifier.TokenType.ParameterSeparator:
                        tokenContainer.addToken(new Token(TokenIdentifier.TokenType.ParameterSeparator));
                        break;
                    case TokenIdentifier.TokenType.LeftBracket:
                        tokenContainer.addToken(new Token(TokenIdentifier.TokenType.LeftBracket));
                        break;
                    case TokenIdentifier.TokenType.RightBracket:
                        tokenContainer.addToken(new Token(TokenIdentifier.TokenType.RightBracket));
                        break;
                    case TokenIdentifier.TokenType.LeftCurlyBracket:
                        tokenContainer.addToken(new Token(TokenIdentifier.TokenType.LeftCurlyBracket));
                        break;
                    case TokenIdentifier.TokenType.RightCurlyBracket:
                        tokenContainer.addToken(new Token(TokenIdentifier.TokenType.RightCurlyBracket));
                        break;
                    case TokenIdentifier.TokenType.AdditionOperator:
                        tokenContainer.addToken(new Token(TokenIdentifier.TokenType.AdditionOperator));
                        break;
                    case TokenIdentifier.TokenType.SubtractionOperator:
                        tokenContainer.addToken(new Token(TokenIdentifier.TokenType.SubtractionOperator));
                        break;
                    case TokenIdentifier.TokenType.MultiplicationOperator:
                        tokenContainer.addToken(new Token(TokenIdentifier.TokenType.MultiplicationOperator));
                        break;
                    case TokenIdentifier.TokenType.DivisionOperator:
                        tokenContainer.addToken(new Token(TokenIdentifier.TokenType.DivisionOperator));
                        break;
                    case TokenIdentifier.TokenType.PowerOperator:
                        tokenContainer.addToken(new Token(TokenIdentifier.TokenType.PowerOperator));
                        break;
                    case TokenIdentifier.TokenType.FactorialOperator:
                        tokenContainer.addToken(new Token(TokenIdentifier.TokenType.FactorialOperator));
                        break;
                    default:
                        // We couldn't find an appropriate case for our token so it is an invalid token
                        throw new RuntimeException("Invalid token at " + tokenStringReader.getPosition());
                }
            }
        }

        return tokenContainer;
    }
}
