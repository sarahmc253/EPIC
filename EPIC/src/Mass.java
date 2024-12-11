public class Mass implements ImperialCalculator{
    @Override
    public String imperialUnitsList() {
        return """
                8: Ounce (oz)
                9: Pound (lb)
                10: Stone (st)
                11: Hundredweight (cwt) +
                12: Ton (t)""";
    }

    public double[] imperialConversionRate(){
        return new double[]{0.0625, 1, 14, 112, 2240};
    }
}
