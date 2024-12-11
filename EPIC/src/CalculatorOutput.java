import java.util.Scanner;

public class CalculatorOutput {

    public ImperialCalculator introToUser(){
        Metric metric = new Metric();
        Volume volume = new Volume();
        Mass mass = new Mass();
        Length length = new Length();
        ImperialCalculator imperialCalculator;

        Scanner input = new Scanner(System.in);

        System.out.println("Enter number corresponding to what you would like to convert:");
        System.out.println("""
                1: Volume\s
                2: Mass\s
                3: Length""");

        int converter = input.nextInt();

        imperialCalculator = switch (converter){
            case 1 -> volume;
            case 2 -> mass;
            case 3 -> length;
            default -> throw new IllegalStateException("Unexpected value: " + converter);
        };

        String currentUnit = metric.currentUnit(converter);
        String currentAbbreviation = metric.currentAbbreviation(converter);
        String currentUnitsList = metric.metricUnitsList(currentUnit, currentAbbreviation);

        System.out.println(currentUnitsList + imperialCalculator.imperialUnitsList());

        return imperialCalculator;
    }

    public void takeInputs(ImperialCalculator imperialCalculator){
        Scanner input = new Scanner(System.in);
        double[] imperialConversionRate = imperialCalculator.imperialConversionRate();

        System.out.println("Convert From: ");
        int convertFrom = input.nextInt();

        System.out.println("Convert To:");
        int convertTo = input.nextInt();

        double convertFromUnit;
        double convertToUnit;

        if(convertFrom > 7){
            convertFromUnit = switch (convertFrom){
                case 8 -> imperialConversionRate[0];
                case 9 -> imperialConversionRate[1];
                case 10 -> imperialConversionRate[2];
                case 11 -> imperialConversionRate[3];
                case 12 -> imperialConversionRate[4];
                default -> throw new IllegalStateException("Unexpected value: " + convertFrom);
            };
        }

        if(convertTo > 7){
            convertToUnit = switch (convertTo){
                case 8 -> imperialConversionRate[0];
                case 9 -> imperialConversionRate[1];
                case 10 -> imperialConversionRate[2];
                case 11 -> imperialConversionRate[3];
                case 12 -> imperialConversionRate[4];
                default -> throw new IllegalStateException("Unexpected value: " + convertFrom);
            };
        }


    }


}
