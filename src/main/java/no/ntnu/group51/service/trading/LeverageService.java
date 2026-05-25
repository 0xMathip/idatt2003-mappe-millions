package no.ntnu.group51.service.trading;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import no.ntnu.group51.model.portfolio.Portfolio;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.trading.Leverage;
import no.ntnu.group51.model.trading.LeveragedPosition;

/**
 * Handles calculations and validation for leveraged trading.
 *
 * <p>Provides leverage summaries, exposure calculations,
 * liquidation price calculations, and liquidation checks.
 */
public class LeverageService {

  private static final int CALCULATION_SCALE = 8;

  /**
   * Creates a leverage summary for a potential leveraged trade.
   *
   * @param stock the stock being traded
   * @param marginRequired the required margin for the trade
   * @param leverage the selected leverage level
   * @return a leverage summary
   * @throws IllegalArgumentException if arguments are invalid
   */
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

  /**
   * Returns the multiplier for a leverage level.
   *
   * @param leverage the leverage level
   * @return the leverage multiplier
   * @throws IllegalArgumentException if leverage is null
   */
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

  /**
   * Calculates total market exposure from margin and leverage multiplier.
   *
   * @param marginRequired the required margin
   * @param multiplier the leverage multiplier
   * @return the total exposure
   * @throws IllegalArgumentException if arguments are null
   */
  public BigDecimal calculateExposure(BigDecimal marginRequired, BigDecimal multiplier) {
    if (marginRequired == null) {
      throw new IllegalArgumentException("Margin required cannot be null.");
    }
    if (multiplier == null) {
      throw new IllegalArgumentException("Multiplier cannot be null.");
    }

    return marginRequired.multiply(multiplier);
  }

  /**
   * Calculates the liquidation price for a leveraged position.
   *
   * @param stock the stock being traded
   * @param multiplier the leverage multiplier
   * @param leverage the leverage level
   * @return the liquidation price, or zero if leverage is off
   * @throws IllegalArgumentException if arguments are null
   */
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

  /**
   * Checks whether a leveraged position has reached liquidation.
   *
   * @param position the leveraged position
   * @return true if the position is liquidated, false otherwise
   * @throws IllegalArgumentException if position is null
   */
  public boolean isLiquidated(LeveragedPosition position) {
    if (position == null) {
      throw new IllegalArgumentException("Leveraged position cannot be null.");
    }

    return position.getShare()
        .getStock()
        .getSalesPrice()
        .compareTo(position.getLiquidationPrice()) <= 0;
  }

  /**
   * Finds all liquidated leveraged positions in a portfolio.
   *
   * @param portfolio the portfolio to inspect
   * @return a list of liquidated positions
   * @throws IllegalArgumentException if portfolio is null
   */
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
