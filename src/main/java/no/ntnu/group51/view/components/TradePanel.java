package no.ntnu.group51.view.components;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.Observer;
import no.ntnu.group51.model.enums.Leverage;
import no.ntnu.group51.model.enums.TradeMode;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.view.View;

public class TradePanel implements View, Observer {
  private final VBox root = new VBox();
  private final GameModel gameModel;
  private TradeMode tradeMode = TradeMode.AMOUNT;
  private Leverage selectedLeverage = Leverage.OFF;

  private Label cashLabel;
  private Button buyButton;
  private Button sellButton;

  private Button shareButton;
  private Button amountButton;

  private Button leverageOffButton;
  private Button leverage5Button;
  private Button leverage10Button;
  private Button leverage20Button;

  private TextField inputField;
  private Label estimateTitleLabel;
  private Label estimateValueLabel;


  public TradePanel(GameModel gameModel) {
    this.gameModel = gameModel;

    root.getStyleClass().add("trade-panel");
    root.setAlignment(Pos.CENTER);
    root.setSpacing(17);

    createLayout();
    registerEvents();
    updateSelectedStyles();
    gameModel.addObserver(this);
    updateDisplay();
  }

  private void createLayout() {
    Label cashTitle = new Label("Available cash");
    cashTitle.getStyleClass().add("trade-panel-label");

    cashLabel = new Label();
    cashLabel.getStyleClass().add("trade-panel-cash");

    VBox cashBox = new VBox(-6, cashTitle, cashLabel);
    cashBox.getStyleClass().add("trade-panel-cashbox");
    cashBox.setAlignment(Pos.CENTER_LEFT);

    shareButton = createButton("Shares", "trade-panel-mode-button");
    amountButton = createButton("Amount", "trade-panel-mode-button");

    HBox modeButtons = new HBox(8, shareButton, amountButton);


    inputField = new TextField();
    inputField.getStyleClass().add("trade-panel-input");

    estimateTitleLabel = new Label();
    estimateTitleLabel.getStyleClass().add("trade-panel-estimate");

    estimateValueLabel = new Label();
    estimateValueLabel.getStyleClass().add("trade-panel-row-value");

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    HBox estimateRow = new HBox(
        estimateTitleLabel,
        spacer,
        estimateValueLabel
    );

    estimateRow.getStyleClass().add("trade-panel-row");
    estimateRow.setAlignment(Pos.CENTER_LEFT);

    Label leverageLabel = new Label("Leverage");
    leverageLabel.getStyleClass().add("trading-panel-lev-label");
    leverageOffButton = createButton("Off","trade-panel-leverage-button");
    leverage5Button = createButton("5x","trade-panel-leverage-button");
    leverage10Button = createButton("10x","trade-panel-leverage-button");
    leverage20Button = createButton("20x","trade-panel-leverage-button");

    HBox leverageButtons = new HBox(
        8,
        leverageLabel,
        leverageOffButton,
        leverage5Button,
        leverage10Button,
        leverage20Button
    );

    modeButtons.setAlignment(Pos.CENTER);
    leverageButtons.setAlignment(Pos.CENTER);

    leverageButtons.getStyleClass().add("trade-panel-leverage-buttons");

    buyButton = createButton("Buy", "trade-panel-buy-button");

    sellButton = createButton("Sell", "trade-panel-sell-button");


    root.getChildren().addAll(
        cashBox,
        modeButtons,
        inputField,
        estimateRow,
        leverageButtons,
        buyButton,
        sellButton);
  }

  private void registerEvents() {
    shareButton.setOnAction(e -> {
      tradeMode = TradeMode.SHARES;
      updateSelectedStyles();
    });

    amountButton.setOnAction(e -> {
      tradeMode = TradeMode.AMOUNT;
      updateSelectedStyles();
    });

    leverageOffButton.setOnAction(e -> {
      selectedLeverage = Leverage.OFF;
      updateSelectedStyles();
    });

    leverage5Button.setOnAction(e -> {
      selectedLeverage = Leverage.X5;
      updateSelectedStyles();
    });

    leverage10Button.setOnAction(e -> {
      selectedLeverage = Leverage.X10;
      updateSelectedStyles();
    });

    leverage20Button.setOnAction(e -> {
      selectedLeverage = Leverage.X20;
      updateSelectedStyles();
    });

  }

  private void updateDisplay(){
    Player player = gameModel.getPlayer();

    cashLabel.setText("$" + player.getMoney().toString());
  }

  private Button createButton(String text, String styleClass) {
    Button button = new Button(text);
    button.getStyleClass().add(styleClass);
    return button;
  }

  private void updateSelectedStyles(){
    shareButton.getStyleClass().remove("trade-panel-selected");
    amountButton.getStyleClass().remove("trade-panel-selected");

    if (tradeMode == TradeMode.SHARES) {
      shareButton.getStyleClass().add("trade-panel-selected");
      inputField.setPromptText("Shares");
      estimateTitleLabel.setText("Estimated cost");
      estimateValueLabel.setText("$" + "Soon");
    } else{
      amountButton.getStyleClass().add("trade-panel-selected");
      inputField.setPromptText("Amount");
      estimateTitleLabel.setText("Estimated shares");
      estimateValueLabel.setText("Soon");
    }

    leverageOffButton.getStyleClass().remove("trade-panel-selected");
    leverage5Button.getStyleClass().remove("trade-panel-selected");
    leverage10Button.getStyleClass().remove("trade-panel-selected");
    leverage20Button.getStyleClass().remove("trade-panel-selected");

    switch (selectedLeverage) {
      case OFF -> leverageOffButton.getStyleClass().add("trade-panel-selected");
      case X5 -> leverage5Button.getStyleClass().add("trade-panel-selected");
      case X10 -> leverage10Button.getStyleClass().add("trade-panel-selected");
      case X20 -> leverage20Button.getStyleClass().add("trade-panel-selected");
    }
  }

  public Button getBuyButton() {
    return buyButton;
  }

  public Button getSellButton() {
    return sellButton;
  }

  public TradeMode getTradeMode() {
    return tradeMode;
  }

  public Leverage getSelectedLeverage() {
    return selectedLeverage;
  }

  public String getInputText() {
    return inputField.getText();
  }

  public void setEstimateText(String text) {
    estimateValueLabel.setText(text);
  }


  @Override
  public Parent getRoot() {
    return root;
  }

  @Override
  public void update(){
    updateDisplay();
  }
}
