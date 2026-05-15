package no.ntnu.group51.view.factories;

import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import no.ntnu.group51.model.transaction.Transaction;

public class TransactionRowFactory {

  private TransactionRowFactory() {

  }

  public static Parent createTransactionRow(Transaction transaction) {
    HBox row = new HBox();
    row.getStyleClass().add("factory-transaction-row");
    return null;
  }
}
