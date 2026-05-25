package no.ntnu.group51.view.components.transaction;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import no.ntnu.group51.service.transaction.TransactionPageSummary;
import no.ntnu.group51.view.factories.StatCardFactory;
import no.ntnu.group51.view.util.CurrencyFormatter;
import no.ntnu.group51.view.util.StyleClass;

public class TransactionStatsSection {

  private static final String ALL_TIME = "All time";

  private final HBox root = new HBox(100);

  private final Label totalTradesLabel = new Label("0");
  private final Label totalBoughtLabel = new Label("0");
  private final Label totalSoldLabel = new Label("0");
  private final Label totalFeesLabel = new Label("$0.00");

  private final Label totalTradesSubtitle = new Label(ALL_TIME);
  private final Label totalBoughtSubtitle = new Label(ALL_TIME);
  private final Label totalSoldSubtitle = new Label(ALL_TIME);
  private final Label totalFeesSubtitle = new Label(ALL_TIME);

  public TransactionStatsSection() {
    createLayout();
  }

  private void createLayout() {
    root.getStyleClass().add("transaction-stat-row");

    HBox totalTradesCard = StatCardFactory.createCard(
        "cil-swap-horizontal",
        "Total Trades",
        totalTradesLabel,
        totalTradesSubtitle,
        StyleClass.TRANSACTION_STAT_TRADES_ICON,
        StyleClass.TRANSACTION_STAT_VALUE,
        StyleClass.TRANSACTION_STAT_CARD_BOTTOM_TEXT
    );

    HBox totalBoughtCard = StatCardFactory.createCard(
        "cil-money",
        "Total Bought",
        totalBoughtLabel,
        totalBoughtSubtitle,
        StyleClass.TRANSACTION_STAT_BOUGHT_ICON,
        StyleClass.TRANSACTION_STAT_VALUE,
        StyleClass.TRANSACTION_STAT_CARD_BOTTOM_TEXT
    );

    HBox totalSoldCard = StatCardFactory.createCard(
        "cil-money",
        "Total Sold",
        totalSoldLabel,
        totalSoldSubtitle,
        StyleClass.TRANSACTION_STAT_SOLD_ICON,
        StyleClass.TRANSACTION_STAT_VALUE,
        StyleClass.TRANSACTION_STAT_CARD_BOTTOM_TEXT
    );

    HBox totalFeesCard = StatCardFactory.createCard(
        "cil-dollar",
        "Fees Paid",
        totalFeesLabel,
        totalFeesSubtitle,
        StyleClass.TRANSACTION_STAT_FEES_ICON,
        StyleClass.TRANSACTION_STAT_VALUE,
        StyleClass.TRANSACTION_STAT_CARD_BOTTOM_TEXT
    );

    root.getChildren().addAll(
        totalTradesCard,
        totalBoughtCard,
        totalSoldCard,
        totalFeesCard
    );
  }

  public void updateSummary(TransactionPageSummary summary) {
    if (summary == null) {
      throw new IllegalArgumentException("Transaction summary cannot be null.");
    }
    totalTradesLabel.setText(String.valueOf(summary.totalTrades()));
    totalBoughtLabel.setText(String.valueOf(summary.totalBought()));
    totalSoldLabel.setText(String.valueOf(summary.totalSold()));
    totalFeesLabel.setText(CurrencyFormatter.format(summary.totalTaxFees()));
  }

  public Parent getRoot() {
    return root;
  }
}
