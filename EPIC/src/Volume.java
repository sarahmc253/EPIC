public class Volume {

    public String imperialUnitsList = ("""
                8: Fluid Ounce (fl oz)
                9: Gill (gi)
                10: Pint (pt)
                11: Quart (qt)
                12: Gallon (gal)""");

    public double[] imperialConversionRate = {1, 5, 20, 40, 160};

    double metricToImperialRate = (35.1951);
    double imperialToMetricRate = (0.0284131);

    String metricUnit = "litre";
    String metricAbbreviation = "l";
}
