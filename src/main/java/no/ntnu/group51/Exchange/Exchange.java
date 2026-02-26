package no.ntnu.group51.Exchange;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import no.ntnu.group51.Player.Player;
import no.ntnu.group51.Stocks.Share;
import no.ntnu.group51.Stocks.Stock;
import no.ntnu.group51.Transaction.Purchase;
import no.ntnu.group51.Transaction.Sale;
import no.ntnu.group51.Transaction.Transaction;

/**
 * Represents a stock exchange.
 *
 * <p>Responsible for holding all available stocks,
 * handling purchases and sales, and advancing the market week by week.
 *
 * <p>Stocks are stored in a map using their symbol as key.
 * ALl transactions are executed through Transaction objects.
 */
public class Exchange {
  private final String name;
  private int week;
  private final Map<String, Stock> stockMap;
  private final Random random;

  /**
   * Creates a new Exchange.
   *
   * <p>The exchange is initialized with a name and a list of available stocks.
   * The starting week is set to 1.
   *
   * @param name   the name of the exchange.
   * @param stocks the list of stocks available.
   * @throws IllegalArgumentException if the name or stocks is null,
   *                                  or if the stock list contains null.
   */
  public Exchange(String name, List<Stock> stocks) {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }
    if (stocks == null) {
      throw new IllegalArgumentException("Stocks cannot be null.");
    }
    this.name = name;
    this.week = 1;
    this.random = new Random();
    this.stockMap = new HashMap<>();

    for (Stock stock : stocks) {
      if (stock == null) {
        throw new IllegalArgumentException("Stock list cannot contain null.");
      }
      stockMap.put(stock.getSymbol().toUpperCase(), stock);
    }
  }

  /**
   * Returns the name of the exchange.
   *
   * @return the name of the exchange
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the current week of the exchange.
   *
   * <p>The week increases each time advance() is called.
   *
   * @return the current week number
   */
  public int getWeek() {
    return week;
  }

  /**
   * Checks if a stock with the entered symbol exist on the exchange.
   *
   * <p>The symbol-search is case-insensitive.
   *
   * @param symbol the stock symbol to check
   * @return true if the stock exists on the exchange, false if not.
   * @throws IllegalArgumentException if symbol is null.
   */
  public boolean hasStock(String symbol) {
    if (symbol == null) {
      throw new IllegalArgumentException("Symbol cannot be null.");
    }
    return stockMap.containsKey(symbol.toUpperCase());
  }

  /**
   * Returns the stock associated with the entered symbol.
   *
   * <p>The symbol-search is case-insensitive.
   *
   * @param symbol the stock symbol
   * @return the Stock object matching the symbol
   * @throws IllegalArgumentException if symbol is null
   * @throws IllegalArgumentException if no stock with the entered symbol exists
   */
  public Stock getStock(String symbol) {
    if (symbol == null) {
      throw new IllegalArgumentException("Symbol cannot be null.");
    }
    Stock stock = stockMap.get(symbol.toUpperCase());
    if (stock == null) {
      throw new IllegalArgumentException("Stock not found: " + symbol);
    }
    return stock;
  }

  /**
   * Searches for stocks that match the entered search term.
   *
   * <p>A stock matches if the search term is contained in either
   * the stock symbol or the company name. The search is case-insensitive.
   *
   * @param searchTerm the term to search for
   * @return a list of stocks matching the search term
   * @throws IllegalArgumentException if searchTerm is null
   */
  public List<Stock> findStocks(String searchTerm) {
    if (searchTerm == null) {
      throw new IllegalArgumentException("Search term cannot be null.");
    }
    String term = searchTerm.toLowerCase();
    return stockMap.values().stream().filter(s -> s.getSymbol().toLowerCase().contains(term)
        || s.getCompany().toLowerCase().contains(term)).collect(Collectors.toList());
  }

  /**
   * Buys a given quantity of a stock for a player.
   *
   * <p>A new Share is created with the current sales price of the stock.
   * The purchase is executed through a Purchase transaction which is committed.
   *
   * @param symbol   the stock symbol to buy
   * @param quantity the number of shares to purchase
   * @param player   the player making the purchase
   * @return the completed Transaction
   * @throws IllegalArgumentException if symbol or player is null
   * @throws IllegalArgumentException if quantity is null, zero or negative
   * @throws IllegalArgumentException if the stock does not exist
   */
  public Transaction buy(String symbol, BigDecimal quantity, Player player) {
    if (symbol == null) {
      throw new IllegalArgumentException("Symbol cannot be null");
    }
    if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Quantity cannot be null, zero or negative.");
    }
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }

    Stock stock = getStock(symbol);
    BigDecimal purchasePricePerShare = stock.getSalesPrice();

    Share purchasedShare = new Share(stock, quantity, purchasePricePerShare);
    Transaction transaction = new Purchase(purchasedShare, week);
    transaction.commit(player);

    return transaction;
  }

  /**
   * Sells a share owned by a player.
   *
   * <p>The sale is executed through a Sale transaction which is committed.
   *
   * @param share  the share to sell
   * @param player the player executing the sale
   * @return the completed Transaction
   * @throws IllegalArgumentException if share or player is null
   */
  public Transaction sell(Share share, Player player) {
    if (share == null) {
      throw new IllegalArgumentException("Share cannot be null.");
    }
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }

    Transaction transaction = new Sale(share, week);
    transaction.commit(player);

    return transaction;
  }

  /**
   * Advances the exchange by one week.
   *
   * <p>The week counter is incremented,
   * each stock on the exchange gets a new sales price based
   * on a random percentage change.
   *
   * <p>The price will never drop below a minimum value.
   */
  public void advance() {
    week++;

    for (Stock stock : stockMap.values()) {
      BigDecimal currentPrice = stock.getSalesPrice();
      double change = (random.nextDouble() * 0.10) - 0.05;

      BigDecimal fac = BigDecimal.valueOf(1.0 + change);
      BigDecimal updatedPrice = currentPrice.multiply(fac);

      BigDecimal min = new BigDecimal("0.01");
      if (updatedPrice.compareTo(min) < 0) {
        updatedPrice = min;
      }
      stock.addNewSalesPrice(updatedPrice.toString());
    }
  }
}