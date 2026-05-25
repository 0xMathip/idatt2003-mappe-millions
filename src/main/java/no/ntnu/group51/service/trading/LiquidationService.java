package no.ntnu.group51.service.trading;

import java.util.List;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.trading.LeveragedPosition;
import no.ntnu.group51.model.transaction.Liquidation;
import no.ntnu.group51.model.transaction.Transaction;

public class LiquidationService {

  private final LeverageService leverageService;

  public LiquidationService(LeverageService leverageService) {
    if (leverageService == null) {
      throw new IllegalArgumentException("Leverage service cannot be null.");
    }

    this.leverageService = leverageService;
  }

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
    player.getPortfolio().removeLeveragedPosition(position);

    Transaction liquidation = new Liquidation(position.getShare(), week);
    player.getTransactionArchive().add(liquidation);
  }
}