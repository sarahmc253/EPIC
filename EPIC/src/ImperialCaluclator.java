public class ImperialCaluclator {

    public String imperialUnitsList;
    public double[] imperialConversionRate;

    double metricToImperialRate;
    double imperialToMetricRate;

    String metricUnit;
    String metricAbbreviation;

    public ImperialCaluclator(String imperialUnitsList, double[] imperialConversionRate, double metricToImperialRate, double imperialToMetricRate, String metricUnit, String metricAbbreviation){
        this.imperialUnitsList = imperialUnitsList;
        this.imperialConversionRate = imperialConversionRate;
        this.metricToImperialRate = metricToImperialRate;
        this.imperialToMetricRate = imperialToMetricRate;
        this.metricUnit = metricUnit;
        this.metricAbbreviation = metricAbbreviation;
    }
}