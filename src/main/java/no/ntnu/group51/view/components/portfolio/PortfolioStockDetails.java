package no.ntnu.group51.view.components.portfolio;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import no.ntnu.group51.service.portfolio.PositionSummary;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.shared.StockChartCard;
import no.ntnu.group51.view.util.CurrencyFormatter;
import no.ntnu.group51.view.util.PercentFormatter;
import no.ntnu.group51.view.util.PriceStyleHelper;


public class PortfolioStockDetails implements View {
  private final VBox root = new VBox(10);
  private final StockChartCard stockChartCard;


  private final Label ticker = new Label("-");
  private final Label company = new Label("No position selected.");
  private final Label priceValue = new Label("$0.00");
  private final Label changeValue = new Label("0.00%");

  private final Label avgBuyPriceValue = new Label("$0.00");
  private final Label totalInvestedValue = new Label("$0.00");
  private final Label sharesOwnedValue = new Label("0");
  private final Label lowestPriceValue = new Label("$0.00");
  private final Label highestPriceValue = new Label("$0.00");
  private final Label positionValue = new Label("$0.00");
  private final Label profitLoss = new Label("$0.00");


  public PortfolioStockDetails() {
    this.stockChartCard = new StockChartCard(false);
    createLayout();
    clear();
  }

  private void createLayout() {
    root.getStyleClass().addAll("card", "transaction-details");
    root.setAlignment(Pos.CENTER_LEFT);

    ticker.getStyleClass().add("transaction-details-ticker");
    company.getStyleClass().add("transaction-details-company");

    VBox companyBox = new VBox(ticker, company);
    companyBox.setAlignment(Pos.CENTER_LEFT);

    VBox priceBox = new VBox(priceValue, changeValue);
    priceBox.setAlignment(Pos.CENTER_RIGHT);

    priceValue.getStyleClass().add("portfolio-details-price");
    changeValue.getStyleClass().add("portfolio-details-change");


    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    HBox headerBox = new HBox(companyBox, spacer, priceBox);

    HBox stockChart = new HBox(createStockChart());
    stockChart.getStyleClass().add("portfolio-details-stock-chart");

    GridPane statsGrid = createStatsGrid();

    Separator separator = new Separator();
    separator.getStyleClass().add("separator-details-grey");

    Label marketButton = new Label("Open in Market ➜ ");
    marketButton.getStyleClass().addAll("dashboard-view-button", "portfolio-market-button");

    VBox topRow = new VBox(headerBox, stockChart);
    VBox botRow = new VBox(statsGrid, separator, marketButton);

    root.getChildren().addAll(topRow, botRow);
  }

  private GridPane createStatsGrid() {
    GridPane statsGrid = new GridPane();
    statsGrid.getStyleClass().addAll("card", "portfolio-details-stats-grid");
    statsGrid.setAlignment(Pos.CENTER);

    statsGrid.add(createStatBox("Avg. Buy Price", avgBuyPriceValue), 0, 0);
    statsGrid.add(createStatBox("Total Invested", totalInvestedValue), 1, 0);
    statsGrid.add(createStatBox("Shares Owned", sharesOwnedValue), 2, 0);

    statsGrid.add(createStatBox("Lowest Price", lowestPriceValue), 0, 1);
    statsGrid.add(createStatBox("Highest Price", highestPriceValue), 1, 1);
    statsGrid.add(createStatBox("Position Value", positionValue), 2, 1);

    VBox pnlBox = createStatBox("Profit/Loss", profitLoss);
    pnlBox.getStyleClass().add("portfolio-details-pnl");


    statsGrid.add(pnlBox, 3, 0, 1, 2);

    return statsGrid;
  }

  private HBox createStockChart() {
    stockChartCard.addRootStyleClass("stock-chart-card-small");

    HBox stockChart = new HBox(stockChartCard.getRoot());
    stockChart.setAlignment(Pos.CENTER);

    return stockChart;
  }

  private VBox createStatBox(String title, Label value) {
    Label titleLabel = new Label(title);
    titleLabel.getStyleClass().add("portfolio-details-stat-label");

    Label valueLabel = value;
    valueLabel.getStyleClass().add("portfolio-details-stat-value");

    VBox box = new VBox(6, titleLabel, valueLabel);
    box.setAlignment(Pos.CENTER_LEFT);
    return box;
  }

  public void updatePosition(PositionSummary position) {
    if (position == null) {
      throw new IllegalArgumentException("Position summary cannot be null.");
    }

    ticker.setText(position.stock().getSymbol());
    company.setText(position.stock().getCompany());
    priceValue.setText(CurrencyFormatter.format(position.currentPrice()));
    changeValue.setText("(" + PercentFormatter.format(position.roiPercent()) + ")");

    avgBuyPriceValue.setText(CurrencyFormatter.format(position.averageBuyPrice()));
    totalInvestedValue.setText(CurrencyFormatter.format(position.totalInvested()));
    sharesOwnedValue.setText(position.sharesOwned().toPlainString());
    lowestPriceValue.setText(CurrencyFormatter.format(position.lowestPrice()));
    highestPriceValue.setText(CurrencyFormatter.format(position.highestPrice()));
    positionValue.setText(CurrencyFormatter.format(position.positionValue()));
    profitLoss.setText(CurrencyFormatter.format(position.profitLoss()));

    PriceStyleHelper.applyPriceChangeStyle(changeValue, position.profitLoss());
    PriceStyleHelper.applyPriceChangeStyle(profitLoss, position.profitLoss());

    stockChartCard.updateStock(position.stock());
  }

  public void clear() {
    ticker.setText("-");
    company.setText("No position selected");
    priceValue.setText("$0.00");
    changeValue.setText("(0.00%)");

    avgBuyPriceValue.setText("$0.00");
    totalInvestedValue.setText("$0.00");
    sharesOwnedValue.setText("0");
    lowestPriceValue.setText("$0.00");
    highestPriceValue.setText("$0.00");
    positionValue.setText("$0.00");
    profitLoss.setText("$0.00");

    stockChartCard.clear();
  }


  @Override
  public Parent getRoot() {
    return root;
  }
}
