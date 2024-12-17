public class CalculateUnits {

    // method to convert metric units to metric units
    public double metricToMetric(int convertFrom, int convertTo, double amount){
        // calculate ratio between metric units
        int ratio = convertFrom - convertTo;
        // calculate conversion between units
        return (amount*Math.pow(10, ratio));
    }

    // method to convert metric units to imperial units
    public double metricToImperial(int convertFrom, int convertTo, double amount, double[] currentImperialConversionRate, double metricToImperialRate){
        int ratio = convertFrom - 4;     // calculate ratio between entered unit and standard unit
        double inBasicMetric = amount*(Math.pow(10, ratio));    // calculate entered amount in standard units

        double inBasicImperial = inBasicMetric*metricToImperialRate;    // convert amount in standard metric to standard imperial
        return inBasicImperial* (1/currentImperialConversionRate[convertTo-8]);     // calculate entered amount in the desired unit
    }

    // method to convert imperial units to metric units
    public double imperialToMetric(int convertFrom, int convertTo, double amount, double[] currentImperialConversionRate, double ImperialToMetricRate){
        double inBasicImperial = currentImperialConversionRate[convertFrom-8]*amount;   // convert entered amount to standard imperial
        double inBasicMetric = inBasicImperial*ImperialToMetricRate;    // convert from standard imperial to standard metric

        int ratio = 4-convertTo;    // calculate ratio between standard metric unit and desired unit
        return Math.pow(10, (ratio))*inBasicMetric;     // convert amount from standard unit to desired unit
    }

    // method to convert imperial units to imperial units
    public double imperialToImperial(int convertFrom, int convertTo, double amount, double[] imperialConversionRate){
        // get ratio between entered unit and desired unit
        double conversionRate = imperialConversionRate[convertFrom-8]/imperialConversionRate[convertTo-8];
        return amount*conversionRate;   // multiply entered amount by the ratio
    }

}
