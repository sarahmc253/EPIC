
/*
 * Anna Maughan, 2024
 */

public class Volume {

    // imperial system volume units
    public String imperialUnitsList = ("""
                8: Fluid Ounce (fl oz)
                9: Gill (gi)
                10: Pint (pt)
                11: Quart (qt)
                12: Gallon (gal)""");

    // each imperial unit in terms of fluid ounces
    public double[] imperialConversionRate = {1, 5, 20, 40, 160};

    // multiply rate to convert litres to fluid ounces
    double metricToImperialRate = (35.1951);
    // multiply rate to convert fluid ounces to litres
    double imperialToMetricRate = (0.0284131);

    // standard metric unit of volume
    String metricUnit = "litre";
    String metricAbbreviation = "l";
}
