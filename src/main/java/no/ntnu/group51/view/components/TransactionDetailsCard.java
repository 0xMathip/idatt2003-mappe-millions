package no.ntnu.group51.view.components;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.Observer;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.factories.TransactionBadgeFactory;

public class TransactionDetailsCard implements View, Observer {
  private final GameModel gameModel;
  private final VBox root = new VBox();

  public TransactionDetailsCard(GameModel gameModel) {
    this.gameModel = gameModel;

    root.getStyleClass().addAll("card","transaction-details-card");
    root.setAlignment(Pos.CENTER_LEFT);

    Node transactionBadge = new TransactionBadgeFactory(gameModel.getSelectedTransaction());
  }


  @Override
  public Parent getRoot() {
    return root;
  }


  @Override
  public void update() {
  }
}
