package vitals;

public class Main {

  static boolean isInRange(final float value, final float lowerLimit, final float upperLimit) {
    return checkIfValueIsLesser(value, upperLimit) && checkIfValueIsGreater(value, lowerLimit);
  }

  static boolean checkIfValueIsGreater(final float value, final float lowerLimit) {
    return Float.compare(lowerLimit, value) < 0;
  }

  private static boolean checkIfValueIsLesser(final float value, final float upperLimit) {
    return Float.compare(value, upperLimit) < 0;
  }


  static boolean checkIfBatteryConditionsOk(final BatteryCondition batteryCondition, final float temperature,
      final float lowerLimit, final float upperLimit) {
    if (!isInRange(temperature, lowerLimit, upperLimit)) {
      System.out.println(batteryCondition + " is out of range!");
      return false;
    }
    return true;
  }

  static boolean checkIfBatteryConditionsOk(final ChargeRate chargeRate, final float soc, final float upperLimit) {
    if (!checkIfValueIsLesser(soc, upperLimit)) {
      System.out.println(chargeRate + " is out of range!");
      return false;
    }
    return true;
  }

  static boolean batteryIsOk(final float temperature, final float soc, final float chargeRate) {
    return new Temperature().conditionIsOk(temperature, 0, 45) && new StateOfCharge().conditionIsOk(soc, 20, 80) &&
        new ChargeRate().conditionIsOk(chargeRate, Float.NaN, 0.8f);
  }

  static void testIsValueLesser() {
    assert (checkIfValueIsLesser(3, 5) == true);
    assert (checkIfValueIsLesser(8, 5) == false);
  }

  private static void testIsValueGreater() {
    assert (checkIfValueIsGreater(8, 5) == true);
    assert (checkIfValueIsGreater(3, 5) == false);

  }

  static void testIsInRange() {
    assert (isInRange(3, 1, 6) == true); // range 1-6
    assert (isInRange(1, 4, 6) == false); // range 4-6
    assert (isInRange(7, 4, 6) == false); // range 4-6
  }

  static void testBatteryIsOk() {
    assert (batteryIsOk(25, 70, 0.7f) == true); // temperature,soc, chargeRate in range
    assert (batteryIsOk(50, 85, 0.9f) == false); // temperature,soc, chargeRate - out of range
    assert (batteryIsOk(50, 70, 0.7f) == false); // temperature - out of range(high)
    assert (batteryIsOk(-10, 75, 0.7f) == false); // temperature - out of range(low)
    assert (batteryIsOk(40, 90, 0.7f) == false); // soc - out of range(high)
    assert (batteryIsOk(40, 20, 0.7f) == false); // soc - out of range(low)
    assert (batteryIsOk(40, 70, 1.0f) == false); // charge rate - out of range (high)
  }

  public static void main(final String[] args) {
    testIsValueGreater();
    testIsValueLesser();
    testIsInRange();
    testBatteryIsOk();
  }
}

abstract class BatteryCondition {

  String condition;

  abstract boolean conditionIsOk(float value, float upperlimit, float lowerlimit);
}

class Temperature extends BatteryCondition {


  @Override
  public boolean conditionIsOk(final float temp, final float lowerLimit, final float upperLimit) {
    return Main.checkIfBatteryConditionsOk(this, temp, lowerLimit, upperLimit);
  }
}

class StateOfCharge extends BatteryCondition {

  @Override
  public boolean conditionIsOk(final float soc, final float lowerLimit, final float upperLimit) {
    return Main.checkIfBatteryConditionsOk(this, soc, lowerLimit, upperLimit);
  }
}

class ChargeRate extends BatteryCondition {

  @Override
  public boolean conditionIsOk(final float soc, final float lowerLimit, final float upperLimit) {
    return Main.checkIfBatteryConditionsOk(this, soc, upperLimit);
  }
}
