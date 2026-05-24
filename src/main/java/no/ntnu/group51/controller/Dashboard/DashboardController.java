package no.ntnu.group51.controller.Dashboard;

import no.ntnu.group51.controller.SceneManager;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.Dashboard.CashPanel;
import no.ntnu.group51.view.Dashboard.DashboardTransactionPanel;
import no.ntnu.group51.view.Dashboard.DashboardView;

/**
 * Controller for everything related to the dashboard view.
 */
public class DashboardController {

  private final GameModel model;
  private final DashboardView view;

  /**
   * Creates a dashboard controller and also refreshes everything in the dashboard.
   *
   * @param model The persistent model for the game.
   * @param view The dashboard view.
   */
  public DashboardController(GameModel model, DashboardView view) {
    this.model = model;
    this.view = view;
    refresh();
  }

  /**
   * Groups all refresh methods.
   */
  public void refresh() {
    refreshCashPanel();
    refreshMovers();
    updateTransactionListings();
  }

  /**
   * Refreshes the cash panel.
   */
  public void refreshCashPanel() {
    view.addCashPanel(model);
  }

  /**
   * Refreshes the movers panel.
   */
  public void refreshMovers() {
    view.addMovers(model.getExchange());
  }

  /**
   * Refreshes the transactions listings panel.
   */
  public void updateTransactionListings() {
    view.createTransactionListings(
        model.getPlayer().getTransactionArchive().getLast3Transactions());
  }

  /**
   * Runs a runnable on pressing the view market button in the movers panel.
   *
   * @param runnable The runnable that should run on press
   */
  public void setOnMarketPress(Runnable runnable) {
    view.setOnMarketPress(e -> runnable.run());
  }

  /**
   * Runs a runnable on pressing the view all transactions in the transaction panel.
   *
   * @param runnable The runnable that should run on press
   */
  public void setOnViewAllPress(Runnable runnable) {
    view.setOnTransactionPress(e -> runnable.run());
  }
}
