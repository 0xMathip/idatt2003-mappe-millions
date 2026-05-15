package no.ntnu.group51.view;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.pages.MarketView;

public class GameView implements View {

  BorderPane root = new BorderPane();
  private GameModel gameModel;

  public GameView(GameModel gameModel) {
    this.gameModel = gameModel;
    SidebarView sidebar = new SidebarView();
    MarketView market = new MarketView(gameModel);

    root.setLeft(sidebar.getRoot());
    root.setCenter(market.getRoot());
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
