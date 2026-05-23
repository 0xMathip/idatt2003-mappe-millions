package no.ntnu.group51.controller.dashboard;

import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.pages.DashboardView;

public class DashboardMoversController {

  private final GameModel model;
  private final DashboardView view;

  public DashboardMoversController(GameModel model, DashboardView view) {
    this.model = model;
    this.view = view;
    updateMovers();
  }

  public void setOnMarketPress(Runnable runnable) {
    view.setOnMarketPress(e -> runnable.run());
  }

  public void updateMovers() {
    view.addMovers(model.getExchange());
  }

}
