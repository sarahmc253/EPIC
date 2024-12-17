import java.util.Scanner;

public class CalculatorOutput {
    Scanner input = new Scanner(System.in);
    CalculateUnits calculateUnits = new CalculateUnits();
    ImperialCalculator imperialCalculator;
    Volume volume = new Volume();
    Mass mass = new Mass();
    Length length = new Length();
    HexConversion hexConversion = new HexConversion();
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

    public void intro(){
        System.out.println("Enter 1 for base conversion or 2 for unit conversion:");
        int baseOrUnit = input.nextInt();
        if(baseOrUnit == 1){
            baseConversion();
        }else if(baseOrUnit == 2){
            unitIntro();
            determineCalculator();
            determineInputs(takeInputs());
        }
    }

    public void unitIntro() {
        System.out.println("Enter number corresponding to what you would like to convert:");
        System.out.println("""
                1: Volume\s
                2: Mass\s
                3: Length""");
    }

    public void determineCalculator(){
        int converter = input.nextInt();

        imperialCalculator = switch (converter){
            case 1 -> new ImperialCalculator(volume.imperialUnitsList, volume.imperialConversionRate, volume.metricToImperialRate, volume.imperialToMetricRate, volume.metricUnit, volume.metricAbbreviation);
            case 2 -> new ImperialCalculator(mass.imperialUnitsList, mass.imperialConversionRate, mass.metricToImperialRate, mass.imperialToMetricRate, mass.metricUnit, mass.metricAbbreviation);
            case 3 -> new ImperialCalculator(length.imperialUnitsList, length.imperialConversionRate, length.metricToImperialRate, length.imperialToMetricRate, length.metricUnit, length.metricAbbreviation);
            default -> throw new IllegalStateException("Unexpected value: " + converter);
        };
    }

    public int[] takeInputs(){
        System.out.println(metricUnitsList(imperialCalculator.metricUnit, imperialCalculator.metricAbbreviation));
        System.out.println(imperialCalculator.imperialUnitsList);

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
            result = calculateUnits.imperialToImperial(convertFrom, convertTo, amount, imperialCalculator.imperialConversionRate);
        }else if(convertFrom <= 7){
            result = calculateUnits.metricToImperial(convertFrom, convertTo, amount, imperialCalculator.imperialConversionRate, imperialCalculator.metricToImperialRate);
        }else{
            result = calculateUnits.imperialToMetric(convertFrom, convertTo, amount, imperialCalculator.imperialConversionRate, imperialCalculator.imperialToMetricRate);
        }

        System.out.println(result);
    }

    public void baseConversion(){
        System.out.println("""
                            Convert from:
                            1: Decimal
                            2: Binary
                            3: Hexadecimal""");

        int converter = input.nextInt();
        System.out.println("Enter amount: ");
        String amount = input.next();
        if(converter == 1){
            hexConversion.decimal(Integer.parseInt(amount));
        }else if(converter == 2){
            hexConversion.binary(amount);
        }else if(converter == 3){
            hexConversion.hex(amount);
        }

    }
}
