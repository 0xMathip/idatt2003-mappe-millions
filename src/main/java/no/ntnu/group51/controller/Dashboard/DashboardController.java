package no.ntnu.group51.controller.Dashboard;

import no.ntnu.group51.controller.SceneManager;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.Dashboard.CashPanel;
import no.ntnu.group51.view.Dashboard.DashboardTransactionPanel;
import no.ntnu.group51.view.Dashboard.DashboardView;

public class DashboardController {

  private final GameModel model;
  private final DashboardView view;

  public DashboardController(GameModel model, DashboardView view) {
    this.model = model;
    this.view = view;
    refresh();
  }

  public void refresh() {
    refreshCashPanel();
    refreshMovers();
    updateTransactionListings();
  }

  public void refreshCashPanel() {
    view.addCashPanel(model);
  }

  public void refreshMovers() {
    view.addMovers(model.getExchange());
  }

  public void updateTransactionListings() {
    view.createTransactionListings(
        model.getPlayer().getTransactionArchive().getLast3Transactions());
  }

  public void setOnMarketPress(Runnable runnable) {
    view.setOnMarketPress(e -> runnable.run());
  }

  public void setOnViewAllPress(Runnable runnable) {
    view.setOnTransactionPress(e -> runnable.run());
  }
}
