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
        return expression(false);
    }

    /// <summary>
    /// Handles the lowest priority operations (addition and subtraction).
    /// </summary>
    /// <param name="expectBrackets"></param>
    /// <returns></returns>
    /// <exception cref="RuntimeException"></exception>
    private AbstractSyntaxTreeNode expression(boolean expectBrackets) {
        // Create our starting node.
        AbstractSyntaxTreeNode node = new AbstractSyntaxTreeNode();

        // Set the left side of the starting node to the first term you meet.
        if (tokensContainer.hasNext()) {
            node.left = term();
        } else {
            // Return null if there is no first term.
            return null;
        }

        // See if the next token is an addition or subtraction operator.
        if (tokensContainer.hasNext() && (tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.AdditionOperator ||
                tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.SubtractionOperator)) {
            // Assign the nodes token property to that token.
            node.token = tokensContainer.forward();
            // Set the right node to the next term.
            node.right = term();

            // While you keep meeting addition or subtraction operators create new nodes.
            while(tokensContainer.hasNext() && (tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.AdditionOperator ||
                    tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.SubtractionOperator)) {
                AbstractSyntaxTreeNode newNode = new AbstractSyntaxTreeNode();

                // Assign the token as being the next token in the token container.
                newNode.token = tokensContainer.forward();
                // The current node becomes the left node as it is a number and needs to be applied to
                // the operator node (the new one we created).
                newNode.left = node;
                // Assign the next term to the right node.
                newNode.right = term();

                // The current node becomes the new node as it is higher up the tree.
                node = newNode;
            }
        }

        // If we are expecting brackets and there are no more tokens or the next tokenType is not a ')' throw an exception.
        if (expectBrackets &&
                (!tokensContainer.hasNext() || tokensContainer.peekForward().tokenType != TokenIdentifier.TokenType.RightBracket)) {
            throw new RuntimeException("Syntax error: Expecting ')' at token " + tokensContainer.getPosition());
        } else if (expectBrackets &&
                tokensContainer.hasNext() &&
                tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.RightBracket) {
            // Move forward in the token container if the next token is a RightBracket and there is another token left.
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
        // Create our starting node.
        AbstractSyntaxTreeNode node = new AbstractSyntaxTreeNode();

        // If there are no more tokens in the token container then throw an error.
        if (!tokensContainer.hasNext()) {
            throw new RuntimeException("Syntax error: Expecting factor at token " + tokensContainer.getPosition());
        }

        // Set the current token as the left factor (the 2 in 2 \times 3 for example)
        node.left = factor();

        // If we aren't at the end of the token container and the next operator is a division or multiplication operator
        if (tokensContainer.hasNext() && (tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.MultiplicationOperator ||
                tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.DivisionOperator)) {
            // Set the nodes token to the next token.
            node.token = tokensContainer.forward();
            // Set the right node to the next factor (the 3 in 2 \times 3)
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
                tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.Identifier &&
                tokensContainer.peekForward(1).tokenType == TokenIdentifier.TokenType.LeftBracket) {
            return functionCall();
        } else if (tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.Number ||
                tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.Identifier) {
            AbstractSyntaxTreeNode outputNode = new AbstractSyntaxTreeNode();

            outputNode.token = tokensContainer.forward();

            return outputNode;
        } else if (tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.LeftBracket) {
            tokensContainer.forward();
            return expression(true);
        }

        throw new RuntimeException("Syntax error: Expecting number, expression, function call or constant at token " + tokensContainer.getPosition());
    }

    /// <summary>
    /// Applies the appropriate rules to function calls.
    /// </summary>
    /// <returns></returns>
    /// <exception cref="RuntimeException"></exception>
    private AbstractSyntaxTreeNode functionCall() {
        FunctionAbstractSyntaxTreeNode node = new FunctionAbstractSyntaxTreeNode();

        if (!tokensContainer.hasNext()) {
            throw new RuntimeException("Syntax error: Expecting function identifier at token " + tokensContainer.getPosition());
        }

        node.token = tokensContainer.forward();

        if (!tokensContainer.hasNext() || tokensContainer.peekForward().tokenType != TokenIdentifier.TokenType.LeftBracket) {
            throw new RuntimeException("Syntax error: Expecting function identifier at token " + tokensContainer.getPosition());
        }

        tokensContainer.forward();

        boolean expectClosingBracket = false;

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