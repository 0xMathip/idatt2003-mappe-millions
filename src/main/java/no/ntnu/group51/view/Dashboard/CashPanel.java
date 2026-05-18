package no.ntnu.group51.view.Dashboard;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;

public class CashPanel {

  private CashPanel() {}

  public static Parent createCashPanel(String title, BigDecimal amount) {
    VBox root = new VBox();

    Label titleLabel = new Label(title);
    titleLabel.getStyleClass().add("dashboard-subtext");

    Label amountLabel = new Label("$" + amount.toString());
    amountLabel.getStyleClass().add("dashboard-cash-stat-amount");

    root.getChildren().addAll(
        titleLabel,
        amountLabel
    );

    root.getStyleClass().addAll("card", "dashboard-cash-stat-window");
    root.setPadding(new Insets(18, 0, 15, 18));
    root.setSpacing(5);

    return root;
  }
}
