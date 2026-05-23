package no.ntnu.group51.controller.dashboard;

import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.pages.DashboardView;

public class DashboardController {

  private final GameModel model;
  private final DashboardView view;

  public DashboardController(GameModel model, DashboardView view) {
    this.model = model;
    this.view = view;

    DashboardTransactionController dashboardTransactionController =
        new DashboardTransactionController(model, view);

    DashboardMoversController dashboardMoversController =
        new DashboardMoversController(model, view);
  }

  public void update() {

  }
}
