package no.ntnu.group51.model.portfolio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import no.ntnu.group51.model.calculator.LeverageCalculator;
import no.ntnu.group51.model.calculator.SaleCalculator;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.trading.LeveragedPosition;

/**
 * Represents a player's portfolio of regular shares and leveraged positions.
 */
public class Portfolio {
  private final List<Share> shares;
  private final List<LeveragedPosition> leveragedPositions;

  /**
   * Creates an empty portfolio.
   */
  public Portfolio() {
    this.shares = new ArrayList<>();
    this.leveragedPositions = new ArrayList<>();
  }

  /**
   * Adds a share to the portfolio.
   *
   * @param share the share you want to add.
   * @return true if the share was added, false if it was not.
   * @throws IllegalArgumentException if share is null.
   */
  public boolean addShare(Share share) {
    if (share == null) {
      throw new IllegalArgumentException("Share cannot be null.");
    }

    for (Share existingShare : shares) {
      if (existingShare.getStock().equals(share.getStock())) {
        BigDecimal totalQuantity = existingShare.getQuantity().add(share.getQuantity());

        BigDecimal totalInvested = existingShare.getPurchasePrice()
            .multiply(existingShare.getQuantity())
            .add(share.getPurchasePrice().multiply(share.getQuantity()));

        BigDecimal averagePurchasePrice = totalInvested.divide(
            totalQuantity,
            8,
            RoundingMode.HALF_UP
        );

        existingShare.addQuantity(share.getQuantity());
        existingShare.setPurchasePrice(averagePurchasePrice);

        return true;
      }
    }

    return shares.add(share);
  }

  /**
   * Removes a share from the portfolio.
   *
   * @param share the share you want to remove.
   * @return true if the share was removed, false if it was not.
   * @throws IllegalArgumentException if share is null.
   */
  public boolean removeShare(Share share) {
    if (share == null) {
      throw new IllegalArgumentException("Share cannot be null.");
    }
    return shares.remove(share);
  }

  /**
   * Creates an unmodifiable list of the shares in the portfolio.
   *
   * @return the unmodifiable list of the portfolio's shares.
   */
  public List<Share> getShares() {
    return Collections.unmodifiableList(shares);
  }

  /**
   * Returns the shares associated with a specific symbol.
   * Searches through the portfolio list with the entered symbol,
   * and adds it to a list.
   *
   * @param symbol the symbol for a stock, i.e "AAPL".
   * @return a list of shares with the entered symbol.
   * @throws IllegalArgumentException if symbol is null.
   */
  public List<Share> getShares(String symbol) {
    if (symbol == null) {
      throw new IllegalArgumentException("Symbol cannot be null.");
    }
    return shares.stream()
        .filter(s -> s.getStock().getSymbol().equalsIgnoreCase(symbol))
        .toList();
  }

  /**
   * Checks if a share is in a portfolio.
   *
   * @param share the share you want to check in a portfolio
   * @return true if the portfolio contains the share, false if it does not.
   * @throws IllegalArgumentException if share is null
   */
  public boolean contains(Share share) {
    if (share == null) {
      throw new IllegalArgumentException("Share cannot be null.");
    }
    return shares.contains(share);
  }

  /**
   * Adds a leveraged position to the portfolio.
   *
   * @param leveragedPosition the leveraged position to add
   * @return true if the position was added
   * @throws IllegalArgumentException if leveragedPosition is null
   */
  public boolean addLeveragedPosition(LeveragedPosition leveragedPosition) {
    if (leveragedPosition == null) {
      throw new IllegalArgumentException("Leveraged position cannot be null.");
    }

    return leveragedPositions.add(leveragedPosition);
  }

  /**
   * Removes a leveraged position from the portfolio.
   *
   * @param leveragedPosition the leveraged position to remove
   * @return true if the position was removed
   * @throws IllegalArgumentException if leveragedPosition is null
   */
  public boolean removeLeveragedPosition(LeveragedPosition leveragedPosition) {
    if (leveragedPosition == null) {
      throw new IllegalArgumentException("Leveraged position cannot be null.");
    }

    return leveragedPositions.remove(leveragedPosition);
  }

  /**
   * Returns an unmodifiable list of leveraged positions in the portfolio.
   *
   * @return the leveraged positions in the portfolio
   */
  public List<LeveragedPosition> getLeveragedPositions() {
    return Collections.unmodifiableList(leveragedPositions);
  }

  /**
   * Calculates the total net worth of the portfolio.
   *
   * <p>The net worth includes the sale value of regular shares
   * and the current value of leveraged positions.
   *
   * @return the total portfolio net worth
   */
  public BigDecimal getPortfolioNetWorth() {
    BigDecimal netWorth = BigDecimal.ZERO;

    for (Share share : getShares()) {
      SaleCalculator saleCalc = new SaleCalculator(share);
      netWorth = netWorth.add(saleCalc.calculateTotal());
    }

    for (LeveragedPosition leveragedPosition : getLeveragedPositions()) {
      LeverageCalculator levCalc = new LeverageCalculator(leveragedPosition);
      netWorth = netWorth.add(levCalc.calculateTotal());
    }
    return netWorth;
  }

  /**
   * Returns the number of regular share positions in the portfolio.
   *
   * @return the number of regular share positions
   */
  public int size() {
    return shares.size();
  }
}
