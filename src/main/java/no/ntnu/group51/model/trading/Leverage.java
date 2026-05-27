package no.ntnu.group51.model.trading;

/**
 * Represents the available leverage options for leveraged trades.
 */
public enum Leverage {

  /**
   * No leverage.
   */
  OFF(1),

  /**
   * 5x leverage.
   */
  X5(5),

  /**
   * 10x leverage.
   */
  X10(10),

  /**
   * 20x leverage.
   */
  X20(20);

  private final int multiplier;

  /**
   * Creates a leverage option.
   *
   * @param multiplier the leverage multiplier
   */
  Leverage(int multiplier) {
    this.multiplier = multiplier;
  }

  /**
   * Returns the leverage multiplier.
   *
   * @return the leverage multiplier
   */
  public int getMultiplier() {
    return multiplier;
  }
}
