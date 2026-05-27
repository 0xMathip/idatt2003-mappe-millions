package no.ntnu.group51.model.calculator;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.trading.Leverage;
import no.ntnu.group51.model.trading.LeveragedPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LeverageCalculatorTest {
  private LeverageCalculator calculator;
  private LeveragedPosition position;
  private Stock apple;

  @BeforeEach
  void setup() {
    apple = new Stock("AAPL", "Apple", new BigDecimal("100"), "icon");
    Share share = new Share(apple, new BigDecimal("100"), new BigDecimal("100"));
    position = new LeveragedPosition(
        share,
        Leverage.X5,
        new BigDecimal("1000"),
        new BigDecimal("5000"),
        new BigDecimal("80")
    );
    calculator = new LeverageCalculator(position);
  }

  @Test
  void constructorThrowsWhenPositionNull() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new LeverageCalculator(null)
    );
  }

  @Test
  void calculateGrossWithProfitAddsToMargin() {
    apple.addNewSalesPrice("110");
    BigDecimal gross = calculator.calculateGross();

    assertEquals(0, gross.compareTo(new BigDecimal("2000")));
  }

  @Test
  void calculateGrossWithLossSubtractsFromMargin() {
    apple.addNewSalesPrice("90");
    BigDecimal gross = calculator.calculateGross();

    assertEquals(0, gross.compareTo(BigDecimal.ZERO));
  }

  @Test
  void calculateGrossNeverNegative() {
    apple.addNewSalesPrice("50");
    BigDecimal gross = calculator.calculateGross();

    assertEquals(0, gross.compareTo(BigDecimal.ZERO));
  }

  @Test
  void calculateCommissionIsPercentageOfGross() {
    apple.addNewSalesPrice("110");
    BigDecimal gross = calculator.calculateGross();
    BigDecimal commission = calculator.calculateCommission();

    BigDecimal expectedCommission = gross.multiply(new BigDecimal("0.01"));
    assertEquals(0, commission.compareTo(expectedCommission));
  }

  @Test
  void calculateLeverageFeeIsPercentageOfGross() {
    apple.addNewSalesPrice("110");
    BigDecimal gross = calculator.calculateGross();
    BigDecimal fee = calculator.calculateLeverageFee();

    BigDecimal expectedFee = gross.multiply(new BigDecimal("0.01"));
    assertEquals(0, fee.compareTo(expectedFee));
  }

  @Test
  void calculateTaxOnlyAppliesIfProfitable() {
    apple.addNewSalesPrice("110");
    BigDecimal tax = calculator.calculateTax();

    assertTrue(tax.compareTo(BigDecimal.ZERO) > 0);
  }

  @Test
  void calculateTaxZeroWhenNotProfitable() {
    apple.addNewSalesPrice("90");
    BigDecimal tax = calculator.calculateTax();

    assertEquals(0, tax.compareTo(BigDecimal.ZERO));
  }

  @Test
  void calculateTaxIs30PercentOfEarnings() {
    apple.addNewSalesPrice("110");

    BigDecimal gross = calculator.calculateGross();
    BigDecimal commission = calculator.calculateCommission();
    BigDecimal fee = calculator.calculateLeverageFee();
    BigDecimal earnings = gross.subtract(commission).subtract(fee).subtract(new BigDecimal("1000"));
    BigDecimal expectedTax = earnings.multiply(new BigDecimal("0.30"));

    BigDecimal actualTax = calculator.calculateTax();

    assertEquals(0, actualTax.compareTo(expectedTax));
  }

  @Test
  void calculateTotalSubtractsAllCosts() {
    apple.addNewSalesPrice("110");

    BigDecimal gross = calculator.calculateGross();
    BigDecimal commission = calculator.calculateCommission();
    BigDecimal fee = calculator.calculateLeverageFee();
    BigDecimal tax = calculator.calculateTax();

    BigDecimal expectedTotal = gross
        .subtract(commission)
        .subtract(fee)
        .subtract(tax);

    BigDecimal actualTotal = calculator.calculateTotal();

    assertEquals(0, actualTotal.compareTo(expectedTotal));
  }

  @Test
  void calculateTotalWithBreakevenTrade() {
    BigDecimal total = calculator.calculateTotal();

    assertTrue(total.compareTo(BigDecimal.ZERO) > 0);
  }

  @Test
  void calculateTotalWithSmallProfit() {
    apple.addNewSalesPrice("101");
    BigDecimal total = calculator.calculateTotal();

    assertTrue(total.compareTo(BigDecimal.ZERO) > 0);
  }

  @Test
  void calculateTotalWithMediumProfit() {
    apple.addNewSalesPrice("120");
    BigDecimal total = calculator.calculateTotal();

    assertTrue(total.compareTo(new BigDecimal("1500")) > 0);
  }

  @Test
  void multiplePriceChanges() {
    apple.addNewSalesPrice("110");
    BigDecimal total1 = calculator.calculateTotal();

    apple.addNewSalesPrice("120");
    BigDecimal total2 = calculator.calculateTotal();

    assertTrue(total2.compareTo(total1) > 0);
  }

  @Test
  void calculateGrossWithZeroMargin() {
    Share share = new Share(apple, new BigDecimal("100"), new BigDecimal("100"));
    LeveragedPosition zeroMarginPos = new LeveragedPosition(
        share,
        Leverage.X5,
        BigDecimal.ZERO,
        new BigDecimal("5000"),
        new BigDecimal("80")
    );
    LeverageCalculator calc = new LeverageCalculator(zeroMarginPos);

    apple.addNewSalesPrice("110");
    BigDecimal gross = calc.calculateGross();

    assertEquals(0, gross.compareTo(new BigDecimal("1000")));
  }

  @Test
  void implementsTransactionCalculator() {
    assertTrue(calculator instanceof TransactionCalculator);
  }
}
