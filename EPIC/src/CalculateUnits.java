public class CalculateUnits {

    public double metricToMetric(int convertFrom, int convertTo, double amount){
        int ratio = convertFrom - convertTo;
        return (amount*Math.pow(10, ratio));
    }

    public double metricToImperial(int convertFrom, int convertTo, double amount, double[] currentImperialConversionRate, double metricToImperialRate){
        int ratio = convertFrom - 4;
        double inBasicMetric = amount*(Math.pow(10, ratio));
        double inBasicImperial = inBasicMetric*metricToImperialRate;
        return inBasicImperial* (1/currentImperialConversionRate[convertTo-8]);
    }

    public double imperialToMetric(int convertFrom, int convertTo, double amount, double[] currentImperialConversionRate, double ImperialToMetricRate){
        double inBasicImperial = currentImperialConversionRate[convertFrom-8]*amount;
        double inBasicMetric = inBasicImperial*ImperialToMetricRate;
        int ratio = 4-convertTo;

        return Math.pow(10, (ratio))*inBasicMetric;
    }

    public double imperialToImperial(int convertFrom, int convertTo, double amount, double[] imperialConversionRate){
        double conversionRate = imperialConversionRate[convertFrom-8]/imperialConversionRate[convertTo-8];
        return amount*conversionRate;
    }

}
