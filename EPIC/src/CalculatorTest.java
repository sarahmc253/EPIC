public class CalculatorTest {
    public static void main(String[] args){

        CalculatorOutput calculatorOutput = new CalculatorOutput();

        calculatorOutput.introToUser();
        calculatorOutput.determineCalculator();
        calculatorOutput.determineInputs(calculatorOutput.takeInputs());

    }

}
