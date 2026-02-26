package no.ntnu.group51.Stocks;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

  @Test
    public void testGet() {
    Stock stock = new Stock("AAPL", "Apple", new BigDecimal("4.7392781"));
    assertEquals("AAPL", stock.getSymbol());
    assertEquals("Apple", stock.getCompany());

    BigDecimal expected = new BigDecimal("4.7392781");
    BigDecimal actual = stock.getSalesPrice();

    assertEquals(expected, actual);
  }

  @Test
    public void testAddPrice() {
    Stock stock = new Stock("AAPL", "Apple", new BigDecimal("4.7392781"));

    BigDecimal expected1 = new BigDecimal("4.7392781");
    BigDecimal actual1 = stock.getSalesPrice();
    assertEquals(expected1, actual1);

    stock.addNewSalesPrice("5.2732663");
    BigDecimal expected2 = new BigDecimal("5.2732663");
    BigDecimal actual2 = stock.getSalesPrice();
    assertEquals(expected2, actual2);
  }

}