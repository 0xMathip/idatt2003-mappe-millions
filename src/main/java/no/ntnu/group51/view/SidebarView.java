package no.ntnu.group51.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class SidebarView implements View {

  private final VBox root = new VBox();

  public SidebarView() {

    ToggleGroup sidebar = new ToggleGroup();

    ToggleButton dashboardButton = new ToggleButton("Dashboard");
    dashboardButton.getStyleClass().add("sidebar-button");
    dashboardButton.setToggleGroup(sidebar);

    FontIcon dashIcon = new FontIcon("cil-applications");
    dashboardButton.setGraphic(dashIcon);
    dashboardButton.setSelected(true);
    dashboardButton.setContentDisplay(ContentDisplay.LEFT);
    dashboardButton.setAlignment(Pos.CENTER_LEFT);
    dashboardButton.setGraphicTextGap(12);

    ToggleButton marketButton = new ToggleButton("Market");
    marketButton.setToggleGroup(sidebar);
    marketButton.getStyleClass().add("sidebar-button");

    FontIcon marketIcon = new FontIcon("cil-chart-line");
    marketButton.setGraphic(marketIcon);
    marketButton.setContentDisplay(ContentDisplay.LEFT);
    marketButton.setAlignment(Pos.CENTER_LEFT);
    marketButton.setGraphicTextGap(12);

    ToggleButton portfolioButton = new ToggleButton("Portfolio");
    portfolioButton.setToggleGroup(sidebar);
    portfolioButton.getStyleClass().add("sidebar-button");

    FontIcon portIcon = new FontIcon("cil-chart-pie");
    portfolioButton.setGraphic(portIcon);
    portfolioButton.setContentDisplay(ContentDisplay.LEFT);
    portfolioButton.setAlignment(Pos.CENTER_LEFT);
    portfolioButton.setGraphicTextGap(12);

    ToggleButton transactionsButton = new ToggleButton("Transactions");
    transactionsButton.setToggleGroup(sidebar);
    transactionsButton.getStyleClass().add("sidebar-button");

    FontIcon trIcon = new FontIcon("cil-cash");
    transactionsButton.setGraphic(trIcon);
    transactionsButton.setContentDisplay(ContentDisplay.LEFT);
    transactionsButton.setAlignment(Pos.CENTER_LEFT);
    transactionsButton.setGraphicTextGap(12);


    root.getChildren().addAll(dashboardButton, marketButton, portfolioButton, transactionsButton);

    root.setSpacing(30);
    root.setAlignment(Pos.CENTER);
    root.setPrefWidth(362);
    root.setStyle("-fx-background-color: #171717");

  }



  @Override
  public Parent getRoot() {
    return root;
  }
}
