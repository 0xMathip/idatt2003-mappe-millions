package no.ntnu.group51.view.components.dashboard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.transaction.Purchase;
import no.ntnu.group51.model.transaction.Sale;
import no.ntnu.group51.model.transaction.Transaction;
import no.ntnu.group51.view.util.CurrencyFormatter;
import no.ntnu.group51.view.util.StyleClass;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Class for a singular transaction listing.
 */
public class TransactionListing {

  private TransactionListing() {}

  /**
   * Creates a transaction listing from a transaction.
   *
   * @param transaction The transaction you want to create a listing for.
   * @return A GridPane listing.
   */
  public static Parent createTransactionListing(Transaction transaction) {

    int spacing = -10;

    Stock stock = transaction.getShare().getStock();
    Share share = transaction.getShare();
    BigDecimal quantity = share.getQuantity().setScale(4, RoundingMode.HALF_UP);
    BigDecimal transactionValue = share.getPurchasePrice().multiply(share.getQuantity());

    VBox leftSide =  new VBox();
    Label statusLabel = new Label();
    leftSide.setSpacing(spacing);
    statusLabel.getStyleClass().add(StyleClass.DASHBOARD_TRANSACTION_TEXT);
    Label sharesAmountLabel = new Label(quantity + " shares");
    sharesAmountLabel.getStyleClass().add(StyleClass.DASHBOARD_SUBTEXT);
    leftSide.getChildren().addAll(statusLabel, sharesAmountLabel);
    leftSide.setAlignment(Pos.CENTER_LEFT);

    VBox rightSide =  new VBox();
    rightSide.setSpacing(spacing);
    Label cashAmountLabel = new Label(CurrencyFormatter.format(transactionValue));
    cashAmountLabel.getStyleClass().add(StyleClass.DASHBOARD_TRANSACTION_AMOUNT);
    Label weekLabel = new Label("Week " + transaction.getWeek());
    weekLabel.getStyleClass().add(StyleClass.DASHBOARD_SUBTEXT);
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
      filledCircle.getStyleClass().add(StyleClass.FILLED_CIRCLE_RED);

    } else if (transaction instanceof Purchase) {
      statusLabel.setText("Bought " + stock.getSymbol());
      filledCircle.getStyleClass().add(StyleClass.FILLED_CIRCLE_GREEN);
    }

    return listing;

  }
}
