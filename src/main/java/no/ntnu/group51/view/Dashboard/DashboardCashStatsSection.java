package no.ntnu.group51.view.Dashboard;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import no.ntnu.group51.view.View;

import java.math.BigDecimal;

public class DashboardCashStatsSection implements View {

  VBox root = new VBox();

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
