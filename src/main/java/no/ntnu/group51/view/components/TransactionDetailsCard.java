package no.ntnu.group51.view.components;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.Observer;
import no.ntnu.group51.view.View;

public class TransactionDetailsCard implements View, Observer {
  private final GameModel gameModel;
  private final HBox root = new HBox();

  public TransactionDetailsCard(GameModel gameModel) {
    this.gameModel = gameModel;

    root.getStyleClass().addAll("card","stock-selector-card");
    root.setAlignment(Pos.CENTER_LEFT);
  }


  @Override
  public Parent getRoot() {
    return root;
  }


  @Override
  public void update() {
  }
}
