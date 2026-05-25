package no.ntnu.group51.view.components.dashboard;

import java.math.BigDecimal;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import no.ntnu.group51.view.util.StyleClass;

/**
 * Class for creating a panel of both net worth and available cash to be used on the dashboard.
 */
public class CashPanel {

  private CashPanel() {}

  /**
   * Creates a VBox with 2 labels. One for the text above and one for the amount.
   *
   * @param title The text above the amount
   * @param amount The amount corresponding to the text
   * @return The VBox
   */
  public static Parent createCashPanel(String title, BigDecimal amount) {
    VBox root = new VBox();

    Label titleLabel = new Label(title);
    titleLabel.getStyleClass().add(StyleClass.DASHBOARD_SUBTEXT);

    Label amountLabel = new Label("$" + amount.toString());
    amountLabel.getStyleClass().add(StyleClass.DASHBOARD_CASH_STAT_AMOUNT);

    root.getChildren().addAll(
        titleLabel,
        amountLabel
    );

    root.getStyleClass().addAll(StyleClass.CARD, StyleClass.DASHBOARD_CASH_STAT_WINDOW);
    root.setPadding(new Insets(18, 0, 15, 18));
    root.setSpacing(5);

    return root;
  }
}
