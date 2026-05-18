package no.ntnu.group51.view.Dashboard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class TransactionListing {

  private TransactionListing() {}

  public static Parent createTransactionListing() {

    VBox leftSide =  new VBox();
    Label statusLabel = new Label("Bought AAPL");
    statusLabel.getStyleClass().add("dashboard-transaction-text");
    Label sharesAmountLabel = new Label("10 shares");
    sharesAmountLabel.getStyleClass().add("dashboard-transaction-subtext");
    leftSide.getChildren().addAll(statusLabel, sharesAmountLabel);
    leftSide.setAlignment(Pos.CENTER_LEFT);

    VBox rightSide =  new VBox();
    Label cashAmountLabel = new Label("$9,032.0");
    cashAmountLabel.getStyleClass().add("dashboard-transaction-amount");
    Label weekLabel = new Label("Week 13");
    weekLabel.getStyleClass().add("dashboard-transaction-subtext");
    rightSide.getChildren().addAll(cashAmountLabel, weekLabel);
    rightSide.setAlignment(Pos.CENTER);

    FontIcon filledCircle = new FontIcon("cil-circle");
    filledCircle.getStyleClass().add("filled-circle-green");
    statusLabel.setGraphic(filledCircle);
    statusLabel.setContentDisplay(ContentDisplay.LEFT);
    statusLabel.setAlignment(Pos.CENTER_LEFT);
    statusLabel.setGraphicTextGap(12);

    ColumnConstraints left  = new ColumnConstraints();
    left.setPercentWidth(65);

    ColumnConstraints right  = new ColumnConstraints();
    right.setPercentWidth(35);

    GridPane listing = new GridPane();
    listing.getColumnConstraints().addAll(left, right);
    listing.add(leftSide, 0, 0);
    listing.add(rightSide, 1, 0);

    listing.setPadding(new Insets(0, 28, 0, 28));

    return listing;

  }
}
