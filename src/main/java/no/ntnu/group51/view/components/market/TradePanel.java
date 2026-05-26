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
import no.ntnu.group51.view.util.StyleClass;

/**
 * Trading panel UI for placing regular and leveraged trades.
 */
public class TradePanel implements View {
  private final VBox root = new VBox();

  private final Label cashLabel = new Label("$0.00");
  private final TextField inputField = new TextField();

  private final Label estimateTitleLabel = new Label();
  private final Label estimateValueLabel = new Label();

  private final Button buyButton = createButton("Buy", StyleClass.TRADE_PANEL_BUY_BUTTON);
  private final Button sellButton = createButton("Sell", StyleClass.TRADE_PANEL_SELL_BUTTON);

  private final Button shareButton = createButton("Shares", StyleClass.TRADE_PANEL_MODE_BUTTON);
  private final Button amountButton = createButton("Amount", StyleClass.TRADE_PANEL_MODE_BUTTON);

  private final Button leverageOffButton =
      createButton("Off", StyleClass.TRADE_PANEL_LEVERAGE_BUTTON);
  private final Button leverage5Button = createButton("5x", StyleClass.TRADE_PANEL_LEVERAGE_BUTTON);
  private final Button leverage10Button =
      createButton("10x", StyleClass.TRADE_PANEL_LEVERAGE_BUTTON);
  private final Button leverage20Button =
      createButton("20x", StyleClass.TRADE_PANEL_LEVERAGE_BUTTON);

  /**
   * Creates the trade panel.
   */
  public TradePanel() {
    createLayout();
    updateTradeMode(TradeMode.AMOUNT);
    updateLeverage(Leverage.OFF);
    setCash(BigDecimal.ZERO);
  }

