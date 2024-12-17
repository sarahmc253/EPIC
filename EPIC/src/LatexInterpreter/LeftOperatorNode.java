/*
 * Cian McNamara, 2024
 */

package LatexInterpreter;

/// <summary>
/// Acts as an interface for parameters.
/// </summary>
public class LeftOperatorNode extends AbstractSyntaxTreeNode {
    /// <summary>
    /// The property which holds the number that the operator is being applied to.
    /// </summary>
    public AbstractSyntaxTreeNode next;
}
