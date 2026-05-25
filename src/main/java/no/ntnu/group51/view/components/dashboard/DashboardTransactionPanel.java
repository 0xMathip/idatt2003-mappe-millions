package no.ntnu.group51.view.components.dashboard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.util.StyleClass;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Class for the transaction panel on the dashboard.
 */
public class DashboardTransactionPanel implements View {

  private final BorderPane root  = new BorderPane();
  private final VBox upper = new VBox();
  private final Button viewAll = new Button("View all transactions");
  private final Label emptyLabel = new Label("No transactions yet");

  /**
   * Creates a transaction panel. HBox for the text on the lower part.
   * Transaction listings created using the TransactionListing class.
   * Everything contained in a VBox.
   */
  public DashboardTransactionPanel() {

    Separator separator = new Separator();
    separator.getStyleClass().add(StyleClass.SEPARATOR_GREY);
    separator.setPadding(new Insets(15, 0, 0, 0));

    HBox text = new HBox();
    viewAll.getStyleClass().add(StyleClass.DASHBOARD_VIEW_BUTTON);
    text.getChildren().add(viewAll);
    text.setPadding(new Insets(10, 0, 15, 15));

    FontIcon arrow = new FontIcon("cil-arrow-right");
    viewAll.setGraphic(arrow);
    viewAll.setContentDisplay(ContentDisplay.RIGHT);
    viewAll.setAlignment(Pos.CENTER_LEFT);
    viewAll.setGraphicTextGap(12);

    VBox lower = new VBox();
    lower.getChildren().addAll(separator, text);
    lower.setAlignment(Pos.CENTER);
    lower.setSpacing(0);

    upper.setSpacing(15);
    upper.setAlignment(Pos.CENTER);

    root.setCenter(upper);
    root.setBottom(lower);


    root.getStyleClass().addAll(StyleClass.CARD, StyleClass.DASHBOARD_TRANSACTION_WINDOW);
  }

  public void clearListings() {
    upper.getChildren().clear();
  }

  public void addToPanel(Node node) {
    upper.getChildren().add(node);
  }

  public void setOnViewAll(EventHandler<ActionEvent> action) {
    viewAll.setOnAction(action);
  }


  @Override
  public Parent getRoot() {
    return root;
  }
}
