/*
 * Cian McNamara, 2024
 */

package LatexInterpreter;

public class EvaluatorFunction {
    public String name;
    public int minArgs;
    public FunctionCallback functionCallback;

    public EvaluatorFunction(String name, int minArgs, FunctionCallback functionCallback) {
        this.name = name;
        this.minArgs = minArgs;
        this.functionCallback = functionCallback;
    }
}
