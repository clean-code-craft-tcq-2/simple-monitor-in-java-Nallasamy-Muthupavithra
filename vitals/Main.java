package vitals;


  static boolean isInRange(final float value, final float lowerLimit, final float upperLimit) {
    if (Float.isNaN(upperLimit)) {
      return Float.compare(lowerLimit, value) < 0;
    }
    if (Float.isNaN(lowerLimit)) {
      return Float.compare(value, upperLimit) < 0;
    }
    return (Float.compare(value, upperLimit) < 0) && (Float.compare(lowerLimit, value) < 0);
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
    else if (!isInRange(chargeRate, Float.NaN, 0.8f)) {
      System.out.println("Charge Rate is out of range!");
      return false;
    }
    return true;
  }

  static void testIsInRange() {
    assert (isInRange(3, 1, 6) == true); // range 1-6
    assert (isInRange(1, 4, 6) == false); // range 4-6
    assert (isInRange(7, 4, 6) == false); // range 4-6
    assert (isInRange(1, Float.NaN, 2) == true); // range <2
    assert (isInRange(5, Float.NaN, 2) == false); // range <2
    assert (isInRange(5, 1, Float.NaN) == true); // range >1
    assert (isInRange(5, 13, Float.NaN) == false); // range >13
  }

  public static void main(final String[] args) {
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
