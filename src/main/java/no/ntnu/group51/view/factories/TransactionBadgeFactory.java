package no.ntnu.group51.view.factories;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import no.ntnu.group51.model.transaction.Purchase;
import no.ntnu.group51.model.transaction.Sale;
import no.ntnu.group51.model.transaction.Transaction;
import org.kordamp.ikonli.javafx.FontIcon;

public class TransactionBadgeFactory extends HBox {

  public TransactionBadgeFactory(Transaction transaction) {
    if (transaction == null) {
      throw new IllegalArgumentException("Transaction cannot be null.");
    }
    super(16);
    setAlignment(Pos.CENTER);
    FontIcon transactionIcon = new FontIcon();
    Label transactionLabel = new Label();

    getStyleClass().add("factory-transaction-status");
    transactionIcon.getStyleClass().add("factory-transaction-status-icon");
    transactionLabel.getStyleClass().add("factory-transaction-status-label");
    getChildren().addAll(transactionIcon, transactionLabel);

    if (transaction instanceof Sale) {
      transactionLabel.setText("SELL");
      transactionIcon.setIconLiteral("cil-arrow-circle-bottom");
      getStyleClass().add("factory-transaction-status-sell");
    } else if (transaction instanceof Purchase) {
      transactionLabel.setText("BUY");
      transactionIcon.setIconLiteral("cil-arrow-circle-top");
      getStyleClass().add("factory-transaction-status-buy");
    } else {
      throw new IllegalArgumentException("Unknown transaction type.");
    }
  }

}
