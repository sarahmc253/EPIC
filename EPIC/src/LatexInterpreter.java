/*
 * Cian McNamara, 2024
 */

import LatexInterpreter.Lexer;
import LatexInterpreter.TokenStringReader;
import LatexInterpreter.TokensContainer;
import LatexInterpreter.Parser;
import LatexInterpreter.ParseTreeNode;
import LatexInterpreter.Evaluator;

public class LatexInterpreter {
    public Double sum;

    public LatexInterpreter(String input) {
        Lexer lexer = new Lexer(new TokenStringReader(input));
        TokensContainer tokensContainer = lexer.tokenize();

        Parser parser = new Parser(tokensContainer);
        ParseTreeNode root = parser.buildParseTree();

        Evaluator evaluator = new Evaluator(root);

        this.sum = evaluator.getEvaluation();
    }

    @Override
    public String toString() {
        return this.sum.toString();
    }
}
