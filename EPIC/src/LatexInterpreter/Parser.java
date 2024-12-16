/*
 * Cian McNamara, 2024
 */

package LatexInterpreter;

public class Parser {
    TokensContainer tokensContainer;

    /// <summary>
    /// Constructor for the Parser class.
    /// </summary>
    /// <param name="tokensContainer"></param>
    public Parser(TokensContainer tokensContainer) {
        this.tokensContainer = tokensContainer;
    }

    /// <summary>
    /// Builds the abstract syntax tree.
    /// </summary>
    /// <returns></returns>
    public AbstractSyntaxTreeNode buildParseTree() {
        AbstractSyntaxTreeNode root = expression();
        return root;
    }

    private AbstractSyntaxTreeNode expression() {
        return expression(false, false);
    }

    /// <summary>
    /// Handles the lowest priority operations (addition and subtraction).
    /// </summary>
    /// <param name="expectBrackets"></param>
    /// <returns></returns>
    /// <exception cref="RuntimeException"></exception>
    private AbstractSyntaxTreeNode expression(boolean expectBrackets, boolean expectCurlyBrackets) {
        AbstractSyntaxTreeNode node = new AbstractSyntaxTreeNode();

        if (tokensContainer.hasNext()) {
            node.left = term();
        } else {
            return null;
        }

        if (tokensContainer.hasNext() && (tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.AdditionOperator ||
                tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.SubtractionOperator)) {
            node.token = tokensContainer.forward();
            node.right = term();

            while(tokensContainer.hasNext() && (tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.AdditionOperator ||
                    tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.SubtractionOperator)) {
                AbstractSyntaxTreeNode newNode = new AbstractSyntaxTreeNode();

                newNode.token = tokensContainer.forward();
                newNode.left = node;
                newNode.right = term();

                node = newNode;
            }
        }

        if (expectBrackets && (!tokensContainer.hasNext() || tokensContainer.peekForward().tokenType != TokenIdentifier.TokenType.RightBracket)) {
            throw new RuntimeException("Syntax error: Expecting ')' at token " + tokensContainer.getPosition());
        } else if (expectBrackets &&
                tokensContainer.hasNext() &&
                tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.RightBracket) {
            tokensContainer.forward();
        }

        if (expectCurlyBrackets && (!tokensContainer.hasNext() || tokensContainer.peekForward().tokenType != TokenIdentifier.TokenType.RightCurlyBracket)) {
            throw new RuntimeException("Syntax error: Expecting '}' at token " + tokensContainer.getPosition());
        } else if (expectCurlyBrackets &&
                tokensContainer.hasNext() &&
                tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.RightCurlyBracket) {
            tokensContainer.forward();
        }

        if (node.right == null) {
            node = node.left;
        }

        return node;
    }

    /// <summary>
    /// Handles the 2nd least important operations (multiplication and division).
    /// </summary>
    /// <returns></returns>
    /// <exception cref="RuntimeException"></exception>
    private AbstractSyntaxTreeNode term() {
        AbstractSyntaxTreeNode node = new AbstractSyntaxTreeNode();

        if (!tokensContainer.hasNext()) {
            throw new RuntimeException("Syntax error: Expecting factor at token " + tokensContainer.getPosition());
        }

        node.left = factor();

        if (tokensContainer.hasNext() && (tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.MultiplicationOperator ||
                        tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.DivisionOperator)) {
            node.token = tokensContainer.forward();
            node.right = factor();

            while (tokensContainer.hasNext() && (tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.MultiplicationOperator ||
                    tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.DivisionOperator)) {
                AbstractSyntaxTreeNode newNode = new AbstractSyntaxTreeNode();

                newNode.token = tokensContainer.forward();
                newNode.left = node;
                newNode.right = factor();
                node = newNode;
            }
        }

        if (node.right == null) {
            node = node.left;
        }

        return node;
    }

    /// <summary>
    /// Handles the 3rd least important operations (numbers with a minus put before them such as -5,
    /// factorials such as 5! or powers such as 2^5.).
    /// </summary>
    /// <returns></returns>
    /// <exception cref="RuntimeException"></exception>
    private AbstractSyntaxTreeNode factor() {
        AbstractSyntaxTreeNode node = new AbstractSyntaxTreeNode();

        if (!tokensContainer.hasNext()) {
            throw new RuntimeException("Syntax error: Expecting factor base at token " + tokensContainer.getPosition());
        }

        LeftOperatorNode leftOperator = null;

        if (tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.SubtractionOperator) {
            leftOperator = new LeftOperatorNode();
            leftOperator.token = tokensContainer.forward();
        }

        node.left = factorBase();

        if (tokensContainer.hasNext() && tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.PowerOperator) {
            node.token = tokensContainer.forward();
            node.right = factor();

            while (tokensContainer.hasNext() && tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.PowerOperator) {
                AbstractSyntaxTreeNode newNode = new AbstractSyntaxTreeNode();

                newNode.token = tokensContainer.forward();
                newNode.left = node;
                newNode.right = factor();
                node = newNode;
            }
        }

        if (node.right == null) {
            node = node.left;
        }

        if (leftOperator != null) {
            leftOperator.next = node;
            node = leftOperator;
        }

        if (tokensContainer.hasNext() && tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.FactorialOperator) {
            LeftOperatorNode newNode = new LeftOperatorNode();
            newNode.token = tokensContainer.forward();
            newNode.next = node;
            node = newNode;
        }

        return node;
    }

