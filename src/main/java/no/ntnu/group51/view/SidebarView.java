package no.ntnu.group51.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class SidebarView implements View {

  private final VBox root = new VBox();

  public SidebarView() {

    ToggleGroup sidebar = new ToggleGroup();
    sidebar.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
      if (newToggle == null) {
        oldToggle.setSelected(true);
      }
    });

    // Dashboard button
    ToggleButton dashboardButton = new ToggleButton("Dashboard");
    dashboardButton.getStyleClass().add("sidebar-button");
    dashboardButton.setToggleGroup(sidebar);

    FontIcon dashIcon = new FontIcon("cil-applications");
    dashboardButton.setGraphic(dashIcon);
    dashboardButton.setSelected(true);
    dashboardButton.setContentDisplay(ContentDisplay.LEFT);
    dashboardButton.setAlignment(Pos.CENTER_LEFT);
    dashboardButton.setGraphicTextGap(12);

    // Market button
    ToggleButton marketButton = new ToggleButton("Market");
    marketButton.setToggleGroup(sidebar);
    marketButton.getStyleClass().add("sidebar-button");

    FontIcon marketIcon = new FontIcon("cil-chart-line");
    marketButton.setGraphic(marketIcon);
    marketButton.setContentDisplay(ContentDisplay.LEFT);
    marketButton.setAlignment(Pos.CENTER_LEFT);
    marketButton.setGraphicTextGap(12);

    // Portfolio button
    ToggleButton portfolioButton = new ToggleButton("Portfolio");
    portfolioButton.setToggleGroup(sidebar);
    portfolioButton.getStyleClass().add("sidebar-button");

    FontIcon portIcon = new FontIcon("cil-chart-pie");
    portfolioButton.setGraphic(portIcon);
    portfolioButton.setContentDisplay(ContentDisplay.LEFT);
    portfolioButton.setAlignment(Pos.CENTER_LEFT);
    portfolioButton.setGraphicTextGap(12);

    // Transaction button
    ToggleButton transactionsButton = new ToggleButton("Transactions");
    transactionsButton.setToggleGroup(sidebar);
    transactionsButton.getStyleClass().add("sidebar-button");

    FontIcon trIcon = new FontIcon("cil-cash");
    transactionsButton.setGraphic(trIcon);
    transactionsButton.setContentDisplay(ContentDisplay.LEFT);
    transactionsButton.setAlignment(Pos.CENTER_LEFT);
    transactionsButton.setGraphicTextGap(12);

    // Level box
    Image novice = new Image("/images/novice.png");
    Image investor = new Image("/images/investor.png");
    Image speculator = new Image("/images/speculator.png");

    ImageView currentLevel = new ImageView(speculator);
    currentLevel.setFitHeight(103);
    currentLevel.setFitWidth(120);

    StackPane yellowContainer = new StackPane();
    yellowContainer.getChildren().add(currentLevel);
    yellowContainer.getStyleClass().add("yellow-boss-baby-box");

    ProgressBar progressBar = new ProgressBar();

    Label playerLevelLabel = new Label("Player level");
    playerLevelLabel.getStyleClass().add("player-level-label");

    Label currentLevelLabel = new Label("<CURRENT_LEVEL>");
    currentLevelLabel.getStyleClass().add("current-level-label");

    Label nextLevelLabel = new Label("Next level: " + "<NEXT_LEVEL>");
    nextLevelLabel.getStyleClass().add("next-level-label");

    VBox levelBox = new VBox();
    levelBox.getChildren().addAll(
        playerLevelLabel,
        yellowContainer,
        currentLevelLabel,
        progressBar,
        nextLevelLabel
    );

    levelBox.getStyleClass().add("level-box");
    levelBox.setAlignment(Pos.CENTER);
    levelBox.setSpacing(12);

    Label currentWeek = new Label("<Week 11>");
    FontIcon currentWeekIcon = new FontIcon("cil-calendar");
    currentWeekIcon.getStyleClass().add("current-week-icon");
    currentWeek.setGraphic(currentWeekIcon);
    currentWeek.setContentDisplay(ContentDisplay.LEFT);
    currentWeek.setAlignment(Pos.CENTER);
    currentWeek.setGraphicTextGap(12);

    currentWeek.getStyleClass().add("current-week-label");

    Region spacer1 = new Region();
    spacer1.setPrefHeight(70);
    Region spacer2 = new Region();
    spacer2.setPrefHeight(70);



    root.getChildren().addAll(
        currentWeek,
        spacer1,
        dashboardButton,
        marketButton,
        portfolioButton,
        transactionsButton,
        spacer2,
        levelBox
    );

    root.setSpacing(15);
    root.setAlignment(Pos.CENTER);
    root.setPrefWidth(362);
    root.setStyle("-fx-background-color: #171717");

  }



  @Override
  public Parent getRoot() {
    return root;
  }
}
