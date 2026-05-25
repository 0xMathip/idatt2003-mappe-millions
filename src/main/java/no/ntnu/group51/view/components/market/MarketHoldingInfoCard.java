package no.ntnu.group51.view.components.market;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.util.StyleClass;

public class MarketHoldingInfoCard implements View {

  private final HBox root = new HBox(14);

  private final Label normalValueLabel = new Label("0 shares");

  private final Label leveragedValueLabel = new Label("0 shares");
  private final Label leveragedSubLabel = new Label("Liq: -");
  private final Label leverageBadge = new Label("-");

  public MarketHoldingInfoCard() {
    createLayout();
    clear();
  }

  private void createLayout() {
    root.getStyleClass().addAll(StyleClass.CARD, StyleClass.MARKET_HOLDING_INFO);
    root.setAlignment(Pos.CENTER);

    VBox normalBox = createNormalBox();
    VBox leveragedBox = createLeveragedBox();

    root.getChildren().addAll(normalBox, leveragedBox);
  }

  private VBox createNormalBox() {
    Label titleLabel = new Label("Normal");
    titleLabel.getStyleClass().add(StyleClass.MARKET_HOLDING_BOX_TITLE);

    normalValueLabel.getStyleClass().add(StyleClass.MARKET_HOLDING_BOX_VALUE);

    VBox box = new VBox(4, titleLabel, normalValueLabel);
    box.getStyleClass().addAll(StyleClass.SURFACE, StyleClass.MARKET_HOLDING_BOX);
    box.setAlignment(Pos.CENTER_LEFT);

    return box;
  }

  private VBox createLeveragedBox() {
    Label titleLabel = new Label("Leveraged");
    titleLabel.getStyleClass().add(StyleClass.MARKET_HOLDING_BOX_TITLE);

    leverageBadge.getStyleClass().add(StyleClass.MARKET_HOLDING_LEVERAGE_BADGE);
    leveragedValueLabel.getStyleClass().add(StyleClass.MARKET_HOLDING_BOX_VALUE);
    leveragedSubLabel.getStyleClass().add(StyleClass.MARKET_HOLDING_BOX_SUBVALUE);

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    HBox header = new HBox(titleLabel, spacer, leverageBadge);
    header.setAlignment(Pos.CENTER_LEFT);

    VBox box = new VBox(4, header, leveragedValueLabel, leveragedSubLabel);
    box.getStyleClass().addAll(
        StyleClass.SURFACE,
        StyleClass.MARKET_HOLDING_BOX,
        StyleClass.MARKET_HOLDING_BOX_LEVERAGED
    );
    box.setAlignment(Pos.CENTER_LEFT);

    return box;
  }

  public void updateNormalHoldings(String sharesText) {
    if (sharesText == null || sharesText.isBlank()) {
      throw new IllegalArgumentException("Shares text cannot be null or blank.");
    }

    normalValueLabel.setText(sharesText);
  }

  public void updateLeveragedHoldings(
      String sharesText,
      String leverageText,
      String liquidationText
  ) {
    if (sharesText == null || sharesText.isBlank()) {
      throw new IllegalArgumentException("Shares text cannot be null or blank.");
    }
    if (leverageText == null || leverageText.isBlank()) {
      throw new IllegalArgumentException("Leverage text cannot be null or blank.");
    }
    if (liquidationText == null || liquidationText.isBlank()) {
      throw new IllegalArgumentException("Liquidation text cannot be null or blank.");
    }

    leveragedValueLabel.setText(sharesText);
    leverageBadge.setText(leverageText);
    leveragedSubLabel.setText(liquidationText);
  }

  public void clear() {
    normalValueLabel.setText("0 shares");
    leveragedValueLabel.setText("0 shares");
    leverageBadge.setText("-");
    leveragedSubLabel.setText("Liq: -");
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}