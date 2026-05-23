package no.ntnu.group51.view.components.dashboard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.view.View;
import org.kordamp.ikonli.javafx.FontIcon;
import no.ntnu.group51.view.util.PriceStyleHelper;

/**
 * Class for a portfolio listing on the portfolio panel.
 */
public class PortfolioListing implements View {

  private PortfolioListing() {}

  /**
   * Creates a listing for the portfolio panel. Contains each part in a VBox, which is
   * all contained in a GridPane.
   *
   * @param share The portfolio you want to create listings for
   * @return The GridPane
   */
  public static Parent portfolioListing(Share share) {

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

    Label cashLabel = new Label("$" + share.getStock().getSalesPrice().toString());
    cashLabel.getStyleClass().add("dashboard-portfolio-amount-cash");
    Label diffLabel = new Label(share.getStock().getLatestPriceChangePercent().toString());
    diffLabel.getStyleClass().add("dashboard-portfolio-diff");
    PriceStyleHelper.applyPriceChangeStyle(diffLabel, share.getStock().getLatestPriceChange());
    diffLabel.setAlignment(Pos.CENTER);
    VBox money = new VBox();
    money.getChildren().addAll(cashLabel, diffLabel);
    money.setAlignment(Pos.CENTER_RIGHT);
    money.setSpacing(7);

    FontIcon icon = new FontIcon(share.getStock().getIcon());
    icon.getStyleClass().add("dashboard-portfolio-icon");
    icon.setIconSize(40);

    StackPane iconBox = new StackPane(icon);
    iconBox.setPadding(new Insets(0, 20, 20, 0));

    ColumnConstraints iconColumn = new ColumnConstraints();
    iconColumn.setPercentWidth(10);

    ColumnConstraints left = new ColumnConstraints();
    left.setPercentWidth(35);

    ColumnConstraints mid = new ColumnConstraints();
    mid.setPercentWidth(20);

    ColumnConstraints right = new ColumnConstraints();
    right.setPercentWidth(30);

    GridPane stock = new GridPane();
    stock.getColumnConstraints().addAll(iconColumn, left, mid, right);
    stock.add(icon, 0, 0);
    stock.add(symbol, 1, 0);
    stock.add(shares, 2, 0);
    stock.add(money, 3, 0);


    stock.setPadding(new Insets(0, 0, 0, 20));

    /* for debugging
    stock.setStyle("-fx-border-color: red");
    symbol.setStyle("-fx-border-color: green");
    shares.setStyle("-fx-border-color: green");
    money.setStyle("-fx-border-color: green");
    iconBox.setStyle("-fx-border-color: red");
     */

    return stock;
  }

  @Override
  public Parent getRoot() {
    return null;
  }
}
