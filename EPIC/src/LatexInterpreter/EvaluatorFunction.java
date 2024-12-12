/*
 * Cian McNamara, 2024
 */

package LatexInterpreter;

/// <summary>
/// Class for creating functions in the Evaluator.
/// </summary>
public class EvaluatorFunction {
    public String name;
    public int minArgs;
    public IFunctionCallback functionCallback;

    public EvaluatorFunction(String name, int minArgs, IFunctionCallback functionCallback) {
        this.name = name;
        this.minArgs = minArgs;
        this.functionCallback = functionCallback;
    }
}
