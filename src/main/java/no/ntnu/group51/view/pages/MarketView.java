package no.ntnu.group51.view.pages;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.StockChartCard;
import no.ntnu.group51.view.components.StockSelectorCard;
import no.ntnu.group51.view.components.TradePanel;

public class MarketView implements View {
  private final VBox root = new VBox();

  public MarketView(GameModel gameModel){
    root.getStyleClass().add("market-view");

    Label title = new Label("Market");
    title.getStyleClass().add("page-title");

    HBox body = new HBox();
    body.getStyleClass().add("market-body");

    VBox leftColumn = new VBox();
    leftColumn.getStyleClass().add("market-left-column");

    Region spacer = new Region();
    leftColumn.setPrefHeight(800);
    VBox.setVgrow(spacer, Priority.ALWAYS);

    HBox stockChart = new HBox();
    HBox.setHgrow(stockChart, Priority.ALWAYS);
    stockChart.getChildren().addAll(new StockChartCard(gameModel).getRoot());
    stockChart.getStyleClass().add("market-chart");

    leftColumn.getChildren().addAll(
       new StockSelectorCard(gameModel).getRoot(),
        spacer,
        new TradePanel(gameModel).getRoot()
   );

    body.getChildren().addAll(
        leftColumn,
        stockChart
    );

    root.getChildren().addAll(title, body);
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
