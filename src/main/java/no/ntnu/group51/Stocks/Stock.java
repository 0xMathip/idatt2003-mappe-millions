package no.ntnu.group51.Stocks;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
  public Stock(String symbol, String company, String salesPrice) {
    this.symbol = symbol;
    this.company = company;

    BigDecimal price = new BigDecimal(salesPrice);
    prices = new ArrayList<>();
    prices.add(price);
  }

  /**
   * Adds a new sales price to the list of prices.
   *
   * @param price The price you want to add.
   */
  public void addNewSalesPrice(String price) {
    BigDecimal newPrice = new BigDecimal(price);
    prices.add(newPrice);
  }

  public String getSymbol() {
    return symbol;
  }

  public String getCompany() {
    return company;
  }

  public BigDecimal getSalesPrice() {
    return prices.getLast();
  }

}

// ? - String salesPrice or BigDecimal?