package no.ntnu.group51.view.components.shared;

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
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.util.StyleClass;

/**
 * UI card displaying a stock's historical price development as an area chart.
 */
public class StockChartCard implements View {
  private final StackPane root = new StackPane();
  private final boolean showLabels;
  private AreaChart<Number, Number> stockChart;

  /**
   * Creates a stock chart card.
   *
   * @param showLabels whether axis labels should be visible
   */
  public StockChartCard(boolean showLabels) {
    this.showLabels = showLabels;

    root.setAlignment(Pos.CENTER_LEFT);
  }

  /**
   * Updates the chart with the selected stock's price history.
   *
   * @param stock the stock to display
   * @throws IllegalArgumentException if stock is null
   */
  public void updateStock(Stock stock) {
    if (stock == null) {
      throw new IllegalArgumentException("Stock cannot be null.");
    }
    updateDisplay(stock);
  }

  /**
   * Clears the chart.
   */
  public void clear() {
    root.getChildren().clear();
    stockChart = null;
  }


  private void updateDisplay(Stock stock) {
    root.getChildren().clear();
    stockChart = createChart(stock);
    root.getChildren().add(stockChart);
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
    stockChart.getStyleClass().add(StyleClass.STOCK_CHART_CARD_CHART);

    XYChart.Series<Number, Number> series = createSeries(prices);
    stockChart.getData().add(series);

    return stockChart;
  }

  private NumberAxis createXAxis(List<BigDecimal> prices) {
    NumberAxis xAxis = new NumberAxis();
    xAxis.setAutoRanging(false);
    xAxis.setLowerBound(1);
    xAxis.setUpperBound(Math.max(1, prices.size()));
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
        return String.format(Locale.US, "$%.1f", value.doubleValue());
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

    for (int i = 0; i < prices.size(); i++) {
      series.getData().add(
          new XYChart.Data<>(i + 1, prices.get(i))
      );
    }
    return series;

  }

  /**
   * Adds a style class to the chart root.
   *
   * @param style the style class to add
   * @throws IllegalArgumentException if style is null or blank
   */
  public void addRootStyleClass(String style) {
    if (style == null || style.isBlank()) {
      throw new IllegalArgumentException("Style cannot be null or blank.");
    }
    root.getStyleClass().add(style);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Parent getRoot() {
    return root;
  }
}
