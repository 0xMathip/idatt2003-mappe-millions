package no.ntnu.group51.view.components.dashboard;

import java.math.BigDecimal;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.util.StyleClass;

/**
 * Class for the big yellow panel on the dashboard showing the portfolio.
 */
public class DashboardPortfolioPanel implements View {

  private final VBox root =  new VBox();
  private final VBox portView = new VBox();

  /**
   * Creates the big yellow portfolio panel by putting portfolio listing
   * within a VBox. Then a separator before an HBox with 2 VBoxes in it.
   */
  public DashboardPortfolioPanel() {

    Label portfolioTitle = new Label("Portfolio");
    portfolioTitle.setAlignment(Pos.CENTER_LEFT);
    portfolioTitle.getStyleClass().add(StyleClass.DASHBOARD_PORTFOLIO_TITLE);
    portView.getChildren().add(portfolioTitle);


    portView.getChildren().addAll(
        PortfolioListing.portfolioListing(new Share(new Stock("AAPL", "Apple Inc.", new BigDecimal("29312"), "cib-apple"), new BigDecimal("14.4"), new BigDecimal("25000"))),
        PortfolioListing.portfolioListing(new Share(new Stock("NVDA", "Nvidia", new BigDecimal("29312"), "cib-nvidia"), new BigDecimal("14.4"), new BigDecimal("25000"))),
        PortfolioListing.portfolioListing(new Share(new Stock("ORCL", "Oracle Corporation", new BigDecimal("3452"), "cib-oracle"), new BigDecimal("123"), new BigDecimal("7843"))),
        PortfolioListing.portfolioListing(new Share(new Stock("V", "Visa", new BigDecimal("293"), "cib-visa"), new BigDecimal("53"), new BigDecimal("20")))
    );



    VBox invested = new VBox();
    Label totalInvested = new Label("Total invested");
    totalInvested.getStyleClass().add(StyleClass.DASHBOARD_PORTFOLIO_BOTTOM_TEXT);
    Label totalInvestedAmount = new Label("$63,093.2");
    totalInvestedAmount.getStyleClass().add(StyleClass.DASHBOARD_PORTFOLIO_AMOUNT_CASH);
    invested.getChildren().addAll(totalInvested, totalInvestedAmount);
    invested.setAlignment(Pos.CENTER_LEFT);

    VBox portfolioReturn = new VBox();
    Label portReturn = new Label("Portfolio return");
    portReturn.getStyleClass().add(StyleClass.DASHBOARD_PORTFOLIO_BOTTOM_TEXT);
    Label portReturnGainLoss = new Label("+52,3%");
    portReturnGainLoss.getStyleClass().add(StyleClass.DASHBOARD_PORTFOLIO_RETURN_GAIN);
    portfolioReturn.getChildren().addAll(portReturn, portReturnGainLoss);
    portfolioReturn.setAlignment(Pos.CENTER_RIGHT);

    HBox bottom = new HBox();
    bottom.getChildren().addAll(invested, portfolioReturn);
    bottom.setAlignment(Pos.CENTER);
    bottom.setSpacing(60);
    bottom.setPadding(new Insets(0, 0, 0, 0));

    Separator separator = new Separator(Orientation.HORIZONTAL);
    separator.getStyleClass().add(StyleClass.SEPARATOR);

    root.getChildren().addAll(portView, separator, bottom);
    root.setAlignment(Pos.CENTER);
    portView.setSpacing(30);
    root.setSpacing(20);
    root.getStyleClass().add(StyleClass.DASHBOARD_PORTFOLIO);

    /*
    invested.setStyle("-fx-border-color: green");
    portfolioReturn.setStyle("-fx-border-color: green");
    separator.setStyle("-fx-border-color: green");
    */
  }

//  public void createListings(Portfolio portfolio) {
//    if (portfolio ==  null) {
//      throw new IllegalArgumentException("Portfolio is null");
//    }
//
//    if (portfolio.size() == 0) {
//      Label emptyPortfolio = new Label("No shares in your portfolio");
//      emptyPortfolio.getStyleClass().add("dashboard-empty-portfolio");
//      portView.getChildren().add(emptyPortfolio);
//      portView.setAlignment(Pos.CENTER);
//
//    } else {
//
//    }
//  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
