package no.ntnu.group51.controller.Dashboard;

import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.portfolio.Portfolio;
import no.ntnu.group51.model.stocks.Share;
import no.ntnu.group51.view.Dashboard.DashboardPortfolioPanel;

import java.util.List;

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
