package no.ntnu.group51;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import no.ntnu.group51.controller.Dashboard.*;
import no.ntnu.group51.controller.MarketController;
import no.ntnu.group51.controller.SceneManager;
import no.ntnu.group51.controller.SidebarController;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.Dashboard.DashboardView;
import no.ntnu.group51.view.GameView;
import no.ntnu.group51.view.SidebarView;
import no.ntnu.group51.view.pages.MarketView;

/**
 * The controller for the game view, which is both the central view and the sidebar.
 */
public class GameViewController {

  private GameModel model;
  private GameView view;
  private SceneManager sceneManager;

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

    SidebarView sidebarView = new SidebarView();
    DashboardView dashboardView = new DashboardView();
    MarketView marketView = new MarketView(model);

    SidebarController sidebarController = new SidebarController(model, sidebarView);
    DashboardController dashboardController = new DashboardController(model, dashboardView);
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
    dashboardController.setOnMarketPress(setMarketCenter);
    dashboardController.setOnViewAllPress(setMarketCenter);

    sceneManager.getScene().setOnKeyPressed(e -> {
      if (e.getCode() == KeyCode.ESCAPE) {
        Platform.exit();
      }
    });
  }
}
