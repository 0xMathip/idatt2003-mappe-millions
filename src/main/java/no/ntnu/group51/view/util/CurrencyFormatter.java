package no.ntnu.group51.view.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class for formatting monetary values.
 */
public final class CurrencyFormatter {

  /**
   * Prevents instantiation of this utility class.
   */
  private CurrencyFormatter() {
  }

  /**
   * Formats a monetary amount as a dollar string with two decimal places.
   *
   * @param amount the amount to format
   * @return the formatted currency string
   * @throws IllegalArgumentException if amount is null
   */
  public static String format(BigDecimal amount) {
    if (amount == null) {
      throw new IllegalArgumentException("Amount cannot be null.");
    }

    BigDecimal scaledAmount = amount.setScale(2, RoundingMode.HALF_UP);

    if (scaledAmount.signum() < 0) {
      return "-$" + scaledAmount.abs().toPlainString();
    }

    return "$" + scaledAmount.toPlainString();
  }
}
