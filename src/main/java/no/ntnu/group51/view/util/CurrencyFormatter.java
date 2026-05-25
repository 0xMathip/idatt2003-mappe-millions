package no.ntnu.group51.view.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class CurrencyFormatter {

  private CurrencyFormatter() {

  }

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
