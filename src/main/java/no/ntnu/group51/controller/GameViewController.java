package no.ntnu.group51.controller;

import javax.sound.sampled.Port;
import no.ntnu.group51.controller.dashboard.DashboardController;
import no.ntnu.group51.controller.dashboard.DashboardMoversController;
import no.ntnu.group51.controller.dashboard.DashboardTransactionController;
import no.ntnu.group51.controller.market.MarketController;
import no.ntnu.group51.controller.portfolio.PortfolioController;
import no.ntnu.group51.controller.sidebar.SidebarController;
import no.ntnu.group51.controller.transaction.TransactionController;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.portfolio.Portfolio;
import no.ntnu.group51.service.portfolio.PortfolioService;
import no.ntnu.group51.service.portfolio.PositionService;
import no.ntnu.group51.service.transaction.TransactionService;
import no.ntnu.group51.view.pages.DashboardView;
import no.ntnu.group51.view.GameView;
import no.ntnu.group51.view.components.shared.SidebarView;
import no.ntnu.group51.view.pages.MarketView;
import no.ntnu.group51.view.pages.PortfolioView;
import no.ntnu.group51.view.pages.TransactionView;

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
    PortfolioView portfolioView = new PortfolioView();
    TransactionView transactionView = new TransactionView();

    TransactionService transactionService = new TransactionService();
    PositionService positionService = new PositionService();
    PortfolioService portfolioService = new PortfolioService(positionService);

    SidebarController sidebarController =
        new SidebarController(model, sidebarView);
    DashboardController dashboardController =
        new DashboardController(model, dashboardView);
    DashboardMoversController dashboardMoversController =
        new DashboardMoversController(model, dashboardView);
    DashboardTransactionController dashboardTransactionController =
        new DashboardTransactionController(model, dashboardView);
    TransactionController transactionController =
        new TransactionController(model, transactionView, transactionService);
    PortfolioController portfolioController =
        new PortfolioController(model, portfolioView, portfolioService, positionService);

    view.setLeftView(sidebarView);
    view.setCenterView(transactionView);

    Runnable setDashboardCenter = () -> {
      sidebarView.toggleDashboard();
      view.setCenterView(dashboardView);
      new DashboardController(model, dashboardView);;
    };

    Runnable setMarketCenter = () -> {
      sidebarView.toggleMarket();
      view.setCenterView(marketView);
      new MarketController();
    };

    Runnable setPortfolioCenter = () -> {
      sidebarView.togglePortfolio();
      view.setCenterView(portfolioView);
    };

    Runnable setTransactionCenter = () -> {
      sidebarView.toggleTransaction();
      view.setCenterView(transactionView);
    };


    sidebarController.setOnDashboard(setDashboardCenter);
    sidebarController.setOnMarket(setMarketCenter);
    sidebarController.setOnPortfolio(setPortfolioCenter);
    sidebarController.setOnTransaction(setTransactionCenter);
    dashboardMoversController.setOnMarketPress(setMarketCenter);
    dashboardTransactionController.setOnViewAllPress(setMarketCenter);
  }



  public void openMarket() {
    MarketView marketView = new MarketView(model);
    view.setCenterView(marketView);
  }

  public void openPortfolio() {
    PortfolioView portfolioView = new PortfolioView();
    view.setCenterView(portfolioView);
  }

  public void openTransactions() {
    TransactionView transactionView = new TransactionView();
    view.setCenterView(transactionView);
  }
}
