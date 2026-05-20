package no.ntnu.group51.view.components;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.Observer;
import no.ntnu.group51.view.View;


public class PortfolioStockDetails implements View, Observer {
  private final VBox root = new VBox(20);
  private final GameModel gameModel;


  private final Label ticker = new Label();
  private final Label company = new Label();
  private final Label priceValue= new Label();
  private final Label changeValue = new Label();
  private final Label avgBuyPriceValue = new Label();
  private final Label totalInvestedValue = new Label();
  private final Label sharesOwnedValue = new Label();
  private final Label lowestPriceValue = new Label();
  private final Label highestPriceValue = new Label();
  private final Label positionValue = new Label();
  private final Label pnlValue = new Label();


  public PortfolioStockDetails(GameModel gameModel) {
    this.gameModel = gameModel;

    root.getStyleClass().addAll("card","transaction-details");
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
