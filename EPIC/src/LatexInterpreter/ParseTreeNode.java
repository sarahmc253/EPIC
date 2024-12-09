/*
 * Cian McNamara, 2024
 */

package LatexInterpreter;

import LatexInterpreter.TokenHeirarchy.Token;

/// <summary>
/// Template class used to assist in parsing AST nodes.
/// </summary>
public class ParseTreeNode {
    public ParseTreeNode left;
    public ParseTreeNode right;
    public Token token;
}
