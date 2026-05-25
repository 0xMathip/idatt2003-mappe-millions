package no.ntnu.group51.view.components.transaction;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import no.ntnu.group51.model.transaction.Purchase;
import no.ntnu.group51.model.transaction.Sale;
import no.ntnu.group51.model.transaction.Transaction;
import no.ntnu.group51.view.util.StyleClass;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * UI badge displaying whether a transaction is a buy or sell.
 */
public class TransactionBadge extends HBox {

  /**
   * Creates a transaction badge for the given transaction.
   *
   * @param transaction the transaction to represent
   * @throws IllegalArgumentException if transaction is null or unsupported
   */
  public TransactionBadge(Transaction transaction) {
    super(16);

    if (transaction == null) {
      throw new IllegalArgumentException("Transaction cannot be null.");
    }

    setAlignment(Pos.CENTER);
    FontIcon transactionIcon = new FontIcon();
    Label transactionLabel = new Label();

    getStyleClass().add(StyleClass.FACTORY_TRANSACTION_STATUS);
    transactionIcon.getStyleClass().add(StyleClass.FACTORY_TRANSACTION_STATUS_ICON);
    transactionLabel.getStyleClass().add(StyleClass.FACTORY_TRANSACTION_STATUS_LABEL);
    getChildren().addAll(transactionIcon, transactionLabel);

    if (transaction instanceof Sale) {
      transactionLabel.setText("SELL");
      transactionIcon.setIconLiteral("cil-arrow-circle-bottom");
      getStyleClass().add(StyleClass.FACTORY_TRANSACTION_STATUS_SELL);
    } else if (transaction instanceof Purchase) {
      transactionLabel.setText("BUY");
      transactionIcon.setIconLiteral("cil-arrow-circle-top");
      getStyleClass().add(StyleClass.FACTORY_TRANSACTION_STATUS_BUY);
    } else {
      throw new IllegalArgumentException("Unknown transaction type.");
    }
  }

}
