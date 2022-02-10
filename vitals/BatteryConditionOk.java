package vitals;

public class BatteryConditionOk {

  static boolean checkIfBatteryConditionOk(final String batteryCondition, final float temperature,
      final float lowerLimit, final float upperLimit) {
    if (!RangeChecker.isInRange(temperature, lowerLimit, upperLimit)) {
      System.out.println(batteryCondition + " is out of range!");
      return false;
    }
    return true;
  }

  static boolean checkIfChargeRateOk(final String chargeRate, final float soc, final float upperLimit) {
    if (!RangeChecker.checkIfValueIsLesser(soc, upperLimit)) {
      System.out.println(chargeRate + " is out of range!");
      return false;
    }
    return true;
  }


  static boolean batteryIsOk(final float temperature, final float soc, final float chargeRate) {
    Temperature tempObject = (final float value, final float lowerLimit,
        final float upperLimit) -> checkIfBatteryConditionOk("Temperature", value, lowerLimit, upperLimit);

    StateOfCharge stateOfChargeObject = (final float value, final float lowerLimit,
        final float upperLimit) -> checkIfBatteryConditionOk("State Of Charge", value, lowerLimit, upperLimit);

    ChargeRate chargeRateObject =
        (final float value, final float upperLimit) -> checkIfChargeRateOk("Charge Rate", value, upperLimit);
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

