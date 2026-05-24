package no.ntnu.group51.view.components.transaction;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import no.ntnu.group51.service.transaction.TransactionPageSummary;
import no.ntnu.group51.view.factories.StatCardFactory;
import no.ntnu.group51.view.util.CurrencyFormatter;

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
        "transaction-stat-trades-icon",
        "transaction-stat-value",
        "transaction-stat-card-bottom-text"
    );

    HBox totalBoughtCard = StatCardFactory.createCard(
        "cil-money",
        "Total Bought",
        totalBoughtLabel,
        totalBoughtSubtitle,
        "transaction-stat-bought-icon",
        "transaction-stat-value",
        "transaction-stat-card-bottom-text"
    );

    HBox totalSoldCard = StatCardFactory.createCard(
        "cil-money",
        "Total Sold",
        totalSoldLabel,
        totalSoldSubtitle,
        "transaction-stat-sold-icon",
        "transaction-stat-value",
        "transaction-stat-card-bottom-text"
    );

    HBox totalFeesCard = StatCardFactory.createCard(
        "cil-dollar",
        "Fees Paid",
        totalFeesLabel,
        totalFeesSubtitle,
        "transaction-stat-fees-icon",
        "transaction-stat-value",
        "transaction-stat-card-bottom-text"
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
