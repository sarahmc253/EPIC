public class ImperialCalculator {

    // instance variables
    // will be initialised when an object is instantiated
    public String imperialUnitsList;
    public double[] imperialConversionRate;

    double metricToImperialRate;
    double imperialToMetricRate;

    String metricUnit;
    String metricAbbreviation;

    // constructor to initialise variables
    public ImperialCalculator(String imperialUnitsList, double[] imperialConversionRate, double metricToImperialRate, double imperialToMetricRate, String metricUnit, String metricAbbreviation){
        this.imperialUnitsList = imperialUnitsList;
        this.imperialConversionRate = imperialConversionRate;
        this.metricToImperialRate = metricToImperialRate;
        this.imperialToMetricRate = imperialToMetricRate;
        this.metricUnit = metricUnit;
        this.metricAbbreviation = metricAbbreviation;
    }
}
