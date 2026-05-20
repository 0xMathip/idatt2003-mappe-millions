package no.ntnu.group51.view.pages;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.StockChartCard;
import no.ntnu.group51.view.components.StockSearchMenu;
import no.ntnu.group51.view.components.StockSelectorCard;
import no.ntnu.group51.view.components.TradePanel;

public class MarketView implements View {
  private final StackPane root = new StackPane();
  private VBox marketContent;
  private StockSearchMenu stockSearchMenu;
  private Pane overlay;
  private final GameModel gameModel;

  public MarketView(GameModel gameModel){
    this.gameModel = gameModel;

    marketContent = new VBox();
    marketContent.getStyleClass().add("market-view");

    Label title = new Label("Market");
    title.getStyleClass().add("page-title");

    HBox body = new HBox();
    body.getStyleClass().add("market-body");

    VBox leftColumn = new VBox();
    leftColumn.getStyleClass().add("market-left-column");

    Region spacer = new Region();
    leftColumn.setPrefHeight(800);
    VBox.setVgrow(spacer, Priority.ALWAYS);

    StockSelectorCard stockSelectorCard = new StockSelectorCard(gameModel);
    TradePanel tradePanel = new TradePanel(gameModel);
    StockChartCard stockChartCard = new StockChartCard(gameModel);
    stockChartCard.addRootStyleClass("card");
    stockChartCard.addRootStyleClass("stock-chart-card");

    HBox stockChart = new HBox();
    HBox.setHgrow(stockChart, Priority.ALWAYS);
    stockChart.setAlignment(Pos.CENTER);
    stockChart.getChildren().addAll(stockChartCard.getRoot());

    leftColumn.getChildren().addAll(
       stockSelectorCard.getRoot(),
        spacer,
        tradePanel.getRoot()
   );

    body.getChildren().addAll(
        leftColumn,
        stockChart
    );

    marketContent.getChildren().addAll(
        title,
        body
    );

    root.getChildren().addAll(marketContent);
    registerEvents(stockSelectorCard);
  }

  private void registerEvents(StockSelectorCard stockSelectorCard) {
    stockSelectorCard.getRoot().setOnMouseClicked(e -> showStockSearchMenu());
  }

  private void showStockSearchMenu() {
    if (stockSearchMenu != null) {
      return;
    }

    overlay = new Pane();
    overlay.getStyleClass().add("market-overlay");

    stockSearchMenu = new StockSearchMenu(gameModel);

    GaussianBlur blur = new GaussianBlur(15);
    marketContent.getChildren()
        .forEach(child -> child.setEffect(blur));

    root.getChildren().addAll(
        overlay,
        stockSearchMenu.getRoot()
    );

    overlay.setOnMouseClicked(e -> closeStockSearchMenu());
    stockSearchMenu.setOnClose(this::closeStockSearchMenu);
  }

  private void closeStockSearchMenu() {
    marketContent.getChildren()
        .forEach(child -> child.setEffect(null));

    root.getChildren().removeAll(
        overlay,
        stockSearchMenu.getRoot()
    );

    overlay = null;
    stockSearchMenu = null;
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
