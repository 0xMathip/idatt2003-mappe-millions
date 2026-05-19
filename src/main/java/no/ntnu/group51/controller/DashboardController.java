package no.ntnu.group51.controller;

import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.portfolio.Portfolio;
import no.ntnu.group51.model.stocks.Share;

import java.util.List;

public class DashboardController {

  private Player player;

  public DashboardController(GameModel gameModel) {
    player = gameModel.getPlayer();
  }

  /*
  public Portfolio portfolioList() {
    Portfolio portfolio = player.getPortfolio();
    List<Share> list = portfolio
  }
  */
}
