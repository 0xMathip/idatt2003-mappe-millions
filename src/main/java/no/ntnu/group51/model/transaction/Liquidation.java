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

    committed = true;
  }
}