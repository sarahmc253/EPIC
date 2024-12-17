import java.util.Scanner;

public class CalculatorOutput {
    Scanner input = new Scanner(System.in);
    CalculateUnits calculateUnits = new CalculateUnits();
    ImperialCaluclator imperialCaluclator;
    Volume volume = new Volume();
    Mass mass = new Mass();
    Length length = new Length();
    double result;

    String metricUnitsList(String currentUnit, String abbreviation) {

        return ("1: Milli" + currentUnit + " (m" + abbreviation + ") \n" +
                "2: Centi" + currentUnit + " (c" + abbreviation + ") \n" +
                "3: Deci" + currentUnit + " (d" + abbreviation + ") \n" +
                "4: " + currentUnit.substring(0, 1).toUpperCase() + currentUnit.substring(1) + " (" + abbreviation + ") \n" +
                "5: Deca" + currentUnit + "(da" + abbreviation + ") \n" +
                "6: Hecto" + currentUnit + "(h" + abbreviation + ") \n" +
                "7: Kilo" + currentUnit + "(k" + abbreviation + ") \n");
    }

    public void introToUser() {
        System.out.println("Enter number corresponding to what you would like to convert:");
        System.out.println("""
                1: Volume\s
                2: Mass\s
                3: Length""");
    }

    public void determineCalculator(){
        int converter = input.nextInt();

        imperialCaluclator = switch (converter){
            case 1 -> new ImperialCaluclator(volume.imperialUnitsList, volume.imperialConversionRate, volume.metricToImperialRate, volume.imperialToMetricRate, volume.metricUnit, volume.metricAbbreviation);
            case 2 -> new ImperialCaluclator(mass.imperialUnitsList, mass.imperialConversionRate, mass.metricToImperialRate, mass.imperialToMetricRate, mass.metricUnit, mass.metricAbbreviation);
            case 3 -> new ImperialCaluclator(length.imperialUnitsList, length.imperialConversionRate, length.metricToImperialRate, length.imperialToMetricRate, length.metricUnit, length.metricAbbreviation);
            default -> throw new IllegalStateException("Unexpected value: " + converter);
        };
    }

    public int[] takeInputs(){
        System.out.println(metricUnitsList(imperialCaluclator.metricUnit, imperialCaluclator.metricAbbreviation));
        System.out.println(imperialCaluclator.imperialUnitsList);

        int convertFrom = 0;

        try {
            System.out.println("Convert From: ");
            convertFrom = input.nextInt();
        } catch (Exception e){
            System.out.println("Invalid value");
        }

        System.out.println("Convert To:");
        int convertTo = input.nextInt();

        System.out.println("Enter amount to be converted: ");
        int amount = input.nextInt();

        return new int[]{convertFrom, convertTo, amount};
    }

    public void determineInputs(int[] inputs){
        int convertFrom = inputs[0];
        int convertTo = inputs[1];
        int amount = inputs[2];

        if(convertFrom <= 7 && convertTo <= 7){
            result = calculateUnits.metricToMetric(convertFrom, convertTo, amount);
        }else if(convertFrom > 7 && convertTo > 7){
            result = calculateUnits.imperialToImperial(convertFrom, convertTo, amount, imperialCaluclator.imperialConversionRate);
        }else if(convertFrom <= 7){
            result = calculateUnits.metricToImperial(convertFrom, convertTo, amount, imperialCaluclator.imperialConversionRate, imperialCaluclator.metricToImperialRate);
        }else{
            result = calculateUnits.imperialToMetric(convertFrom, convertTo, amount, imperialCaluclator.imperialConversionRate, imperialCaluclator.imperialToMetricRate);
        }

        System.out.println(result);
    }
}
