package no.ntnu.group51.controller;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import no.ntnu.group51.controller.dashboard.ActionsController;
import no.ntnu.group51.controller.dashboard.DashboardController;
import no.ntnu.group51.controller.market.MarketController;
import no.ntnu.group51.controller.portfolio.PortfolioController;
import no.ntnu.group51.controller.sidebar.SidebarController;
import no.ntnu.group51.controller.transaction.TransactionController;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.service.portfolio.PortfolioService;
import no.ntnu.group51.service.portfolio.PositionService;
import no.ntnu.group51.service.trading.LeverageService;
import no.ntnu.group51.service.trading.LiquidationService;
import no.ntnu.group51.service.trading.TradeService;
import no.ntnu.group51.service.transaction.TransactionService;
import no.ntnu.group51.view.GameView;
import no.ntnu.group51.view.components.shared.SidebarView;
import no.ntnu.group51.view.pages.DashboardView;
import no.ntnu.group51.view.pages.MarketView;
import no.ntnu.group51.view.pages.PortfolioView;
import no.ntnu.group51.view.pages.TransactionView;

/**
 * The controller for the game view, which is both the central view and the sidebar.
 */
public class GameViewController {

  private final GameModel model;
  private final GameView view;
  private final SceneManager sceneManager;

  /**
   * Creates a game view controller and primes the view to be sidebar and dashboard.
   * Has runnable methods for what happens to the game view on sidebar presses.
   *
   * @param model The persistent model for the game.
   * @param view The game view.
   * @param sceneManager The persistent scene manager for the program.
   */
  public GameViewController(GameModel model, GameView view, SceneManager sceneManager) {
    this.model = model;
    this.view = view;
    this.sceneManager = sceneManager;

    SidebarView sidebarView = view.getSidebarView();
    DashboardView dashboardView = view.getDashboardView();
    MarketView marketView = view.getMarketView();
    PortfolioView portfolioView = view.getPortfolioView();
    TransactionView transactionView = view.getTransactionView();

    TransactionService transactionService = new TransactionService();
    PositionService positionService = new PositionService();
    LeverageService leverageService = new LeverageService();
    LiquidationService liquidationService = new LiquidationService(leverageService);
    PortfolioService portfolioService = new PortfolioService(positionService);
    TradeService tradeService = new TradeService(leverageService);


    SidebarController sidebarController =
        new SidebarController(model, sidebarView);
    DashboardController dashboardController =
        new DashboardController(model, dashboardView);
    ActionsController actionsController =
        new ActionsController(model, dashboardView, sidebarView, liquidationService);
    TransactionController transactionController =
        new TransactionController(model, transactionView, transactionService);
    PortfolioController portfolioController =
        new PortfolioController(model, portfolioView, portfolioService, positionService);
    MarketController marketController =
        new MarketController(model, marketView, tradeService);

    view.setLeftView(sidebarView);
    view.setCenterView(dashboardView);

    Runnable setDashboardCenter = () -> {
      sidebarView.toggleDashboard();
      view.setCenterView(dashboardView);
      dashboardController.refresh();
    };

    Runnable setMarketCenter = () -> {
      sidebarView.toggleMarket();
      view.setCenterView(marketView);
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
    dashboardController.setOnMarketPress(setMarketCenter);
    dashboardController.setOnViewAllPress(setTransactionCenter);
    portfolioController.setOnOpenMarketPress(setMarketCenter);

    sceneManager.getScene().setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ESCAPE) {
        Platform.exit();
      }
    });
  }
}
