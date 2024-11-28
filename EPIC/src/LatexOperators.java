/*
 * Cian McNamara, 2024
 */

import java.util.Arrays;

final class LatexOperators {
    private static final String[] additionOperators = new String[] {"+"};
    private static final String[] subtractionOperators = new String[] {"-"};
    private static final String[] multiplicationOperators = new String[] {"\\times", "\\cdot"};
    private static final String[] divisionOperators = new String[] {"\\div", "\\frac", "\\dfrac"};
    private static final String[] exponentOperators = new String[] {"^"};

    public static final String[][] operators = new String[][] {
            additionOperators,
            subtractionOperators,
            multiplicationOperators,
            divisionOperators,
            exponentOperators
    };

    public enum LatexOperations {
        NaLO,
        Add,
        Subtract,
        Multiply,
        Divide,
        Exponent
    }

    /// <summary>
    /// Gets the LaTeX operator type of a given input string.
    /// </summary>
    /// <param name="op"></param>
    /// <returns></returns>
    public static LatexOperations GetOperatorType(String op) {
        if (Arrays.stream(operators[0]).anyMatch(op::equals)) {
            return LatexOperations.Add;
        } else if (Arrays.stream(operators[1]).anyMatch(op::equals)) {
            return LatexOperations.Subtract;
        } else if (Arrays.stream(operators[2]).anyMatch(op::equals)) {
            return LatexOperations.Multiply;
        } else if (Arrays.stream(operators[3]).anyMatch(op::equals)) {
            return LatexOperations.Divide;
        } else if (Arrays.stream(operators[4]).anyMatch(op::equals)) {
            return LatexOperations.Exponent;
        } else {
            return LatexOperations.NaLO;
        }
    }
}
