package no.ntnu.group51.view.components.dashboard;

import java.math.BigDecimal;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import no.ntnu.group51.view.View;

/**
 * The section of the dashboard where the net worth and available cash is.
 */
public class DashboardCashStatsSection implements View {

  VBox root = new VBox();

  /**
   * Creates the section by using the CashPanel class to
   * create 2 VBoxes to be used within another VBox.
   */
  public DashboardCashStatsSection() {

    root.getChildren().addAll(
        CashPanel.createCashPanel("Net Worth", new BigDecimal("173057.4")),
        CashPanel.createCashPanel("Available cash", new BigDecimal("13057.4"))
    );

    root.setSpacing(20);
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
