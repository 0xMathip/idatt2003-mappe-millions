package no.ntnu.group51.model.calculator;

import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SaleCalculatorTest {


  // Negative tax
  @Test
  void testSaleCalculator() {
    Stock stock = new Stock("AAPL", "Apple", new BigDecimal("3.55"), "no-icon");
    Share share = new Share(stock, new BigDecimal("30"), new BigDecimal("4.55"));
    SaleCalculator calc = new SaleCalculator(share);

    assertEquals(new BigDecimal("106.50"), calc.calculateGross());
    assertEquals(new BigDecimal("1.0650"), calc.calculateCommission());

    // 106.5 (sales gross) - 1.065 (commission) - 4.55 (purchase price) * 30 (quantity) = minus something so should return 0
    assertEquals(new BigDecimal("0"), calc.calculateTax());

    // 106.5 (sales gross) - 1.0650 (commission) - 0 (tax) = 105.4350
    assertEquals(new BigDecimal("105.4350"), calc.calculateTotal());

  }



  // Positive tax
  @Test
  void testSaleCalculator2() {
    Stock stock = new Stock("AAPL", "Apple", new BigDecimal("4.55"), "no-icon");
    Share share = new Share(stock, new BigDecimal("30"), new BigDecimal("3.55"));
    SaleCalculator calc = new SaleCalculator(share);

    // 136.5 (sales gross) - 1.365 (commission) - 106.5 (purchase gross) = 28.635 * 0.3 = 8.59050
    assertEquals(new BigDecimal("8.59050"), calc.calculateTax());
  }

  @Test
  void testSaleCalculatorWithTax() {
    Stock apple = new Stock("AAPL", "Apple", new BigDecimal("200"), "icon");
    Share share = new Share(apple, new BigDecimal("10"), new BigDecimal("100"));

    SaleCalculator calc = new SaleCalculator(share);

    BigDecimal gross = calc.calculateGross();
    BigDecimal commission = calc.calculateCommission();
    BigDecimal tax = calc.calculateTax();
    BigDecimal total = calc.calculateTotal();

    assertEquals(0, gross.compareTo(new BigDecimal("2000")));
    assertEquals(0, commission.compareTo(new BigDecimal("20")));
    assertTrue(tax.compareTo(new BigDecimal("293")) >= 0);
    assertTrue(tax.compareTo(new BigDecimal("295")) <= 0);
    assertEquals(0, total.compareTo(new BigDecimal("1686")));
  }
}