package no.ntnu.group51.Calculator;

import java.math.BigDecimal;

public interface TransactionCalculator {

  /**
   * Calculates gross.
   *
   * @return purchase price or sales price multiplied by the quantity
   */
  BigDecimal calculateGross();

  /**
   * Calculates the commission.
   *
   * @return The gross multiplied by a percentage decided by the commission
   */
  BigDecimal calculateCommission();

  /**
   * Calculates the tax.
   *
   * @return The tax. There is no tax when you purchase, so it returns 0 in those cases
   */
  BigDecimal calculateTax();

  /**
   * Calculates the total cost or value of the purchase/sale.
   *
   * @return The total cost of the purchase or the total value of the sale
   */
  BigDecimal calculateTotal();
}
