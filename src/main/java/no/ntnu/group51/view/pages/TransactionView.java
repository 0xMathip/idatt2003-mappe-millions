package no.ntnu.group51.view.pages;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.TransactionDetailsCard;
import no.ntnu.group51.view.components.TransactionSearchMenu;
import no.ntnu.group51.view.factories.StatCardFactory;

public class TransactionView implements View {
  private final GridPane root = new GridPane();
  private final TransactionSearchMenu transactionSearchMenu;
  private final TransactionDetailsCard transactionDetailsCard;
  private final GameModel gameModel;
  private static final String ALL_TIME = "All time";

  public TransactionView(GameModel gameModel) {
    this.gameModel = gameModel;
    this.transactionSearchMenu = new TransactionSearchMenu(gameModel);
    this.transactionDetailsCard = new TransactionDetailsCard(gameModel);

    createLayout();
  }

  private void createLayout() {
    root.getStyleClass().add("transaction-view");

    Label title = createTitle();
    HBox statsRow = createStatsRow();
    HBox body = createBody();

    root.add(title, 0, 0);
    root.add(statsRow, 0, 1);
    root.add(body, 0, 2);
  }

  private Label createTitle() {
    Label title = new Label("Transactions");
    title.getStyleClass().add("page-title");
    return title;
  }

  private HBox createStatsRow() {
    HBox statsRow = new HBox(82,
        createTotalTradesCard(),
        createTotalBoughtCard(),
        createTotalSoldCard(),
        createTotalFeesCard()
    );

    statsRow.getStyleClass().add("transaction-stats-row");
    return statsRow;
  }

  private HBox createBody() {
    HBox body = new HBox(82,
        transactionSearchMenu.getRoot(),
        transactionDetailsCard.getRoot()
    );

    body.getStyleClass().add("transaction-body");
    return body;
  }

  private HBox createTotalTradesCard() {
    return StatCardFactory.createIconCard(
        "cil-swap-horizontal",
        "Total Trades",
        String.valueOf(
            gameModel.getPlayer()
                .getTransactionArchive()
                .getTransactions(gameModel.getExchange().getWeek())
                .size()
        ),
        ALL_TIME,
        "transaction-stat-trades-icon",
        "transaction-stat-value",
        "transaction-stat-card-bottom-text"
    );
  }

  private HBox createTotalBoughtCard() {
    return StatCardFactory.createIconCard(
        "cil-money",
        "Total Bought",
        String.valueOf(gameModel.getPlayer().getTransactionArchive().getPurchases(gameModel.getExchange().getWeek()).size()),
        ALL_TIME,
        "transaction-stat-bought-icon",
        "transaction-stat-value",
        "transaction-stat-card-bottom-text"
    );
  }

  private HBox createTotalSoldCard() {
    return StatCardFactory.createIconCard(
        "cil-money",
        "Total Sold",
        String.valueOf(gameModel.getPlayer().getTransactionArchive().getSales(gameModel.getExchange().getWeek()).size()),
        ALL_TIME,
        "transaction-stat-sold-icon",
        "transaction-stat-value",
        "transaction-stat-card-bottom-text"
    );
  }

  private HBox createTotalFeesCard() {
    return StatCardFactory.createIconCard(
        "cil-dollar",
        "Fees Paid",
        "COMING",
        ALL_TIME,
        "transaction-stat-fees-icon",
        "transaction-stat-value",
        "transaction-stat-card-bottom-text"
      );
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
