package no.ntnu.group51.model.transaction;

import no.ntnu.group51.model.calculator.PurchaseCalculator;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stocks.Share;
import no.ntnu.group51.model.xp.XpCalc;

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
    if (share == null) {
      throw new IllegalArgumentException("share is null");
    }

    if (week <= 0) {
      throw new IllegalArgumentException("week is 0 or negative");
    }

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
