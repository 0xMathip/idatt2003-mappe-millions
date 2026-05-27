package no.ntnu.group51.service.trading;

import java.util.List;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.trading.LeveragedPosition;
import no.ntnu.group51.model.transaction.Liquidation;
import no.ntnu.group51.model.transaction.Transaction;

/**
 * Handles automatic liquidation of leveraged positions.
 */

public class LiquidationService {

  private final LeverageService leverageService;

  /**
   * Creates a liquidation service.
   *
   * @param leverageService the leverage service used to detect liquidations
   * @throws IllegalArgumentException if leverageService is null
   */
  public LiquidationService(LeverageService leverageService) {
    if (leverageService == null) {
      throw new IllegalArgumentException("Leverage service cannot be null.");
    }

    this.leverageService = leverageService;
  }

  /**
   * Checks a player's portfolio for liquidated leveraged positions.
   *
   * <p>Any liquidated positions are removed from the portfolio
   * and recorded in the transaction archive.
   *
   * @param player the player whose portfolio should be checked
   * @param week the current trading week
   * @throws IllegalArgumentException if player is null or week is not positive
   */
  public void checkLiquidations(Player player, int week) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    if (week <= 0) {
      throw new IllegalArgumentException("Week must be positive.");
    }

    List<LeveragedPosition> liquidatedPositions =
        leverageService.findLiquidatedPositions(player.getPortfolio());

    liquidatedPositions.forEach(position -> liquidate(player, position, week));
  }

  private void liquidate(Player player, LeveragedPosition position, int week) {
    Transaction liquidation = new Liquidation(position.getShare(), week);
    liquidation.commit(player);
    player.getPortfolio().removeLeveragedPosition(position);
    player.getTransactionArchive().add(liquidation);
  }
}