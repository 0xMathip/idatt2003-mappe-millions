package no.ntnu.group51.view.Dashboard;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.view.View;

public class DashboardPortfolioView implements View {

  private final VBox root =  new VBox();

  public DashboardPortfolioView() {

    VBox portView = new VBox();

    Label portfolioTitle = new Label("Portfolio");
    portfolioTitle.setAlignment(Pos.CENTER_LEFT);
    portfolioTitle.getStyleClass().add("dashboard-portfolio-title");
    portView.getChildren().add(portfolioTitle);

    for (int i = 0; i < 4; i++) {
      HBox stock1 = new HBox();

      VBox symbol1 = new VBox();
      Label symbol1Label = new Label("AAPL");
      symbol1Label.getStyleClass().add("dashboard-portfolio-symbol");
      Label company1Label = new Label("Apple Inc.");
      company1Label.getStyleClass().add("dashboard-portfolio-subtext");
      symbol1.getChildren().addAll(symbol1Label, company1Label);
      symbol1.setAlignment(Pos.CENTER_LEFT);

      VBox shares1 = new VBox();
      Label amount1Label = new Label("10");
      amount1Label.getStyleClass().add("dashboard-portfolio-amount-shares");
      Label shares1Label = new Label("shares");
      shares1Label.getStyleClass().add("dashboard-portfolio-subtext");
      shares1.getChildren().addAll(amount1Label, shares1Label);
      shares1.setAlignment(Pos.CENTER);

      VBox money1 = new VBox();
      Label cash1Label = new Label("$ 9,423.0");
      cash1Label.getStyleClass().add("dashboard-portfolio-amount-cash");
      Label diff1Label = new Label("+3.2%");
      diff1Label.getStyleClass().add("dashboard-portfolio-diff-gain");
      diff1Label.setAlignment(Pos.CENTER);
      money1.getChildren().addAll(cash1Label, diff1Label);
      money1.setAlignment(Pos.CENTER_RIGHT);
      money1.setSpacing(7);

      stock1.getChildren().addAll(symbol1, shares1, money1);
      stock1.setSpacing(70);
      stock1.setAlignment(Pos.CENTER);

      portView.getChildren().add(stock1);
    }



    VBox invested = new VBox();
    Label totalInvested = new Label("Total invested");
    totalInvested.getStyleClass().add("dashboard-portfolio-bottom-text");
    Label totalInvestedAmount = new Label("$63,093.2");
    totalInvestedAmount.getStyleClass().add("dashboard-portfolio-amount-cash");
    invested.getChildren().addAll(totalInvested, totalInvestedAmount);
    invested.setAlignment(Pos.CENTER_LEFT);

    VBox portfolioReturn = new VBox();
    Label portReturn = new Label("Portfolio return");
    portReturn.getStyleClass().add("dashboard-portfolio-bottom-text");
    Label portReturnGainLoss = new Label("+52,3%");
    portReturnGainLoss.getStyleClass().add("dashboard-portfolio-return-gain");
    portfolioReturn.getChildren().addAll(portReturn, portReturnGainLoss);
    portfolioReturn.setAlignment(Pos.CENTER_RIGHT);

    HBox bottom = new HBox();
    bottom.getChildren().addAll(invested, portfolioReturn);
    bottom.setAlignment(Pos.CENTER);
    bottom.setSpacing(60);

    Separator separator = new Separator(Orientation.HORIZONTAL);

    root.getChildren().addAll(portView, separator, bottom);
    root.setAlignment(Pos.CENTER);
    portView.setSpacing(30);
    root.setSpacing(30);
    root.getStyleClass().add("dashboard-portfolio");

  }



  @Override
  public Parent getRoot() {
    return root;
  }
}
