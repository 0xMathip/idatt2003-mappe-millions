package no.ntnu.group51.view.components.portfolio;

import java.math.RoundingMode;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.trading.Leverage;
import no.ntnu.group51.service.portfolio.PositionSummary;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.shared.StockChartCard;
import no.ntnu.group51.view.util.CurrencyFormatter;
import no.ntnu.group51.view.util.PercentFormatter;
import no.ntnu.group51.view.util.PriceStyleHelper;
import no.ntnu.group51.view.util.StyleClass;

public class PortfolioStockDetails implements View {

  private final VBox root = new VBox(10);
  private final StockChartCard stockChartCard;

  private final Label ticker = new Label("-");
  private final Label company = new Label("No position selected.");
  private final Label priceValue = new Label("$0.00");
  private final Label changeValue = new Label("0.00%");

  private final Label stat1Title = new Label();
  private final Label stat2Title = new Label();
  private final Label stat3Title = new Label();
  private final Label stat4Title = new Label();
  private final Label stat5Title = new Label();
  private final Label stat6Title = new Label();

  private final Label stat1Value = new Label("$0.00");
  private final Label stat2Value = new Label("$0.00");
  private final Label stat3Value = new Label("0");
  private final Label stat4Value = new Label("$0.00");
  private final Label stat5Value = new Label("$0.00");
  private final Label stat6Value = new Label("$0.00");

  private final Label profitLoss = new Label("$0.00");
  private final Label marketButton = new Label("Open in Market ➜ ");

  public PortfolioStockDetails() {
    this.stockChartCard = new StockChartCard(false);
    createLayout();
    clear();
  }

  private void createLayout() {
    root.getStyleClass().addAll(StyleClass.CARD, StyleClass.TRANSACTION_DETAILS);
    root.setAlignment(Pos.CENTER_LEFT);

    ticker.getStyleClass().add(StyleClass.TRANSACTION_DETAILS_TICKER);
    company.getStyleClass().add(StyleClass.TRANSACTION_DETAILS_COMPANY);

    VBox companyBox = new VBox(ticker, company);
    companyBox.setAlignment(Pos.CENTER_LEFT);

    VBox priceBox = new VBox(priceValue, changeValue);
    priceBox.setAlignment(Pos.CENTER_RIGHT);

    priceValue.getStyleClass().add(StyleClass.PORTFOLIO_DETAILS_PRICE);
    changeValue.getStyleClass().add(StyleClass.PORTFOLIO_DETAILS_CHANGE);

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    HBox headerBox = new HBox(companyBox, spacer, priceBox);

    HBox stockChart = new HBox(createStockChart());
    stockChart.getStyleClass().add(StyleClass.PORTFOLIO_DETAILS_STOCK_CHART);

    GridPane statsGrid = createStatsGrid();

    Separator separator = new Separator();
    separator.getStyleClass().add(StyleClass.SEPARATOR_DETAILS_GREY);

    marketButton.getStyleClass().addAll(StyleClass.DASHBOARD_VIEW_BUTTON, StyleClass.PORTFOLIO_MARKET_BUTTON);

    VBox topRow = new VBox(headerBox, stockChart);
    VBox botRow = new VBox(statsGrid, separator, marketButton);

    root.getChildren().addAll(topRow, botRow);
  }

  private GridPane createStatsGrid() {
    GridPane statsGrid = new GridPane();
    ColumnConstraints col1 = new ColumnConstraints();
    col1.setPercentWidth(26);

    ColumnConstraints col2 = new ColumnConstraints();
    col2.setPercentWidth(26);

    ColumnConstraints col3 = new ColumnConstraints();
    col3.setPercentWidth(26);

    ColumnConstraints col4 = new ColumnConstraints();
    col4.setPercentWidth(22);

    statsGrid.getColumnConstraints().addAll(
        col1,
        col2,
        col3,
        col4
    );

    statsGrid.getStyleClass().addAll(StyleClass.CARD, StyleClass.PORTFOLIO_DETAILS_STATS_GRID);
    statsGrid.setAlignment(Pos.CENTER);

    statsGrid.add(createStatBox(stat1Title, stat1Value), 0, 0);
    statsGrid.add(createStatBox(stat2Title, stat2Value), 1, 0);
    statsGrid.add(createStatBox(stat3Title, stat3Value), 2, 0);

    statsGrid.add(createStatBox(stat4Title, stat4Value), 0, 1);
    statsGrid.add(createStatBox(stat5Title, stat5Value), 1, 1);
    statsGrid.add(createStatBox(stat6Title, stat6Value), 2, 1);

    VBox pnlBox = createStatBox(new Label("Profit/Loss"), profitLoss);
    profitLoss.getStyleClass().remove(StyleClass.PORTFOLIO_DETAILS_STAT_VALUE);
    profitLoss.getStyleClass().add(StyleClass.PORTFOLIO_DETAILS_STAT_VALUE_WITH_STATE);
    pnlBox.getStyleClass().add(StyleClass.PORTFOLIO_DETAILS_PNL);

    statsGrid.add(pnlBox, 3, 0, 1, 2);

    return statsGrid;
  }

