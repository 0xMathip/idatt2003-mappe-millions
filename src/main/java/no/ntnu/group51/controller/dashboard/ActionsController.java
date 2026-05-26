package no.ntnu.group51.controller.dashboard;

import no.ntnu.group51.controller.sidebar.SidebarController;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.service.trading.LiquidationService;
import no.ntnu.group51.view.pages.DashboardView;
import no.ntnu.group51.view.components.shared.SidebarView;

import java.math.BigDecimal;

/**
 * The controller for the different actions you can press on the dashboard.
 *
 * <p>Currently: Advance week button.
 * </p>
 */
public class ActionsController {

  private final GameModel model;
  private final DashboardView view;
  private final DashboardController dashboardController;
  private final SidebarController sidebarController;
  private final LiquidationService liquidationService;

  /**
   * Creates a controller for actions.
   *
   * @param model The persistent model of the game.
   * @param view The dashboard view of the program.
   * @param sidebarView The sidebar view of the program.
   */
  public ActionsController(
      GameModel model,
      DashboardView view,
      SidebarView sidebarView,
      LiquidationService liquidationService
  ) {
    this.model = model;
    this.view = view;
    this.liquidationService = liquidationService;

    dashboardController = new DashboardController(model, view);
    sidebarController = new SidebarController(model, sidebarView);
    setupButtons();
  }

  /**
   * Sets up any buttons.
   *
   * <p>Advance week: Advances the week in the exchange then updates the dashboard and the sidebar.
   * </p>
   */
  public void setupButtons() {
    view.setOnAdvanceWeekPress(e -> {
      model.getExchange().advance();

      liquidationService.checkLiquidations(
          model.getPlayer(),
          model.getExchange().getWeek()
      );

      model.recordNetWorth();
      model.notifyObservers();
      dashboardController.refresh();
      sidebarController.updateSidebar();
    });
  }
}
