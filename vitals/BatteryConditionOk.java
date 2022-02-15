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

  static boolean batteryIsOk(final float temperature, final float soc, final float chargeRate) {
    Temperature tempObject = (final float value, final float lowerLimit,
        final float upperLimit) -> checkIfBatteryConditionOk("Temperature", value, lowerLimit, upperLimit, true);

    StateOfCharge stateOfChargeObject = (final float value, final float lowerLimit,
        final float upperLimit) -> checkIfBatteryConditionOk("State Of Charge", value, lowerLimit, upperLimit, true);

    ChargeRate chargeRateObject =
        (final float value, final float upperLimit) -> checkIfChargeRateOk("Charge Rate", value, upperLimit, true);
    return tempObject.conditionIsOk(temperature, 0, 45) && stateOfChargeObject.conditionIsOk(soc, 20, 80) &&
        chargeRateObject.conditionIsOk(chargeRate, 0.8f);
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

}
