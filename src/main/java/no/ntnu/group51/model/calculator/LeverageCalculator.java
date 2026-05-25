package no.ntnu.group51.model.calculator;

import java.math.BigDecimal;
import no.ntnu.group51.model.trading.LeveragedPosition;

/**
 * Calculates values, fees, tax, and total return for a leveraged position.
 */
public class LeverageCalculator implements TransactionCalculator {

  private static final BigDecimal TAX_RATE = new BigDecimal("0.30");
  private static final BigDecimal COMMISSION_RATE = new BigDecimal("0.01");
  private static final BigDecimal LEVERAGE_FEE_RATE = new BigDecimal("0.01");

  private final LeveragedPosition leveragedPosition;

  /**
   * Creates a calculator for a leveraged position.
   *
   * @param leveragedPosition the leveraged position to calculate values for
   * @throws IllegalArgumentException if the leveraged position is null
   */
  public LeverageCalculator(LeveragedPosition leveragedPosition) {
    if (leveragedPosition == null) {
      throw new IllegalArgumentException("Leveraged position cannot be null.");
    }

    this.leveragedPosition = leveragedPosition;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BigDecimal calculateGross() {
    BigDecimal currentPrice = leveragedPosition.getShare().getStock().getSalesPrice();
    BigDecimal entryPrice = leveragedPosition.getShare().getPurchasePrice();
    BigDecimal quantity = leveragedPosition.getShare().getQuantity();

    BigDecimal profitLoss = currentPrice
        .subtract(entryPrice)
        .multiply(quantity);

    BigDecimal gross = leveragedPosition.getMarginRequired().add(profitLoss);

    if (gross.compareTo(BigDecimal.ZERO) < 0) {
      return BigDecimal.ZERO;
    }

    return gross;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BigDecimal calculateCommission() {
    return calculateGross().multiply(COMMISSION_RATE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BigDecimal calculateTax() {
    BigDecimal earnings = calculateGross()
        .subtract(calculateCommission())
        .subtract(calculateLeverageFee())
        .subtract(leveragedPosition.getMarginRequired());

    BigDecimal tax = earnings.multiply(TAX_RATE);

    if (tax.compareTo(BigDecimal.ZERO) < 0) {
      return BigDecimal.ZERO;
    }
    return tax;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public BigDecimal calculateTotal() {
    return calculateGross()
        .subtract(calculateCommission())
        .subtract(calculateLeverageFee())
        .subtract(calculateTax());
  }

  /**
   * Calculates the leverage fee based on the current gross value.
   *
   * @return the leverage fee
   */
  public BigDecimal calculateLeverageFee() {
    return calculateGross().multiply(LEVERAGE_FEE_RATE);
  }

}
