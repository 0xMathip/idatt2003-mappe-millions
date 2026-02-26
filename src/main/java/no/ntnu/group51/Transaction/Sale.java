package no.ntnu.group51.Transaction;

import no.ntnu.group51.Calculator.SaleCalculator;
import no.ntnu.group51.Player.Player;
import no.ntnu.group51.Stocks.Share;

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
      System.out.println("Purchase is already committed");

    } else if (player.getPortfolio().getShares().stream().anyMatch(x -> x.equals(share))) {
      player.getPortfolio().removeShare(share);
      player.addMoney(share.getStock().getSalesPrice());
      committed = true;

    } else {
      System.out.println("You don't own this share.");
    }
  }
}
