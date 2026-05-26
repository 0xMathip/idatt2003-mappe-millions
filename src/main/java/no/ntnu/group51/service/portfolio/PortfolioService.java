package no.ntnu.group51.service.portfolio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import no.ntnu.group51.model.player.Player;

/**
 * Creates portfolio summaries for presentation and UI use.
 *
 * <p>Calculates portfolio value, net worth, available cash,
 * invested capital, and total return metrics.
 */
public class PortfolioService {

  private static final int MONEY_SCALE = 2;
  private static final int PERCENT_SCALE = 2;
  private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

  private final PositionService positionService;

  /**
   * Creates a portfolio service.
   *
   * @param positionService the position service used to generate position summaries
   * @throws IllegalArgumentException if positionService is null
   */
  public PortfolioService(PositionService positionService) {
    if (positionService == null) {
      throw new IllegalArgumentException("Position service cannot be null.");
    }
    this.positionService = positionService;
  }

  /**
   * Creates a portfolio summary for the given player.
   *
   * @param player the player whose portfolio should be summarized
   * @return a portfolio summary containing calculated portfolio metrics
   * @throws IllegalArgumentException if player is null
   */
  public PortfolioSummary createPortfolioSummary(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }

    List<PositionSummary> positions =
        positionService.createPositionSummaries(player.getPortfolio());

    BigDecimal portfolioValue =
        player.getPortfolio().getPortfolioNetWorth().setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    BigDecimal netWorth =
        player.getNetWorth().setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    BigDecimal availableCash =
        player.getMoney().setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    BigDecimal totalInvested = calculateTotalInvested(positions);

    BigDecimal startingMoney =
        player.getStartingMoney().setScale(MONEY_SCALE, RoundingMode.HALF_UP);

    BigDecimal totalReturn = calculateTotalReturn(netWorth, startingMoney);
    BigDecimal totalReturnPercent = calculateTotalReturnPercent(totalReturn, startingMoney);

    return new PortfolioSummary(
        portfolioValue,
        netWorth,
        availableCash,
        totalInvested,
        totalReturn,
        totalReturnPercent
    );
  }

  private BigDecimal calculateTotalInvested(List<PositionSummary> positions) {
    return positions
        .stream()
        .map(PositionSummary::totalInvested)
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .setScale(MONEY_SCALE, RoundingMode.HALF_UP);
  }

  private BigDecimal calculateTotalReturn(BigDecimal currentValue, BigDecimal baselineValue) {
    return currentValue
        .subtract(baselineValue)
        .setScale(MONEY_SCALE, RoundingMode.HALF_UP);
  }

  private BigDecimal calculateTotalReturnPercent(BigDecimal totalReturn, BigDecimal baselineValue) {
    if (baselineValue.compareTo(BigDecimal.ZERO) == 0) {
      return BigDecimal.ZERO;
    }

    return totalReturn
        .divide(baselineValue, PERCENT_SCALE + 2, RoundingMode.HALF_UP)
        .multiply(ONE_HUNDRED)
        .setScale(PERCENT_SCALE, RoundingMode.HALF_UP);
  }
}
