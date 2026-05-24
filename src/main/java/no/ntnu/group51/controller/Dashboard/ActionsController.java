package no.ntnu.group51.controller.Dashboard;

import no.ntnu.group51.controller.GameController;
import no.ntnu.group51.controller.SidebarController;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.Dashboard.DashboardView;
import no.ntnu.group51.view.SidebarView;

public class ActionsController {

  private final GameModel model;
  private final DashboardView view;
  private final GameController controller;
  private final SidebarController sidebarController;

  public ActionsController(GameModel model, DashboardView view, SidebarView sidebarView) {
    this.model = model;
    this.view = view;
    controller = new GameController(model, view);
    sidebarController = new SidebarController(model, sidebarView);
    setupButtons();
  }

  public void setupButtons() {
    view.setOnAdvanceWeekPress(e -> {
      model.getExchange().advance();
      controller.refresh();
      sidebarController.updateSidebar();
      System.out.println("advance week pressed");
    });
  }
}
