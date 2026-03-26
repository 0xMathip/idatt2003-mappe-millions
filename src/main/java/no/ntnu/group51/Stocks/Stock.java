package no.ntnu.group51.Stocks;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Stock class represents a stock for a company.
 */
public class Stock {
  private final String symbol;
  private final String company;
  private final List<BigDecimal> prices;

  /**
   * Creates a new stock.
   *
   * @param symbol The symbol of the company. Example: AAPL for Apple.
   * @param company The name of the company.
   * @param salesPrice The sales price of the stock.
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
   */
  public void addNewSalesPrice(String price) {
    if (price == null) {
      throw new IllegalArgumentException("price cannot be null");
    }
    BigDecimal newPrice = new BigDecimal(price);
    prices.add(newPrice);
  }

  public String getSymbol() {
    return symbol;
  }

  public List<BigDecimal> getHistoricalPrices() {
    return List.copyOf(prices);
  }

  public BigDecimal getHighestPrice() {

    return prices.stream()
                 .max(BigDecimal::compareTo)
                 .orElse(BigDecimal.ZERO);
  }

  public BigDecimal getLowestPrice() {
    return prices.stream()
                 .min(BigDecimal::compareTo)
                 .orElse(BigDecimal.ZERO);
  }

  public BigDecimal getLatestPriceChange() {
    if (prices == null || prices.size() < 2) {
      return BigDecimal.ZERO;
    }

    BigDecimal lastChange = prices.get(prices.size() - 1);
    BigDecimal secondLastChange = prices.get(prices.size() - 2);

    return lastChange.subtract(secondLastChange);
  }

  public String getCompany() {
    return company;
  }

  public BigDecimal getSalesPrice() {
    return prices.getLast();
  }

}