package no.ntnu.group51.controller.Dashboard;

import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.Dashboard.DashboardView;

public class CashPanelController {

  private final GameModel model;
  private final DashboardView view;

  public CashPanelController(GameModel model, DashboardView view) {
    this.model = model;
    this.view = view;
    refreshCashPanel();
  }

  public void refreshCashPanel() {
    view.addCashPanel(model);
  }
}
