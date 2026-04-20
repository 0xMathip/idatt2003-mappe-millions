package no.ntnu.group51.Stocks;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a stock listed for a company.
 */
public class Stock {
  private final String symbol;
  private final String company;
  private final List<BigDecimal> prices;

  /**
   * Creates a new stock.
   *
   * @param symbol     The symbol of the company. Example: AAPL for Apple
   * @param company    The name of the company
   * @param salesPrice The sales price of the stock
   * @throws IllegalArgumentException if symbol, company or salesPrice is null
   */
  public Stock(String symbol, String company, BigDecimal salesPrice) {
    if (symbol == null) {
      throw new IllegalArgumentException("symbol cannot be null");
    }

    if (company == null) {
      throw new IllegalArgumentException("company cannot be null");
    }

    if (salesPrice == null) {
      throw new IllegalArgumentException("salesPrice cannot be null");
    }
    this.symbol = symbol;
    this.company = company;

    prices = new ArrayList<>();
    prices.add(salesPrice);
  }

  /**
   * Adds a new sales price to the list of prices.
   *
   * @param price The price you want to add.
   * @throws IllegalArgumentException if price is null or invalid price format
   */
  public void addNewSalesPrice(String price) {
    if (price == null) {
      throw new IllegalArgumentException("price cannot be null");
    }

    try {
      BigDecimal newPrice = new BigDecimal(price);
      prices.add(newPrice);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid format", e);
    }
  }

  /**
   * Returns the stock symbol.
   *
   * @return the symbol of a stock
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Returns an immutable copy of all registered prices for this stock.
   *
   * @return an unmodifiable list of historical prices
   */
  public List<BigDecimal> getHistoricalPrices() {
    return List.copyOf(prices);
  }

  /**
   * Returns the highest price for this stock.
   *
   * @return the highest price of a stock, or {@code BigDecimal.ZERO}
   *         if no prices are available
   */
  public BigDecimal getHighestPrice() {

    return prices.stream()
        .max(BigDecimal::compareTo)
        .orElse(BigDecimal.ZERO);
  }

  /**
   * Returns the lowest price for this stock.
   *
   * @return the lowest price of a stock, or {@code BigDecimal.ZERO}
   *         if prices unexpectedly is an empty list
   */
  public BigDecimal getLowestPrice() {
    return prices.stream()
        .min(BigDecimal::compareTo)
        .orElse(BigDecimal.ZERO);
  }

  /**
   * Returns the difference between the most recent price
   * and the previous price.
   *
   * @return the latest price change, or {@code BigDecimal.ZERO}
   *         if fewer than two prices have been recorded
   */
  public BigDecimal getLatestPriceChange() {
    if (prices.size() < 2) {
      return BigDecimal.ZERO;
    }

    BigDecimal lastChange = prices.get(prices.size() - 1);
    BigDecimal secondLastChange = prices.get(prices.size() - 2);

    return lastChange.subtract(secondLastChange);
  }

  /**
   * Returns the company name.
   *
   * @return the company name
   */
  public String getCompany() {
    return company;
  }

  /**
   * Returns the most recently registered sales price for this stock.
   *
   * @return the latest sales price
   */
  public BigDecimal getSalesPrice() {
    return prices.getLast();
  }

}