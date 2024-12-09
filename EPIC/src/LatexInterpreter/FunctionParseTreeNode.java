/*
 * Cian McNamara, 2024
 */

package LatexInterpreter;

import java.util.ArrayList;
import java.util.List;

/// <summary>
/// Acts as an interface for parameters.
/// </summary>
public class FunctionParseTreeNode extends ParseTreeNode {
    public List<ParseTreeNode> parameters = new ArrayList<ParseTreeNode>();
}
