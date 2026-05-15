package no.ntnu.group51.model.enums;

public enum Leverage {
  OFF(1),
  X5(5),
  X10(10),
  X20(20);

  private final int multiplier;

  Leverage(int multiplier) {
    this.multiplier = multiplier;
  }
  public int getMultiplier(){
    return multiplier;
  }
}
