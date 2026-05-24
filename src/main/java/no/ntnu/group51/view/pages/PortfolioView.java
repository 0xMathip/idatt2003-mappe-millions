package no.ntnu.group51.view.pages;

import java.util.List;
import java.util.function.Consumer;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import no.ntnu.group51.service.portfolio.PortfolioSummary;
import no.ntnu.group51.service.portfolio.PositionSummary;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.portfolio.PortfolioSearchMenu;
import no.ntnu.group51.view.components.portfolio.PortfolioStatsSection;
import no.ntnu.group51.view.components.portfolio.PortfolioStockDetails;

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
    HBox body = createBody();

    root.add(title, 0, 0);
    root.add(statsSection.getRoot(), 0, 1);
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
    if (summary == null) {
      throw new IllegalArgumentException("Summary cannot be null.");
    }
    statsSection.updateSummary(summary);
  }

  public void updatePositions(List<PositionSummary> positions) {
    if (positions == null) {
      throw new IllegalArgumentException("Positions cannot be null.");
    }
    pSearchMenu.updatePositions(positions);
  }

  public void setOnPositionSelected(Consumer<PositionSummary> handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }
    pSearchMenu.setOnPositionSelected(handler);
  }

  public void updateSelectedPosition(PositionSummary position) {
    if (position == null) {
      throw new IllegalArgumentException("Position cannot be null.");
    }

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
