/*
calculate the mean, mode and median
 */
import java.util.HashMap;
import java.util.Arrays;

public class Averages {

    //calculate the MEAN (average)
    public static class MeanICalculation extends AveragesBasis {
        @Override
        public double calculate(double[] numbersToAverage) {
            double sum = 0.0; //initialise a variable to store the sum of the numbers
            for (double num : numbersToAverage) {
                sum += num; //add each number in numbersToAverage array to the sum
            }
            return sum / numbersToAverage.length; //divide the sum by the number of elements and return value
        }
    }

    //calculate the MODE (most frequent value)
    public static class ModeICalculation extends AveragesBasis {
        @Override
        public double calculate(double[] numbersToAverage) {
            HashMap<Double, Integer> frequencyMap = new HashMap<>(); //map to track how often each number occurs

            //count how many times each number occurs
            for (double num : numbersToAverage) {
                frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1); //increment count for the number
            }

            double mode = numbersToAverage[0]; //assume the first number is the mode
            int maxCount = 0; //track the highest frequency
            boolean hasRepeatedValues = false; //flag to check if any number repeats

            //iterate through the map to find the most frequent number
            for (double key : frequencyMap.keySet()) {
                int count = frequencyMap.get(key); //get the frequency of the current number
                if (count > maxCount) { //check if this number appears more than the current max
                    maxCount = count; //update the maximum frequency
                    mode = key; //update the mode
                    if(count > 1) { //if this number appears more than once, set the flag
                        hasRepeatedValues = true;
                    }
                }
            }

            //if there's no repeated values indicate there's no mode
            if(!hasRepeatedValues) {
                System.out.println("No mode found. All values occur only once.");
                return Double.NaN; //return a sentinel value to signify no mode
            }
            return mode; //return the mode
        }
    }

    //calculate MEDIAN (middle value)
    public static class MedianICalculation extends AveragesBasis {
        @Override
        public double calculate(double[] numbersToAverage) {
            Arrays.sort(numbersToAverage); //sort the numbers in ascending order
            int n = numbersToAverage.length; //get the total number of elements

            //if there's an even number of elements, calculate the average of the two middle values
            if(n % 2 == 0) {
                return (numbersToAverage[n / 2 - 1] + numbersToAverage[n / 2]) / 2.0;
            } else {
                //if there's an odd number of elements, return the middle value
                return numbersToAverage[n / 2];
            }
        }
    }
}
