/*
 * Cian McNamara, 2024
 */

package LatexInterpreter;

/// <summary>
/// Interface for declaring a function callback (the body of code which it will run).
/// </summary>
public interface IFunctionCallback {
    /// <summary>
    /// The function which the lambda expression overrides.
    /// </summary>
    double singleVariableExecute(double[] number);
}
