package no.ntnu.group51.Stocks;

import java.math.BigDecimal;

/**
 * Share class.
 */
public class Share {
  private final Stock stock;
  private BigDecimal quantity;
  private BigDecimal purchasePrice;

  /**
   * Constructor for the Share class.
   *
   * @param stock         the purchased stock.
   * @param quantity      the amount of shares purchased.
   * @param purchasePrice the purchase price of the share.
   * @throws IllegalArgumentException if stock is null,
   *                                  if quantity is null, zero or negative,
   *                                  if purchasePrice is null or negative.
   */
  public Share(Stock stock, BigDecimal quantity, BigDecimal purchasePrice) {
    if (stock == null) {
      throw new IllegalArgumentException("Stock cannot be null");
    }
    if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Quantity must be positive.");
    }
    if (purchasePrice == null || purchasePrice.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Purchase price cannot be negative.");
    }
    this.stock = stock;
    this.quantity = quantity;
    this.purchasePrice = purchasePrice;
  }

  /**
   * Returns the stock associated with a share object.
   *
   * @return the stock instance.
   */
  public Stock getStock() {
    return stock;
  }

  /**
   * Returns the number of shares owned.
   *
   * @return the quantity of shares.
   */
  public BigDecimal getQuantity() {
    return quantity;
  }

  /**
   * Returns the purchase price per share.
   *
   * @return the purchase price.
   */
  public BigDecimal getPurchasePrice() {
    return purchasePrice;
  }
}
