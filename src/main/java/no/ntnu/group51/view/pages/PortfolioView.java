package no.ntnu.group51.view.pages;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.PortfolioSearchMenu;
import no.ntnu.group51.view.components.PortfolioStockDetails;
import no.ntnu.group51.view.factories.StatCardFactory;

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
    root.getStyleClass().add("transaction-view");

    Label title = createTitle();
    HBox statsRow = createStatsRow();
    HBox body = createBody();

    root.add(title, 0, 0);
    root.add(statsRow, 0, 1);
    root.add(body, 0, 2);
  }

  private Label createTitle() {
    Label title = new Label("Transactions");
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
        "portfolio-stat-value",
        "portfolio-stat-value"
    );
  }

  private VBox createCashCard() {
    return StatCardFactory.createTextCard(
        "Available Cash",
        String.valueOf(gameModel.getPlayer().getMoney().toString()),
        "portfolio-stat-value",
        "portfolio-stat-value"
    );
  }

  private VBox createTotalReturnCard() {
    return StatCardFactory.createTextCard(
        "Portfolio Value",
        "+18,232.322",
        "(17.2%)",
        "portfolio-stat-value",
        "portfolio-stat-card-bottom-text"
    );
  }

  @Override
  public Parent getRoot() {
    return root;
  }
}
