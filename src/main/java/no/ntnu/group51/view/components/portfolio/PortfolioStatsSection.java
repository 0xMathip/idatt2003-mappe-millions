package no.ntnu.group51.view.components.portfolio;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import no.ntnu.group51.service.portfolio.PortfolioSummary;
import no.ntnu.group51.view.factories.StatCardFactory;
import no.ntnu.group51.view.util.CurrencyFormatter;
import no.ntnu.group51.view.util.PercentFormatter;
import no.ntnu.group51.view.util.PriceStyleHelper;

public class PortfolioStatsSection {
  private final HBox root = new HBox(100);

  private Label portfolioValueLabel = new Label("$0.00");
  private Label portfolioValueChangeLabel = new Label("$0.00");
  private Label netWorthLabel = new Label("$0.00");
  private Label cashLabel = new Label("$0.00");
  private Label totalReturnLabel = new Label("$0.00");
  private Label totalReturnPercentLabel = new Label("(0.00%)");

  public PortfolioStatsSection() {
    createLayout();
  }

  private void createLayout() {
    HBox portfolioValueCard = StatCardFactory.createCard(
        "cil-chart-line",
        "Portfolio Value",
        portfolioValueLabel,
        portfolioValueChangeLabel,
        "transaction-stat-trades-icon",
        "portfolio-stat-value",
        "portfolio-stat-card-bottom-text-with-state"
    );

    HBox netWorthCard = StatCardFactory.createCard(
        "cil-gem",
        "Net Worth",
        netWorthLabel,
        "transaction-stat-trades-icon",
        "portfolio-stat-value"
    );

    HBox availableCash = StatCardFactory.createCard(
        "cil-wallet",
        "Available Cash",
        cashLabel,
        "transaction-stat-trades-icon",
        "portfolio-stat-value"
    );

    HBox totalReturn = StatCardFactory.createCard(
        "cil-loop-circular",
        "Total Return",
        totalReturnLabel,
        totalReturnPercentLabel,
        "transaction-stat-trades-icon",
        "portfolio-stat-value-with-state",
        "portfolio-stat-card-bottom-text-with-state"
    );

    root.getChildren().addAll(
        portfolioValueCard,
        netWorthCard,
        availableCash,
        totalReturn
    );
  }

  public void updateSummary(PortfolioSummary summary) {
    if (summary == null) {
      throw new IllegalArgumentException("Portfolio summary cannot be null.");
    }

    portfolioValueLabel.setText(CurrencyFormatter.format(summary.portfolioValue()));
    portfolioValueChangeLabel.setText(CurrencyFormatter.format(summary.totalReturn()));
    netWorthLabel.setText(CurrencyFormatter.format(summary.netWorth()));
    cashLabel.setText(CurrencyFormatter.format(summary.availableCash()));
    totalReturnLabel.setText(CurrencyFormatter.format(summary.totalReturn()));
    totalReturnPercentLabel.setText(
        "(" + PercentFormatter.format(summary.totalReturnPercent()) + ")"
    );

    PriceStyleHelper.applyPriceChangeStyle(portfolioValueChangeLabel, summary.totalReturn());
    PriceStyleHelper.applyPriceChangeStyle(totalReturnLabel, summary.totalReturn());
    PriceStyleHelper.applyPriceChangeStyle(totalReturnPercentLabel, summary.totalReturn());
  }

  public Parent getRoot() {
    return root;
  }
}
