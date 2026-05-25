package no.ntnu.group51.model.transaction;

import no.ntnu.group51.model.calculator.PurchaseCalculator;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Share;

/**
 * Class for a purchase transaction.
 */
public class Purchase extends Transaction {

  /**
   * Creates a purchase.
   *
   * @param share The share being purchased
   * @param week The week the transaction is happening
   */
  public Purchase(Share share, int week) {
    super(share, week, new PurchaseCalculator(share));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void commit(Player player) {
    if (committed) {
      System.out.println("Purchase is already committed");

    } else if (player.getMoney().compareTo(getTotal()) < 0) {
      System.out.println("Insufficient funds.");

    } else {
      player.withdrawMoney(getTotal());
      player.getPortfolio().addShare(share);
      committed = true;
    }
  }
}
