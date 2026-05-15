package no.ntnu.group51.view.Dashboard;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.stocks.Share;
import no.ntnu.group51.view.View;

import java.math.BigDecimal;

public class PortfolioListing implements View {

  private PortfolioListing() {}

  public static Parent portfolioListing(Share share) {

    HBox stock1 = new HBox();

    VBox symbol1 = new VBox();
    Label symbol1Label = new Label(share.getStock().getSymbol());
    symbol1Label.getStyleClass().add("dashboard-portfolio-symbol");
    Label company1Label = new Label(share.getStock().getCompany());
    company1Label.getStyleClass().add("dashboard-portfolio-subtext");
    symbol1.getChildren().addAll(symbol1Label, company1Label);
    symbol1.setAlignment(Pos.CENTER_LEFT);

    VBox shares1 = new VBox();
    Label amount1Label = new Label(share.getQuantity().toString());
    amount1Label.getStyleClass().add("dashboard-portfolio-amount-shares");
    Label shares1Label = new Label("shares");
    shares1Label.getStyleClass().add("dashboard-portfolio-subtext");
    shares1.getChildren().addAll(amount1Label, shares1Label);
    shares1.setAlignment(Pos.CENTER);

    VBox money1 = new VBox();
    Label cash1Label = new Label(share.getStock().getSalesPrice().toString());
    cash1Label.getStyleClass().add("dashboard-portfolio-amount-cash");
    Label diff1Label = new Label(share.getStock().getLatestPriceChangePercent().toString());
    diff1Label.getStyleClass().add("dashboard-portfolio-diff");
    applyStyleChange(diff1Label, share.getStock().getLatestPriceChange());
    diff1Label.setAlignment(Pos.CENTER);
    money1.getChildren().addAll(cash1Label, diff1Label);
    money1.setAlignment(Pos.CENTER_RIGHT);
    money1.setSpacing(7);
    stock1.getChildren().addAll(symbol1, shares1, money1);
    stock1.setSpacing(70);
    stock1.setAlignment(Pos.CENTER);

    // portView.getChildren().add(stock1);
    return stock1;
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
