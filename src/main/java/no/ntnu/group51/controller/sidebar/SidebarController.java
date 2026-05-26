package no.ntnu.group51.controller.sidebar;

import javafx.application.Platform;
import no.ntnu.group51.controller.dashboard.DashboardController;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.player.PlayerLevel;
import no.ntnu.group51.view.components.shared.SidebarView;

import java.math.BigDecimal;

/**
 * Controller for the sidebar.
 */
public class SidebarController {

  private final GameModel model;
  private final SidebarView view;

  /**
   * Creates a sidebar controller and sets up the necessary buttons.
   *
   * @param model The persistent model of the game.
   * @param view The sidebar view.
   */
  public SidebarController(GameModel model, SidebarView view) {
    this.model = model;
    this.view = view;
    setupButtons();
  }

  /**
   * Setup all possible buttons here.
   */
  public void setupButtons() {
    view.setOnPauseButton(e -> Platform.exit());
  }

  /**
   * Runs a runnable when the dashboard button is clicked.
   *
   * @param runnable The runnable to run.
   */
  public void setOnDashboard(Runnable runnable) {
    view.setOnDashboardButton(e -> runnable.run());
    updateSidebar();
  }

  /**
   * Runs a runnable when the market button is clicked.
   *
   * @param runnable The runnable to run.
   */
  public void setOnMarket(Runnable runnable) {
    view.setOnMarketButton(e -> runnable.run());
    updateSidebar();
  }

  public void setOnPortfolio(Runnable runnable) {
    view.setOnPortfolioButton(e -> runnable.run());
    updateSidebar();
  }

  public void setOnTransaction(Runnable runnable) {
    view.setOnTransactionButton(e -> runnable.run());
    updateSidebar();
  }

  /**
   * Updates the entire sidebar in one method.
   */
  public void updateSidebar() {
    updateWeek();
    updateLevel();
    updateNextLevel();
    updateLevelTab();
  }

  /**
   * Updates the entire level tab.
   */
  public void updateLevelTab() {
    updateLevel();
    updateNextLevel();
    updateLevelImage();
    updateProgressBar();
  }

  /**
   * Updates the current week label.
   */
  public void updateWeek() {
    view.setCurrentWeek(model.getExchange().getWeek());
  }

  /**
   * Updates the level.
   */
  public void updateLevel() {
    view.setCurrentLevelLabel(model.getPlayer().getPlayerLevel());
  }

  /**
   * Updates the next level.
   */
  public void updateNextLevel() {
    if (model.getPlayer().getPlayerLevel() == PlayerLevel.NOVICE) {
      view.setNextLevelLabel(PlayerLevel.INVESTOR);
    }
    if (model.getPlayer().getPlayerLevel() == PlayerLevel.INVESTOR) {
      view.setNextLevelLabel(PlayerLevel.SPECULATOR);
    }
  }

  /**
   * Sets the level image to the current one.
   */
  public void updateLevelImage() {
    view.setLevelImage(model.getPlayer().getPlayerLevel());
  }

  /**
   * Sets the progress bar to match the current state.
   */
  public void updateProgressBar() {
    view.setProgressBar(model.getPlayer().getXp().divide(model.getPlayer().getXpRequired()));
  }

}
