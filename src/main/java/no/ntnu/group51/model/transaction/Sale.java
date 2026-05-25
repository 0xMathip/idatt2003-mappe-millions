package no.ntnu.group51.model.transaction;

import java.math.BigDecimal;
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
    Share ownedShare = player.getPortfolio()
        .getShares()
        .stream()
        .filter(s -> s.getStock().equals(share.getStock()))
        .filter(s -> s.getQuantity().compareTo(share.getQuantity()) >= 0)
        .findFirst()
        .orElse(null);

    if (committed) {
      System.out.println("Sale is already committed");

    } else if (ownedShare != null) {
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

    } else {
      System.out.println("You don't own this share.");
    }
  }
}
