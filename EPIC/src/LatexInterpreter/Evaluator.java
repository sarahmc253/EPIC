/*
 * Cian McNamara, 2024
 */

package LatexInterpreter;

import LatexInterpreter.TokenHeirarchy.*;
import java.util.HashMap;

public class Evaluator {
    private final HashMap<String, Double> variables;
    private final HashMap<String, EvaluatorFunction> functions;
    private final AbstractSyntaxTreeNode root;

    public Evaluator(AbstractSyntaxTreeNode root) {
        this.variables = new HashMap<>();
        this.functions = new HashMap<>();
        this.root = root;

        // Variables
        addVariable("\\pi", Math.PI);
        addVariable("e", Math.E);

        // Functions
        // arccos(x)
        addFunction(new EvaluatorFunction("arccos", 1, number -> Math.acos(number[0])));
        // arcsin(x)
        addFunction(new EvaluatorFunction("arcsin", 1, number -> Math.asin(number[0])));
        // arctan(x)
        addFunction(new EvaluatorFunction("arctan", 1, number -> Math.atan(number[0])));
        // cos(x)
        addFunction(new EvaluatorFunction("cos", 1, number -> Math.cos(number[0])));
        // sin(x)
        addFunction(new EvaluatorFunction("sin", 1, number -> Math.sin(number[0])));
        // tan(x)
        addFunction(new EvaluatorFunction("tan", 1, number -> Math.tan(number[0])));
        // cosh(x)
        addFunction(new EvaluatorFunction("cosh", 1, number -> Math.cosh(number[0])));
        // sinh(x)
        addFunction(new EvaluatorFunction("sinh", 1, number -> Math.sinh(number[0])));
        // tanh(x)
        addFunction(new EvaluatorFunction("tanh", 1, number -> Math.tanh(number[0])));
        // log(x)
        addFunction(new EvaluatorFunction("log", 1, number -> Math.log(number[0])));
        // ln(x)
        addFunction(new EvaluatorFunction("ln", 1, number -> Math.log(number[0])));
        // \sqrt(x)
        addFunction(new EvaluatorFunction("\\sqrt", 1, number -> Math.sqrt(number[0])));
        // isolatedDivide(x, y)
        addFunction(new EvaluatorFunction("isolatedDivide", 2, number -> number[0] / number[1]));
    }

    /// <summary>
    /// Adds a variable to the list of valid variables.
    /// </summary>
    /// <param name="name"></param>
    /// <param name="value"></param>
    public void addVariable(String name, double value) {
        variables.put(name, value);
    }

    /// <summary>
    /// Adds a function to the list of valid functions.
    /// </summary>
    /// <param name="function"></param>
    /// <exception cref="IllegalArgumentException"></exception>
    public void addFunction(EvaluatorFunction function) {
        try {
            functions.put(function.name, function);
        } catch(Exception e) {
            throw new IllegalArgumentException("The function callback is null.");
        }
    }

    /// <summary>
    /// Evaluates each AST node.
    /// </summary>
    /// <param name="node"></param>
    /// <returns></returns>
    /// <exception cref="RuntimeException"></exception>
    private double evaluate(AbstractSyntaxTreeNode node) {
        if (node instanceof LeftOperatorNode) {
            // see if its something like -5
            if (node.token.tokenType == TokenIdentifier.TokenType.SubtractionOperator) {
                // return the negative version of the next node
                return -evaluate(((LeftOperatorNode)node).next);
            } else {
                // factorial is the only other option
                double value = evaluate(((LeftOperatorNode)node).next);
                double newValue = 1;

                // Conduct the factorial process
                while (value > 1) {
                    newValue = newValue * value--;
                }

                // return the finished value
                return newValue;
            }
        } else if (node instanceof FunctionAbstractSyntaxTreeNode) {
            // function
            // sets up the function node and gets the name
            FunctionAbstractSyntaxTreeNode functionNode = (FunctionAbstractSyntaxTreeNode)node;
            String name = ((IdentifierToken)functionNode.token).identifier;

            // checks if the function exists
            if (functions.containsKey(name)) {
                // if the parameter count is under the minimum paramater count then throw an error
                if (functionNode.parameters.size() < functions.get(name).minArgs) {
                    throw new RuntimeException("The function " + name + " needs at least " + functions.get(name).minArgs + " argument(s).");
                }

                double[] parameterValues = new double[functionNode.parameters.size()];

                // evaluates each parameter and puts it into the parameterValues array
                for (int i = 0; i < functionNode.parameters.size(); i++) {
                    parameterValues[i] = evaluate(functionNode.parameters.get(i));
                }

                // returns the output of the function
                return functions.get(name).functionCallback.singleVariableExecute(parameterValues);
            } else {
                throw new RuntimeException("The function '" + name + "' does not exist.");
            }
        } else {
            // nice one it isn't a number with some operator tacked onto it or a function
            // check if the left node exists
            if (node.left != null) {
                // evaluate the left and right nodes
                double first = evaluate(node.left);
                double second = evaluate(node.right);

                // apply the correct operations based on token type
                switch (node.token.tokenType) {
                    case TokenIdentifier.TokenType.AdditionOperator:
                        return first + second;
                    case TokenIdentifier.TokenType.SubtractionOperator:
                        return first - second;
                    case TokenIdentifier.TokenType.MultiplicationOperator:
                        return first * second;
                    case TokenIdentifier.TokenType.DivisionOperator:
                        return first / second;
                    case TokenIdentifier.TokenType.PowerOperator:
                        return Math.pow(first, second);
                }
            } else if (node.token.tokenType == TokenIdentifier.TokenType.Identifier) {
                // variable
                // cast IdentifierToken to the nodes token and set its identifier property to be the name variable
                String name = ((IdentifierToken)node.token).identifier;
                // see if the variable exists
                if (variables.containsKey(name)) {
                    // return the variables value
                    return variables.get(name);
                } else {
                    // throw an error if it doesnt exist
                    throw new RuntimeException("There is exists no variables with the name '" + name + "'.");
                }
            }
            // cast NumberToken to the nodes token and return the value
            return ((NumberToken)node.token).value;
        }
    }

    /// <summary>
    /// Gets the evaluated form of the abstract syntax tree.
    /// </summary>
    /// <returns></returns>
    public double getEvaluation() {
        return evaluate(root);
    }
}