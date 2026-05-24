package no.ntnu.group51.view.components.portfolio;

import java.math.BigDecimal;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.view.factories.StatCardFactory;
import no.ntnu.group51.view.util.PriceStyleHelper;

public class PortfolioStatsSection {
  private final HBox root;

  private Label portfolioValueLabel = new Label("0.00");
  private Label netWorthLabel = new Label("0.00");
  private Label cashLabel = new Label("0.00");
  private Label totalReturnLabel = new Label("0.00");
  private Label totalReturnPercentLabel = new Label("(0.00%)");

  public PortfolioStatsSection() {
    this.root = createStatsRow();
  }

  private HBox createStatsRow() {
    return new HBox(
        100,
        createPortfolioValueCard(),
        createNetWorthCard(),
        createCashCard(),
        createTotalReturnCard()
    );
  }

  private VBox createPortfolioValueCard() {
    return StatCardFactory.createTextCard(
        "Portfolio Value",
        portfolioValueLabel,
        "+$332.23",
        "portfolio-stat-value",
        "portfolio-stat-card-bottom-text"
    );
  }

  private VBox createNetWorthCard() {
    return StatCardFactory.createTextCard(
        "Net Worth",
        String.valueOf(gameModel.getPlayer().getNetWorth().toString()),
        "portfolio-stat-value"
    );
  }

  private VBox createCashCard() {
    return StatCardFactory.createTextCard(
        "Available Cash",
        String.valueOf(gameModel.getPlayer().getMoney().toString()),
        "portfolio-stat-value"
    );
  }

  private VBox createTotalReturnCard() {
    BigDecimal totalReturn = new BigDecimal("+18232.322");

    VBox card = StatCardFactory.createTextCard(
        "Total return",
        "+18,232.322",
        "(17.2%)",
        "portfolio-stat-value-with-state",
        "portfolio-stat-card-bottom-text-with-state",
        PriceStyleHelper.getPriceChangeStyle(totalReturn)
    );
    return card;
  }
}
