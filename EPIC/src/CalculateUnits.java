public class CalculateUnits {
    public double metricToMetric(int convertFrom, int convertTo, double amount){
        int ratio = convertFrom - convertTo;
        return (amount*Math.pow(10, ratio));
    }

    public double metricToImperial(int convertFrom, int convertTo, double amount, double currentImperialConversionRate, double metricToImperialRate){
        int ratio = convertFrom - 4;
        double inBasicMetric = amount*(Math.pow(10, ratio));
        double inBasicImperial = inBasicMetric*metricToImperialRate;

        return inBasicImperial* currentImperialConversionRate;
    }

    public double imperialToMetric(int convertFrom, int convertTo, double amount, double currentImperialConversionRate, double ImperialToMetricRate){
        double inBasicImperial = currentImperialConversionRate*amount;
        double inBasicMetric = inBasicImperial*ImperialToMetricRate;
        int ratio = convertTo-4;

        return Math.pow(10, (ratio));
    }

    public double imperialToImperial(double convertFrom, double convertTo, double amount){
        return(amount * convertFrom/convertTo);
    }

}
