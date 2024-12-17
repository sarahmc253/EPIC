/*
calculate the mean, mode and median
 */

import java.util.Arrays;
import java.util.HashMap;

public class Averages {

    //calculate the MEAN (add all values together & divide by how many numbers there are)
    public static class MeanICalculation extends AveragesBasis {
        @Override
        public double calculate(double[] numbersToAverage) {
            double sum = 0.0;
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

            //count occurrences of each number
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
                    if(count > 1) { // if this number appears more than once, set the flag
                        hasRepeatedValues = true;
                    }
                }
            }

            //if there's no repeated values recognise there's no mode
            if(!hasRepeatedValues) {
                System.out.println("No mode found. All values occur only once.");
                return Double.NaN; //sentinel value showing there's no mode.
            }
            return mode;
        }
    }

        //calculate MEDIAN
        public static class MedianICalculation extends AveragesBasis {
            @Override
            public double calculate(double[] numbersToAverage) {
                Arrays.sort(numbersToAverage);
                int n = numbersToAverage.length;

                if(n % 2 == 0) {
                    return (numbersToAverage[n / 2 - 1] + numbersToAverage[n / 2]) / 2.0;
                } else {
                    return numbersToAverage[n / 2];
                }
            }
        }
}
