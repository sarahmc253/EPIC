
/*
 * Anna Maughan, 2024
 */

public class Mass {

    // imperial system mass units
    public String imperialUnitsList = """
                8: Ounce (oz)
                9: Pound (lb)
                10: Stone (st)
                11: Hundredweight (cwt) +
                12: Ton (t)""";

    // each imperial unit in terms of pounds
    public double[] imperialConversionRate = {0.0625, 1, 14, 112, 2240};

    // multiply rate to convert grams to pounds
    double metricToImperialRate = (0.00220462);
    // multiply rate to convert pounds to grams
    double imperialToMetricRate = (453.59237);

    // standard metric unit of mass
    String metricUnit = "gram";
    String metricAbbreviation = "g";
}
