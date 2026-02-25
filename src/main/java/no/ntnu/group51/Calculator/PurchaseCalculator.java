package no.ntnu.group51.Calculator;

import java.math.BigDecimal;
import no.ntnu.group51.Share;

/**
 * Calculator for purchases.
 */
public class PurchaseCalculator implements TransactionCalculator {
  private final BigDecimal purchasePrice;
  private final BigDecimal quantity;

  /**
   * Creates a purchase calculator for a share.
   *
   * @param share The share you want to create a calculator for
   */
  public PurchaseCalculator(Share share) {
    this.purchasePrice = share.getPurchasePrice();
    this.quantity = share.getQuantity();
  }

  @Override
  public BigDecimal calculateGross() {
    return purchasePrice.multiply(quantity);
  }

  @Override
  public BigDecimal calculateCommission() {
    return calculateGross().multiply(new BigDecimal("0.005"));
  }

  @Override
  public BigDecimal calculateTax() {
    return new BigDecimal("0");
  }

  @Override
  public BigDecimal calculateTotal() {
    return calculateGross().add(calculateCommission());
  }
}
