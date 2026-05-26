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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.util.StyleClass;
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
    topMovers.getStyleClass().add(StyleClass.DASHBOARD_MOVERS_TITLE);

    Label thisWeek = new Label("(This week)");
    thisWeek.getStyleClass().add(StyleClass.DASHBOARD_MOVERS_THIS_WEEK);

    HBox title = new HBox();
    title.getChildren().addAll(topMovers, thisWeek);
    title.setAlignment(Pos.CENTER_LEFT);
    title.setPadding(new Insets(23, 0, 25, 27));
    title.setSpacing(15);

    Separator separator = new Separator();
    separator.getStyleClass().add(StyleClass.SEPARATOR_GREY);
    separator.setPadding(new Insets(15, 0, 0, 0));


    HBox text = new HBox();
    viewMarket.getStyleClass().add(StyleClass.DASHBOARD_VIEW_BUTTON);
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

    root.getStyleClass().addAll(StyleClass.CARD, StyleClass.DASHBOARD_MOVERS_WINDOW);
    root.setAlignment(Pos.CENTER);
    root.setSpacing(10);
  }

  /**
   * Adds a node to the panel.
   *
   * @param node The node you want to add.
   */
  public void addToPanel(Node node) {
    movers.getChildren().add(node);
  }

  /**
   * Clears the entire panel.
   */
  public void clearMovers() {
    movers.getChildren().clear();
  }

  /**
   * Sets an action on pressing the view market button.
   *
   * @param action The action you want to happen.
   */
  public void setOnViewMarket(EventHandler<ActionEvent> action) {
    viewMarket.setOnAction(action);
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
