package no.ntnu.group51.view.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class for formatting percentage values.
 */
public final class PercentFormatter {

  /**
   * Prevents instantiation of this utility class.
   */
  private PercentFormatter() {
  }

  /**
   * Formats a percentage value with two decimal places.
   *
   * @param value the percentage value to format
   * @return the formatted percentage string
   */
  public static String format(BigDecimal value) {
    if (value == null) {
      throw new IllegalArgumentException("Value cannot be null.");
    }

    return value
        .setScale(2, RoundingMode.HALF_UP) + "%";
  }
}
