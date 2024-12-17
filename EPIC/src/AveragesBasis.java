import java.util.Scanner;

public abstract class AveragesBasis implements ICalculation {
    protected  Scanner scanner = new Scanner(System.in);

    protected double[] parseInput() {
        while (true){
            try {
                System.out.println("Enter numbers in a comma-separated list:");
                String input = scanner.nextLine().trim();

                if(input.isEmpty()) {
                    System.out.println("You didn't enter any numbers. Please try again.");
                    continue;
                }

                String[] rawInputs = input.split(",");
                double[] numbersToAverage = new double[rawInputs.length];

                for(int i = 0; i < rawInputs.length; i++) {
                    numbersToAverage[i] = Double.parseDouble(rawInputs[i].trim());
                }

                return numbersToAverage;
            } catch(NumberFormatException e) {
                System.out.println("Invalid input. Please enter list of numbers separted by commas.");
            }
        }
    }

    //abstract method for subclasses to implement
    @Override
    public abstract double calculate(double[] numbersToAverage);
}
