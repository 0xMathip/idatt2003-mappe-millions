package no.ntnu.group51.view.pages;

import java.math.BigDecimal;
import java.util.List;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.service.portfolio.PortfolioSummary;
import no.ntnu.group51.service.portfolio.PositionSummary;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.portfolio.PortfolioSearchMenu;
import no.ntnu.group51.view.components.portfolio.PortfolioStatsSection;
import no.ntnu.group51.view.components.portfolio.PortfolioStockDetails;
import no.ntnu.group51.view.factories.StatCardFactory;
import no.ntnu.group51.view.util.PriceStyleHelper;

public class PortfolioView implements View {
  private final GridPane root = new GridPane();

  private final PortfolioStatsSection statsSection;
  private final PortfolioSearchMenu pSearchMenu;
  private final PortfolioStockDetails pStockDetails;

  public PortfolioView() {
    this.statsSection = new PortfolioStatsSection();
    this.pSearchMenu = new PortfolioSearchMenu();
    this.pStockDetails = new PortfolioStockDetails();

    createLayout();
  }

  private void createLayout() {
    root.getStyleClass().addAll("page-layout", "portfolio-view");

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

  public void updateSummary(PortfolioSummary summary) {
    statsSection.updateSummary(summary);
  }

  public void updatePositions(List<PositionSummary> positions) {
    statsSection.updatePositions(positions);
  }

  public void updateSelectedPosition(PositionSummary position) {
    pStockDetails.updatePosition(position);
  }

  public void clearSelectedPosition() {
    pStockDetails.clear();
  }


  @Override
  public Parent getRoot() {
    return root;
  }
}
