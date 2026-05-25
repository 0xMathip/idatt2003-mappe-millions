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
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.market.MarketHoldingInfoCard;
import no.ntnu.group51.view.components.market.MarketSearchMenu;
import no.ntnu.group51.view.components.market.StockSelectorCard;
import no.ntnu.group51.view.components.market.TradePanel;
import no.ntnu.group51.view.components.shared.StockChartCard;

public class MarketView implements View {

  private final StackPane root = new StackPane();

  private final StockSelectorCard stockSelectorCard;
  private final TradePanel tradePanel;
  private final StockChartCard stockChartCard;
  private final MarketHoldingInfoCard holdingInfoCard;

  private VBox marketContent;
  private MarketSearchMenu stockSearchMenu;
  private Pane overlay;

  private Runnable onStockSelectorClicked = () -> {};

  public MarketView() {
    this.stockSelectorCard = new StockSelectorCard();
    this.tradePanel = new TradePanel();
    this.stockChartCard = new StockChartCard(true);
    this.holdingInfoCard = new MarketHoldingInfoCard();

    createLayout();
    registerEvents();
  }

  private void createLayout() {
    marketContent = new VBox(24);
    marketContent.getStyleClass().addAll("page-layout", "market-view");

    Label title = new Label("Market");
    title.getStyleClass().add("page-title");

    HBox body = createBody();

    marketContent.getChildren().addAll(title, body);
    StackPane.setAlignment(marketContent, Pos.TOP_CENTER);
    root.getChildren().add(marketContent);
  }

  private HBox createBody() {
    HBox body = new HBox(25);
    body.getStyleClass().add("market-body");

    VBox leftColumn = createLeftColumn();
    HBox stockChart = createStockChart();

    body.getChildren().addAll(leftColumn, stockChart);

    return body;
  }

  private VBox createLeftColumn() {
    VBox leftColumn = new VBox(25);
    leftColumn.getStyleClass().add("market-left-column");
    leftColumn.setPrefHeight(800);

    leftColumn.getChildren().addAll(
        stockSelectorCard.getRoot(),
        holdingInfoCard.getRoot(),
        tradePanel.getRoot()
    );

    return leftColumn;
  }

  private HBox createStockChart() {
    stockChartCard.addRootStyleClass("card");
    stockChartCard.addRootStyleClass("stock-chart-card-large");

    HBox chartBox = new HBox(stockChartCard.getRoot());
    HBox.setHgrow(chartBox, Priority.ALWAYS);
    chartBox.setAlignment(Pos.CENTER);

    return chartBox;
  }

  private void registerEvents() {
    stockSelectorCard.getRoot().setOnMouseClicked(event -> onStockSelectorClicked.run());
  }

  public void showStockSearchMenu(MarketSearchMenu searchMenu) {
    if (searchMenu == null) {
      throw new IllegalArgumentException("Search menu cannot be null.");
    }

    if (stockSearchMenu != null) {
      return;
    }

    overlay = new Pane();
    overlay.getStyleClass().add("market-overlay");

    stockSearchMenu = searchMenu;

    GaussianBlur blur = new GaussianBlur(15);
    marketContent.getChildren().forEach(child -> child.setEffect(blur));

    root.getChildren().addAll(
        overlay,
        stockSearchMenu.getRoot()
    );

    overlay.setOnMouseClicked(event -> closeStockSearchMenu());
    stockSearchMenu.setOnClose(this::closeStockSearchMenu);
  }

  public void closeStockSearchMenu() {
    if (stockSearchMenu == null) {
      return;
    }

    marketContent.getChildren().forEach(child -> child.setEffect(null));

    root.getChildren().removeAll(
        overlay,
        stockSearchMenu.getRoot()
    );

    overlay = null;
    stockSearchMenu = null;
  }

  public void setOnStockSelectorClicked(Runnable handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }

    this.onStockSelectorClicked = handler;
  }

  public StockSelectorCard getStockSelectorCard() {
    return stockSelectorCard;
  }

  public TradePanel getTradePanel() {
    return tradePanel;
  }

  public StockChartCard getStockChartCard() {
    return stockChartCard;
  }

  public MarketHoldingInfoCard getHoldingInfoCard() {
    return holdingInfoCard;
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}