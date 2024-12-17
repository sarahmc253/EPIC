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

            // While we still have more things in the tokens container and the next tokenType is a multiplication
            // or division operator
            while (tokensContainer.hasNext() && (tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.MultiplicationOperator ||
                    tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.DivisionOperator)) {
                // Create a new base node
                AbstractSyntaxTreeNode newNode = new AbstractSyntaxTreeNode();

                // The new nodes token is the next one in the container.
                newNode.token = tokensContainer.forward();
                // The left node is the current node as that is a number.
                newNode.left = node;
                // The right node is some factor.
                newNode.right = factor();
                // Change the base node to this new node.
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
        // Create a new starting node
        AbstractSyntaxTreeNode node = new AbstractSyntaxTreeNode();

        // If there are no other tokens in the container throw an exception.
        if (!tokensContainer.hasNext()) {
            throw new RuntimeException("Syntax error: Expecting factor base at token " + tokensContainer.getPosition());
        }

        // Create a null LeftOperatorNode (any number that has an operator tacked onto it such as -5, 5!, 5^x)
        LeftOperatorNode leftOperator = null;

        // If a subtraction operator is the next token set the LeftOperatorNodes token to the subtraction operator
        if (tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.SubtractionOperator) {
            leftOperator = new LeftOperatorNode();
            leftOperator.token = tokensContainer.forward();
        }

        // Set the left node to the base factor (a number without an operator)
        node.left = factorBase();

        // If the tokens container isn't at the end and the next token is the power operator then make the token
        // the power operator and set the right node to the factor of the token after it.
        if (tokensContainer.hasNext() && tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.PowerOperator) {
            node.token = tokensContainer.forward();
            node.right = factor();

            // While there are more tokens in the container and the next token type is a power operator
            while (tokensContainer.hasNext() && tokensContainer.peekForward().tokenType == TokenIdentifier.TokenType.PowerOperator) {
                // Create a new AST node
                AbstractSyntaxTreeNode newNode = new AbstractSyntaxTreeNode();

                // Make the token the power operator
                newNode.token = tokensContainer.forward();
                // Set the current node as the left node of this new node
                newNode.left = node;
                // Set the right node to some factor
                newNode.right = factor();
                // Set the current node to the new node
                node = newNode;
            }
        }

        if (node.right == null) {
            node = node.left;
        }

        if (leftOperator != null) {
            // Set the current number we have to the left operator nodes "next"
            // (the node it's going to apply its operation to) value
            leftOperator.next = node;
            // Set the base node to the left operator node
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