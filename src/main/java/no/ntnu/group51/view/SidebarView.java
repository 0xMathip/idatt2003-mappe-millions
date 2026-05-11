package no.ntnu.group51.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SidebarView implements View {

  private final VBox root = new VBox();

  public SidebarView() {

    Button dashboardButton = new Button("Dashboard");
    Button marketButton = new Button("Market");
    Button portfolioButton = new Button("Portfolio");
    Button transactionsButton = new Button("Transactions");

    dashboardButton.getStyleClass().add("sidebar-button-active");
    marketButton.getStyleClass().add("sidebar-button-active");
    portfolioButton.getStyleClass().add("sidebar-button-active");
    transactionsButton.getStyleClass().add("sidebar-button-active");

    root.getChildren().addAll(dashboardButton, marketButton, portfolioButton, transactionsButton);

    root.setSpacing(30);
    root.setAlignment(Pos.CENTER);
    root.setTranslateY(0);
    root.setTranslateX(0);
    root.setStyle("-fx-background-color: #171717");

  }



  @Override
  public Parent getRoot() {
    return root;
  }
}
