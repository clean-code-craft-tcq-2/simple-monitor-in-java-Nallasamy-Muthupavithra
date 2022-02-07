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

  static boolean batteryIsOk(final float temperature, final float soc, final float chargeRate) {
    if (!isInRange(temperature, 0, 45)) {
      System.out.println("Temperature is out of range!");
      return false;
    }
    else if (!isInRange(soc, 20, 80)) {
      System.out.println("State of Charge is out of range!");
      return false;
    }
    else if (!checkIfValueIsGreater(chargeRate, 0.8f)) {
      System.out.println("Charge Rate is out of range!");
      return false;
    }
    return true;
  }

  static void testIsValueLesser() {
    assert (checkIfValueIsLesser(3, 5) == true);
    assert (checkIfValueIsLesser(8, 5) == false);
  }

  /**
   *
   */
  private static void testIsValueGreater() {
    assert (checkIfValueIsGreater(8, 5) == true);
    assert (checkIfValueIsGreater(3, 5) == false);

  }

  static void testIsInRange() {
    assert (isInRange(3, 1, 6) == true); // range 1-6
    assert (isInRange(1, 4, 6) == false); // range 4-6
    assert (isInRange(7, 4, 6) == false); // range 4-6
  }

  public static void main(final String[] args) {
    testIsValueGreater();
    testIsValueLesser();
    testIsInRange();
    testBatteryIsOk();
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
}
