package no.ntnu.group51.view.Dashboard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.stocks.Share;
import no.ntnu.group51.model.stocks.Stock;
import no.ntnu.group51.model.transaction.Purchase;
import no.ntnu.group51.model.transaction.Sale;
import no.ntnu.group51.model.transaction.Transaction;
import org.kordamp.ikonli.javafx.FontIcon;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TransactionListing {

  private TransactionListing() {}

  public static Parent createTransactionListing(Transaction transaction) {

    int spacing = -10;

    Stock stock = transaction.getShare().getStock();
    Share share = transaction.getShare();
    BigDecimal quantity = share.getQuantity();
    BigDecimal stockSalesPrice = stock.getSalesPrice();

    VBox leftSide =  new VBox();
    Label statusLabel = new Label();
    leftSide.setSpacing(spacing);
    statusLabel.getStyleClass().add("dashboard-transaction-text");
    Label sharesAmountLabel = new Label(quantity + " shares");
    sharesAmountLabel.getStyleClass().add("dashboard-subtext");
    leftSide.getChildren().addAll(statusLabel, sharesAmountLabel);
    leftSide.setAlignment(Pos.CENTER_LEFT);

    VBox rightSide =  new VBox();
    rightSide.setSpacing(spacing);
    Label cashAmountLabel = new Label("$" + stockSalesPrice.multiply(quantity).setScale(2, RoundingMode.HALF_UP));
    cashAmountLabel.getStyleClass().add("dashboard-transaction-amount");
    Label weekLabel = new Label("Week " + transaction.getWeek());
    weekLabel.getStyleClass().add("dashboard-subtext");
    rightSide.getChildren().addAll(cashAmountLabel, weekLabel);
    rightSide.setAlignment(Pos.CENTER);
    rightSide.setPrefWidth(250);

    FontIcon filledCircle = new FontIcon("cil-circle");

    ColumnConstraints icon = new ColumnConstraints();
    icon.setPercentWidth(10);

    ColumnConstraints left  = new ColumnConstraints();
    left.setPercentWidth(10);

    ColumnConstraints right  = new ColumnConstraints();
    right.setPercentWidth(60);

    GridPane listing = new GridPane();
    listing.getColumnConstraints().addAll(left, right);
    listing.add(filledCircle, 0, 0);
    listing.add(leftSide, 1, 0);
    listing.add(rightSide, 2, 0);

    listing.setPadding(new Insets(0, 28, 0, 28));

    if (transaction instanceof Sale) {
      statusLabel.setText("Sold " + stock.getSymbol());
      filledCircle.getStyleClass().add("filled-circle-red");

    } else if (transaction instanceof Purchase) {
      statusLabel.setText("Bought " + stock.getSymbol());
      filledCircle.getStyleClass().add("filled-circle-green");
    }

    return listing;

  }
}
