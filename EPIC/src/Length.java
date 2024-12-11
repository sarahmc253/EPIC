public class Length implements ImperialCalculator{
    public String imperialUnitsList(){
            return "8: Inch (in) \n" +
                    "9: Foot (ft) \n" +
                    "10: Yard (yd) \n" +
                    "11: Furlong (fur) \n" +
                    "12: Mile (mi) ";}

    public double[] imperialConversionRate(){
        return new double[]{1.0/12, 1, 3, 660, 5280};
    }

    double metricToImperialRate = (3.28084);
    double imperialToMetricRate = (0.3048);
}
