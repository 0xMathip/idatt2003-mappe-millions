package no.ntnu.group51.service.portfolio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import no.ntnu.group51.model.calculator.LeverageCalculator;
import no.ntnu.group51.model.portfolio.Portfolio;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.trading.Leverage;
import no.ntnu.group51.model.trading.LeveragedPosition;

/**
 * Creates summarized portfolio position data for UI presentation.
 *
 * <p>Supports both regular stock positions and leveraged positions.
 */
public class PositionService {

  private static final int MONEY_SCALE = 2;
  private static final int PERCENT_SCALE = 2;
  private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

  /**
   * Creates summaries for all positions in a portfolio.
   *
   * <p>Includes both regular share positions and leveraged positions.
   *
   * @param portfolio the portfolio to summarize
   * @return a list of position summaries
   * @throws IllegalArgumentException if portfolio is null
   */
  public List<PositionSummary> createPositionSummaries(Portfolio portfolio) {
    if (portfolio == null) {
      throw new IllegalArgumentException("Portfolio cannot be null.");
    }

    Map<Stock, List<Share>> sharesByStock = portfolio.getShares().stream()
        .collect(Collectors.groupingBy(Share::getStock));

    List<PositionSummary> summaries = new ArrayList<>();

    sharesByStock.forEach((stock, shares) ->
        summaries.add(createPositionSummary(stock, shares)));

    portfolio.getLeveragedPositions().forEach(position ->
        summaries.add(createLeveragedPositionSummary(position))
    );

    return summaries;
  }

  /**
   * Creates a summary for a regular stock position.
   *
   * @param stock the stock being summarized
   * @param shares the owned shares for that stock
   * @return a position summary
   * @throws IllegalArgumentException if stock is null, or shares is null or empty
   */
  public PositionSummary createPositionSummary(Stock stock, List<Share> shares) {
    if (stock == null) {
      throw new IllegalArgumentException("Stock cannot be null.");
    }

    if (shares == null || shares.isEmpty()) {
      throw new IllegalArgumentException("Shares cannot be null or empty.");
    }

    BigDecimal sharesOwned = calculateSharesOwned(shares);
    BigDecimal totalInvested = calculateTotalInvested(shares);
    BigDecimal averageBuyPrice = calculateAverageBuyPrice(totalInvested, sharesOwned);
    BigDecimal currentPrice = stock.getSalesPrice();
    BigDecimal positionValue =
        currentPrice.multiply(sharesOwned).setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    BigDecimal profitLoss =
        positionValue.subtract(totalInvested).setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    BigDecimal roiPercent = calculateRoiPercent(profitLoss, totalInvested);

    return new PositionSummary(
        stock,
        sharesOwned,
        averageBuyPrice,
        currentPrice,
        positionValue,
        totalInvested,
        profitLoss,
        roiPercent,
        stock.getLowestPrice(),
        stock.getHighestPrice(),
        false,
        Leverage.OFF,
        BigDecimal.ZERO,
        BigDecimal.ZERO
    );
  }

  private PositionSummary createLeveragedPositionSummary(LeveragedPosition position) {
    if (position == null) {
      throw new IllegalArgumentException("Leveraged position cannot be null.");
    }

    Share share = position.getShare();
    Stock stock = share.getStock();

    LeverageCalculator calculator = new LeverageCalculator(position);

    BigDecimal sharesOwned = share.getQuantity();
    BigDecimal totalInvested = position.getMarginRequired();
    BigDecimal averageBuyPrice = share.getPurchasePrice();
    BigDecimal currentPrice = stock.getSalesPrice();

    BigDecimal positionValue =
        calculator.calculateTotal().setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    BigDecimal profitLoss =
        positionValue.subtract(totalInvested).setScale(MONEY_SCALE, RoundingMode.HALF_UP);

    BigDecimal roiPercent = calculateRoiPercent(profitLoss, totalInvested);

    return new PositionSummary(
        stock,
        sharesOwned,
        averageBuyPrice,
        currentPrice,
        positionValue,
        totalInvested,
        profitLoss,
        roiPercent,
        stock.getLowestPrice(),
        stock.getHighestPrice(),
        true,
        position.getLeverage(),
        position.getMarginRequired(),
        position.getLiquidationPrice()
    );
  }

  private BigDecimal calculateSharesOwned(List<Share> shares) {
    return shares
        .stream()
        .map(Share::getQuantity)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private BigDecimal calculateTotalInvested(List<Share> shares) {
    return shares
        .stream()
        .map(share -> share.getPurchasePrice().multiply(share.getQuantity()))
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .setScale(MONEY_SCALE, RoundingMode.HALF_UP);
  }

  private BigDecimal calculateAverageBuyPrice(BigDecimal totalInvested, BigDecimal sharesOwned) {
    if (sharesOwned.compareTo(BigDecimal.ZERO) == 0) {
      return BigDecimal.ZERO;
    }

    return totalInvested
        .divide(sharesOwned, MONEY_SCALE, RoundingMode.HALF_UP);
  }

  private BigDecimal calculateRoiPercent(BigDecimal profitLoss, BigDecimal totalInvested) {
    if (totalInvested.compareTo(BigDecimal.ZERO) == 0) {
      return BigDecimal.ZERO;
    }

    return profitLoss
        .divide(totalInvested, PERCENT_SCALE + 2, RoundingMode.HALF_UP)
        .multiply(ONE_HUNDRED)
        .setScale(PERCENT_SCALE, RoundingMode.HALF_UP);
  }
}
