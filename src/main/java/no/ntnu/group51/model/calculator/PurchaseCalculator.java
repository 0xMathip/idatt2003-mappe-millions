package no.ntnu.group51.model.calculator;

import java.math.BigDecimal;
import no.ntnu.group51.model.stock.Share;

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
    if (share == null) {
      throw new IllegalArgumentException("share cannot be null");
    }
    this.purchasePrice = share.getPurchasePrice();
    this.quantity = share.getQuantity();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BigDecimal calculateGross() {
    return purchasePrice.multiply(quantity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BigDecimal calculateCommission() {
    return calculateGross().multiply(new BigDecimal("0.005"));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BigDecimal calculateTax() {
    return new BigDecimal("0");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BigDecimal calculateTotal() {
    return calculateGross().add(calculateCommission());
  }
}
