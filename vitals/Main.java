package vitals;

public class Main {
  
  static void testIsValueLesser() {
    assert (RangeChecker.checkIfValueIsLesser(3, 5) == true);
    assert (RangeChecker.checkIfValueIsLesser(8, 5) == false);
  }

  private static void testIsValueGreater() {
    assert (RangeChecker.checkIfValueIsGreater(8, 5) == true);
    assert (RangeChecker.checkIfValueIsGreater(3, 5) == false);

  }

  static void testIsInRange() {
    assert (RangeChecker.isInRange(3, 1, 6) == true); // range 1-6
    assert (RangeChecker.isInRange(1, 4, 6) == false); // range 4-6
    assert (RangeChecker.isInRange(7, 4, 6) == false); // range 4-6
  }

  static void testBatteryIsOk() {
    assert (BatteryConditionOk.batteryIsOk(25, 70, 0.7f) == true); // temperature,soc, chargeRate in range
    assert (BatteryConditionOk.batteryIsOk(50, 85, 0.9f) == false); // temperature,soc, chargeRate - out of range
    assert (BatteryConditionOk.batteryIsOk(50, 70, 0.7f) == false); // temperature - out of range(high)
    assert (BatteryConditionOk.batteryIsOk(-10, 75, 0.7f) == false); // temperature - out of range(low)
    assert (BatteryConditionOk.batteryIsOk(40, 90, 0.7f) == false); // soc - out of range(high)
    assert (BatteryConditionOk.batteryIsOk(40, 20, 0.7f) == false); // soc - out of range(low)
    assert (BatteryConditionOk.batteryIsOk(40, 70, 1.0f) == false); // charge rate - out of range (high)
  }

  public static void main(final String[] args) {
    testIsValueGreater();
    testIsValueLesser();
    testIsInRange();
    testBatteryIsOk();
  }
}

