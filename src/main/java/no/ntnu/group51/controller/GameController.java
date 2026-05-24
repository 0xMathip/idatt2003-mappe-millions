package no.ntnu.group51.controller;

import no.ntnu.group51.controller.Dashboard.DashboardController;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.Dashboard.DashboardView;

public class GameController {

  private final GameModel model;
  private final DashboardView view;
  DashboardController dashboardController;

  public GameController(GameModel model, DashboardView view) {
    this.model = model;
    this.view = view;
    dashboardController = new DashboardController(model, view);
  }

  public void refresh() {
    dashboardController.refresh();
  }

}
