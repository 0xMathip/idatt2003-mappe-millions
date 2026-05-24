package no.ntnu.group51.view.pages;

import java.util.List;
import java.util.function.Consumer;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import no.ntnu.group51.service.transaction.TransactionPageSummary;
import no.ntnu.group51.service.transaction.TransactionSummary;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.transaction.TransactionDetailsCard;
import no.ntnu.group51.view.components.transaction.TransactionSearchMenu;
import no.ntnu.group51.view.components.transaction.TransactionStatsSection;

public class TransactionView implements View {

  private final GridPane root = new GridPane();

  private final TransactionSearchMenu transactionSearchMenu;
  private final TransactionStatsSection statsSection;
  private final TransactionDetailsCard transactionDetailsCard;

  public TransactionView() {
    this.statsSection = new TransactionStatsSection();
    this.transactionSearchMenu = new TransactionSearchMenu();
    this.transactionDetailsCard = new TransactionDetailsCard();

    createLayout();
  }

  private void createLayout() {
    root.getStyleClass().addAll("page-layout", "transaction-view");

    Label title = createTitle();
    HBox body = createBody();

    root.add(title, 0, 0);
    root.add(statsSection.getRoot(), 0, 1);
    root.add(body, 0, 2);
  }

  private Label createTitle() {
    Label title = new Label("Transactions");
    title.getStyleClass().add("page-title");
    return title;
  }

  private HBox createBody() {
    HBox body = new HBox(
        95,
        transactionSearchMenu.getRoot(),
        transactionDetailsCard.getRoot()
    );

    body.getStyleClass().add("transaction-body");
    return body;
  }

  public void updateSummary(TransactionPageSummary summary) {
    if (summary == null) {
      throw new IllegalArgumentException("Transactions page summary cannot be null.");
    }
    statsSection.updateSummary(summary);
  }

  public void updateTransactions(List<TransactionSummary> transactions) {
    if (transactions == null) {
      throw new IllegalArgumentException("Transactions cannot be null.");
    }
    transactionSearchMenu.updateTransactions(transactions);
  }

  public void updateSelectedTransaction(TransactionSummary transaction) {
    if (transaction == null) {
      throw new IllegalArgumentException("Transaction cannot be null.");
    }

    transactionDetailsCard.updateTransaction(transaction);
  }

  public void clearSelectedTransaction() {
    transactionDetailsCard.clear();
  }

  public void setOnTransactionSelected(Consumer<TransactionSummary> handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }
    transactionSearchMenu.setOnTransactionSelected(handler);
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
