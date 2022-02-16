package vitals;

public class RangeChecker {

  static boolean isInRange(final float value, final float lowerLimit, final float upperLimit) {
    return checkIfValueIsLesser(value, upperLimit) && checkIfValueIsGreater(value, lowerLimit);
  }

  static boolean checkIfValueIsGreater(final float value, final float lowerLimit) {
    return Float.compare(lowerLimit, value) < 0;
  }

  static boolean checkIfValueIsLesser(final float value, final float upperLimit) {
    return Float.compare(value, upperLimit) < 0;
  }

}
