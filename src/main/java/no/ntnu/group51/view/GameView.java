package no.ntnu.group51.view;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import no.ntnu.group51.view.Dashboard.DashboardView;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.pages.MarketView;
import no.ntnu.group51.view.pages.TransactionView;

public class GameView implements View {

  BorderPane root = new BorderPane();
  private GameModel gameModel;

  public GameView(GameModel gameModel) {
    this.gameModel = gameModel;
    SidebarView sidebar = new SidebarView();
    DashboardView dashboard = new DashboardView();
    MarketView market = new MarketView(gameModel);
    TransactionView transaction = new TransactionView(gameModel);

    root.setLeft(sidebar.getRoot());
    root.setCenter(transaction.getRoot());
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
