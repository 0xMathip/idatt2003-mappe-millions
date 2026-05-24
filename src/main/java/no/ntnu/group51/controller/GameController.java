package no.ntnu.group51.controller;

import no.ntnu.group51.controller.Dashboard.CashPanelController;
import no.ntnu.group51.controller.Dashboard.DashboardController;
import no.ntnu.group51.controller.Dashboard.DashboardMoversController;
import no.ntnu.group51.controller.Dashboard.DashboardTransactionController;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.Dashboard.DashboardView;

public class GameController {

  private final GameModel model;
  private final DashboardView view;
  private CashPanelController cashPanelController;
  private DashboardMoversController dashboardMoversController;
  private DashboardTransactionController dashboardTransactionController;

  public GameController(GameModel model, DashboardView view) {
    this.model = model;
    this.view = view;
    cashPanelController = new CashPanelController(model, view);
    dashboardMoversController = new DashboardMoversController(model, view);
    dashboardTransactionController = new DashboardTransactionController(model, view);
  }

  public void refresh() {
    cashPanelController.refreshCashPanel();
    dashboardMoversController.refreshMovers();
    dashboardTransactionController.updateTransactionListings();
  }

}
