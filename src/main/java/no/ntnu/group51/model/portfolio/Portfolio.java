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
 * Portfolio class.
 */
public class Portfolio {
  private final List<Share> shares;
  private final List<LeveragedPosition> leveragedPositions;

  /**
   * Constructor for the Portfolio class.
   * Creates a list with intention to add shares.
   */
  public Portfolio() {
    this.shares = new ArrayList<>();
    this.leveragedPositions = new ArrayList<>();
  }

  /**
   * Adds a share in the Portfolio-list.
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
   * Removes a share in the Portfolio-list.
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
   * @throws IllegalArgumentException if symbol is null.
   */
  public boolean contains(Share share) {
    if (share == null) {
      throw new IllegalArgumentException("Share cannot be null.");
    }
    return shares.contains(share);
  }

  public boolean addLeveragedPosition(LeveragedPosition leveragedPosition) {
    if (leveragedPosition == null) {
      throw new IllegalArgumentException("Leveraged position cannot be null.");
    }

    return leveragedPositions.add(leveragedPosition);
  }

  public boolean removeLeveragedPosition(LeveragedPosition leveragedPosition) {
    if (leveragedPosition == null) {
      throw new IllegalArgumentException("Leveraged position cannot be null.");
    }

    return leveragedPositions.remove(leveragedPosition);
  }

  public List<LeveragedPosition> getLeveragedPositions() {
    return Collections.unmodifiableList(leveragedPositions);
  }

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
}
