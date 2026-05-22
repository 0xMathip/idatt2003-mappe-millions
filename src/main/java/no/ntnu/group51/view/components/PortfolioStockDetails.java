package no.ntnu.group51.view.components;

import java.math.BigDecimal;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.Observer;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.util.PriceStyleHelper;


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

    root.getStyleClass().addAll("card", "transaction-details");
    root.setAlignment(Pos.CENTER_LEFT);

    stockChartCard = new StockChartCard(gameModel, false);
    ticker.setText(gameModel.getSelectedStock().getSymbol());
    company.setText(gameModel.getSelectedStock().getCompany());

    ticker.getStyleClass().add("transaction-details-ticker");
    company.getStyleClass().add("transaction-details-company");

    VBox companyBox = new VBox(ticker, company);
    companyBox.setAlignment(Pos.CENTER_LEFT);

    VBox priceBox = new VBox(priceValue, changeValue);
    priceBox.setAlignment(Pos.CENTER_RIGHT);

    priceValue.getStyleClass().add("portfolio-details-price");
    changeValue.getStyleClass().add("portfolio-details-change");

    priceValue.setText("<$3.2322>");
    changeValue.setText("<(3.265%)>");

    PriceStyleHelper.applyPriceChangeStyle(changeValue, new BigDecimal("3.2"));


    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    HBox headerBox = new HBox(companyBox, spacer, priceBox);

    HBox stockShart = new HBox(createStockChart());
    stockShart.getStyleClass().add("portfolio-details-stock-chart");

    Separator separator1 = new Separator();
    separator1.getStyleClass().add("separator-details-grey");

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

    Separator separator = new Separator();
    separator.getStyleClass().add("separator-details-grey");

    Label marketButton = new Label("Open in Market ➜ ");
    marketButton.getStyleClass().addAll("dashboard-view-button", "portfolio-market-button");

    VBox topRow = new VBox(headerBox, stockShart);
    VBox botRow = new VBox(statsGrid, separator, marketButton);


    root.getChildren().addAll(
        topRow,
        botRow
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
