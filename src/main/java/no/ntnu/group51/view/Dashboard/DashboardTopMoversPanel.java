package no.ntnu.group51.view.Dashboard;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.exchange.Exchange;
import no.ntnu.group51.view.View;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Class for the top movers panel on the dashboard.
 */
public class DashboardTopMoversPanel implements View {

  VBox root  = new VBox();
  VBox movers = new VBox();
  Button viewMarket = new Button("View market");

  /**
   * Creates the top movers panel. Top and bottom part contained in HBoxes.
   * Top gainer and loser created using the TopMovers class. Everything contained in a VBox.
   */
  public DashboardTopMoversPanel() {

    Label topMovers = new Label("Top Movers");
    topMovers.getStyleClass().add("dashboard-movers-title");

    Label thisWeek = new Label("(This week)");
    thisWeek.getStyleClass().add("dashboard-movers-this-week");

    HBox title = new HBox();
    title.getChildren().addAll(topMovers, thisWeek);
    title.setAlignment(Pos.CENTER_LEFT);
    title.setPadding(new Insets(23, 0, 25, 27));
    title.setSpacing(15);

    Separator separator = new Separator();
    separator.getStyleClass().add("separator-grey");
    separator.setPadding(new Insets(15, 0, 0, 0));


    HBox text = new HBox();
    viewMarket.getStyleClass().add("dashboard-view-button");
    text.getChildren().add(viewMarket);
    text.setPadding(new Insets(10, 0, 15, 15));

    VBox lower =  new VBox();
    lower.getChildren().addAll(separator, text);
    lower.setAlignment(Pos.CENTER);


    FontIcon arrow = new FontIcon("cil-arrow-right");
    viewMarket.setGraphic(arrow);
    viewMarket.setContentDisplay(ContentDisplay.RIGHT);
    viewMarket.setAlignment(Pos.CENTER_LEFT);
    viewMarket.setGraphicTextGap(12);
    viewMarket.setPadding(new Insets(0, 0, 15, 15));

    movers.setSpacing(30);

    root.getChildren().addAll(
        title,
        movers,
        separator,
        lower
    );

    root.getStyleClass().addAll("card", "dashboard-movers-window");
    root.setAlignment(Pos.CENTER);
    root.setSpacing(10);
  }

  public void addToPanel(Node node) {
    movers.getChildren().add(node);
  }

  public void clearMovers() {
    movers.getChildren().clear();
  }

  public void setOnViewMarket(EventHandler<ActionEvent> action) {
    viewMarket.setOnAction(action);
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
