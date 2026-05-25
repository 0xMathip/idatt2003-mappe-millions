package no.ntnu.group51.view.components.dashboard;

import java.math.BigDecimal;

import javafx.scene.Node;
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

    root.setSpacing(20);
  }

  /**
   * Used to add something to the networth or available cash panel.
   *
   * @param node the node you want to add
   */
  public void setPanel(Node node) {
    root.getChildren().add(node);
  }

  /**
   * Clears the panels. Used to reset/update.
   */
  public void clearPanel() {
    root.getChildren().clear();
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
