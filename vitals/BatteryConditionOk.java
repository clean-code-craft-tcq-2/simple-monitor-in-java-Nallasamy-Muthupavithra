package vitals;

public class BatteryConditionOk {

  static final float WARNING_TOLERANCE_PERCENT = (float) 0.05;

  static float calculateWarningTolerance(final float upperLimit) {
    return WARNING_TOLERANCE_PERCENT * upperLimit;
  }

  static void checkAndWarnEarly(final float batteryCondition, final float lowerLimit, final float upperLimit) {
    checkAndWarnEarlyHigh(batteryCondition, upperLimit);
    checkAndWarnEarlyLow(batteryCondition, lowerLimit, upperLimit);
  }

  static void checkAndWarnEarlyHigh(final float batteryCondition, final float upperLimit) {
    if (highWarningLevelReached(batteryCondition, upperLimit, calculateWarningTolerance(upperLimit))) {
      System.out.println("Warning: " + batteryCondition + "Approaching High!");
    }
  }

  static void checkAndWarnEarlyLow(final float batteryCondition, final float lowerLimit, final float upperLimit) {
    if (lowWarningLevelReached(batteryCondition, lowerLimit, calculateWarningTolerance(upperLimit))) {
      System.out.println("Warning: " + batteryCondition + "Approaching Low!");
    }
  }

  static boolean highWarningLevelReached(final float batteryCondition, final float upperLimit,
      final float warningTolerance) {
    return batteryCondition >= (upperLimit - warningTolerance);
  }

  static boolean lowWarningLevelReached(final float batteryCondition, final float lowerLimit,
      final float warningTolerance) {
    return batteryCondition <= (lowerLimit + warningTolerance);
  }

  static boolean checkIfBatteryConditionOk(final String batteryCondition, final float value, final float lowerLimit,
      final float upperLimit, final boolean earlyWarningRequired) {
    if (!RangeChecker.isInRange(value, lowerLimit, upperLimit)) {
      System.out.println(batteryCondition + " is out of range!");
      return false;
    }
    if (earlyWarningRequired) {
      checkAndWarnEarly(value, lowerLimit, upperLimit);
    }
    return true;
  }

  static boolean checkIfChargeRateOk(final String chargeRate, final float soc, final float upperLimit,
      final boolean earlyWarningRequired) {
    if (!RangeChecker.checkIfValueIsLesser(soc, upperLimit)) {
      System.out.println(chargeRate + " is out of range!");
      return false;
    }
    if (earlyWarningRequired) {
      checkAndWarnEarlyHigh(soc, upperLimit);
    }
    return true;
  }

  static boolean batteryIsOk(final String temperature, final String soc, final String chargeRate) {

    Temperature tempObject = (value, lowerLimit, upperLimit) -> checkIfBatteryConditionOk("Temperature", value,
        lowerLimit, upperLimit, true);

    StateOfCharge stateOfChargeObject = (value, lowerLimit, upperLimit) -> checkIfBatteryConditionOk("State Of Charge",
        value, lowerLimit, upperLimit, true);

    ChargeRate chargeRateObject = (value, upperLimit) -> checkIfChargeRateOk("Charge Rate", value, upperLimit, true);

    float tempInCelcius = getTemperatureInCelcius(temperature);
    float socInPercent = getSocInPercent(soc);
    float chargeRateInCRate = getChargeRateInCRate(chargeRate);

    return tempObject.conditionIsOk(tempInCelcius, 0, 45) && stateOfChargeObject.conditionIsOk(socInPercent, 20, 80) &&
        chargeRateObject.conditionIsOk(chargeRateInCRate, 0.8f);
  }

  private static float getChargeRateInCRate(final String chargeRate) {
    // ChargeRate Eg: 0.8 C
    float chargeRateInCRate = 0;
    String[] unitAndValue = extractUnitAndValue(chargeRate);
    float value = Float.parseFloat(unitAndValue[0]);
    String unit = unitAndValue[1];
    if (unit.equals("C")) {
      chargeRateInCRate = value;
    }
    return chargeRateInCRate;
  }

  private static float getSocInPercent(final String soc) {
    // SOC Eg: 40 %
    float socInPercent = 0;
    String[] unitAndValue = extractUnitAndValue(soc);
    float value = Float.parseFloat(unitAndValue[0]);
    String unit = unitAndValue[1];
    if (unit.equals("%")) {
      socInPercent = value;
    }
    return socInPercent;
  }

  private static float getTemperatureInCelcius(final String temperature) {
    // Eg: Farenheit - 100 F, Celcius - 40 C
    String[] unitAndValue = extractUnitAndValue(temperature);
    float value = Float.parseFloat(unitAndValue[0]);
    String unit = unitAndValue[1];
    if (unit.equals("F")) {
      value = ((value - 32) * 5) / 9;
    }
    return value;
  }

  private static String[] extractUnitAndValue(final String valueInStringFormat) {
    String regex = "\\s";
    return valueInStringFormat.split(regex);
  }

  interface Temperature {

    boolean conditionIsOk(final float temp, final float lowerLimit, final float upperLimit);
  }

  interface StateOfCharge {

    boolean conditionIsOk(final float temp, final float lowerLimit, final float upperLimit);
  }

  interface ChargeRate {

    boolean conditionIsOk(final float temp, final float upperLimit);
  }

}
