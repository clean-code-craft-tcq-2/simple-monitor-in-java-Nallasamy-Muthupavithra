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

  static void testHighWarningLevelReached() {
    assert (BatteryConditionOk.highWarningLevelReached(76, 80, 4) == true);
    assert (BatteryConditionOk.highWarningLevelReached(75, 80, 4) == false);
  }

  static void testLowWarningLevelReached() {
    assert (BatteryConditionOk.lowWarningLevelReached(24, 20, 4) == true);
    assert (BatteryConditionOk.lowWarningLevelReached(26, 20, 4) == false);
  }

  static void testCalculateWarningTolerance() {
    assert (BatteryConditionOk.calculateWarningTolerance(100f) == 5.0f);
    assert (BatteryConditionOk.calculateWarningTolerance(80f) == 4.0f);
  }

  static void testcheckAndWarnEarly() {
    assert (BatteryConditionOk.batteryIsOk("25 C", "24 %", "0.7 C") == true); // Exhibit early warning (low) -soc
    assert (BatteryConditionOk.batteryIsOk("25 C", "76 %", "0.7 C") == true); // Exhibit early warning (high)-soc
    assert (BatteryConditionOk.batteryIsOk("25 C", "26 %", "0.7 C") == true); // No early warning
    assert (BatteryConditionOk.batteryIsOk("25 C", "75 %", "0.7 C") == true); // No early warning
  }

  static void testBatteryIsOk() {
    assert (BatteryConditionOk.batteryIsOk("25 C", "70 %", "0.7 C") == true); // temperature,soc, chargeRate in range
    assert (BatteryConditionOk.batteryIsOk("50 C", "84 %", "0.9 C") == false); // temperature,soc, chargeRate - out of
                                                                               // range
    assert (BatteryConditionOk.batteryIsOk("50 C", "70 %", "0.7 C") == false); // temperature - out of range(high)
    assert (BatteryConditionOk.batteryIsOk("-10 C", "75 %", "0.7 C") == false); // temperature - out of range(low)
    assert (BatteryConditionOk.batteryIsOk("40 C", "90 %", "0.7 C") == false); // soc - out of range(high)
    assert (BatteryConditionOk.batteryIsOk("40 C", "20 %", "0.7 C") == false); // soc - out of range(low)
    assert (BatteryConditionOk.batteryIsOk("40 C", "70 %", "1.0 C") == false); // charge rate - out of range (high)
  }

  static void testTempInFarenheit() {
    // range 32 F - 113 F
    assert (BatteryConditionOk.batteryIsOk("100 F", "24 %", "0.7 C") == true);
    assert (BatteryConditionOk.batteryIsOk("115 F", "24 %", "0.7 C") == false);
    assert (BatteryConditionOk.batteryIsOk("110 F", "24 %", "0.7 C") == true); // early warning
    assert (BatteryConditionOk.batteryIsOk("35 F", "24 %", "0.7 C") == true); // early warning
    assert (BatteryConditionOk.batteryIsOk("31 F", "24 %", "0.7 C") == false);

  }

  public static void main(final String[] args) {
    testIsValueGreater();
    testIsValueLesser();
    testIsInRange();
    testCalculateWarningTolerance();
    testLowWarningLevelReached();
    testHighWarningLevelReached();
    testcheckAndWarnEarly();
    testTempInFarenheit();
    testBatteryIsOk();
    System.out.println("All Tests passed!");
  }
}
