package no.ntnu.group51.view.components.shared;

import java.math.BigDecimal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.player.PlayerLevel;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.util.StyleClass;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Sidebar navigation view for the main application.
 */
public class SidebarView implements View {

  private final VBox root = new VBox();
  private final ToggleButton dashboardButton = new ToggleButton("Dashboard");
  private final ToggleButton marketButton = new ToggleButton("Market");
  private final ToggleButton portfolioButton = new ToggleButton("Portfolio");
  private final ToggleButton transactionsButton = new ToggleButton("Transactions");
  private final Label currentWeek = new Label("Week <N>");
  private final Label currentLevelLabel = new Label("<CURRENT_LEVEL>");
  private final Label nextLevelLabel = new Label("Next level: " + "<NEXT_LEVEL>");
  private final Button pauseButton = new Button();
  private final ImageView currentLevel = new ImageView();
  private final ProgressBar progressBar = new ProgressBar();
  private Image novice = new Image("/images/novice.png");
  private Image investor = new Image("/images/investor.png");
  private Image speculator = new Image("/images/speculator.png");

  /**
   * Creates the sidebar view.
   */
  public SidebarView() {

    ToggleGroup sidebar = new ToggleGroup();
    sidebar.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
      if (newToggle == null) {
        oldToggle.setSelected(true);
      }
    });

    // Pause button
    FontIcon pauseIcon = new FontIcon("cil-account-logout");
    pauseIcon.setTranslateX(-2);
    pauseButton.setGraphic(pauseIcon);
    pauseButton.getStyleClass().add("sidebar-pause-button");
    HBox pause =  new HBox();
    pause.getChildren().add(pauseButton);
    pause.setAlignment(Pos.CENTER_LEFT);

    // Dashboard button
    dashboardButton.getStyleClass().add(StyleClass.SIDEBAR_BUTTON);
    dashboardButton.setToggleGroup(sidebar);

    FontIcon dashIcon = new FontIcon("cil-applications");
    dashboardButton.setGraphic(dashIcon);
    dashboardButton.setSelected(true);
    dashboardButton.setContentDisplay(ContentDisplay.LEFT);
    dashboardButton.setAlignment(Pos.CENTER_LEFT);
    dashboardButton.setGraphicTextGap(12);

    // Market button
    marketButton.setToggleGroup(sidebar);
    marketButton.getStyleClass().add(StyleClass.SIDEBAR_BUTTON);

    FontIcon marketIcon = new FontIcon("cil-chart-line");
    marketButton.setGraphic(marketIcon);
    marketButton.setContentDisplay(ContentDisplay.LEFT);
    marketButton.setAlignment(Pos.CENTER_LEFT);
    marketButton.setGraphicTextGap(12);

    // Portfolio button
    portfolioButton.setToggleGroup(sidebar);
    portfolioButton.getStyleClass().add(StyleClass.SIDEBAR_BUTTON);

    FontIcon portIcon = new FontIcon("cil-chart-pie");
    portfolioButton.setGraphic(portIcon);
    portfolioButton.setContentDisplay(ContentDisplay.LEFT);
    portfolioButton.setAlignment(Pos.CENTER_LEFT);
    portfolioButton.setGraphicTextGap(12);

    // Transaction button
    transactionsButton.setToggleGroup(sidebar);
    transactionsButton.getStyleClass().add(StyleClass.SIDEBAR_BUTTON);

    FontIcon trIcon = new FontIcon("cil-cash");
    transactionsButton.setGraphic(trIcon);
    transactionsButton.setContentDisplay(ContentDisplay.LEFT);
    transactionsButton.setAlignment(Pos.CENTER_LEFT);
    transactionsButton.setGraphicTextGap(12);

    // Level box

    currentLevel.setImage(novice);
    currentLevel.setFitHeight(103);
    currentLevel.setFitWidth(120);

    StackPane yellowContainer = new StackPane();
    yellowContainer.getChildren().add(currentLevel);
    yellowContainer.getStyleClass().add(StyleClass.YELLOW_BOSS_BABY_BOX);


    Label playerLevelLabel = new Label("Player level");
    playerLevelLabel.getStyleClass().add(StyleClass.PLAYER_LEVEL_LABEL);

    currentLevelLabel.getStyleClass().add(StyleClass.CURRENT_LEVEL_LABEL);

    nextLevelLabel.getStyleClass().add(StyleClass.NEXT_LEVEL_LABEL);

    VBox levelBox = new VBox();
    levelBox.getChildren().addAll(
        playerLevelLabel,
        yellowContainer,
        currentLevelLabel,
        progressBar,
        nextLevelLabel
    );

    levelBox.getStyleClass().add(StyleClass.LEVEL_BOX);
    levelBox.setAlignment(Pos.CENTER);
    levelBox.setSpacing(12);

    FontIcon currentWeekIcon = new FontIcon("cil-calendar");
    currentWeekIcon.getStyleClass().add(StyleClass.CURRENT_WEEK_ICON);
    currentWeek.setGraphic(currentWeekIcon);
    currentWeek.setContentDisplay(ContentDisplay.LEFT);
    currentWeek.setAlignment(Pos.CENTER);
    currentWeek.setGraphicTextGap(12);

    currentWeek.getStyleClass().add(StyleClass.CURRENT_WEEK_LABEL);

    Region spacer1 = new Region();
    spacer1.setPrefHeight(70);
    Region spacer2 = new Region();
    spacer2.setPrefHeight(70);



    root.getChildren().addAll(
        pauseButton,
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
    root.getStyleClass().add(StyleClass.SIDEBAR_PANEL);

  }

  /**
   * Sets the dashboard button action.
   *
   * @param action the action handler
   */
  public void setOnDashboardButton(EventHandler<ActionEvent> action) {
    if (action == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }
    dashboardButton.setOnAction(action);
  }

  /**
   * Sets the market button action.
   *
   * @param action the action handler
   */
  public void setOnMarketButton(EventHandler<ActionEvent> action) {
    if (action == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }
    marketButton.setOnAction(action);
  }

  /**
   * Sets the transaction button action.
   *
   * @param action the action handler
   */
  public void setOnPortfolioButton(EventHandler<ActionEvent> action) {
    if (action == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }
    portfolioButton.setOnAction(action);
  }

  /**
   * Sets the transaction button action.
   *
   * @param action the action handler
   */
  public void setOnTransactionButton(EventHandler<ActionEvent> action) {
    if (action == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }
    transactionsButton.setOnAction(action);
  }

  /**
   * Updates the displayed current week.
   *
   * @param week the current week
   */
  public void setCurrentWeek(int week) {
    currentWeek.setText("Week " + week);
  }

  public void setOnPauseButton(EventHandler<ActionEvent> action) {
    if (action == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }
    pauseButton.setOnAction(action);
  }

  /**
   * Updates the displayed current player level.
   *
   * @param level the current player level
   */
  public void setCurrentLevelLabel(PlayerLevel level) {
    if (level == null) {
      throw new IllegalArgumentException("Level cannot be null.");
    }
    currentLevelLabel.setText(level.toString());
  }

  /**
   * Updates the displayed next player level.
   *
   * @param level the next player level
   */
  public void setNextLevelLabel(PlayerLevel level) {
    if (level != PlayerLevel.NOVICE) {
      nextLevelLabel.setText("Next level: " + level.toString());

    } else {
      nextLevelLabel.setText("Max level");
    }
  }

  /**
   * Sets the level image on the sidebar.
   *
   * @param level The level which the corresponding image should be changed to.
   */
  public void setLevelImage(PlayerLevel level) {
    currentLevel.setImage(new Image("/images/" + level.toString().toLowerCase() + ".png"));
  }

  /**
   * Sets the progress bar. Works as a relation, so inputting 0 will empty it, and 1 will fill it.
   * Inputting something like 200 out of 1000 xp would be the same as 200/1000 = 0.2, so 20% filled.
   *
   * @param xp The xp relation you want to set the progress bar to.
   */
  public void setProgressBar(BigDecimal xp) {
    progressBar.setProgress(xp.doubleValue());
  }

  /**
   * Selects the dashboard button.
   */
  public void toggleDashboard() {
    dashboardButton.setSelected(true);
  }

  /**
   * Selects the market button.
   */
  public void toggleMarket() {
    marketButton.setSelected(true);
  }

  /**
   * Selects the portfolio button.
   */
  public void togglePortfolio() {
    portfolioButton.setSelected(true);
  }

  /**
   * Selects the transaction button.
   */
  public void toggleTransaction() {
    transactionsButton.setSelected(true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Parent getRoot() {
    return root;
  }
}
