package no.ntnu.group51.model.calculator;

import java.math.BigDecimal;
import no.ntnu.group51.model.trading.LeveragedPosition;

public class LeverageCalculator implements TransactionCalculator {

  private static final BigDecimal TAX_RATE = new BigDecimal("0.30");
  private static final BigDecimal COMMISSION_RATE = new BigDecimal("0.01");
  private static final BigDecimal LEVERAGE_FEE_RATE = new BigDecimal("0.01");

  private final LeveragedPosition leveragedPosition;

  public LeverageCalculator(LeveragedPosition leveragedPosition) {
    if (leveragedPosition == null) {
      throw new IllegalArgumentException("Leveraged position cannot be null.");
    }

    this.leveragedPosition = leveragedPosition;
  }

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

  @Override
  public BigDecimal calculateCommission() {
    return calculateGross().multiply(COMMISSION_RATE);
  }

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

  @Override
  public BigDecimal calculateTotal() {
    return calculateGross()
        .subtract(calculateCommission())
        .subtract(calculateLeverageFee())
        .subtract(calculateTax());
  }

  public BigDecimal calculateLeverageFee() {
    return calculateGross().multiply(LEVERAGE_FEE_RATE);
  }

}
