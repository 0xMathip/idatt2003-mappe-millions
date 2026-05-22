package no.ntnu.group51.view.components;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.Observer;
import no.ntnu.group51.model.stocks.Stock;
import no.ntnu.group51.view.View;

public class StockChartCard implements View, Observer {
  private final StackPane root = new StackPane();
  private final GameModel gameModel;
  private final boolean showLabels;
  private AreaChart<Number, Number> stockChart;

  public StockChartCard(GameModel gameModel, boolean showLabels) {
    if (gameModel == null) {
      throw new IllegalArgumentException("Game model cannot be null.");
    }

    this.gameModel = gameModel;
    this.showLabels = showLabels;

    root.setAlignment(Pos.CENTER_LEFT);

    gameModel.addObserver(this);
    updateDisplay();
  }

  public StockChartCard(GameModel gameModel) {
    this(gameModel, true);
  }

  private AreaChart<Number, Number> createChart(Stock stock) {
    List<BigDecimal> prices = stock.getHistoricalPrices();

    NumberAxis xAxis = createXAxis(prices);
    NumberAxis yAxis = createYAxis(prices);

    AreaChart<Number, Number> stockChart = new AreaChart<>(xAxis, yAxis);
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
    xAxis.setUpperBound(prices.size() - 1);
    xAxis.setTickUnit(2);
    xAxis.setTickLabelsVisible(showLabels);
    xAxis.setTickMarkVisible(false);
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
    yAxis.setSide(Side.RIGHT);
    yAxis.setTickMarkVisible(false);
    yAxis.setMinorTickVisible(false);
    yAxis.setTickLabelsVisible(showLabels);
    yAxis.setLowerBound(lowerBound);
    yAxis.setUpperBound(upperBound);
    yAxis.setTickUnit(range / 5);

    yAxis.setTickLabelFormatter(new StringConverter<>() {
      @Override
      public String toString(Number value) {
        return String.format(Locale.US,"$%.1f", value.doubleValue());
      }

      @Override
      public Number fromString(String string) {
        return null;
      }
    });

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
