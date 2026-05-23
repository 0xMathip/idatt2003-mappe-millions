package no.ntnu.group51.view.factories;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.stocks.Share;
import no.ntnu.group51.view.components.shared.SearchRow;
import org.kordamp.ikonli.javafx.FontIcon;

public class PortfolioRowFactory {

  private PortfolioRowFactory() {

  }

  public static SearchRow createPortfolioRow(Share share) {
    SearchRow row = new SearchRow(40, 10, 40, 10);
    row.getStyleClass().addAll("card", "factory-search-row");

    Label ticker = new Label(share.getStock().getSymbol());
    ticker.getStyleClass().add("factory-search-row-ticker");

    Label company = new Label(share.getStock().getCompany());
    company.getStyleClass().add("factory-search-row-company");
    company.setAlignment(Pos.CENTER_LEFT);


    VBox stockBox = new VBox(2, ticker, company);
    stockBox.setAlignment(Pos.CENTER_LEFT);

    Label quantityLabel = new Label("shares");
    quantityLabel.getStyleClass().add("factory-portfolio-quantity");

    Label quantityValue = new Label(String.valueOf(share.getQuantity()));
    quantityValue.getStyleClass().add("factory-portfolio-quantity-value");

    VBox quantityBox = new VBox(2, quantityValue, quantityLabel);
    quantityBox.setAlignment(Pos.CENTER);

    Label total = new Label("$" + "1234.32");
    total.getStyleClass().add("factory-search-row-price");

    Label changeValue = new Label("+$323.32");
    Label changePercent = new Label("(+4,3%)");
    changeValue.getStyleClass().add("factory-portfolio-change");
    changePercent.getStyleClass().add("factory-portfolio-change");

    HBox changeBox = new HBox(changeValue, changePercent);

    VBox priceBox = new VBox(2, total, changeBox);
    priceBox.setAlignment(Pos.CENTER_RIGHT);

    FontIcon arrowIcon = new FontIcon("cil-chevron-circle-right-alt");
    arrowIcon.getStyleClass().add("factory-search-row-arrow");

    row.addToCell(stockBox, 0, 0, 2, 2);
    row.addToCell(quantityBox, 1, 0, 1, 2);
    row.addToCell(priceBox, 2, 0, 2, 2);
    row.addToCell(arrowIcon, 3, 0, 1, 2);

    GridPane.setHalignment(arrowIcon, HPos.CENTER);
    GridPane.setValignment(arrowIcon, VPos.CENTER);

    return row;
  }
}
