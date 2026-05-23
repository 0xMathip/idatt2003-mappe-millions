package no.ntnu.group51.view.components.dashboard;

import java.math.BigDecimal;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Class for creating a VBox difference to be used above the dashboard chart.
 */
public class ChartDifference {

  private ChartDifference() {}

  /**
   * Creates a VBox with whatever differences you may want over
   * the chart in the dashboard.
   *
   * @param title The text above the difference
   * @param relation The relation between the new number and the old number
   * @return The VBox
   */
  public static Parent createChartDifference(String title, BigDecimal relation) {
    if (title == null) {
      throw new IllegalArgumentException("title is null");
    }
    if (relation == null) {
      throw new IllegalArgumentException("relation is null");
    }

    Label titleLabel = new Label(title);
    titleLabel.getStyleClass().add("dashboard-subtext");

    Label differenceLabel = new Label();

    if (relation.compareTo(BigDecimal.ONE) < 0) {
      differenceLabel.setText("-" + BigDecimal.ONE.subtract(relation)
          .multiply(BigDecimal.valueOf(100)).toString() + "%");
      differenceLabel.getStyleClass().add("dashboard-percent-red");

    } else if (relation.compareTo(BigDecimal.ONE) > 0) {
      differenceLabel.setText("+" + relation.subtract(BigDecimal.ONE)
          .multiply(BigDecimal.valueOf(100)).toString() + "%");
      differenceLabel.getStyleClass().add("dashboard-percent-green");
    }

    VBox root = new VBox();
    root.getChildren().addAll(titleLabel, differenceLabel);
    root.setAlignment(Pos.CENTER);

    return root;

  }
}
