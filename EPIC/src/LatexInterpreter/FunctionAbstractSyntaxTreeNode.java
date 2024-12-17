/*
 * Cian McNamara, 2024
 */

package LatexInterpreter;

import java.util.ArrayList;
import java.util.List;

/// <summary>
/// Acts as an interface for parameters.
/// </summary>
public class FunctionAbstractSyntaxTreeNode extends AbstractSyntaxTreeNode {
    /// <summary>
    /// The function parameters which are passed to a function.
    /// </summary>
    public List<AbstractSyntaxTreeNode> parameters = new ArrayList<AbstractSyntaxTreeNode>();
}
