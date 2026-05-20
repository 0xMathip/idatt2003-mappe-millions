package no.ntnu.group51.view.components;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.Observer;
import no.ntnu.group51.view.View;


public class PortfolioStockDetails implements View, Observer {
  private final VBox root = new VBox(10);
  private final GameModel gameModel;
  private StockChartCard stockChartCard;


  private final Label ticker = new Label();
  private final Label company = new Label();
  private final Label priceValue = new Label();
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

    stockChartCard = new StockChartCard(gameModel);
    ticker.setText(gameModel.getSelectedStock().getSymbol());
    company.setText(gameModel.getSelectedStock().getCompany());

    ticker.getStyleClass().add("transaction-details-ticker");
    company.getStyleClass().add("transaction-details-company");

    VBox companyBox = new VBox(ticker, company);
    companyBox.setAlignment(Pos.CENTER_LEFT);


    VBox priceBox = new VBox(priceValue, changeValue);

    HBox stockChart = createStockChart();
    stockChart.getStyleClass().add("portfolio-details-stock-chart");

    GridPane statsGrid = new GridPane();
    statsGrid.getStyleClass().addAll("card", "portfolio-details-stats-grid");
    statsGrid.setAlignment(Pos.CENTER);

    statsGrid.add(createStatBox("Avg. Buy Price", "$3.2421"), 0, 0);
    statsGrid.add(createStatBox("Total Invested", "$60,432.34"), 1, 0);
    statsGrid.add(createStatBox("Shares Owned", "14"), 2, 0);

    statsGrid.add(createStatBox("Lowest Price", "$1.9322"), 0, 1);
    statsGrid.add(createStatBox("Highest Price", "$3.6564"), 1, 1);
    statsGrid.add(createStatBox("Position Value", "$173,057.4"), 2, 1);

    VBox pnlBox = createStatBox("Profit/Loss", "+$3,057.4");
    pnlBox.getStyleClass().add("portfolio-details-pnl");

    statsGrid.add(pnlBox, 3, 0, 1, 2);

    VBox headerBox = new VBox(companyBox, priceBox);

    Label marketButton = new Label("Open in Market ➜ ");
    marketButton.getStyleClass().add("dashboard-view-button");
    marketButton.setPadding();

    root.getChildren().addAll(
        headerBox,
        stockChart,
        statsGrid,
        marketButton
    );

  }


  private HBox createStockChart() {
    stockChartCard.addRootStyleClass("stock-chart-card-small");

    HBox stockChart = new HBox(stockChartCard.getRoot());
    stockChart.setAlignment(Pos.CENTER);

    return stockChart;
  }

  private VBox createStatBox(String title, String value) {
    Label titleLabel = new Label(title);
    titleLabel.getStyleClass().add("portfolio-details-stat-label");

    Label valueLabel = new Label(value);
    valueLabel.getStyleClass().add("portfolio-details-stat-value");

    VBox box = new VBox(6, titleLabel, valueLabel);
    box.setAlignment(Pos.CENTER_LEFT);
    return box;
  }

  @Override
  public Parent getRoot() {
    return root;
  }


  @Override
  public void update() {
  }
}
