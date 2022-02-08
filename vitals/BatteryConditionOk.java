package vitals;

public class BatteryConditionOk {

  static boolean checkIfBatteryConditionsOk(final BatteryCondition batteryCondition, final float temperature,
      final float lowerLimit, final float upperLimit) {
    if (!RangeChecker.isInRange(temperature, lowerLimit, upperLimit)) {
      System.out.println(batteryCondition + " is out of range!");
      return false;
    }
    return true;
  }

  static boolean checkIfBatteryConditionsOk(final ChargeRate chargeRate, final float soc, final float upperLimit) {
    if (!RangeChecker.checkIfValueIsLesser(soc, upperLimit)) {
      System.out.println(chargeRate + " is out of range!");
      return false;
    }
    return true;
  }

  static boolean batteryIsOk(final float temperature, final float soc, final float chargeRate) {
    return new Temperature().conditionIsOk(temperature, 0, 45) && new StateOfCharge().conditionIsOk(soc, 20, 80) &&
        new ChargeRate().conditionIsOk(chargeRate, Float.NaN, 0.8f);
  }

}

