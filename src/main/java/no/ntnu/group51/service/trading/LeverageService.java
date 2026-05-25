package no.ntnu.group51.service.trading;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import no.ntnu.group51.model.portfolio.Portfolio;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.trading.Leverage;
import no.ntnu.group51.model.trading.LeveragedPosition;

public class LeverageService {

  private static final int CALCULATION_SCALE = 8;

  public LeverageSummary createSummary(
      Stock stock,
      BigDecimal marginRequired,
      Leverage leverage
  ) {
    if (stock == null) {
      throw new IllegalArgumentException("Stock cannot be null.");
    }
    if (marginRequired == null) {
      throw new IllegalArgumentException("Margin required cannot be null.");
    }
    if (leverage == null) {
      throw new IllegalArgumentException("Leverage cannot be null.");
    }
    if (marginRequired.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Margin required cannot be negative.");
    }

    BigDecimal multiplier = getMultiplier(leverage);
    BigDecimal exposure = calculateExposure(marginRequired, multiplier);
    BigDecimal liquidationPrice = calculateLiquidationPrice(stock, multiplier, leverage);

    return new LeverageSummary(
        leverage,
        multiplier,
        marginRequired,
        exposure,
        liquidationPrice
    );
  }

  public BigDecimal getMultiplier(Leverage leverage) {
    if (leverage == null) {
      throw new IllegalArgumentException("Leverage cannot be null.");
    }

    return switch (leverage) {
      case OFF -> BigDecimal.ONE;
      case X5 -> BigDecimal.valueOf(5);
      case X10 -> BigDecimal.valueOf(10);
      case X20 -> BigDecimal.valueOf(20);
    };
  }

  public BigDecimal calculateExposure(BigDecimal marginRequired, BigDecimal multiplier) {
    if (marginRequired == null) {
      throw new IllegalArgumentException("Margin required cannot be null.");
    }
    if (multiplier == null) {
      throw new IllegalArgumentException("Multiplier cannot be null.");
    }

    return marginRequired.multiply(multiplier);
  }

  public BigDecimal calculateLiquidationPrice(
      Stock stock,
      BigDecimal multiplier,
      Leverage leverage
  ) {
    if (stock == null) {
      throw new IllegalArgumentException("Stock cannot be null.");
    }
    if (multiplier == null) {
      throw new IllegalArgumentException("Multiplier cannot be null.");
    }
    if (leverage == null) {
      throw new IllegalArgumentException("Leverage cannot be null.");
    }

    if (leverage == Leverage.OFF) {
      return BigDecimal.ZERO;
    }

    BigDecimal entryPrice = stock.getSalesPrice();

    BigDecimal liquidationFactor = BigDecimal.ONE.subtract(
        BigDecimal.ONE.divide(multiplier, CALCULATION_SCALE, RoundingMode.HALF_UP)
    );

    return entryPrice.multiply(liquidationFactor);
  }

  public boolean isLiquidated(LeveragedPosition position) {
    if (position == null) {
      throw new IllegalArgumentException("Leveraged position cannot be null.");
    }

    return position.getShare()
        .getStock()
        .getSalesPrice()
        .compareTo(position.getLiquidationPrice()) <= 0;
  }

  public List<LeveragedPosition> findLiquidatedPositions(Portfolio portfolio) {
    if (portfolio == null) {
      throw new IllegalArgumentException("Portfolio cannot be null.");
    }

    return portfolio.getLeveragedPositions()
        .stream()
        .filter(this::isLiquidated)
        .toList();
  }

}
