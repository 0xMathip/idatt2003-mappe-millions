package no.ntnu.group51.controller.dashboard;

import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.pages.DashboardView;

public class DashboardTransactionController {

  private final GameModel model;
  private final DashboardView view;

  public DashboardTransactionController(GameModel model, DashboardView view) {
    this.model = model;
    this.view = view;
//    updateTransactionListings();
  }

//  public void updateTransactionListings() {
//    view.createTransactionListings(
//        model.getPlayer().getTransactionArchive());
//  }

  public void setOnViewAllPress(Runnable runnable) {
    view.setOnTransactionPress(e -> runnable.run());
  }

}
