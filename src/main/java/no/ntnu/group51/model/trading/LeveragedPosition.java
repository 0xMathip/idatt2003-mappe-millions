package no.ntnu.group51.model.trading;

import java.math.BigDecimal;
import no.ntnu.group51.model.stock.Share;

public class LeveragedPosition {
  private final Share share;
  private final Leverage leverage;
  private final BigDecimal marginRequired;
  private final BigDecimal exposure;
  private final BigDecimal liquidationPrice;

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

  public Share getShare() {
    return share;
  }

  public Leverage getLeverage() {
    return leverage;
  }

  public BigDecimal getMarginRequired() {
    return marginRequired;
  }

  public BigDecimal getExposure() {
    return exposure;
  }

  public BigDecimal getLiquidationPrice() {
    return liquidationPrice;
  }

  public boolean isLeveraged() {
    return leverage != Leverage.OFF;
  }
}
