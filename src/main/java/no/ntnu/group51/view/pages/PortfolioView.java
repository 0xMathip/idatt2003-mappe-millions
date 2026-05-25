package no.ntnu.group51.view.pages;

import java.util.List;
import java.util.function.Consumer;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import no.ntnu.group51.service.portfolio.PortfolioSummary;
import no.ntnu.group51.service.portfolio.PositionSummary;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.portfolio.PortfolioSearchMenu;
import no.ntnu.group51.view.components.portfolio.PortfolioStatsSection;
import no.ntnu.group51.view.components.portfolio.PortfolioStockDetails;
import no.ntnu.group51.view.util.StyleClass;

/**
 * Portfolio page view showing portfolio stats, positions, and selected position details.
 */
public class PortfolioView implements View {
  private final GridPane root = new GridPane();

  private final PortfolioStatsSection statsSection;
  private final PortfolioSearchMenu pSearchMenu;
  private final PortfolioStockDetails pStockDetails;

  /**
   * Creates the portfolio view.
   */
  public PortfolioView() {
    this.statsSection = new PortfolioStatsSection();
    this.pSearchMenu = new PortfolioSearchMenu();
    this.pStockDetails = new PortfolioStockDetails();

    createLayout();
  }


  private void createLayout() {
    root.getStyleClass().addAll(StyleClass.PAGE_LAYOUT, StyleClass.PORTFOLIO_VIEW);

    Label title = createTitle();
    HBox body = createBody();

    root.add(title, 0, 0);
    root.add(statsSection.getRoot(), 0, 1);
    root.add(body, 0, 2);
  }

  private Label createTitle() {
    Label title = new Label("Portfolio");
    title.getStyleClass().add(StyleClass.PAGE_TITLE);
    return title;
  }

  private HBox createBody() {
    HBox body = new HBox(95,
        pSearchMenu.getRoot(),
        pStockDetails.getRoot()
    );

    body.getStyleClass().add(StyleClass.TRANSACTION_BODY);
    return body;
  }

  /**
   * Updates the portfolio statistics section.
   *
   * @param summary the portfolio summary to display
   * @throws IllegalArgumentException if summary is null
   */
  public void updateSummary(PortfolioSummary summary) {
    if (summary == null) {
      throw new IllegalArgumentException("Portfolio summary cannot be null.");
    }
    statsSection.updateSummary(summary);
  }

  /**
   * Updates the displayed portfolio positions.
   *
   * @param positions the positions to display
   * @throws IllegalArgumentException if positions is null
   */
  public void updatePositions(List<PositionSummary> positions) {
    if (positions == null) {
      throw new IllegalArgumentException("Position cannot be null.");
    }
    pSearchMenu.updatePositions(positions);
  }

  /**
   * Updates the selected position details.
   *
   * @param position the selected position
   * @throws IllegalArgumentException if position is null
   */
  public void updateSelectedPosition(PositionSummary position) {
    if (position == null) {
      throw new IllegalArgumentException("Position cannot be null.");
    }

    pStockDetails.updatePosition(position);
  }

  /**
   * Clears the selected position details.
   */
  public void clearSelectedPosition() {
    pStockDetails.clear();
  }

  /**
   * Sets the action to run when a position is selected.
   *
   * @param handler the position selection handler
   * @throws IllegalArgumentException if handler is null
   */
  public void setOnPositionSelected(Consumer<PositionSummary> handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }
    pSearchMenu.setOnPositionSelected(handler);
  }

  /**
   * Sets the action to run when opening the selected stock in the market.
   *
   * @param action the click handler
   * @throws IllegalArgumentException if action is null
   */
  public void setOnOpenMarketPress(EventHandler<MouseEvent> action) {
    if (action == null) {
      throw new IllegalArgumentException("Action cannot be null.");
    }
    pStockDetails.setOnOpenMarketPress(action);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Parent getRoot() {
    return root;
  }
}
