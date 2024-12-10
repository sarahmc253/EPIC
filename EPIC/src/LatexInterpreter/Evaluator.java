/*
 * Cian McNamara, 2024
 */

package LatexInterpreter;

import LatexInterpreter.TokenHeirarchy.*;
import java.util.HashMap;

public class Evaluator {
    // delegates are a list of method parameters
    // params are arguments which then get stored as some collection type (like our double array).
    private double[] args;

    private HashMap<String, Double> variables;
    private HashMap<String, EvaluatorFunction> functions;
    private ParseTreeNode root;

    public Evaluator(ParseTreeNode root) {
        this.variables = new HashMap<String, Double>();
        this.functions = new HashMap<String, EvaluatorFunction>();
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
        // sqrt(x)
        addFunction(new EvaluatorFunction("sqrt", 1, number -> Math.sqrt(number[0])));
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
        // If function isn't null it returns function, otherwise it returns the ArgumentNullException.
        // ?? is called a nullish coalescing operator.

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
    private double evaluate(ParseTreeNode node) {
        if (node instanceof LeftOperatorNode) {
            // left operators
            if (node.token.tokenType == TokenIdentifier.TokenType.SubtractionOperator) {
                // minus
                return -evaluate(((LeftOperatorNode)node).next);
            } else {
                // factorial
                double value = evaluate(((LeftOperatorNode)node).next);
                double newValue = 1;

                while (value > 1) {
                    newValue = newValue * value--;
                }

                return newValue;
            }
        } else if (node instanceof FunctionParseTreeNode) {
            // function
            // sets up the function node and gets the name
            FunctionParseTreeNode functionNode = (FunctionParseTreeNode)node;
            String name = ((IdentifierToken)functionNode.token).identifier;

            // checks if the function exists
            if (functions.containsKey(name)) {
                if (functionNode.parameters.size() < functions.get(name).minArgs) {
                    throw new RuntimeException("The function" + name + " needs at least " + functions.get(name).minArgs + "argument(s).");
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
            if (node.left != null) {
                // operator
                double first = evaluate(node.left);
                double second = evaluate(node.right);

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
                String name = ((IdentifierToken)node.token).identifier;
                if (variables.containsKey(name)) {
                    return variables.get(name);
                } else {
                    throw new RuntimeException("There is exists no variables with the name '" + name + "'.");
                }
            }

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