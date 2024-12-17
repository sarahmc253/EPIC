import java.util.Scanner;

public class CalculatorOutput {

    // instances of all relevant classes
    // this class inherits by composition
    Scanner input = new Scanner(System.in);
    CalculateUnits calculateUnits = new CalculateUnits();
    ImperialCalculator imperialCalculator;  // this variable is not initialised to allow polymorphic implementation
    Volume volume = new Volume();
    Mass mass = new Mass();
    Length length = new Length();
    HexConversion hexConversion = new HexConversion();
    double result;


    // list of metric units displayed to user
    private String metricUnitsList(String currentUnit, String abbreviation) {

        return ("1: Milli" + currentUnit + " (m" + abbreviation + ") \n" +
                "2: Centi" + currentUnit + " (c" + abbreviation + ") \n" +
                "3: Deci" + currentUnit + " (d" + abbreviation + ") \n" +
                "4: " + currentUnit.substring(0, 1).toUpperCase() + currentUnit.substring(1) + " (" + abbreviation + ") \n" +
                "5: Deca" + currentUnit + "(da" + abbreviation + ") \n" +
                "6: Hecto" + currentUnit + "(h" + abbreviation + ") \n" +
                "7: Kilo" + currentUnit + "(k" + abbreviation + ") \n");
    }

    // intro displayed to user
    // user chooses between base or unit  conversion
    public void intro(){
        System.out.println("Enter 1 for base conversion or 2 for unit conversion:");
        int baseOrUnit = input.nextInt();
        // relevant methods are called based on user input
        if(baseOrUnit == 1){
            baseConversion();
        }else if(baseOrUnit == 2){
            unitChoice();
            determineCalculator();
            determineInputs(takeInputs());
        }
    }

    // method allowing user to choose which units to convert
    public void unitChoice() {
        System.out.println("Enter number corresponding to what you would like to convert:");
        System.out.println("""
                1: Volume\s
                2: Mass\s
                3: Length""");
    }

    // determines which unit calculator to use based on user input
    public void determineCalculator(){
        int converter = input.nextInt();

        // object of the selected calculator is assigned to the imperialCalculator variable
        imperialCalculator = switch (converter){
            case 1 -> new ImperialCalculator(volume.imperialUnitsList, volume.imperialConversionRate, volume.metricToImperialRate, volume.imperialToMetricRate, volume.metricUnit, volume.metricAbbreviation);
            case 2 -> new ImperialCalculator(mass.imperialUnitsList, mass.imperialConversionRate, mass.metricToImperialRate, mass.imperialToMetricRate, mass.metricUnit, mass.metricAbbreviation);
            case 3 -> new ImperialCalculator(length.imperialUnitsList, length.imperialConversionRate, length.metricToImperialRate, length.imperialToMetricRate, length.metricUnit, length.metricAbbreviation);
            default -> throw new IllegalStateException("Unexpected value: " + converter);
        };
    }

    // method to allow user to input units and value to be converted
    public double[] takeInputs(){
        // prints metric units list with relevant units
        System.out.println(metricUnitsList(imperialCalculator.metricUnit, imperialCalculator.metricAbbreviation));
        // prints relevant imperial units list
        System.out.println(imperialCalculator.imperialUnitsList);

        int convertFrom = 0;
        int convertTo = 0;
        double amount = 0;

        // exception handling for invalid values
        try {
            // store unit to be converted from
            System.out.println("Convert From: ");
            convertFrom = input.nextInt();
        } catch (Exception e){
            System.out.println("Invalid value");
        }

        try {
            // store unit to be converted to
            System.out.println("Convert To:");
            convertTo = input.nextInt();

            // store amount to be converted
            System.out.println("Enter amount to be converted: ");
            amount = input.nextDouble();
        } catch (Exception e){
            System.out.println("Invalid value");
        }

        // return inputted values
        return new double[]{convertFrom, convertTo, amount};
    }

    // method to determine if the inputs refer to metric or imperial
    public void determineInputs(double[] inputs){
        int convertFrom = (int) inputs[0];
        int convertTo = (int) inputs[1];
        double amount = inputs[2];

        // calls relevant method and stores result of calculation
        if(convertFrom <= 7 && convertTo <= 7){
            result = calculateUnits.metricToMetric(convertFrom, convertTo, amount);
        }else if(convertFrom > 7 && convertTo > 7){
            result = calculateUnits.imperialToImperial(convertFrom, convertTo, amount, imperialCalculator.imperialConversionRate);
        }else if(convertFrom <= 7){
            result = calculateUnits.metricToImperial(convertFrom, convertTo, amount, imperialCalculator.imperialConversionRate, imperialCalculator.metricToImperialRate);
        }else{
            result = calculateUnits.imperialToMetric(convertFrom, convertTo, amount, imperialCalculator.imperialConversionRate, imperialCalculator.imperialToMetricRate);
        }

        // print result of calculation
        System.out.println(result);
    }

    // method called if user chooses base conversion
    public void baseConversion(){
        // options displayed to user
        System.out.println("""
                            Convert from:
                            1: Decimal
                            2: Binary
                            3: Hexadecimal""");
        int converter = input.nextInt();

        // store amount as a string
        System.out.println("Enter amount: ");
        String amount = input.next();

        // calls relevant method depending on user's input
        if(converter == 1){
            hexConversion.decimal(amount);
        }else if(converter == 2){
            hexConversion.binary(amount);
        }else if(converter == 3){
            hexConversion.hex(amount);
        }

    }
}
