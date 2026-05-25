package no.ntnu.group51.model.transaction;

import java.math.BigDecimal;
import no.ntnu.group51.model.calculator.SaleCalculator;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Share;

/**
 * Represents a stock sale transaction.
 */
public class Sale extends Transaction {

  /**
   * Creates a sale transaction.
   *
   * @param share the share being sold
   * @param week  the trading week when the transaction occurs
   */
  public Sale(Share share, int week) {
    super(share, week, new SaleCalculator(share));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void commit(Player player) {
    if (committed) {
      throw new IllegalStateException("Sale already committed.");
    }
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }

    Share ownedShare = player.getPortfolio()
        .getShares()
        .stream()
        .filter(s -> s.getStock().equals(share.getStock()))
        .filter(s -> s.getQuantity().compareTo(share.getQuantity()) >= 0)
        .findFirst()
        .orElse(null);

    if (ownedShare == null) {
      throw new IllegalArgumentException("You don't own " + share.getQuantity()
          + " shares of " + share.getStock().getSymbol() + ".");
    }

    player.getPortfolio().removeShare(ownedShare);
    BigDecimal remainingQuantity = ownedShare.getQuantity()
        .subtract(share.getQuantity());

    if (remainingQuantity.compareTo(BigDecimal.ZERO) > 0) {
      Share remainingShare = new Share(
          ownedShare.getStock(),
          remainingQuantity,
          ownedShare.getPurchasePrice()
      );
      player.getPortfolio().addShare(remainingShare);
    }

    player.addMoney(getTotal());
    committed = true;
  }
}
