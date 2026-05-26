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
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.util.StyleClass;

/**
 * UI card displaying portfolio net worth history as an area chart.
 */
public class PortfolioChartCard implements View {

  private final StackPane root = new StackPane();
  private final boolean showLabels;
  private AreaChart<Number, Number> portfolioChart;

  /**
   * Creates a portfolio chart card.
   *
   * @param showLabels whether axis labels should be visible
   */
  public PortfolioChartCard(boolean showLabels) {
    this.showLabels = showLabels;
    root.setAlignment(Pos.CENTER_LEFT);
  }

  /**
   * Updates the chart with net worth history values.
   *
   * @param netWorthHistory the net worth values to display
   * @throws IllegalArgumentException if netWorthHistory is null or empty
   */
  public void updateValues(List<BigDecimal> netWorthHistory) {
    if (netWorthHistory == null) {
      throw new IllegalArgumentException("Net worth history cannot be null.");
    }
    if (netWorthHistory.isEmpty()) {
      throw new IllegalArgumentException("Net worth history cannot be empty.");
    }

    updateDisplay(netWorthHistory);
  }

  /**
   * Clears the chart.
   */
  public void clear() {
    root.getChildren().clear();
    portfolioChart = null;
  }

  private void updateDisplay(List<BigDecimal> netWorthHistory) {
    root.getChildren().clear();
    portfolioChart = createChart(netWorthHistory);
    root.getChildren().add(portfolioChart);
  }

  private AreaChart<Number, Number> createChart(List<BigDecimal> netWorthHistory) {
    NumberAxis xAxis = createXAxis(netWorthHistory);
    NumberAxis yAxis = createYAxis(netWorthHistory);

    AreaChart<Number, Number> portfolioChart = new AreaChart<>(xAxis, yAxis);
    portfolioChart.setLegendVisible(false);
    portfolioChart.setAnimated(false);
    portfolioChart.setCreateSymbols(false);
    portfolioChart.setHorizontalGridLinesVisible(false);
    portfolioChart.setVerticalGridLinesVisible(false);
    portfolioChart.getStyleClass().add(StyleClass.STOCK_CHART_CARD_CHART);

    XYChart.Series<Number, Number> series = createSeries(netWorthHistory);
    portfolioChart.getData().add(series);

    return portfolioChart;
  }

  private NumberAxis createXAxis(List<BigDecimal> netWorthHistory) {
    NumberAxis xAxis = new NumberAxis();
    xAxis.setAutoRanging(false);
    xAxis.setLowerBound(1);
    xAxis.setUpperBound(Math.max(1, netWorthHistory.size()));
    xAxis.setTickUnit(2);
    xAxis.setTickLabelsVisible(showLabels);
    xAxis.setTickMarkVisible(false);
    xAxis.setMinorTickVisible(false);

    return xAxis;
  }

  private NumberAxis createYAxis(List<BigDecimal> netWorthHistory) {
    BigDecimal minValue = netWorthHistory.stream()
        .min(BigDecimal::compareTo)
        .orElse(BigDecimal.ZERO);

    BigDecimal maxValue = netWorthHistory.stream()
        .max(BigDecimal::compareTo)
        .orElse(BigDecimal.ONE);

    BigDecimal padding = maxValue
        .subtract(minValue)
        .multiply(BigDecimal.valueOf(0.2))
        .max(BigDecimal.valueOf(1));

    double lowerBound = minValue.subtract(padding).doubleValue();
    double upperBound = maxValue.add(padding).doubleValue();

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

  private XYChart.Series<Number, Number> createSeries(List<BigDecimal> netWorthHistory) {
    XYChart.Series<Number, Number> series = new XYChart.Series<>();

    for (int i = 0; i < netWorthHistory.size(); i++) {
      series.getData().add(
          new XYChart.Data<>(i + 1, netWorthHistory.get(i))
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