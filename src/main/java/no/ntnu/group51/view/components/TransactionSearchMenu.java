package no.ntnu.group51.view.components;

import java.util.List;
import javafx.scene.Parent;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.Observer;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.factories.TransactionRowFactory;

public class TransactionSearchMenu implements View, Observer {
  private final SearchMenu root;
  private final GameModel gameModel;

  public TransactionSearchMenu(GameModel gameModel) {
    this.gameModel = gameModel;
    this.root = new SearchMenu("Search transactions");

    root.getSearchField().textProperty()
        .addListener((obs, oldValue, newValue) -> updateDisplay());

    gameModel.addObserver(this);
    updateDisplay();
  }

  private void updateDisplay() {
    List<SearchRow> rows = gameModel.getPlayer()
        .getTransactionArchive()
        .findTransactions(root.getSearchField().getText())
        .stream()
        .map(TransactionRowFactory::createTransactionRow)
        .toList();

    root.setRows(rows);
  }

  public void setOnClose(Runnable onClose) {
    root.setOnClose(onClose);
  }

  @Override
  public Parent getRoot() {
    return root;
  }

  @Override
  public void update() {
    updateDisplay();
  }
}
