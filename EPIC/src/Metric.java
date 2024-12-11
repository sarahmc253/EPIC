public class Metric {

    String currentUnit(int converter){

        return switch (converter) {
            case 1 -> "litre";
            case 2 -> "gram";
            case 3 -> "metre";
            default -> "";
        };

    }

    String currentAbbreviation(int converter){

        return switch (converter) {
            case 1 -> "l";
            case 2 -> "g";
            case 3 -> "m";
            default -> "";
        };

    }


    String metricUnitsList(String currentUnit, String abbreviation){

        return ("1: Milli" + currentUnit + " (m" + abbreviation + ") \n" +
                "2: Centi" + currentUnit + " (c" + abbreviation + ") \n" +
                "3: Deci" + currentUnit + " (d" + abbreviation + ") \n" +
                "4: " + currentUnit.substring(0, 1).toUpperCase() + currentUnit.substring(1) + " (" + abbreviation + ") \n" +
                "5: Deca" + currentUnit + "(da" + abbreviation + ") \n" +
                "6: Hecto" + currentUnit + "(h" + abbreviation + ") \n" +
                "7: Kilo" + currentUnit + "(k" + abbreviation + ") \n");
    }

}
