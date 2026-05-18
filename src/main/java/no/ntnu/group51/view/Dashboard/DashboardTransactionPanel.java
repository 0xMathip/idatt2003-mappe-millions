package no.ntnu.group51.view.Dashboard;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import no.ntnu.group51.view.View;
import org.kordamp.ikonli.javafx.FontIcon;

public class DashboardTransactionPanel implements View {

  VBox root  = new VBox();

  public DashboardTransactionPanel() {

    Separator separator = new Separator();
    separator.getStyleClass().add("separator-grey");

    Button viewAll = new Button("View all transactions");
    viewAll.getStyleClass().add("dashboard-transaction-view-all");

    FontIcon arrow = new FontIcon("cil-arrow-right");
    viewAll.setGraphic(arrow);
    viewAll.setContentDisplay(ContentDisplay.RIGHT);
    viewAll.setAlignment(Pos.CENTER_LEFT);
    viewAll.setGraphicTextGap(12);

    root.getChildren().addAll(
        TransactionListing.createTransactionListing(),
        TransactionListing.createTransactionListing(),
        TransactionListing.createTransactionListing(),
        separator,
        viewAll
    );

    root.getStyleClass().addAll("card", "dashboard-transaction-window");
  }


  @Override
  public Parent getRoot() {
    return root;
  }
}
