package no.ntnu.group51.model.transaction;

import java.math.BigDecimal;
import java.util.List;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Share;

/**
 * Represents a forced sale caused by liquidation of a leveraged position.
 */
public class Liquidation extends Sale {

  /**
   * Creates a liquidation transaction.
   *
   * @param share the share being liquidated
   * @param week  the current trading week
   */
  public Liquidation(Share share, int week) {
    super(share, week);
  }

  /**
   * Commits the liquidation transaction by removing the liquidated shares
   * from the player's portfolio and crediting the proceeds.
   *
   * @param player the player whose leveraged position is being liquidated
   * @throws IllegalArgumentException if player is null
   * @throws IllegalStateException    if the transaction has already been committed
   *                                  or the player does not own the required shares
   */
  @Override
  public void commit(Player player) {
    if (committed) {
      throw new IllegalStateException("Liquidation is already committed");
    }

    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }

    List<Share> sharesOfStock = player.getPortfolio().getShares(share.getStock().getSymbol());

    if (sharesOfStock.isEmpty()) {
      throw new IllegalStateException("You don't own any shares of " + share.getStock().getSymbol());
    }

    Share portfolioShare = sharesOfStock.get(0);

    if (portfolioShare.getQuantity().compareTo(share.getQuantity()) >= 0) {
      player.getPortfolio().removeShare(portfolioShare);
      player.addMoney(share.getStock().getSalesPrice().multiply(share.getQuantity()));

      BigDecimal remaining = portfolioShare.getQuantity().subtract(share.getQuantity());
      if (remaining.compareTo(BigDecimal.ZERO) > 0) {
        Share remainingShare =
            new Share(share.getStock(), remaining, portfolioShare.getPurchasePrice());
        player.getPortfolio().addShare(remainingShare);
      }

      committed = true;
    } else {
      throw new IllegalStateException(
          "You don't own " + share.getQuantity()
              + " shares of " + share.getStock().getSymbol()
      );
    }
  }
}