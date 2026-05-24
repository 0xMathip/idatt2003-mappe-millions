package no.ntnu.group51;

import javafx.application.Platform;
import no.ntnu.group51.controller.Dashboard.*;
import no.ntnu.group51.controller.MarketController;
import no.ntnu.group51.controller.SceneManager;
import no.ntnu.group51.controller.SidebarController;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.Dashboard.DashboardView;
import no.ntnu.group51.view.GameView;
import no.ntnu.group51.view.SidebarView;
import no.ntnu.group51.view.pages.MarketView;

public class GameViewController {

  private GameModel model;
  private GameView view;
  private SceneManager sceneManager;

  public GameViewController(GameModel model, GameView view, SceneManager sceneManager) {
    this.model = model;
    this.view = view;
    this.sceneManager = sceneManager;

    SidebarView sidebarView = new SidebarView();
    DashboardView dashboardView = new DashboardView();
    MarketView marketView = new MarketView(model);

    SidebarController sidebarController = new SidebarController(model, sidebarView);
    DashboardController dashboardController = new DashboardController(model, dashboardView);
    DashboardMoversController dashboardMoversController = new DashboardMoversController(model, dashboardView);
    DashboardTransactionController dashboardTransactionController = new DashboardTransactionController(model, dashboardView);
    CashPanelController cashPanelController = new CashPanelController(model, dashboardView);
    ActionsController actionsController = new ActionsController(model, dashboardView, sidebarView);

    view.setLeftView(sidebarView);
    view.setCenterView(dashboardView);

    Runnable setDashboardCenter = () -> {
      sidebarView.toggleDashboard();
      view.setCenterView(dashboardView);
      new DashboardController(model, dashboardView);
    };

    Runnable setMarketCenter = () -> {
      sidebarView.toggleMarket();
      view.setCenterView(marketView);
      new MarketController();
    };

    sidebarController.setOnDashboard(setDashboardCenter);
    sidebarController.setOnMarket(setMarketCenter);
    dashboardMoversController.setOnMarketPress(setMarketCenter);
    dashboardTransactionController.setOnViewAllPress(setMarketCenter);
  }



  public void openMarket() {
    MarketView marketView = new MarketView(model);
    view.setCenterView(marketView);
  }

  public void openPortfolio() {}

  public void openTransactions() {}
}
