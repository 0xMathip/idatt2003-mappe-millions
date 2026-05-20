package no.ntnu.group51.model.portfolio;

import java.math.BigDecimal;
import java.util.*;

import no.ntnu.group51.model.calculator.SaleCalculator;
import no.ntnu.group51.model.stocks.Share;
import no.ntnu.group51.model.stocks.Stock;

/**
 * Portfolio class.
 */
public class Portfolio {
  private final Map<String, BigDecimal> shares;

  /**
   * Constructor for the Portfolio class.
   * Creates a map with intention to keep track of how many shares the player own.
   */
  public Portfolio() {
    this.shares = new HashMap<>();
  }

  /**
   * Either adds an entry into the map using the share -> stock -> symbol
   * and the quantity of shares, or add a value to the corresponding symbol
   * if the symbol exists.
   *
   * @param share the share you want to add.
   * @throws IllegalArgumentException if share is null.
   */
  public void addShare(Share share) {
    if (share == null) {
      throw new IllegalArgumentException("Share cannot be null.");
    }

    String symbol = share.getStock().getSymbol().toUpperCase();
    BigDecimal quantity = share.getQuantity();

    shares.merge(
        symbol,
        quantity,
        BigDecimal::add
    );
  }

  /**
   * Removes shares from the map. If the new quantity of shares after removing
   * is less than or equal to 0, it will also remove the entire entry from the map.
   *
   * @param share the share you want to remove.
   * @throws IllegalArgumentException if share is null.
   */
  public void removeShare(Share share) {
    if (share == null) {
      throw new IllegalArgumentException("Share cannot be null.");
    }

    String symbol = share.getStock().getSymbol().toUpperCase();
    BigDecimal quantity = share.getQuantity();

    shares.merge(
        symbol,
        quantity,
        BigDecimal::subtract
    );

    BigDecimal newQuantity = shares.get(symbol);

    if (newQuantity == null || shares.get(symbol).compareTo(BigDecimal.ZERO) <= 0) {
      shares.remove(symbol);
    }
  }

  /**
   * Creates an unmodifiable map of the shares in the portfolio.
   *
   * @return the unmodifiable map of the portfolio's shares.
   */
  public Map<String, BigDecimal> getShares() {
    return Collections.unmodifiableMap(shares);
  }

  /**
   * Checks if a share is in a portfolio.
   *
   * @param share the share you want to check in a portfolio
   * @return true if the portfolio contains the share, false if it does not.
   * @throws IllegalArgumentException if symbol is null.
   */
  public boolean contains(Share share) {
    if (share == null) {
      throw new IllegalArgumentException("Share cannot be null.");
    }
    String symbol = share.getStock().getSymbol().toUpperCase();

    return shares.containsKey(symbol);
  }
}
