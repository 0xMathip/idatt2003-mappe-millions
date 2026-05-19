package no.ntnu.group51.view.Dashboard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.math.BigDecimal;

/**
 * Class for creating the top movers on the dashboard.
 * Has 2 methods for creating a gainer and a loser. They are slightly
 * different
 */
public class TopMovers {

  private TopMovers() {}

  /**
   * Creates a mover.
   *
   * @param type Type "gainer" if gainer, and "loser" if loser
   * @return The GridPane
   */
  public static Parent createMover(String type) {

    if (type == null || !type.equalsIgnoreCase("Gainer") && !type.equalsIgnoreCase("Loser")) {
      throw new IllegalArgumentException("Type is null or invalid type");
    }

    VBox text = new VBox();
    Label topMover = new Label();

    Label company = new Label("TSLA");
    company.getStyleClass().add("dashboard-subtext");

    text.getChildren().addAll(topMover, company);
    text.setAlignment(Pos.CENTER_LEFT);

    VBox gainAmount = new VBox();
    Label gain = new Label();

    Label amount = new Label("$" + "152.3");
    amount.getStyleClass().add("dashboard-subtext");

    gainAmount.getChildren().addAll(gain, amount);
    gainAmount.setAlignment(Pos.CENTER_RIGHT);

    FontIcon circle = new FontIcon("cil-circle");

    if (type.equalsIgnoreCase("Gainer")) {
      topMover.setText("Top Gainer");
      topMover.getStyleClass().add("dashboard-movers-gainer");

      gain.setText("+" + "3.2" + "%");
      gain.getStyleClass().add("dashboard-movers-gainer");

      circle.getStyleClass().add("filled-circle-green");
    }

    if (type.equalsIgnoreCase("Loser")) {
      topMover.setText("Top Loser");
      topMover.getStyleClass().add("dashboard-movers-loser");

      gain.setText("-" + "7.2" + "%");
      gain.getStyleClass().add("dashboard-movers-loser");

      circle.getStyleClass().add("filled-circle-red");
    }

    ColumnConstraints icon = new ColumnConstraints();
    icon.setPercentWidth(10);

    ColumnConstraints left  = new ColumnConstraints();
    left.setPercentWidth(50);

    ColumnConstraints right  = new ColumnConstraints();
    right.setPercentWidth(40);

    GridPane mover = new GridPane();
    mover.getColumnConstraints().addAll(icon, left, right);
    mover.add(circle, 0, 0);
    mover.add(text, 1, 0);
    mover.add(gainAmount, 2, 0);

    mover.setPadding(new Insets(0, 28, 0, 28));

    return mover;
  }
}
