package no.ntnu.group51.view.components.dashboard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.util.StyleClass;

/**
 * Class for the big yellow panel on the dashboard showing the portfolio.
 */
public class DashboardPortfolioPanel implements View {

  private static final int MONEY_SCALE = 2;
  private static final int PERCENT_SCALE = 2;

  private final VBox root = new VBox();
  private final VBox portView = new VBox();
  private final Label totalInvestedAmount = new Label();
  private final Label portReturnGainLoss = new Label();

  /**
   * Creates the big yellow portfolio panel by putting portfolio listing
   * within a VBox. Then a separator before an HBox with 2 VBoxes in it.
   */
  public DashboardPortfolioPanel() {

    Label portfolioTitle = new Label("Portfolio");
    portfolioTitle.setAlignment(Pos.CENTER_LEFT);
    portfolioTitle.getStyleClass().add(StyleClass.DASHBOARD_PORTFOLIO_TITLE);
    portView.getChildren().add(portfolioTitle);

    VBox invested = new VBox();
    Label totalInvested = new Label("Total invested");
    totalInvested.getStyleClass().add(StyleClass.DASHBOARD_PORTFOLIO_BOTTOM_TEXT);
    totalInvestedAmount.getStyleClass().add(StyleClass.DASHBOARD_PORTFOLIO_AMOUNT_CASH);
    invested.getChildren().addAll(totalInvested, totalInvestedAmount);
    invested.setAlignment(Pos.CENTER_LEFT);

    VBox portfolioReturn = new VBox();
    Label portReturn = new Label("Portfolio return");
    portReturn.getStyleClass().add(StyleClass.DASHBOARD_PORTFOLIO_BOTTOM_TEXT);
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

    showEmptyPortfolio();
    updateBottomValues(BigDecimal.ZERO, BigDecimal.ZERO);
  }

  public void updatePortfolio(
      List<Share> shares,
      BigDecimal totalInvested,
      BigDecimal returnPercent
  ) {
    if (shares == null) {
      throw new IllegalArgumentException("Shares cannot be null.");
    }
    if (totalInvested == null) {
      throw new IllegalArgumentException("Total invested cannot be null.");
    }
    if (returnPercent == null) {
      throw new IllegalArgumentException("Return percent cannot be null.");
    }

    clearListings();

    if (shares.isEmpty()) {
      showEmptyPortfolio();
    } else {
      shares.stream()
          .sorted((a, b) -> {
            BigDecimal aValue = a.getStock().getSalesPrice().multiply(a.getQuantity());
            BigDecimal bValue = b.getStock().getSalesPrice().multiply(b.getQuantity());
            return bValue.compareTo(aValue);
          })
          .limit(4)
          .forEach(share -> portView.getChildren().add(
              PortfolioListing.portfolioListing(share)
          ));
    }

    updateBottomValues(totalInvested, returnPercent);
  }

  private void clearListings() {
    while (portView.getChildren().size() > 1) {
      portView.getChildren().remove(1);
    }
  }

  private void showEmptyPortfolio() {
    Label emptyPortfolio = new Label("No shares in your portfolio");
    emptyPortfolio.getStyleClass().add(StyleClass.DASHBOARD_EMPTY_PORTFOLIO);
    emptyPortfolio.setAlignment(Pos.CENTER);
    portView.getChildren().add(emptyPortfolio);
  }

  private void updateBottomValues(BigDecimal totalInvested, BigDecimal returnPercent) {
    totalInvestedAmount.setText("$" + totalInvested.setScale(MONEY_SCALE, RoundingMode.HALF_UP));

    String sign = returnPercent.compareTo(BigDecimal.ZERO) > 0 ? "+" : "";
    portReturnGainLoss.setText(
        sign + returnPercent.setScale(PERCENT_SCALE, RoundingMode.HALF_UP) + "%"
    );

    portReturnGainLoss.getStyleClass().removeAll(
        StyleClass.DASHBOARD_PORTFOLIO_RETURN_GAIN,
        StyleClass.DASHBOARD_PORTFOLIO_RETURN_LOSS
    );

    portReturnGainLoss.getStyleClass().add(
        returnPercent.compareTo(BigDecimal.ZERO) >= 0
            ? StyleClass.DASHBOARD_PORTFOLIO_RETURN_GAIN
            : StyleClass.DASHBOARD_PORTFOLIO_RETURN_LOSS
    );
  }

@Override
public Parent getRoot() {
  return root;
}
}
