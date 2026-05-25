package no.ntnu.group51.view.components.transaction;

import java.math.RoundingMode;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import no.ntnu.group51.service.transaction.TransactionSummary;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.factories.TransactionBadgeFactory;
import no.ntnu.group51.view.util.CurrencyFormatter;
import org.kordamp.ikonli.javafx.FontIcon;

public class TransactionDetailsCard implements View {

  private final VBox root = new VBox(20);

  private final Label ticker = new Label("-");
  private final Label company = new Label("No transaction selected.");
  private final Label weekValue = new Label("-");
  private final Label quantityValue = new Label("-");
  private final Label priceValue = new Label("$0.00");
  private final Label grossValue = new Label("$0.00");
  private final Label feesValue = new Label("$0.00");
  private final Label totalValue = new Label("$0.00");
  private final Label noteValue = new Label("Select a transaction to view details.");

  private HBox badgeContainer;

  public TransactionDetailsCard() {
    createLayout();
    clear();
  }

  private void createLayout() {
    root.getStyleClass().addAll("card","transaction-details");
    root.setAlignment(Pos.CENTER_LEFT);

    ticker.getStyleClass().add("transaction-details-ticker");
    company.getStyleClass().add("transaction-details-company");

    VBox companyBox = new VBox(ticker, company);
    companyBox.setAlignment(Pos.CENTER_LEFT);

    badgeContainer = new HBox();
    badgeContainer.setAlignment(Pos.CENTER_LEFT);

    HBox detailTitle = new HBox(15, badgeContainer, companyBox);
    detailTitle.getStyleClass().add("transaction-details-title");
    detailTitle.setAlignment(Pos.CENTER_LEFT);

    VBox detailRows = new VBox(27,
        createDetailRow("Week", weekValue),
        createDetailRow("Quantity", quantityValue),
        createDetailRow("Price per share", priceValue),
        createDetailRow("Gross value", grossValue),
        createDetailRow("Tax / Fees", feesValue)
    );

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    Label totalLabel = new Label("Total cost");
    totalLabel.getStyleClass().add("transaction-details-total");
    totalValue.getStyleClass().add("transaction-details-total");

    HBox totalRow = new HBox(totalLabel, spacer, totalValue);
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

    Region vSpacer = new Region();
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

  public void updateTransaction(TransactionSummary transaction) {
    if (transaction == null) {
      throw new IllegalArgumentException("Transaction cannot be null.");
    }

    badgeContainer.getChildren().setAll(
        new TransactionBadgeFactory(transaction.transaction())
    );

    ticker.setText(transaction.stock().getSymbol());
    company.setText(transaction.stock().getCompany());
    weekValue.setText(String.valueOf(transaction.week()));
    quantityValue.setText(
        transaction.quantity()
            .setScale(4, RoundingMode.HALF_UP)
            .stripTrailingZeros()
            .toPlainString()
    );

    priceValue.setText(CurrencyFormatter.format(transaction.unitPrice()));
    grossValue.setText(CurrencyFormatter.format(transaction.gross()));
    feesValue.setText(
        CurrencyFormatter.format(
            transaction.tax().add(transaction.commission())
        )
    );

    totalValue.setText(CurrencyFormatter.format(transaction.total()));
    noteValue.setText(transaction.note());

  }

  public void clear() {
    badgeContainer.getChildren().clear();

    ticker.setText("-");
    company.setText("No transaction selected");
    weekValue.setText("-");
    quantityValue.setText("-");
    priceValue.setText("$0.00");
    grossValue.setText("$0.00");
    feesValue.setText("$0.00");
    totalValue.setText("$0.00");
    noteValue.setText("Select a transaction to view details.");
  }

  private HBox createDetailRow(String labelText, Label valueLabel) {
    Label label = new Label(labelText);
    label.getStyleClass().add("transaction-details-label");
    valueLabel.getStyleClass().add("transaction-details-value");

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    HBox row = new HBox(label, spacer, valueLabel);
    row.setAlignment(Pos.CENTER_LEFT);

    return row;
  }


  @Override
  public Parent getRoot() {
    return root;
  }
}
