package no.ntnu.group51.view.components.transaction;

import java.util.List;
import java.util.function.Consumer;
import javafx.scene.Parent;
import no.ntnu.group51.service.transaction.TransactionSummary;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.shared.SearchMenu;
import no.ntnu.group51.view.components.shared.SearchRow;
import no.ntnu.group51.view.factories.TransactionRowFactory;

public class TransactionSearchMenu implements View {

  private final SearchMenu root;
  private List<TransactionSummary> transactions = List.of();
  private Consumer<TransactionSummary> onTransactionSelected
      =transaction -> {};

  public TransactionSearchMenu() {
    this.root = new SearchMenu("⌕ Search transactions", false);

    root.getSearchField().textProperty().addListener(
            (obs, oldValue, newValue) -> updateDisplay()
    );
  }

  public void updateTransactions(List<TransactionSummary> transactions) {
    if (transactions == null) {
      throw new IllegalArgumentException("Transactions cannot be null.");
    }

    this.transactions = transactions;
    updateDisplay();
  }

  public void setOnTransactionSelected(Consumer<TransactionSummary> handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }

    this.onTransactionSelected = handler;
  }

  private void updateDisplay() {
    String searchText = root.getSearchField().getText();

    List<SearchRow> rows = transactions
        .stream()
        .filter(transaction -> matchesSearch(transaction, searchText))
        .map(this::createRow)
        .toList();

    root.setRows(rows);
  }

  private SearchRow createRow(TransactionSummary transaction) {
    SearchRow row = TransactionRowFactory.createTransactionRow(transaction);
    row.setOnMouseClicked(e -> onTransactionSelected.accept(transaction));
    return row;
  }

  private boolean matchesSearch(TransactionSummary transaction, String searchText) {
    if (searchText == null || searchText.isBlank()) {
      return true;
    }

    String lowerCase = searchText.toLowerCase();

    return transaction.stock().getSymbol().toLowerCase().contains(lowerCase)
        || transaction.stock().getCompany().toLowerCase().contains(lowerCase)
        || transaction.type().toLowerCase().contains(lowerCase)
        || String.valueOf(transaction.week()).contains(lowerCase);
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
