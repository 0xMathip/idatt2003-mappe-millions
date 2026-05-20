package no.ntnu.group51.view.components;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.Observer;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.factories.TransactionBadgeFactory;
import org.kordamp.ikonli.javafx.FontIcon;

public class TransactionDetailsCard implements View, Observer {
  private final GameModel gameModel;
  private final VBox root = new VBox(20);
  private final TransactionBadgeFactory transactionBadge;
  private final Label ticker = new Label();
  private final Label company = new Label();
  private final Label weekValue = new Label();
  private final Label quantityValue = new Label();
  private final Label priceValue = new Label();
  private final Label grossValue = new Label();
  private final Label taxValue = new Label();
  private final Label totalValue = new Label();
  private final Label noteValue = new Label();



  public TransactionDetailsCard(GameModel gameModel) {
    this.gameModel = gameModel;

    root.getStyleClass().addAll("card","transaction-details");
    root.setAlignment(Pos.CENTER_LEFT);

    transactionBadge = new TransactionBadgeFactory(gameModel.getSelectedTransaction());
    ticker.setText(gameModel.getSelectedStock().getSymbol());
    company.setText(gameModel.getSelectedStock().getCompany());

    VBox companyBox = new VBox(ticker, company);
    companyBox.setAlignment(Pos.CENTER_LEFT);

    HBox detailTitle = new HBox(15, transactionBadge, companyBox);
    detailTitle.getStyleClass().add("transaction-details-title");
    ticker.getStyleClass().add("transaction-details-ticker");
    company.getStyleClass().add("transaction-details-company");
    detailTitle.setAlignment(Pos.CENTER_LEFT);

    VBox detailRows = new VBox(27,
        createDetailRow("Week", weekValue),
        createDetailRow("Quantity", quantityValue),
        createDetailRow("Price per share", priceValue),
        createDetailRow("Gross value", grossValue),
        createDetailRow("Tax", taxValue)
    );

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    Label totalLabel = new Label("Total cost");
    totalLabel.getStyleClass().add("transaction-details-total");
    totalValue.getStyleClass().add("transaction-details-total");

    HBox totalRow = new HBox(
        totalLabel,
        spacer,
        totalValue);

    totalRow.setAlignment(Pos.CENTER_LEFT);

    Region sep1 = new Region();
    Region sep2 = new Region();
    sep1.getStyleClass().add("transaction-details-separator");
    sep2.getStyleClass().add("transaction-details-separator");

    FontIcon noteIcon = new FontIcon("cil-notes");
    Label noteLabel = new Label("Notes");
    noteIcon.getStyleClass().add("transaction-details-note-icon");
    noteLabel.getStyleClass().add("transaction-details-note-label");
    noteValue.getStyleClass().add("transaction-details-note-value");

    HBox noteTitle = new HBox(15, noteIcon, noteLabel);
    VBox noteBox = new VBox(20, noteTitle, noteValue);
    noteBox.setAlignment(Pos.CENTER_LEFT);
    noteBox.getStyleClass().add("transaction-details-notebox");

    weekValue.setText(String.valueOf(gameModel.getExchange().getWeek()));
    quantityValue.setText(String.valueOf(gameModel.getSelectedTransaction().getShare().getQuantity()));
    priceValue.setText(gameModel.getSelectedTransaction().getShare().getPurchasePrice().toString());
    grossValue.setText(gameModel.getSelectedTransaction().getCalculator().calculateGross().toString());
    taxValue.setText(gameModel.getSelectedTransaction().getCalculator().calculateTax().toString());
    totalValue.setText("$" + gameModel.getSelectedTransaction().getTotal().stripTrailingZeros().toString());
    noteValue.setText("Note coming soon.");

    Region vSpacer = new Region();
    //VBox.setVgrow(vSpacer, Priority.ALWAYS);
    vSpacer.setPrefHeight(30);

    root.getChildren().addAll(
        detailTitle,
        vSpacer,
        detailRows,
        sep1,
        totalRow,
        sep2,
        noteBox
    );

  }

  private HBox createDetailRow(String labelText, Label valueLabel) {
    Label label = new Label(labelText);
    label.getStyleClass().add("transaction-details-label");
    valueLabel.getStyleClass().add("transaction-details-value");

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    HBox row = new HBox(
        label,
        spacer,
        valueLabel
    );

    row.setAlignment(Pos.CENTER_LEFT);

    return row;
  }


  @Override
  public Parent getRoot() {
    return root;
  }


  @Override
  public void update() {
  }
}
