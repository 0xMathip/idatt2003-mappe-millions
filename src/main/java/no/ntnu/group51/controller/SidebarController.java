package no.ntnu.group51.controller;

import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.player.PlayerLevel;
import no.ntnu.group51.view.SidebarView;

public class SidebarController {

  private final GameModel model;
  private final SidebarView view;

  public SidebarController(GameModel model, SidebarView view) {
    this.model = model;
    this.view = view;
  }

  public void setOnDashboard(Runnable runnable) {
    view.setOnDashboardButton(e -> runnable.run());
    updateSidebar();
  }

  public void setOnMarket(Runnable runnable) {
    view.setOnMarketButton(e -> runnable.run());
    updateSidebar();
  }

  public void updateWeek() {
    view.setCurrentWeek(model.getExchange().getWeek());
  }

  public void updateLevel() {
    view.setCurrentLevelLabel(model.getPlayer().getPlayerLevel());
  }

  public void updateNextLevel() {
    if (model.getPlayer().getPlayerLevel() == PlayerLevel.NOVICE) {
      view.setNextLevelLabel(PlayerLevel.INVESTOR);
    }
    if (model.getPlayer().getPlayerLevel() == PlayerLevel.INVESTOR) {
      view.setNextLevelLabel(PlayerLevel.SPECULATOR);
    }
  }

  public void updateSidebar() {
    updateWeek();
    updateLevel();
    updateNextLevel();
  }
}
