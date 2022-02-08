abstract class BatteryCondition {

  String condition;

  abstract boolean conditionIsOk(float value, float upperlimit, float lowerlimit);
}

class Temperature extends BatteryCondition {


  @Override
  public boolean conditionIsOk(final float temp, final float lowerLimit, final float upperLimit) {
    return BatteryConditionOk.checkIfBatteryConditionsOk(this, temp, lowerLimit, upperLimit);
  }
}

class StateOfCharge extends BatteryCondition {

  @Override
  public boolean conditionIsOk(final float soc, final float lowerLimit, final float upperLimit) {
    return BatteryConditionOk.checkIfBatteryConditionsOk(this, soc, lowerLimit, upperLimit);
  }
}

class ChargeRate extends BatteryCondition {

  @Override
  public boolean conditionIsOk(final float soc, final float lowerLimit, final float upperLimit) {
    return BatteryConditionOk.checkIfBatteryConditionsOk(this, soc, upperLimit);
  }
}