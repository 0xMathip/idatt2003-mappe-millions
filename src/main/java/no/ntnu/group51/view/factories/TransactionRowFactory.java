package no.ntnu.group51.view.factories;

import java.math.RoundingMode;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.transaction.Purchase;
import no.ntnu.group51.model.transaction.Sale;
import no.ntnu.group51.model.transaction.Transaction;
import no.ntnu.group51.view.components.SearchRow;
import org.kordamp.ikonli.javafx.FontIcon;

public class TransactionRowFactory {

  private TransactionRowFactory() {

  }

  public static SearchRow createTransactionRow(Transaction transaction) {
    SearchRow row = new SearchRow(25, 24, 14, 27, 10);
    row.getStyleClass().addAll("card", "factory-search-row");

    HBox transactionBadge = new TransactionBadgeFactory(transaction);

    Label ticker = new Label(transaction.getShare().getStock().getSymbol());
    ticker.getStyleClass().add("factory-search-row-ticker");

    Label company = new Label(transaction.getShare().getStock().getCompany());
    company.getStyleClass().add("factory-search-row-company");
    company.setAlignment(Pos.CENTER_LEFT);


    VBox stockBox = new VBox(2, ticker, company);
    stockBox.setAlignment(Pos.CENTER_LEFT);

    Label week = new Label("Week");
    week.getStyleClass().add("factory-transaction-week");

    Label weekCount = new Label(String.valueOf(transaction.getWeek()));
    weekCount.getStyleClass().add("factory-transaction-week-count");

    VBox weekBox = new VBox(2, week, weekCount);
    weekBox.setAlignment(Pos.CENTER);

    Label shareCount = new Label(transaction.getShare().getQuantity().toString() + " shares");
    shareCount.getStyleClass().add("factory-transaction-share-count");

    Label total = new Label("$" + transaction.getTotal().setScale(2, RoundingMode.HALF_UP));
    total.getStyleClass().add("factory-search-row-price");

    VBox tradeBox = new VBox(2, shareCount, total);
    tradeBox.setAlignment(Pos.CENTER_RIGHT);

    FontIcon arrowIcon = new FontIcon("cil-chevron-circle-right-alt");
    arrowIcon.getStyleClass().add("factory-search-row-arrow");

    row.addToCell(transactionBadge, 0, 0, 1, 2);
    row.addToCell(stockBox, 1, 0, 1, 2);
    row.addToCell(weekBox, 2, 0, 1, 2);
    row.addToCell(tradeBox, 3, 0, 1, 2);
    row.addToCell(arrowIcon, 4, 0, 1, 2);

    GridPane.setHalignment(transactionBadge, HPos.LEFT);
    GridPane.setValignment(transactionBadge, VPos.CENTER);
    GridPane.setHalignment(arrowIcon, HPos.CENTER);
    GridPane.setValignment(arrowIcon, VPos.CENTER);
    return row;
  }
}
