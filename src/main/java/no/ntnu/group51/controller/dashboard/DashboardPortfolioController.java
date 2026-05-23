package no.ntnu.group51.controller.dashboard;

import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.components.dashboard.DashboardPortfolioPanel;

public class DashboardPortfolioController {

  private final GameModel model;
  private final DashboardPortfolioPanel view;

  public DashboardPortfolioController(GameModel model, DashboardPortfolioPanel view) {
    this.model = model;
    this.view = view;
  }

  public void updateListings() {

  }
}
