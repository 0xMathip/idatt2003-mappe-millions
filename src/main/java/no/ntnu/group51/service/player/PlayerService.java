package no.ntnu.group51.service.player;

import java.math.BigDecimal;
import java.util.Objects;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.player.PlayerLevel;


public class PlayerService {

  private final GameModel model;

  public PlayerService(GameModel model) {
    this.model = Objects.requireNonNull(model);
  }

  /**
   * Used to do checks for the player level after events such as
   * a transaction or going to the stats page. Think of it as something
   * that updates the player level when conditions are met.
   */
  public void updatePlayerLevel() {
    int week = model.getExchange().getWeek();
    Player player = model.getPlayer();

    if (player.getPlayerLevel() == PlayerLevel.INVESTOR
        && week >= 20
        && player.getNetWorth().compareTo(
            player.getStartingMoney().multiply(BigDecimal.valueOf(2))) >= 0) {
      player.setPlayerLevel(PlayerLevel.SPECULATOR);

    } else if (player.getPlayerLevel() == PlayerLevel.NOVICE
        && week >= 10
        && player.getNetWorth().compareTo(
            player.getStartingMoney().multiply(BigDecimal.valueOf(1.2))) >= 0) {
      player.setPlayerLevel(PlayerLevel.INVESTOR);

    } else if (player.getPlayerLevel() == PlayerLevel.SPECULATOR
        && player.getNetWorth().compareTo(
            player.getStartingMoney().multiply(BigDecimal.valueOf(2))) <= 0) {
      player.setPlayerLevel(PlayerLevel.INVESTOR);

    } else if (player.getPlayerLevel() == PlayerLevel.INVESTOR
        && player.getNetWorth().compareTo(
            player.getStartingMoney().multiply(BigDecimal.valueOf(1.2))) <= 0) {
      player.setPlayerLevel(PlayerLevel.NOVICE);
    }
  }
}