package no.ntnu.group51.view.components;

import java.math.BigDecimal;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.Observer;
import no.ntnu.group51.model.stocks.Stock;
import no.ntnu.group51.view.View;

public class StockChartCard implements View, Observer {
  private final StackPane root = new StackPane();
  private final GameModel gameModel;
  private LineChart<Number, Number> stockChart;

  public StockChartCard(GameModel gameModel) {
    if (gameModel == null) {
      throw new IllegalArgumentException("Game model cannot be null.");
    }

    this.gameModel = gameModel;
    root.setAlignment(Pos.CENTER_LEFT);

    gameModel.addObserver(this);
    updateDisplay();
  }

  private LineChart<Number, Number> createChart(Stock stock) {
    List<BigDecimal> prices = stock.getHistoricalPrices();

    NumberAxis xAxis = createXAxis(prices);
    NumberAxis yAxis = createYAxis(prices);

    LineChart<Number, Number> stockChart = new LineChart<>(xAxis, yAxis);
    stockChart.setLegendVisible(false);
    stockChart.setAnimated(false);
    stockChart.setCreateSymbols(false);
    stockChart.setHorizontalGridLinesVisible(false);
    stockChart.setVerticalGridLinesVisible(false);
    stockChart.getStyleClass().add("stock-chart-card-chart");

    XYChart.Series<Number, Number> series = createSeries(prices);
    stockChart.getData().add(series);

    return stockChart;
  }

  private void updateDisplay() {
    root.getChildren().clear();
    stockChart = createChart(gameModel.getSelectedStock());
    root.getChildren().add(stockChart);
  }

  private NumberAxis createXAxis(List<BigDecimal> prices) {
    NumberAxis xAxis = new NumberAxis();
    xAxis.setAutoRanging(false);
    xAxis.setLowerBound(1);
    xAxis.setUpperBound(prices.size());
    xAxis.setTickUnit(1);
    xAxis.setMinorTickVisible(false);

    return xAxis;
  }

  private NumberAxis createYAxis(List<BigDecimal> prices) {
    BigDecimal minPrice = prices.stream()
        .min(BigDecimal::compareTo)
        .orElse(BigDecimal.ZERO);

    BigDecimal maxPrice = prices.stream()
        .max(BigDecimal::compareTo)
        .orElse(BigDecimal.ONE);

    BigDecimal padding = maxPrice
        .subtract(minPrice)
        .multiply(BigDecimal.valueOf(0.2))
        .max(BigDecimal.valueOf(0.1));

    double lowerBound = minPrice.subtract(padding).doubleValue();
    double upperBound = maxPrice.add(padding).doubleValue();

    double range = upperBound - lowerBound;
    NumberAxis yAxis = new NumberAxis();
    yAxis.setAutoRanging(false);
    yAxis.setLowerBound(lowerBound);
    yAxis.setUpperBound(upperBound);
    yAxis.setTickUnit(range / 10);

    return yAxis;
  }

  private XYChart.Series<Number, Number> createSeries(List<BigDecimal> prices) {
    XYChart.Series<Number, Number> series = new XYChart.Series<>();

    for (int i=0; i < prices.size(); i++) {
      series.getData().add(
          new XYChart.Data<>(i + 1, prices.get(i))
      );
    }
    return series;

  }

  public void addRootStyleClass(String style) {
    if (style == null){
      throw new IllegalArgumentException("Enter a valid style.");
    }
    root.getStyleClass().add(style);
  }

  @Override
  public Parent getRoot() {
    return root;
  }

  @Override
  public void update() {
    updateDisplay();
  }
}
