package no.ntnu.group51.view.Dashboard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import no.ntnu.group51.model.stocks.Share;
import no.ntnu.group51.view.View;

import java.math.BigDecimal;
import java.util.Objects;

public class PortfolioListing implements View {

  private PortfolioListing() {}

  public static Parent portfolioListing(Share share) {


    GridPane stock = new GridPane();

    VBox symbol = new VBox();
    Label symbolLabel = new Label(share.getStock().getSymbol());
    symbolLabel.getStyleClass().add("dashboard-portfolio-symbol");
    Label companyLabel = new Label(share.getStock().getCompany());
    companyLabel.getStyleClass().add("dashboard-portfolio-subtext");
    symbol.getChildren().addAll(symbolLabel, companyLabel);
    symbol.setAlignment(Pos.CENTER_LEFT);

    VBox shares = new VBox();
    Label amountLabel = new Label(share.getQuantity().toString());
    amountLabel.getStyleClass().add("dashboard-portfolio-amount-shares");
    Label sharesLabel = new Label("shares");
    sharesLabel.getStyleClass().add("dashboard-portfolio-subtext");
    shares.getChildren().addAll(amountLabel, sharesLabel);
    shares.setAlignment(Pos.CENTER);

    VBox money = new VBox();
    Label cashLabel = new Label("$" + share.getStock().getSalesPrice().toString());
    cashLabel.getStyleClass().add("dashboard-portfolio-amount-cash");
    Label diffLabel = new Label(share.getStock().getLatestPriceChangePercent().toString());
    diffLabel.getStyleClass().add("dashboard-portfolio-diff");
    applyStyleChange(diffLabel, share.getStock().getLatestPriceChange());
    diffLabel.setAlignment(Pos.CENTER);
    money.getChildren().addAll(cashLabel, diffLabel);
    money.setAlignment(Pos.CENTER_RIGHT);
    money.setSpacing(7);

    ColumnConstraints left = new ColumnConstraints();
    left.setPercentWidth(33.333);

    ColumnConstraints mid = new ColumnConstraints();
    mid.setPercentWidth(33.333);

    ColumnConstraints right = new ColumnConstraints();
    right.setPercentWidth(33.333);

    stock.getColumnConstraints().addAll(left, mid, right);
    stock.add(symbol, 0, 0);
    stock.add(shares, 1, 0);
    stock.add(money, 2, 0);

    stock.setPadding(new Insets(0, 33, 0, 33));

    return stock;
  }

  private static void applyStyleChange(Label label, BigDecimal latestChange) {
    label.getStyleClass().removeAll(
        "positive-price-change",
        "negative-price-change",
        "neutral-price-change"
    );

    int sign = latestChange.signum();

    if (sign < 0) {
      label.getStyleClass().add("negative-price-change");
    } else if (sign > 0) {
      label.getStyleClass().add("positive-price-change");
    } else {
      label.getStyleClass().add("neutral-price-change");
    }
  }

  @Override
  public Parent getRoot() {
    return null;
  }
}
