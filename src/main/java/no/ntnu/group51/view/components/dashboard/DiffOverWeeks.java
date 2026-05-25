package no.ntnu.group51.view.components.dashboard;

import java.math.BigDecimal;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.util.StyleClass;

/**
 * Class for the section above the chart.
 */
public class DiffOverWeeks implements View {

  private final HBox root = new HBox();

  /**
   * Creates the difference labels using the ChartDifference class.
   * Contains it all in an HBox with separators between.
   */
  public DiffOverWeeks() {
    root.setSpacing(60);
    root.setAlignment(Pos.CENTER);

    updateDifferences(
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO
    );
  }

  /**
   * Updates the displayed differences.
   *
   * @param thisWeek   The change for this week.
   * @param last4Weeks The change for the last 4 weeks.
   * @param allTime    The all time change.
   */
  public void updateDifferences(
      BigDecimal thisWeek,
      BigDecimal last4Weeks,
      BigDecimal allTime
  ) {
    if (thisWeek == null || last4Weeks == null || allTime == null) {
      throw new IllegalArgumentException("Differences cannot be null.");
    }

    root.getChildren().clear();

    Separator separator1 = new Separator();
    separator1.getStyleClass().add(StyleClass.SEPARATOR_WHITE);
    separator1.setOrientation(Orientation.VERTICAL);

    Separator separator2 = new Separator();
    separator2.getStyleClass().add(StyleClass.SEPARATOR_WHITE);
    separator2.setOrientation(Orientation.VERTICAL);

    root.getChildren().addAll(
        ChartDifference.createChartDifference("This week", thisWeek),
        separator1,
        ChartDifference.createChartDifference("Last 4 weeks", last4Weeks),
        separator2,
        ChartDifference.createChartDifference("All time", allTime)
    );
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
