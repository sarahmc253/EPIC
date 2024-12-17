import java.util.ArrayList;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> memory = new ArrayList<>(); //

        while (true) {
        System.out.println("Pick an action, enter: ");
        //System.out.println("1 for Basic Calculator");
        System.out.println("1 for Mean.");
        System.out.println("2 for Mode.");
        System.out.println("3 for Median.");
        //System.out.println("4 for Percentages.");
        System.out.println("4 for Calculator memory.");
        System.out.println("5 to exit.");

        //getting users desired operation and directing to
            int desiredAction = scanner.nextInt();
            scanner.nextLine();


            if(desiredAction == 1) {
                performCalculation(new Averages.MeanICalculation(), memory);
            } else if(desiredAction == 2) {
                performCalculation(new Averages.ModeICalculation(), memory);
            } else if(desiredAction == 3) {
                performCalculation(new Averages.MedianICalculation(), memory);
            } else if(desiredAction == 4) {
                System.out.println("Calculator memory:");
                if(memory.isEmpty()) {
                    System.out.println("Calculator memory is empty.");
                } else {
                    for (String entry : memory) {
                        System.out.println(entry);
                    }
                }
            } else if(desiredAction == 5) {
                System.out.println("Exiting calculator.");
                break;
            } else {
                System.out.println("Invalid choice, try again.");
            }
        }
    }

    private static void performCalculation(AveragesBasis calculation, ArrayList<String> memory) {
        double[] numbers = calculation.parseInput();
        double result = calculation.calculate(numbers);
        System.out.println("Result: " + result);
        System.out.println();

        //add result to calculator memory
        memory.add(calculation.getClass().getSimpleName() + ": " + result);
    }
}