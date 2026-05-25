package no.ntnu.group51.view.components.portfolio;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import no.ntnu.group51.service.portfolio.PortfolioSummary;
import no.ntnu.group51.view.factories.StatCardFactory;
import no.ntnu.group51.view.util.CurrencyFormatter;
import no.ntnu.group51.view.util.PercentFormatter;
import no.ntnu.group51.view.util.PriceStyleHelper;
import no.ntnu.group51.view.util.StyleClass;

/**
 * UI section displaying portfolio summary statistics.
 */
public class PortfolioStatsSection {
  private final HBox root = new HBox(100);

  private final Label portfolioValueLabel = new Label("$0.00");
  private final Label portfolioValueChangeLabel = new Label("$0.00");
  private final Label netWorthLabel = new Label("$0.00");
  private final Label cashLabel = new Label("$0.00");
  private final Label totalReturnLabel = new Label("$0.00");
  private final Label totalReturnPercentLabel = new Label("(0.00%)");

  /**
   * Creates the portfolio statistics section.
   */
  public PortfolioStatsSection() {
    createLayout();
  }

  private void createLayout() {
    HBox portfolioValueCard = StatCardFactory.createCard(
        "cil-chart-line",
        "Portfolio Value",
        portfolioValueLabel,
        portfolioValueChangeLabel,
        StyleClass.TRANSACTION_STAT_TRADES_ICON,
        StyleClass.PORTFOLIO_STAT_VALUE,
        StyleClass.PORTFOLIO_STAT_CARD_BOTTOM_TEXT_WITH_STATE
    );

    HBox netWorthCard = StatCardFactory.createCard(
        "cil-gem",
        "Net Worth",
        netWorthLabel,
        StyleClass.TRANSACTION_STAT_TRADES_ICON,
        StyleClass.PORTFOLIO_STAT_VALUE
    );

    HBox availableCash = StatCardFactory.createCard(
        "cil-wallet",
        "Available Cash",
        cashLabel,
        StyleClass.TRANSACTION_STAT_TRADES_ICON,
        StyleClass.PORTFOLIO_STAT_VALUE
    );

    HBox totalReturn = StatCardFactory.createCard(
        "cil-loop-circular",
        "Total Return",
        totalReturnLabel,
        totalReturnPercentLabel,
        StyleClass.TRANSACTION_STAT_TRADES_ICON,
        StyleClass.PORTFOLIO_STAT_VALUE_WITH_STATE,
        StyleClass.PORTFOLIO_STAT_CARD_BOTTOM_TEXT_WITH_STATE
    );

    root.getChildren().addAll(
        portfolioValueCard,
        netWorthCard,
        availableCash,
        totalReturn
    );
  }

  /**
   * Updates the displayed portfolio statistics.
   *
   * @param summary the portfolio summary to display
   * @throws IllegalArgumentException if summary is null
   */
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

  /**
   * Returns the root UI node.
   *
   * @return the root node
   */
  public Parent getRoot() {
    return root;
  }
}
