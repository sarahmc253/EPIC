public class Length {

    // imperial system length units
    public String imperialUnitsList ="""
                8: Inch (in)
                9: Foot (ft)
                10: Yard (yd)
                11: Furlong (fur)
                12: Mile (mi)""";

    // each imperial unit in terms of feet
    public double[] imperialConversionRate = {1.0/12, 1, 3, 660, 5280};

    // multiply rate to convert metres to feet
    double metricToImperialRate = (3.28084);
    // multiply rate to convert feet to metres
    double imperialToMetricRate = (0.3048);

    // standard metric unit of length
    String metricUnit = "metre";
    String metricAbbreviation = "m";


}