  private HBox createStockChart() {
    stockChartCard.addRootStyleClass(StyleClass.STOCK_CHART_CARD_SMALL);

    HBox stockChart = new HBox(stockChartCard.getRoot());
    stockChart.setAlignment(Pos.CENTER);

    return stockChart;
  }

  private VBox createStatBox(Label titleLabel, Label valueLabel) {
    titleLabel.getStyleClass().add(StyleClass.PORTFOLIO_DETAILS_STAT_LABEL);
    valueLabel.getStyleClass().add(StyleClass.PORTFOLIO_DETAILS_STAT_VALUE);

    titleLabel.setMaxWidth(Double.MAX_VALUE);
    valueLabel.setMaxWidth(Double.MAX_VALUE);

    titleLabel.setAlignment(Pos.CENTER);
    valueLabel.setAlignment(Pos.CENTER);

    VBox box = new VBox(6, titleLabel, valueLabel);
    box.setAlignment(Pos.CENTER);
    box.setMaxWidth(Double.MAX_VALUE);

    return box;
  }

  public void updatePosition(PositionSummary position) {
    if (position == null) {
      throw new IllegalArgumentException("Position summary cannot be null.");
    }

    ticker.setText(position.stock().getSymbol());
    company.setText(position.stock().getCompany());
    priceValue.setText(CurrencyFormatter.format(position.currentPrice()));
    changeValue.setText(
        "(" + PercentFormatter.format(position.stock().getLatestPriceChangePercent()) + ")"
    );

    if (position.leveraged()) {
      updateLeveragedStats(position);
    } else {
      updateNormalStats(position);
    }

    profitLoss.setText(CurrencyFormatter.format(position.profitLoss()));

    PriceStyleHelper.applyPriceChangeStyle(changeValue, position.stock().getLatestPriceChange());
    PriceStyleHelper.applyPriceChangeStyle(profitLoss, position.profitLoss());

    stockChartCard.updateStock(position.stock());
  }

  private void updateNormalStats(PositionSummary position) {
    stat1Title.setText("Avg. Buy Price");
    stat1Value.setText(CurrencyFormatter.format(position.averageBuyPrice()));

    stat2Title.setText("Total Invested");
    stat2Value.setText(CurrencyFormatter.format(position.totalInvested()));

    stat3Title.setText("Shares Owned");
    stat3Value.setText(formatQuantity(position.sharesOwned()));

    stat4Title.setText("Lowest Price");
    stat4Value.setText(CurrencyFormatter.format(position.lowestPrice()));

    stat5Title.setText("Highest Price");
    stat5Value.setText(CurrencyFormatter.format(position.highestPrice()));

    stat6Title.setText("Position Value");
    stat6Value.setText(CurrencyFormatter.format(position.positionValue()));
  }

  private void updateLeveragedStats(PositionSummary position) {
    stat1Title.setText("Entry Price");
    stat1Value.setText(CurrencyFormatter.format(position.averageBuyPrice()));

    stat2Title.setText("Margin Used");
    stat2Value.setText(CurrencyFormatter.format(position.marginRequired()));

    stat3Title.setText("Controlled Shares");
    stat3Value.setText(formatQuantity(position.sharesOwned()));

    stat4Title.setText("Leverage");
    stat4Value.setText(formatLeverage(position.leverage()));

    stat5Title.setText("Liquidation Price");
    stat5Value.setText(CurrencyFormatter.format(position.liquidationPrice()));

    stat6Title.setText("Position Value");
    stat6Value.setText(CurrencyFormatter.format(position.positionValue()));
  }

  private String formatQuantity(java.math.BigDecimal quantity) {
    return quantity
        .setScale(4, RoundingMode.HALF_UP)
        .stripTrailingZeros()
        .toPlainString();
  }

  private String formatLeverage(Leverage leverage) {
    if (leverage == null || leverage == Leverage.OFF) {
      return "Off";
    }

    return leverage.getMultiplier() + "x";
  }

  public void clear() {
    ticker.setText("-");
    company.setText("No position selected");
    priceValue.setText("$0.00");
    changeValue.setText("(0.00%)");

    stat1Title.setText("Avg. Buy Price");
    stat1Value.setText("$0.00");

    stat2Title.setText("Total Invested");
    stat2Value.setText("$0.00");

    stat3Title.setText("Shares Owned");
    stat3Value.setText("0");

    stat4Title.setText("Lowest Price");
    stat4Value.setText("$0.00");

    stat5Title.setText("Highest Price");
    stat5Value.setText("$0.00");

    stat6Title.setText("Position Value");
    stat6Value.setText("$0.00");

    profitLoss.setText("$0.00");

    stockChartCard.clear();
  }

  public void setOnOpenMarketPress(EventHandler<MouseEvent> action) {
    marketButton.setOnMouseClicked(action);
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}