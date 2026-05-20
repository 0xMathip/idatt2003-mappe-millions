package no.ntnu.group51.model.player;

import java.math.BigDecimal;
import java.util.Objects;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.exchange.Exchange;
import no.ntnu.group51.model.portfolio.Portfolio;


public class PlayerService {

  private final GameModel model;

  public PlayerService(GameModel model) {
    this.model = Objects.requireNonNull(model);
  }

  public BigDecimal getNetWorth() {
    BigDecimal netWorth = BigDecimal.ZERO;
    Exchange exchange = model.getExchange();
    Portfolio portfolio = model.getPlayer().getPortfolio();

    for (String symbol : portfolio.getShares().keySet()) {
      BigDecimal currentPrice = exchange.getStock(symbol).getSalesPrice();
      BigDecimal sharesQuantity = portfolio.getShares().get(symbol);
      BigDecimal playerMoney = model.getPlayer().getMoney();

      netWorth = netWorth.add(currentPrice.multiply(sharesQuantity).add(playerMoney));
    }
    return netWorth;
  }

  /**
   * Used to do checks for the player level after events such as
   * a transaction or going to the stats page. Think of it as something
   * that updates the player level when conditions are met.
   *
   * @param totalWeeks The total weeks the player has been trading
   */
  public void updatePlayerLevel(int totalWeeks) {
    if (this.playerLevel == PlayerLevel.INVESTOR
        && totalWeeks >= 20
        && getNetWorth().compareTo(this.startingMoney.multiply(BigDecimal.valueOf(2))) >= 0) {
      this.playerLevel = PlayerLevel.SPECULATOR;

    } else if (this.playerLevel == PlayerLevel.NOVICE
        && totalWeeks >= 10
        && getNetWorth().compareTo(this.startingMoney.multiply(BigDecimal.valueOf(1.2))) >= 0) {
      this.playerLevel = PlayerLevel.INVESTOR;

    } else if (this.playerLevel == PlayerLevel.SPECULATOR
        && getNetWorth().compareTo(this.startingMoney.multiply(BigDecimal.valueOf(2))) <= 0) {
      this.playerLevel = PlayerLevel.INVESTOR;

    } else if (this.playerLevel == PlayerLevel.INVESTOR
        && getNetWorth().compareTo(this.startingMoney.multiply(BigDecimal.valueOf(1.2))) <= 0) {
      this.playerLevel = PlayerLevel.NOVICE;
    }
  }
}