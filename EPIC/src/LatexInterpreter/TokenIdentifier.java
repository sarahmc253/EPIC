package LatexInterpreter;

public class TokenIdentifier {
    private String lastMultiplicationType;

    public enum TokenType {
        Number,
        Identifier,
        ParameterSeparator,
        LeftBracket,
        RightBracket,
        LeftCurlyBracket,
        RightCurlyBracket,
        AdditionOperator,
        SubtractionOperator,
        MultiplicationOperator,
        DivisionOperator,
        PowerOperator,
        FactorialOperator,
        Unknown,
    }
}
