package no.ntnu.group51.view.Dashboard;

import java.math.BigDecimal;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import no.ntnu.group51.view.View;

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

    Separator separator1 = new Separator();
    separator1.getStyleClass().add("separator-white");
    separator1.setOrientation(Orientation.VERTICAL);

    Separator separator2 = new Separator();
    separator2.getStyleClass().add("separator-white");
    separator2.setOrientation(Orientation.VERTICAL);

    root.getChildren().addAll(
        ChartDifference.createChartDifference("This week", BigDecimal.valueOf(1.032)),
        separator1,
        ChartDifference.createChartDifference("Last 4 weeks", BigDecimal.valueOf(0.781)),
        separator2,
        ChartDifference.createChartDifference("All time", BigDecimal.valueOf(2.422))
    );

    root.setSpacing(60);
    root.setAlignment(Pos.CENTER);
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
