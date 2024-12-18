import java.util.ArrayList;
import java.util.Scanner;

public class AveragesCalculator {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> memory = new ArrayList<>(); //ArrayList to store calculation history

        while (true) { //infinite loop to keep program running until the user exists
            //display menu of options to the user
            System.out.println("Pick an action, enter: ");
            System.out.println("1 for Mean.");
            System.out.println("2 for Mode.");
            System.out.println("3 for Median.");
            System.out.println("4 for Calculator memory.");
            System.out.println("5 to exit.");

            //get the user's choice
            int desiredAction = scanner.nextInt();
            scanner.nextLine(); //consume the leftover newline character after the number


            //check the user's choice and perform the corresponding action
            if(desiredAction == 1) {
                //calculate mean
                performCalculation(new Averages.MeanICalculation(), memory);
            } else if(desiredAction == 2) {
                //calculate mode
                performCalculation(new Averages.ModeICalculation(), memory);
            } else if(desiredAction == 3) {
                //calculate median
                performCalculation(new Averages.MedianICalculation(), memory);
            } else if(desiredAction == 4) {
                //display calculation history
                System.out.println("Calculator memory:");
                if(memory.isEmpty()) {
                    System.out.println("Calculator memory is empty.");
                } else {
                    for (String entry : memory) {
                        System.out.println(entry); //print each result stored in memory
                    }
                }
            } else if(desiredAction == 5) {
                //exit the program
                System.out.println("Exiting calculator.");
                break;
            } else {
                //display error message if invalid choice
                System.out.println("Invalid choice, try again.");
            }
        }
    }

    //helper method to handle calculations and store results in memory
    private static void performCalculation(AveragesBasis calculation, ArrayList<String> memory) {
        double[] numbers = calculation.parseInput(); //call parseInput() to get user inputs
        double result = calculation.calculate(numbers); //perform calculation using chosen method
        System.out.println("Result: " + result); //display result
        System.out.println(); //blank line for readability

        //add result to calculator memory along with the calculation type
        memory.add(calculation.getClass().getSimpleName() + ": " + result);
    }
}