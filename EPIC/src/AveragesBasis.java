/*
get user input and convert into double array to be used in averaging calculations
 */
import java.util.Scanner;

public abstract class AveragesBasis implements ICalculation {
    protected  Scanner scanner = new Scanner(System.in); //scanner for user input

    //method to handle user input and convert it into a double array
    protected double[] parseInput() {
        while (true){ //keep prompting until a valid input is received
            try {
                System.out.println("Enter numbers in a comma-separated list:");
                String input = scanner.nextLine().trim(); //read input and remove any extra spaces

                if(input.isEmpty()) { //check if user didn't input any numbers
                    System.out.println("You didn't enter any numbers. Please try again.");
                    continue; //prompt the user again
                }

                //split the input string into an array of strings
                String[] rawInputs = input.split(","); //split at the commas
                double[] numbersToAverage = new double[rawInputs.length]; //array to hold parsed numbers, matching the length of rawInputs

                //convert each string in rawInputs to a double
                for(int i = 0; i < rawInputs.length; i++) { //iterate through rawInputs
                    numbersToAverage[i] = Double.parseDouble(rawInputs[i].trim()); //trim spaces, convert to double and store in numbersToAverage
                }

                return numbersToAverage; //return the array of numbers
            } catch(NumberFormatException e) { //handle invalid number format
                System.out.println("Invalid input. Please enter list of numbers separated by commas.");
            }
        }
    }

    //abstract method to be implemented by subclasses for specific calculations
    @Override
    public abstract double calculate(double[] numbersToAverage);
}
