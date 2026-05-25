package no.ntnu.group51.view.pages;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.market.MarketHoldingInfoCard;
import no.ntnu.group51.view.components.market.MarketSearchMenu;
import no.ntnu.group51.view.components.market.StockSelectorCard;
import no.ntnu.group51.view.components.market.TradePanel;
import no.ntnu.group51.view.components.shared.StockChartCard;
import no.ntnu.group51.view.util.StyleClass;

/**
 * Market page view containing stock selection,
 * holdings information, trading controls, and chart display.
 */
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

  /**
   * Creates the market view.
   */
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
    marketContent.getStyleClass().addAll(StyleClass.PAGE_LAYOUT, StyleClass.MARKET_VIEW);

    Label title = new Label("Market");
    title.getStyleClass().add(StyleClass.PAGE_TITLE);

    HBox body = createBody();

    marketContent.getChildren().addAll(title, body);
    StackPane.setAlignment(marketContent, Pos.TOP_CENTER);
    root.getChildren().add(marketContent);
  }

  private HBox createBody() {
    HBox body = new HBox(25);
    body.getStyleClass().add(StyleClass.MARKET_BODY);

    VBox leftColumn = createLeftColumn();
    HBox stockChart = createStockChart();

    body.getChildren().addAll(leftColumn, stockChart);

    return body;
  }

  private VBox createLeftColumn() {
    VBox leftColumn = new VBox(25);
    leftColumn.getStyleClass().add(StyleClass.MARKET_LEFT_COLUMN);
    leftColumn.setPrefHeight(800);

    leftColumn.getChildren().addAll(
        stockSelectorCard.getRoot(),
        holdingInfoCard.getRoot(),
        tradePanel.getRoot()
    );

    return leftColumn;
  }

  private HBox createStockChart() {
    stockChartCard.addRootStyleClass(StyleClass.CARD);
    stockChartCard.addRootStyleClass(StyleClass.STOCK_CHART_CARD_LARGE);

    HBox chartBox = new HBox(stockChartCard.getRoot());
    HBox.setHgrow(chartBox, Priority.ALWAYS);
    chartBox.setAlignment(Pos.CENTER);

    return chartBox;
  }

  private void registerEvents() {
    stockSelectorCard.getRoot().setOnMouseClicked(event -> onStockSelectorClicked.run());
  }

  /**
   * Displays the stock search menu as an overlay.
   *
   * @param searchMenu the search menu to display
   * @throws IllegalArgumentException if searchMenu is null
   */
  public void showStockSearchMenu(MarketSearchMenu searchMenu) {
    if (searchMenu == null) {
      throw new IllegalArgumentException("Search menu cannot be null.");
    }

    if (stockSearchMenu != null) {
      return;
    }

    overlay = new Pane();
    overlay.getStyleClass().add(StyleClass.MARKET_OVERLAY);

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

  /**
   * Closes the active stock search menu if one is open.
   */
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

  /**
   * Updates the selected stock display and chart.
   *
   * @param stock the selected stock
   * @throws IllegalArgumentException if stock is null
   */
  public void updateSelectedStock(Stock stock) {
    if (stock == null) {
      throw new IllegalArgumentException("Stock cannot be null.");
    }
    stockSelectorCard.updateStock(stock);
    stockChartCard.updateStock(stock);
  }

  /**
   * Sets the action triggered when the stock selector is clicked.
   *
   * @param handler the click handler
   * @throws IllegalArgumentException if handler is null
   */
  public void setOnStockSelectorClicked(Runnable handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }

    this.onStockSelectorClicked = handler;
  }

  /**
   * Returns the stock selector card.
   *
   * @return the stock selector card
   */
  public StockSelectorCard getStockSelectorCard() {
    return stockSelectorCard;
  }

  /**
   * Returns the trade panel.
   *
   * @return the trade panel
   */
  public TradePanel getTradePanel() {
    return tradePanel;
  }

  /**
   * Returns the stock chart card.
   *
   * @return the stock chart card
   */
  public StockChartCard getStockChartCard() {
    return stockChartCard;
  }

  /**
   * Returns the holding info card.
   *
   * @return the holding info card
   */
  public MarketHoldingInfoCard getHoldingInfoCard() {
    return holdingInfoCard;
  }

  /**
   * Clears the selected stock and stock chart.
   */
  public void clear() {
    stockSelectorCard.clear();
    stockChartCard.clear();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Parent getRoot() {
    return root;
  }
}