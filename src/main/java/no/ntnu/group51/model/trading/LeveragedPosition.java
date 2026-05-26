package no.ntnu.group51.model.trading;

import java.math.BigDecimal;
import no.ntnu.group51.model.stock.Share;

/**
 * Represents an active leveraged trading position.
 *
 * <p>Stores the purchased share, leverage level, required margin,
 * total market exposure, and liquidation price.
 */
public class LeveragedPosition {
  private final Share share;
  private final Leverage leverage;
  private final BigDecimal marginRequired;
  private final BigDecimal exposure;
  private final BigDecimal liquidationPrice;

  /**
   * Creates a leveraged position.
   *
   * @param share the underlying share position
   * @param leverage the leverage level
   * @param marginRequired the capital required to open the position
   * @param exposure the total leveraged market exposure
   * @param liquidationPrice the price at which the position is liquidated
   * @throws IllegalArgumentException if any argument is null or if numeric values are negative
   */
  public LeveragedPosition(
      Share share,
      Leverage leverage,
      BigDecimal marginRequired,
      BigDecimal exposure,
      BigDecimal liquidationPrice
  ) {
    if (share == null) {
      throw new IllegalArgumentException("Share cannot be null.");
    }
    if (leverage == null) {
      throw new IllegalArgumentException("Leverage cannot be null.");
    }
    if (marginRequired == null || marginRequired.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Margin required cannot be null or negative.");
    }
    if (exposure == null || exposure.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Exposure cannot be null or negative.");
    }
    if (liquidationPrice == null || liquidationPrice.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Liquidation price cannot be null or negative.");
    }

    this.share = share;
    this.leverage = leverage;
    this.marginRequired = marginRequired;
    this.exposure = exposure;
    this.liquidationPrice = liquidationPrice;
  }

  /**
   * Returns the underlying share.
   *
   * @return the share
   */
  public Share getShare() {
    return share;
  }

  /**
   * Returns the leverage level for this position.
   *
   * @return the leverage level
   */
  public Leverage getLeverage() {
    return leverage;
  }

  /**
   * Returns the margin required for the leveraged position.
   *
   * @return the required margin
   */
  public BigDecimal getMarginRequired() {
    return marginRequired;
  }

  /**
   * Returns the asset exposure.
   *
   * @return the exposure
   */
  public BigDecimal getExposure() {
    return exposure;
  }

  /**
   * Returns the liquidation price.
   *
   * @return the liquidation price
   */
  public BigDecimal getLiquidationPrice() {
    return liquidationPrice;
  }

  /**
   * Checks whether this position uses leverage.
   *
   * @return true if leverage is enabled, false otherwise
   */
  public boolean isLeveraged() {
    return leverage != Leverage.OFF;
  }
}
