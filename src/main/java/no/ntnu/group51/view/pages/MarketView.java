package no.ntnu.group51.view.pages;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.StockChartCard;
import no.ntnu.group51.view.components.StockSelectorCard;
import no.ntnu.group51.view.components.TradePanel;

public class MarketView implements View {
  private final VBox root = new VBox();

  public MarketView(){
    root.getStyleClass().add("market-view");

    Label title = new Label("Market");
    title.getStyleClass().add("page-title");

    HBox body = new HBox();
    body.getStyleClass().add("market-body");

    VBox leftColumn = new VBox();
    leftColumn.getStyleClass().add("market-left-column");

    //leftColumn.getChildren().addAll(
       // new StockSelectorCard().getRoot(),
        //new TradePanel().getRoot()
   // );

    body.getChildren().addAll(
        leftColumn,
        new StockChartCard().getRoot()
    );

    root.getChildren().addAll(title, body);
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