  private void createLayout() {
    root.getStyleClass().addAll(StyleClass.CARD, StyleClass.TRADE_PANEL);
    root.setAlignment(Pos.CENTER);
    root.setSpacing(17);

    Label cashTitle = new Label("Available cash");
    cashTitle.getStyleClass().add(StyleClass.TRADE_PANEL_LABEL);

    cashLabel.getStyleClass().add(StyleClass.TRADE_PANEL_CASH);

    VBox cashBox = new VBox(-6, cashTitle, cashLabel);
    cashBox.getStyleClass().addAll(StyleClass.SURFACE, StyleClass.TRADE_PANEL_CASHBOX);
    cashBox.setAlignment(Pos.CENTER_LEFT);

    HBox modeButtons = new HBox(8, shareButton, amountButton);
    modeButtons.setAlignment(Pos.CENTER);

    inputField.getStyleClass().addAll(StyleClass.SURFACE, StyleClass.TRADE_PANEL_INPUT);

    estimateTitleLabel.getStyleClass().add(StyleClass.TRADE_PANEL_ESTIMATE);
    estimateValueLabel.getStyleClass().add(StyleClass.TRADE_PANEL_ROW_VALUE);

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    HBox estimateRow = new HBox(estimateTitleLabel, spacer, estimateValueLabel);
    estimateRow.getStyleClass().addAll(StyleClass.SURFACE, StyleClass.TRADE_PANEL_ROW);
    estimateRow.setAlignment(Pos.CENTER_LEFT);

    Label leverageLabel = new Label("Leverage");
    leverageLabel.getStyleClass().add(StyleClass.TRADE_PANEL_LEVERAGE_LABEL);

    HBox leverageButtons = new HBox(
        8,
        leverageLabel,
        leverageOffButton,
        leverage5Button,
        leverage10Button,
        leverage20Button
    );
    leverageButtons.setAlignment(Pos.CENTER);

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

  /**
   * Updates the displayed available cash.
   *
   * @param cash the available cash amount
   * @throws IllegalArgumentException if cash is null
   */
  public void setCash(BigDecimal cash) {
    if (cash == null) {
      throw new IllegalArgumentException("Cash cannot be null.");
    }
    cashLabel.setText(CurrencyFormatter.format(cash));
  }

  /**
   * Updates the selected trade mode and related UI labels.
   *
   * @param tradeMode the active trade mode
   * @throws IllegalArgumentException if tradeMode is null
   */
  public void updateTradeMode(TradeMode tradeMode) {
    if (tradeMode == null) {
      throw new IllegalArgumentException("Trade mode cannot be null.");
    }

    shareButton.getStyleClass().remove(StyleClass.TRADE_PANEL_SELECTED);
    amountButton.getStyleClass().remove(StyleClass.TRADE_PANEL_SELECTED);

    if (tradeMode == TradeMode.SHARES) {
      shareButton.getStyleClass().add(StyleClass.TRADE_PANEL_SELECTED);
      inputField.setPromptText("Shares");
      estimateTitleLabel.setText("Cost");
      estimateValueLabel.setText("$0.00");
    } else {
      amountButton.getStyleClass().add(StyleClass.TRADE_PANEL_SELECTED);
      inputField.setPromptText("Amount");
      estimateTitleLabel.setText("Shares");
      estimateValueLabel.setText("0");
    }
  }

  /**
   * Updates the selected leverage state in the UI.
   *
   * @param leverage the active leverage level
   * @throws IllegalArgumentException if leverage is null
   */
  public void updateLeverage(Leverage leverage) {
    if (leverage == null) {
      throw new IllegalArgumentException("Leverage cannot be null.");
    }
    leverageOffButton.getStyleClass().remove(StyleClass.TRADE_PANEL_SELECTED);
    leverage5Button.getStyleClass().remove(StyleClass.TRADE_PANEL_SELECTED);
    leverage10Button.getStyleClass().remove(StyleClass.TRADE_PANEL_SELECTED);
    leverage20Button.getStyleClass().remove(StyleClass.TRADE_PANEL_SELECTED);

    switch (leverage) {
      case OFF -> leverageOffButton.getStyleClass().add(StyleClass.TRADE_PANEL_SELECTED);
      case X5 -> leverage5Button.getStyleClass().add(StyleClass.TRADE_PANEL_SELECTED);
      case X10 -> leverage10Button.getStyleClass().add(StyleClass.TRADE_PANEL_SELECTED);
      case X20 -> leverage20Button.getStyleClass().add(StyleClass.TRADE_PANEL_SELECTED);
    }
  }

  /**
   * Updates the estimated trade output text.
   *
   * @param text the estimate text
   * @throws IllegalArgumentException if text is null or blank
   */
  public void setEstimateText(String text) {
    if (text == null || text.isBlank()) {
      throw new IllegalArgumentException("Text cannot be null or blank.");
    }

    estimateValueLabel.setText(text);
  }

  /**
   * Returns the current trade input text.
   *
   * @return the entered input text
   */
  public String getInputText() {
    return inputField.getText();
  }

  /**
   * Clears the trade input field.
   */
  public void clearInput() {
    inputField.clear();
  }

  /**
   * Sets the action to run when the buy button is pressed.
   *
   * @param handler the buy handler
   * @throws IllegalArgumentException if handler is null
   */
  public void setOnBuy(Runnable handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }

    buyButton.setOnAction(e -> handler.run());
  }

  /**
   * Sets the action to run when the sell button is pressed.
   *
   * @param handler the sell handler
   * @throws IllegalArgumentException if handler is null
   */
  public void setOnSell(Runnable handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }

    sellButton.setOnAction(e -> handler.run());
  }

  /**
   * Sets the action to run when the trade mode changes.
   *
   * @param handler the trade mode handler
   * @throws IllegalArgumentException if handler is null
   */
  public void setOnTradeModeChanged(Consumer<TradeMode> handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }

    shareButton.setOnAction(e -> handler.accept(TradeMode.SHARES));
    amountButton.setOnAction(e -> handler.accept(TradeMode.AMOUNT));
  }

  /**
   * Sets the action to run when the leverage selection changes.
   *
   * @param handler the leverage change handler
   * @throws IllegalArgumentException if handler is null
   */
  public void setOnLeverageChanged(Consumer<Leverage> handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }

    leverageOffButton.setOnAction(event -> handler.accept(Leverage.OFF));
    leverage5Button.setOnAction(event -> handler.accept(Leverage.X5));
    leverage10Button.setOnAction(event -> handler.accept(Leverage.X10));
    leverage20Button.setOnAction(event -> handler.accept(Leverage.X20));
  }

  /**
   * Sets the action to run when the trade input changes.
   *
   * @param handler the input change handler
   * @throws IllegalArgumentException if handler is null
   */
  public void setOnInputChanged(Runnable handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }

    inputField.textProperty().addListener(
        (obs, oldValue, newValue) -> handler.run()
    );
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Parent getRoot() {
    return root;
  }
}
