package no.ntnu.group51.model.stock;

import java.math.BigDecimal;

/**
 * Represents an owned position in a stock, including quantity and purchase price.
 */
public class Share {
  private final Stock stock;
  private BigDecimal quantity;
  private BigDecimal purchasePrice;

  /**
   * Constructor for the Share class.
   *
   * @param stock         the stock associated with this position.
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
   * Adds additional shares to this position.
   *
   * @param additionalQuantity the quantity to add
   * @throws IllegalArgumentException if the quantity is null, zero, or negative
   */
  public void addQuantity(BigDecimal additionalQuantity) {
    if (additionalQuantity == null || additionalQuantity.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Additional quantity must be positive.");
    }

    this.quantity = this.quantity.add(additionalQuantity);
  }

  /**
   * Updates the purchase price per share.
   *
   * @param purchasePrice the new purchase price
   * @throws IllegalArgumentException if the price is null or negative
   */
  public void setPurchasePrice(BigDecimal purchasePrice) {
    if (purchasePrice == null || purchasePrice.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Purchase price cannot be null or negative.");
    }

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
