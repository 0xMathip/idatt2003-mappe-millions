package no.ntnu.group51.controller.transaction;

import java.util.List;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.Observer;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.service.transaction.TransactionPageSummary;
import no.ntnu.group51.service.transaction.TransactionService;
import no.ntnu.group51.service.transaction.TransactionSummary;
import no.ntnu.group51.view.pages.TransactionView;

public class TransactionController implements Observer {
  private final GameModel gameModel;
  private final TransactionView transactionView;
  private final TransactionService transactionService;

  public TransactionController(
      GameModel gameModel,
      TransactionView transactionView,
      TransactionService transactionService
  ) {
    if (gameModel == null) {
      throw new IllegalArgumentException("Game model cannot be null.");
    }
    if (transactionView == null) {
      throw new IllegalArgumentException("Transaction view cannot be null.");
    }
    if (transactionService == null) {
      throw new IllegalArgumentException("Transaction service cannot be null.");
    }

    this.gameModel = gameModel;
    this.transactionView = transactionView;
    this.transactionService = transactionService;

    gameModel.addObserver(this);

    initialize();
  }

  public void initialize() {
    transactionView.setOnTransactionSelected(transactionView::updateSelectedTransaction);
    updateView();
  }

  @Override
  public void update() {
    updateView();
  }

  public void updateView() {
    Player player = gameModel.getPlayer();

    TransactionPageSummary pageSummary =
        transactionService.createPageSummary(player);

    List<TransactionSummary> transactionSummaries =
        transactionService.createTransactionSummaries(player);

    transactionView.updateSummary(pageSummary);
    transactionView.updateTransactions(transactionSummaries);

    if (!transactionSummaries.isEmpty()) {
      transactionView.updateSelectedTransaction(transactionSummaries.get(0));
    } else {
      transactionView.clearSelectedTransaction();
    }
  }
}
