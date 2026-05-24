package no.ntnu.group51.model.transaction;

import no.ntnu.group51.model.calculator.SaleCalculator;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Share;

/**
 * Class for a sale transaction.
 */
public class Sale extends Transaction {

  /**
   * Creates a sale.
   *
   * @param share The share being sold
   * @param week The week the transaction is happening
   */
  public Sale(Share share, int week) {
    super(share, week, new SaleCalculator(share));
  }

  @Override
  public void commit(Player player) {
    if (committed) {
      System.out.println("Sale is already committed");

    } else if (player.getPortfolio()
        .getShares()
        .stream()
        .anyMatch(x -> x.equals(share))) {
      player.getPortfolio().removeShare(share);
      player.addMoney(getTotal());
      committed = true;

    } else {
      System.out.println("You don't own this share.");
    }
  }
}
