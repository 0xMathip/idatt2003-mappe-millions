package no.ntnu.group51.Calculator;

import no.ntnu.group51.Stocks.Share;
import no.ntnu.group51.Stocks.Stock;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseCalculatorTest {

  Stock stock = new Stock("AAPL", "Apple", new BigDecimal("3.55"));
  Share share = new Share(stock, new BigDecimal("30"), new BigDecimal("4.55"));
  PurchaseCalculator calc = new PurchaseCalculator(share);

  @Test
  void testPurchaseCalculator() {
    assertEquals(new BigDecimal("136.50"), calc.calculateGross());
    assertEquals(new BigDecimal("0.68250"), calc.calculateCommission());
    assertEquals(new BigDecimal("0"), calc.calculateTax());
    assertEquals(new BigDecimal("137.18250"), calc.calculateTotal());
  }

}