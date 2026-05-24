package no.ntnu.group51.view.components.market;

import java.math.BigDecimal;
import java.util.function.Consumer;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.trading.Leverage;
import no.ntnu.group51.model.trading.TradeMode;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.util.CurrencyFormatter;

public class TradePanel implements View {
  private final VBox root = new VBox();

  private final Label cashLabel = new Label("$0.00");
  private final TextField inputField = new TextField();

  private final Label estimateTitleLabel = new Label();
  private final Label estimateValueLabel = new Label();

  private final Button buyButton = createButton("Buy", "trade-panel-buy-button");
  private final Button sellButton = createButton("Sell", "trade-panel-sell-button");

  private final Button shareButton = createButton("Shares", "trade-panel-mode-button");
  private final Button amountButton = createButton("Amount", "trade-panel-mode-button");

  private final Button leverageOffButton = createButton("Off", "trade-panel-leverage-button");
  private final Button leverage5Button = createButton("5x", "trade-panel-leverage-button");
  private final Button leverage10Button = createButton("10x", "trade-panel-leverage-button");
  private final Button leverage20Button = createButton("20x", "trade-panel-leverage-button");


  public TradePanel() {
    createLayout();
    updateTradeMode(TradeMode.AMOUNT);
    updateLeverage(Leverage.OFF);
    setCash(BigDecimal.ZERO);
  }

  private void createLayout() {
    root.getStyleClass().addAll("card", "trade-panel");
    root.setAlignment(Pos.CENTER);
    root.setSpacing(17);

    Label cashTitle = new Label("Available cash");
    cashTitle.getStyleClass().add("trade-panel-label");

    cashLabel.getStyleClass().add("trade-panel-cash");

    VBox cashBox = new VBox(-6, cashTitle, cashLabel);
    cashBox.getStyleClass().addAll("surface", "trade-panel-cashbox");
    cashBox.setAlignment(Pos.CENTER_LEFT);

    HBox modeButtons = new HBox(8, shareButton, amountButton);
    modeButtons.setAlignment(Pos.CENTER);

    inputField.getStyleClass().addAll("surface", "trade-panel-input");

    estimateTitleLabel.getStyleClass().add("trade-panel-estimate");
    estimateValueLabel.getStyleClass().add("trade-panel-row-value");

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    HBox estimateRow = new HBox(estimateTitleLabel, spacer, estimateValueLabel);
    estimateRow.getStyleClass().addAll("surface", "trade-panel-row");
    estimateRow.setAlignment(Pos.CENTER_LEFT);

    Label leverageLabel = new Label("Leverage");
    leverageLabel.getStyleClass().add("trading-panel-lev-label");

    HBox leverageButtons = new HBox(
        8,
        leverageLabel,
        leverageOffButton,
        leverage5Button,
        leverage10Button,
        leverage20Button
    );
    leverageButtons.setAlignment(Pos.CENTER);
    leverageButtons.getStyleClass().add("trade-panel-leverage-buttons");

    root.getChildren().addAll(
        cashBox,
        modeButtons,
        inputField,
        estimateRow,
        leverageButtons,
        buyButton,
        sellButton);
  }

  private Button createButton(String text, String styleClass) {
    Button button = new Button(text);
    button.getStyleClass().add(styleClass);
    return button;
  }

  public void setCash(BigDecimal cash) {
    if (cash == null) {
      throw new IllegalArgumentException("Cash cannot be null.");
    }
    cashLabel.setText(CurrencyFormatter.format(cash));
  }

  public void updateTradeMode(TradeMode tradeMode) {
    if (tradeMode == null) {
      throw new IllegalArgumentException("Trade mode cannot be null.");
    }

    shareButton.getStyleClass().remove("trade-panel-selected");
    amountButton.getStyleClass().remove("trade-panel-selected");

    if (tradeMode == TradeMode.SHARES) {
      shareButton.getStyleClass().add("trade-panel-selected");
      inputField.setPromptText("Shares");
      estimateTitleLabel.setText("Estimated cost");
      estimateValueLabel.setText("$0.00");
    } else {
      amountButton.getStyleClass().add("trade-panel-selected");
      inputField.setPromptText("Amount");
      estimateTitleLabel.setText("Estimated shares");
      estimateValueLabel.setText("0 shares");
    }
  }

  public void updateLeverage(Leverage leverage) {
    if (leverage == null) {
      throw new IllegalArgumentException("Leverage cannot be null.");
    }
    leverageOffButton.getStyleClass().remove("trade-panel-selected");
    leverage5Button.getStyleClass().remove("trade-panel-selected");
    leverage10Button.getStyleClass().remove("trade-panel-selected");
    leverage20Button.getStyleClass().remove("trade-panel-selected");

    switch (leverage) {
      case OFF -> leverageOffButton.getStyleClass().add("trade-panel-selected");
      case X5 -> leverage5Button.getStyleClass().add("trade-panel-selected");
      case X10 -> leverage10Button.getStyleClass().add("trade-panel-selected");
      case X20 -> leverage20Button.getStyleClass().add("trade-panel-selected");
    }
  }

  public void setEstimateText(String text) {
    if (text == null || text.isBlank()) {
      throw new IllegalArgumentException("Text cannot be null or blank.");
    }

    estimateValueLabel.setText(text);
  }

  public String getInputText() {
    return inputField.getText();
  }

  public void clearInput() {
    inputField.clear();
  }

  public void setOnBuy(Runnable handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }

    buyButton.setOnAction(e -> handler.run());
  }

  public void setOnSell(Runnable handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }

    sellButton.setOnAction(e -> handler.run());
  }

  public void setOnTradeModeChanged(Consumer<TradeMode> handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }

    shareButton.setOnAction(e -> handler.accept(TradeMode.SHARES));
    amountButton.setOnAction(e -> handler.accept(TradeMode.AMOUNT));
  }

  public void setOnLeverageChanged(Consumer<Leverage> handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }

    leverageOffButton.setOnAction(event -> handler.accept(Leverage.OFF));
    leverage5Button.setOnAction(event -> handler.accept(Leverage.X5));
    leverage10Button.setOnAction(event -> handler.accept(Leverage.X10));
    leverage20Button.setOnAction(event -> handler.accept(Leverage.X20));
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
