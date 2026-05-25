package no.ntnu.group51.service.portfolio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import no.ntnu.group51.model.player.Player;

public class PortfolioService {

  private static final int MONEY_SCALE = 2;
  private static final int PERCENT_SCALE = 2;
  private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

  private final PositionService positionService;

  public PortfolioService(PositionService positionService) {
    if (positionService == null) {
      throw new IllegalArgumentException("Position service cannot be null.");
    }
    this.positionService = positionService;
  }

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

  private BigDecimal calculateTotalReturn(BigDecimal portfolioValue, BigDecimal totalInvested) {
    return portfolioValue
        .subtract(totalInvested)
        .setScale(MONEY_SCALE, RoundingMode.HALF_UP);
  }

  private BigDecimal calculateTotalReturnPercent(BigDecimal totalReturn, BigDecimal totalInvested) {
    if (totalInvested.compareTo(BigDecimal.ZERO) == 0) {
      return BigDecimal.ZERO;
    }

    return totalReturn
        .divide(totalInvested, PERCENT_SCALE + 2, RoundingMode.HALF_UP)
        .multiply(ONE_HUNDRED)
        .setScale(PERCENT_SCALE, RoundingMode.HALF_UP);
  }
}
