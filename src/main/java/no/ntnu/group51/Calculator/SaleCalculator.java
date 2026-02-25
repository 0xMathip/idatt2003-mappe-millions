package no.ntnu.group51.Calculator;

import no.ntnu.group51.Share;
import no.ntnu.group51.Stocks.Stock;

import java.math.BigDecimal;

/**
 * Calculator for sales.
 */
public class SaleCalculator implements TransactionCalculator {
  private final BigDecimal purchasePrice;
  private final BigDecimal salesPrice;
  private final BigDecimal quantity;

  /**
   * Creates a sale calculator for a share.
   *
   * @param share The share you want to create the calculator for
   */
  public SaleCalculator(Share share) {
    this.purchasePrice = share.getPurchasePrice();
    this.salesPrice = share.getStock().getSalesPrice();
    this.quantity = share.getQuantity();
  }

  @Override
  public BigDecimal calculateGross() {
    return salesPrice.multiply(quantity);
  }

  @Override
  public BigDecimal calculateCommission() {
    return calculateGross().multiply(new BigDecimal("0.01"));
  }

  @Override
  public BigDecimal calculateTax() {
    BigDecimal sharesValue = purchasePrice.multiply(quantity);
    BigDecimal earnings = calculateGross().subtract(calculateCommission()).subtract(sharesValue);
    BigDecimal tax = earnings.multiply(new BigDecimal("0.3"));

    if (tax.compareTo(BigDecimal.ZERO) < 0) {
      return BigDecimal.ZERO;

    } else {
      return tax;
    }

  }

  @Override
  public BigDecimal calculateTotal() {
    return calculateGross().subtract(calculateCommission()).subtract(calculateTax());
  }
}
