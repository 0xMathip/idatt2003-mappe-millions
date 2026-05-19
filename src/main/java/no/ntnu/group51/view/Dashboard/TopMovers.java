package no.ntnu.group51.view.Dashboard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Class for creating the top movers on the dashboard.
 * Has 2 methods for creating a gainer and a loser. They are slightly
 * different
 */
public class TopMovers {

  private TopMovers() {}

  /**
   * Creates a top gainer.
   *
   * @return
   */
  public static Parent createGainer() {

    VBox text = new VBox();
    Label gainer = new Label("Top Gainer");
    gainer.getStyleClass().add("dashboard-movers-gainer");

    Label company = new Label("TSLA");
    company.getStyleClass().add("dashboard-subtext");

    text.getChildren().addAll(gainer, company);
    text.setAlignment(Pos.CENTER_LEFT);

    VBox gainAmount = new VBox();
    Label gain = new Label("+" + "7.3" + "%");
    gain.getStyleClass().add("dashboard-movers-gainer");

    Label amount = new Label("$" + "152.3");
    amount.getStyleClass().add("dashboard-subtext");

    gainAmount.getChildren().addAll(gain, amount);
    gainAmount.setAlignment(Pos.CENTER_RIGHT);

    GridPane mover = new GridPane();
    FontIcon circle = new FontIcon("cil-circle");
    circle.getStyleClass().add("filled-circle-green");

    ColumnConstraints icon = new ColumnConstraints();
    icon.setPercentWidth(10);

    ColumnConstraints left  = new ColumnConstraints();
    left.setPercentWidth(50);

    ColumnConstraints right  = new ColumnConstraints();
    right.setPercentWidth(40);

    mover.getColumnConstraints().addAll(icon, left, right);
    mover.add(circle, 0, 0);
    mover.add(text, 1, 0);
    mover.add(gainAmount, 2, 0);

    mover.setPadding(new Insets(0, 28, 0, 28));

    return mover;
  }

  public static Parent createLoser() {

    VBox text = new VBox();
    Label gainer = new Label("Top Loser");
    gainer.getStyleClass().add("dashboard-movers-loser");

    Label company = new Label("META");
    company.getStyleClass().add("dashboard-subtext");

    text.getChildren().addAll(gainer, company);
    text.setAlignment(Pos.CENTER_LEFT);

    VBox gainAmount = new VBox();
    Label gain = new Label("-" + "2.3" + "%");
    gain.getStyleClass().add("dashboard-movers-loser");

    Label amount = new Label("$" + "152.3");
    amount.getStyleClass().add("dashboard-subtext");

    gainAmount.getChildren().addAll(gain, amount);
    gainAmount.setAlignment(Pos.CENTER_RIGHT);

    GridPane mover = new GridPane();
    FontIcon circle = new FontIcon("cil-circle");
    circle.getStyleClass().add("filled-circle-red");

    ColumnConstraints icon = new ColumnConstraints();
    icon.setPercentWidth(10);

    ColumnConstraints left  = new ColumnConstraints();
    left.setPercentWidth(50);

    ColumnConstraints right  = new ColumnConstraints();
    right.setPercentWidth(40);

    mover.getColumnConstraints().addAll(icon, left, right);
    mover.add(circle, 0, 0);
    mover.add(text, 1, 0);
    mover.add(gainAmount, 2, 0);

    mover.setPadding(new Insets(0, 28, 0, 28));

    return mover;
  }
}
