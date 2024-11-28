/*
 * Cian McNamara, 2024
 */

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Equation {
    private final String equationString;
    private double[] numbers;
    private String[] operators;
    private String sum;

    /// <summary>
    /// Constructor for the Equation class.
    /// </summary>
    public Equation(String equationString) {
        this.equationString = equationString;
        setupProperties();
        evaluate();
    }

    /// <summary>
    /// Evaluates the equation and sets the sum property to the solution.
    /// </summary>
    private void evaluate() {
        Double result = this.numbers[0];

        for (int i = 0; i < this.operators.length; i++) {
            double nextNumber = this.numbers[i + 1];
            String op = this.operators[i];
            result = performOperation(result, op, nextNumber);
        }

        this.sum = result.toString();
    }

    /// <summary>
    /// Converts the equation string into a usable format and sets the respective properties.
    /// </summary>
    private void setupProperties() {
        String operatorSelection = "(?:^|(?<=))(\\\\dfrac|\\\\frac|\\\\div|\\\\times|\\\\cdot|\\+|-|\\^|\\^{})(?:(?=)|$)?";
        String numberSelection = "\\d+(\\.\\d+)?";

        String[] operatorMatches = getMatches(this.equationString, operatorSelection);
        double[] numberMatches = convertStringArrayToDoubleArray(getMatches(this.equationString, numberSelection));

        this.operators = operatorMatches;
        this.numbers = numberMatches;
    }

    /// <summary>
    /// Performs a singular mathematical operation on two numbers.
    /// </summary>
    /// <param name="firstNumber"></param>
    /// <param name="op"></param>
    /// <param name="secondNumber"></param>
    /// <returns></returns>
    /// <exception cref="RuntimeException"></exception>
    private double performOperation(double firstNumber, String op, double secondNumber) {
        double result;
        LatexOperators.LatexOperations opType = LatexOperators.GetOperatorType(op);

        switch (opType) {
            case LatexOperators.LatexOperations.Add:
                result = firstNumber + secondNumber;
                break;
            case LatexOperators.LatexOperations.Subtract:
                result = firstNumber - secondNumber;
                break;
            case LatexOperators.LatexOperations.Multiply:
                result = firstNumber * secondNumber;
                break;
            case LatexOperators.LatexOperations.Divide:
                result = firstNumber / secondNumber;
                break;
            case LatexOperators.LatexOperations.Exponent:
                result = Math.pow(firstNumber, secondNumber);
                break;
            default:
                throw new RuntimeException("Syntax Error: Invalid operator " + op);
        }

        return result;
    }

    /// <summary>
    /// Gets all the contents in a string that match a regex pattern.
    /// </summary>
    /// <param name="equationString"></param>
    /// <param name="regexPattern"></param>
    /// <returns></returns>
    private String[] getMatches(String equationString, String regexPattern) {
        List<String> matches = new ArrayList<String>();
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(equationString);

        while (matcher.find()) {
            matches.add(matcher.group());
        }

        return matches.toArray(new String[matches.size()]);
    }

    private double[] convertStringArrayToDoubleArray(String[] stringArray) {
        double[] doubleArray = new double[stringArray.length];

        for (int i = 0; i < stringArray.length; i++) {
            doubleArray[i] = Double.parseDouble(stringArray[i]);
        }

        return doubleArray;
    }

    /// <summary>
    /// Clears all the empty entries in an array and outputs a new array excluding those empty entries.
    /// </summary>
    /// <param name="arr"></param>
    /// <returns></returns>
    private String[] clearEmptyEntries(String[] arr) {
        List<String> temp = new ArrayList<String>();

        for (int entry = 0; entry < arr.length; entry++) {
            if (!(arr[entry].isEmpty())) {
                temp.add(arr[entry]);
            }
        }

        return temp.toArray(new String[temp.size()]);
    }

    private String removeCurlyBracketsAndFormat(String commaSeperatedNumbers) {
        commaSeperatedNumbers = commaSeperatedNumbers.replace("{", "");
        commaSeperatedNumbers = commaSeperatedNumbers.replace("}", ",");

        return commaSeperatedNumbers;
    }

    private String removeCurlyBracketsAndContents(String commaSeperatedOperators) {
        commaSeperatedOperators = commaSeperatedOperators.replace("{,}", "");

        return commaSeperatedOperators;
    }
}
