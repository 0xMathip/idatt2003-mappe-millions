package no.ntnu.group51.view.pages;

import java.math.BigDecimal;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.portfolio.PortfolioSearchMenu;
import no.ntnu.group51.view.components.portfolio.PortfolioStockDetails;
import no.ntnu.group51.view.factories.StatCardFactory;
import no.ntnu.group51.view.util.PriceStyleHelper;

public class PortfolioView implements View {
  private final GridPane root = new GridPane();
  private final PortfolioSearchMenu pSearchMenu;
  private final PortfolioStockDetails pStockDetails;
  private GameModel gameModel;

  public PortfolioView(GameModel gameModel) {
    this.gameModel = gameModel;
    this.pSearchMenu = new PortfolioSearchMenu(gameModel);
    this.pStockDetails = new PortfolioStockDetails(gameModel);

    createLayout();
  }

  private void createLayout() {
    root.getStyleClass().addAll("page-layout","portfolio-view");

    Label title = createTitle();
    HBox statsRow = createStatsRow();
    HBox body = createBody();

    root.add(title, 0, 0);
    root.add(statsRow, 0, 1);
    root.add(body, 0, 2);
  }

  private Label createTitle() {
    Label title = new Label("Portfolio");
    title.getStyleClass().add("page-title");
    return title;
  }

  private HBox createBody() {
    HBox body = new HBox(95,
        pSearchMenu.getRoot(),
        pStockDetails.getRoot()
    );

    body.getStyleClass().add("transaction-body");
    return body;
  }

  private HBox createStatsRow() {
    HBox statsRow = new HBox(100,
        createPortfolioValueCard(),
        createNetWorthCard(),
        createCashCard(),
        createTotalReturnCard()
    );

    return statsRow;
  }

  private VBox createPortfolioValueCard() {
    return StatCardFactory.createTextCard(
        "Portfolio Value",
        String.valueOf(
            gameModel.getPlayer()
                .getTransactionArchive()
                .getTransactions(gameModel.getExchange().getWeek())
                .size()
        ),
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

  @Override
  public Parent getRoot() {
    return root;
  }
}
