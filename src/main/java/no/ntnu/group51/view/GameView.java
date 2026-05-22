package no.ntnu.group51.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import no.ntnu.group51.view.Dashboard.DashboardView;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.pages.MarketView;
import no.ntnu.group51.view.pages.TransactionView;

public class GameView implements View {

  private final BorderPane root = new BorderPane();
  private GameModel gameModel;
  private final SidebarView sidebar;
  private final DashboardView dashboard;
  private final MarketView market;
  private final TransactionView transaction;

  public GameView(GameModel gameModel) {
    this.gameModel = gameModel;
    this.sidebar = new SidebarView();
    this.dashboard = new DashboardView();
    this.market = new MarketView(gameModel);
    this.transaction = new TransactionView(gameModel);
  }

  public void setCenterView(View view) {
    root.setCenter(view.getRoot());
  }

  public void setLeftView(View view) {
    root.setLeft(view.getRoot());
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
