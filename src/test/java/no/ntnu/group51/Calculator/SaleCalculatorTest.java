package no.ntnu.group51.Calculator;

import no.ntnu.group51.Share;
import no.ntnu.group51.Stocks.Stock;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SaleCalculatorTest {


  // Negative tax
  @Test
  void testSaleCalculator() {
    Stock stock = new Stock("AAPL", "Apple", new BigDecimal("3.55"));
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
    Stock stock = new Stock("AAPL", "Apple", new BigDecimal("4.55"));
    Share share = new Share(stock, new BigDecimal("30"), new BigDecimal("3.55"));
    SaleCalculator calc = new SaleCalculator(share);

    // 136.5 (sales gross) - 1.365 (commission) - 106.5 (purchase gross) = 28.635 * 0.3 = 8.59050
    assertEquals(new BigDecimal("8.59050"), calc.calculateTax());
  }


}