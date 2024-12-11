public class Volume implements ImperialCalculator {
    public String imperialUnitsList(){
        return """
            8: Fluid Ounce (fl oz)\s
            9: Gill (gi)\s
            10: Pint (pt)\s
            11: Quart (qt)\s
            12: Gallon (gal)\s""";
    }

    public double[] imperialConversionRate(){
        return new double[]{1, 5, 20, 40, 160};
    }

    double[] imperialConversionRates = {1, 5, 20, 40, 160};

    double metricToImperialRate = (3.28084);
    double imperialToMetricRate = (0.3048);


}
