package no.ntnu.group51.view.factories;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.service.transaction.TransactionSummary;
import no.ntnu.group51.view.components.shared.SearchRow;
import no.ntnu.group51.view.util.CurrencyFormatter;
import no.ntnu.group51.view.util.StyleClass;
import org.kordamp.ikonli.javafx.FontIcon;

public class TransactionRowFactory {

  private TransactionRowFactory() {
  }

  public static SearchRow createTransactionRow(TransactionSummary transaction) {
    if (transaction == null) {
      throw new IllegalArgumentException("Transaction summary cannot be null.");
    }

    SearchRow row = new SearchRow(25, 24, 14, 27, 10);
    row.getStyleClass().addAll(StyleClass.CARD, StyleClass.FACTORY_SEARCH_ROW);

    HBox transactionBadge = new TransactionBadgeFactory(transaction.transaction());

    Label ticker = new Label(transaction.stock().getSymbol());
    ticker.getStyleClass().add(StyleClass.FACTORY_SEARCH_ROW_TICKER);

    Label company = new Label(transaction.stock().getCompany());
    company.getStyleClass().add(StyleClass.FACTORY_SEARCH_ROW_COMPANY);
    company.setAlignment(Pos.CENTER_LEFT);


    VBox stockBox = new VBox(2, ticker, company);
    stockBox.setAlignment(Pos.CENTER_LEFT);

    Label week = new Label("Week");
    week.getStyleClass().add(StyleClass.FACTORY_TRANSACTION_WEEK);

    Label weekCount = new Label(String.valueOf(transaction.week()));
    weekCount.getStyleClass().add(StyleClass.FACTORY_TRANSACTION_WEEK_COUNT);

    VBox weekBox = new VBox(2, week, weekCount);
    weekBox.setAlignment(Pos.CENTER);

    Label shareCount = new Label(transaction.quantity().toPlainString() + " shares");
    shareCount.getStyleClass().add(StyleClass.FACTORY_TRANSACTION_SHARE_COUNT);

    Label total = new Label(CurrencyFormatter.format(transaction.total()));
    total.getStyleClass().add(StyleClass.FACTORY_SEARCH_ROW_PRICE);

    VBox tradeBox = new VBox(2, shareCount, total);
    tradeBox.setAlignment(Pos.CENTER_RIGHT);

    FontIcon arrowIcon = new FontIcon("cil-chevron-circle-right-alt");
    arrowIcon.getStyleClass().add(StyleClass.FACTORY_SEARCH_ROW_ARROW);

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
