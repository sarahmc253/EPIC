public class Mass {

    public String imperialUnitsList = """
                8: Ounce (oz)
                9: Pound (lb)
                10: Stone (st)
                11: Hundredweight (cwt) +
                12: Ton (t)""";

    public double[] imperialConversionRate = {0.0625, 1, 14, 112, 2240};

    double metricToImperialRate = (0.00220462);
    double imperialToMetricRate = (453.59237);

    String metricUnit = "gram";
    String metricAbbreviation = "g";
}