    /// <summary>
    /// Handles the most important inputs (individual numbers, brackets and identifiers such as variables or functions).
    /// </summary>
    /// <returns></returns>
    /// <exception cref="RuntimeException"></exception>
    private AbstractSyntaxTreeNode factorBase() {
        if (!tokensContainer.hasNext()) {
            throw new RuntimeException("Syntax error: Expecting token at token " + tokensContainer.getPosition());
        }

        if (tokensContainer.hasNext(2) &&
                (tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.Identifier &&
                        tokensContainer.peekForward(1).tokenType == TokenIdentifier.TokenType.LeftBracket)) {
            return functionCall(false);
        } else if ((tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.Identifier &&
                tokensContainer.peekForward(1).tokenType == TokenIdentifier.TokenType.LeftCurlyBracket)) {
            return functionCall(true);
        } else if (tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.Number ||
                tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.Identifier) {
            AbstractSyntaxTreeNode outputNode = new AbstractSyntaxTreeNode();

            outputNode.token = tokensContainer.forward();

            return outputNode;
        } else if (tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.LeftBracket) {
            tokensContainer.forward();
            return expression(true, false);
        } else if (tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.LeftCurlyBracket) {
            tokensContainer.forward();
            return  expression(false, true);
        }

        throw new RuntimeException("Syntax error: Expecting number, expression, function call or constant at token " + tokensContainer.getPosition());
    }

    /// <summary>
    /// Applies the appropriate rules to function calls.
    /// </summary>
    /// <returns></returns>
    /// <exception cref="RuntimeException"></exception>
    private AbstractSyntaxTreeNode functionCall(boolean isLatexFunction) {
        FunctionAbstractSyntaxTreeNode node = new FunctionAbstractSyntaxTreeNode();

        if (!tokensContainer.hasNext()) {
            throw new RuntimeException("Syntax error: Expecting function identifier at token " + tokensContainer.getPosition());
        }

        node.token = tokensContainer.forward();

        if (isLatexFunction) {
            if (!tokensContainer.hasNext() || tokensContainer.peekForward().tokenType != TokenIdentifier.TokenType.LeftCurlyBracket) {
                throw new RuntimeException("Syntax error: Expecting function identifier at token " + tokensContainer.getPosition());
            }
        } else {
            if (!tokensContainer.hasNext() || tokensContainer.peekForward().tokenType != TokenIdentifier.TokenType.LeftBracket) {
                throw new RuntimeException("Syntax error: Expecting function identifier at token " + tokensContainer.getPosition());
            }
        }

        tokensContainer.forward();

        boolean expectClosingBracket = false;

        if (isLatexFunction) {
            while (tokensContainer.hasNext()) {
                if (tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.RightCurlyBracket &&
                        !(tokensContainer.hasNext(2) ||
                                (tokensContainer.hasNext(2) && tokensContainer.peekForward(1).tokenType != TokenIdentifier.TokenType.LeftCurlyBracket))) {
                    tokensContainer.forward();
                    return node;
                }

                if (expectClosingBracket && tokensContainer.hasNext(2) &&
                        (tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.RightCurlyBracket &&
                        tokensContainer.peekForward(1).tokenType == TokenIdentifier.TokenType.LeftCurlyBracket)) {
                    tokensContainer.forward();
                    tokensContainer.forward();
                    expectClosingBracket = false;
                } else {
                    node.parameters.add(expression());
                    expectClosingBracket = true;
                }
            }

            throw new RuntimeException("Syntax error: Expecting function end '}' at token " + tokensContainer.getPosition());
        } else {
            while (tokensContainer.hasNext()) {
                if (tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.RightBracket) {
                    tokensContainer.forward();
                    return node;
                }

                if (expectClosingBracket && tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.ParameterSeparator) {
                    tokensContainer.forward();
                    expectClosingBracket = false;
                } else {
                    node.parameters.add(expression());
                    expectClosingBracket = true;
                }
            }

            throw new RuntimeException("Syntax error: Expecting function end ')' at token " + tokensContainer.getPosition());
        }
    }
}
