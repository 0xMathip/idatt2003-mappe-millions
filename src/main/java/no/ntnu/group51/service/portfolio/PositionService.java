package no.ntnu.group51.service.portfolio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import no.ntnu.group51.model.portfolio.Portfolio;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;

public class PositionService {

  private static final int MONEY_SCALE = 2;
  private static final int PERCENT_SCALE = 2;
  private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

  public List<PositionSummary> createPositionSummaries(Portfolio portfolio) {
    if (portfolio == null) {
      throw new IllegalArgumentException("Portfolio cannot be null.");
    }

    Map<Stock, List<Share>> sharesByStock = portfolio.getShares().stream()
        .collect(Collectors.groupingBy(Share::getStock));

    List<PositionSummary> summaries = new ArrayList<>();

    sharesByStock.forEach((stock, shares) ->
        summaries.add(createPositionSummary(stock, shares)));

    return summaries;
  }

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
        stock.getHighestPrice()
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
