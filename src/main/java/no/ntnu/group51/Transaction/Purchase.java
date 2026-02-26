package no.ntnu.group51.Transaction;

import no.ntnu.group51.Calculator.PurchaseCalculator;
import no.ntnu.group51.Player.Player;
import no.ntnu.group51.Share;

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

  @Override
  public void commit(Player player) {
    if (committed) {
      System.out.println("Purchase is already committed");

    } else if (player.getMoney().compareTo(share.getStock().getSalesPrice()) < 0) {
      System.out.println("Insufficient funds.");

    } else {
      player.withdrawMoney(share.getStock().getSalesPrice());
      player.getPortfolio().addShare(share);
      committed = true;
    }
  }
}
