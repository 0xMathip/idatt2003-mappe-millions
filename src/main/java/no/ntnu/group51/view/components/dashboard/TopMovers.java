package no.ntnu.group51.view.components.dashboard;

import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.view.util.CurrencyFormatter;
import no.ntnu.group51.view.util.PercentFormatter;
import no.ntnu.group51.view.util.StyleClass;
import org.kordamp.ikonli.javafx.FontIcon;

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
  public static Parent createMover(String type, List<Stock> movers) {

    if (type == null || !type.equalsIgnoreCase("Gainer") && !type.equalsIgnoreCase("Loser")) {
      throw new IllegalArgumentException("Type is null or invalid type");
    }

    VBox text = new VBox();
    Label topMover = new Label();

    Label company = new Label(movers.getFirst().getSymbol());
    company.getStyleClass().add(StyleClass.DASHBOARD_SUBTEXT);

    text.getChildren().addAll(topMover, company);
    text.setAlignment(Pos.CENTER_LEFT);

    VBox gainAmount = new VBox();
    Label gain = new Label();

    Label amount = new Label(CurrencyFormatter.format(movers.getFirst().getLatestPriceChange()));
    amount.getStyleClass().add(StyleClass.DASHBOARD_SUBTEXT);

    gainAmount.getChildren().addAll(gain, amount);
    gainAmount.setAlignment(Pos.CENTER_RIGHT);

    FontIcon circle = new FontIcon("cil-circle");

    if (type.equalsIgnoreCase("Gainer")) {
      topMover.setText("Top Gainer");
      topMover.getStyleClass().add(StyleClass.DASHBOARD_MOVERS_GAINER);

      gain.setText(PercentFormatter.format(movers.getFirst().getLatestPriceChangePercent()));
      gain.getStyleClass().add(StyleClass.DASHBOARD_MOVERS_GAINER);

      circle.getStyleClass().add(StyleClass.FILLED_CIRCLE_GREEN);
    }

    if (type.equalsIgnoreCase("Loser")) {
      topMover.setText("Top Loser");
      topMover.getStyleClass().add(StyleClass.DASHBOARD_MOVERS_LOSER);

      gain.setText(PercentFormatter.format(movers.getFirst().getLatestPriceChangePercent()));
      gain.getStyleClass().add(StyleClass.DASHBOARD_MOVERS_LOSER);

      circle.getStyleClass().add(StyleClass.FILLED_CIRCLE_RED);
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
