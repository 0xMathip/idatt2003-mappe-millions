package no.ntnu.group51.Calculator;

import java.math.BigDecimal;
import no.ntnu.group51.Share;

/**
 * Calculator for purchases.
 */
public class PurchaseCalculator implements TransactionCalculator {
  private BigDecimal purchasePrice;
  private BigDecimal quantity;

  /**
   * Creates a purchase calculator for a share.
   *
   * @param share The share you want to create a calculator for
   */
  public PurchaseCalculator(Share share) {
    this.purchasePrice = share.getPurchasePrice();
    this.quantity = share.getQuantity();
  }

  /**
   * Calculates the gross.
   *
   * @return Purchase price multiplied by the quantity
   */
  public BigDecimal calculateGross() {
    return purchasePrice.multiply(quantity);
  }

  /**
   * Calculates the commission. Commission is supposed to be 0.5% of the gross.
   *
   * @return The gross multiplied by the commission
   */
  public BigDecimal calculateCommission() {
    return calculateGross().multiply(new BigDecimal("0.005"));
  }

  /**
   * Calculates the tax.
   *
   * @return There is no tax for purchases, so it returns 0
   */
  public BigDecimal calculateTax() {
    return new BigDecimal("0");
  }

  /**
   * Calculates the total.
   *
   * @return The gross + the commission
   */
  public BigDecimal calculateTotal() {
    return calculateGross().add(calculateCommission());
  }
}
