package no.ntnu.group51.model.stock;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

  @Test
  void constructorThrowsWhenSymbolIsNull() {
    assertThrows(IllegalArgumentException.class,
        () -> new Stock(null, "Apple", new BigDecimal("4.7392781"), "no-icon"));
  }

  @Test
  void constructorThrowsWhenCompanyIsNull() {
    assertThrows(IllegalArgumentException.class,
        () -> new Stock("AAPL", null, new BigDecimal("4.7392781"), "no-icon"));
  }

  @Test
  void constructorThrowsWhenPriceIsNull() {
    assertThrows(IllegalArgumentException.class,
        () -> new Stock("AAPL", "Apple", null, "no-icon"));
  }


  @Test
  void getLatestPriceChangeReturnsZeroWhenOnePrice() {
    Stock appleStockTest = new Stock("AAPL", "Apple", new BigDecimal("4.7392781"), "no-icon");

    assertEquals(BigDecimal.ZERO, appleStockTest.getLatestPriceChange());
  }

  @Test
  void addNewSalesPriceThrowsWhenPriceIsNull() {
    Stock appleStockTest = new Stock("AAPL", "Apple", new BigDecimal("4.7392781"), "no-icon");
    assertThrows(IllegalArgumentException.class,
        () -> appleStockTest.addNewSalesPrice(null));
  }

  @Test
  void addNewSalesPriceThrowsWhenPriceIsInvalid() {
    Stock appleStockTest = new Stock("AAPL", "Apple", new BigDecimal("4.7392781"), "no-icon");
    assertThrows(IllegalArgumentException.class,
        () -> appleStockTest.addNewSalesPrice("invalid input"));
  }

  @Test
    public void addNewSalesPriceUpdatesSalesPrice() {
    Stock stock = new Stock("AAPL", "Apple", new BigDecimal("4.7392781"), "no-icon");

    BigDecimal expected1 = new BigDecimal("4.7392781");
    BigDecimal actual1 = stock.getSalesPrice();
    assertEquals(expected1, actual1);

    stock.addNewSalesPrice("5.2732663");
    BigDecimal expected2 = new BigDecimal("5.2732663");
    BigDecimal actual2 = stock.getSalesPrice();
    assertEquals(expected2, actual2);
  }

  @Test
  public void gettersReturnSymbolCompanyAndSalesPrice() {
    Stock stock = new Stock("AAPL", "Apple", new BigDecimal("4.7392781"), "no-icon");
    assertEquals("AAPL", stock.getSymbol());
    assertEquals("Apple", stock.getCompany());

    BigDecimal expected = new BigDecimal("4.7392781");
    BigDecimal actual = stock.getSalesPrice();

    assertEquals(expected, actual);
  }

  @Test
  void getLatestPriceChangeReturnsPositive() {
    Stock appleStockTest = new Stock("AAPL", "Apple", new BigDecimal("4.7392781"), "no-icon");
    appleStockTest.addNewSalesPrice("5.443323");
    appleStockTest.addNewSalesPrice("8.534221");

    BigDecimal expectedChange = new BigDecimal("8.534221").subtract(new BigDecimal("5.443323"));

    assertEquals(expectedChange, appleStockTest.getLatestPriceChange());
  }

  @Test
  void getLatestPriceChangeReturnsNegative() {
    Stock appleStockTest = new Stock("AAPL", "Apple", new BigDecimal("4.7392781"), "no-icon");
    appleStockTest.addNewSalesPrice("2.463521");

    BigDecimal expectedChange = new BigDecimal("2.463521").subtract(new BigDecimal("4.7392781"));

    assertEquals(expectedChange, appleStockTest.getLatestPriceChange());
  }

  @Test
  void getLowestPriceReturnsLowestPrice() {
    Stock appleStockTest = new Stock("AAPL", "Apple", new BigDecimal("4.7392781"), "no-icon");
    appleStockTest.addNewSalesPrice("1.163421");
    appleStockTest.addNewSalesPrice("5.443323");
    appleStockTest.addNewSalesPrice("2.463521");
    appleStockTest.addNewSalesPrice("8.534221");

    BigDecimal expected = new BigDecimal("1.163421");

    assertEquals(expected, appleStockTest.getLowestPrice());
  }

  @Test
  void getLowestPriceReturnsOriginalPriceWhenOnePrice() {
    Stock appleStockTest = new Stock("AAPL", "Apple", new BigDecimal("4.7392781"), "no-icon");
    assertEquals(new BigDecimal("4.7392781"), appleStockTest.getLowestPrice());
  }
  @Test
  void getHighestPriceReturnsOriginalPriceWhenOnePrice() {
    Stock appleStockTest = new Stock("AAPL", "Apple", new BigDecimal("7.1324781"), "no-icon");
    assertEquals(new BigDecimal("7.1324781"), appleStockTest.getHighestPrice());
  }

  @Test
  void getHighestPriceReturnsHighestPrice() {
    Stock appleStockTest = new Stock("AAPL", "Apple", new BigDecimal("4.7392781"), "no-icon");
    appleStockTest.addNewSalesPrice("1.163421");
    appleStockTest.addNewSalesPrice("5.443323");
    appleStockTest.addNewSalesPrice("2.463521");
    appleStockTest.addNewSalesPrice("8.534221");

    BigDecimal expected = new BigDecimal("8.534221");

    assertEquals(expected, appleStockTest.getHighestPrice());
  }

  @Test
  void testStockEqualsWithSameSymbol() {
    Stock stock1 = new Stock("AAPL", "Apple", new BigDecimal("150"), "icon");
    Stock stock2 = new Stock("AAPL", "Apple Inc", new BigDecimal("155"), "icon2");

    assertEquals(stock1, stock2);  // Should be equal (same symbol)
  }

  @Test
  void testStockEqualsIsCaseInsensitive() {
    Stock stock1 = new Stock("AAPL", "Apple", new BigDecimal("150"), "icon");
    Stock stock2 = new Stock("aapl", "Apple", new BigDecimal("150"), "icon");

    assertEquals(stock1, stock2);
  }

  @Test
  void testStockNotEqualsWithDifferentSymbol() {
    Stock stock1 = new Stock("AAPL", "Apple", new BigDecimal("150"), "icon");
    Stock stock2 = new Stock("GOOGL", "Google", new BigDecimal("150"), "icon");

    assertNotEquals(stock1, stock2);
  }

  @Test
  void testStockHashCodeConsistent() {
    Stock stock1 = new Stock("AAPL", "Apple", new BigDecimal("150"), "icon");
    Stock stock2 = new Stock("AAPL", "Apple", new BigDecimal("150"), "icon");

    assertEquals(stock1.hashCode(), stock2.hashCode());
  }

  @Test
  void testStockInSet() {
    Set<Stock> stocks = new HashSet<>();
    Stock apple1 = new Stock("AAPL", "Apple", new BigDecimal("150"), "icon");
    Stock apple2 = new Stock("AAPL", "Apple Inc", new BigDecimal("160"), "icon2");

    stocks.add(apple1);
    stocks.add(apple2);

    assertEquals(1, stocks.size());  // Should be 1 (same symbol)
  }

}