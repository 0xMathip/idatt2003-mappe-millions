package no.ntnu.group51.view.Dashboard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.view.View;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Class for the transaction panel on the dashboard.
 */
public class DashboardTransactionPanel implements View {

  VBox root  = new VBox();

  /**
   * Creates a transaction panel. HBox for the text on the lower part.
   * Transaction listings created using the TransactionListing class.
   * Everything contained in a VBox.
   */
  public DashboardTransactionPanel() {

    Separator separator = new Separator();
    separator.getStyleClass().add("separator-grey");
    separator.setPadding(new Insets(15, 0, 0, 0));

    HBox lower = new HBox();
    Button viewAll = new Button("View all transactions");
    viewAll.getStyleClass().add("dashboard-view-button");
    lower.getChildren().add(viewAll);
    lower.setPadding(new Insets(0, 0, 0, 15));

    FontIcon arrow = new FontIcon("cil-arrow-right");
    viewAll.setGraphic(arrow);
    viewAll.setContentDisplay(ContentDisplay.RIGHT);
    viewAll.setAlignment(Pos.CENTER_LEFT);
    viewAll.setGraphicTextGap(12);

    VBox upper = new VBox();
    upper.getChildren().addAll(
        TransactionListing.createTransactionListing(),
        TransactionListing.createTransactionListing(),
        TransactionListing.createTransactionListing()
    );

    upper.setSpacing(15);

    root.getChildren().addAll(
        upper,
        separator,
        lower
    );

    root.getStyleClass().addAll("card", "dashboard-transaction-window");
    root.setAlignment(Pos.CENTER);
  }


  @Override
  public Parent getRoot() {
    return root;
  }
}
