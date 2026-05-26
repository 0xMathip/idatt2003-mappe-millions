package no.ntnu.group51.view.components.portfolio;

import java.util.List;
import java.util.function.Consumer;
import javafx.scene.Parent;
import no.ntnu.group51.service.portfolio.PositionSummary;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.shared.SearchMenu;
import no.ntnu.group51.view.components.shared.SearchRow;
import no.ntnu.group51.view.factories.PortfolioRowFactory;

/**
 * Search menu for displaying and selecting portfolio positions.
 */
public class PortfolioSearchMenu implements View {
  private final SearchMenu root;
  private List<PositionSummary> positions = List.of();
  private Consumer<PositionSummary> onPositionSelected = position -> {
  };

  /**
   * Creates the portfolio search menu.
   */
  public PortfolioSearchMenu() {
    this.root = new SearchMenu("⌕ Search portfolio", false);

    root.getSearchField().textProperty().addListener(
        (obs, oldValue, newValue) -> updateDisplay()
    );
  }

  /**
   * Updates the displayed portfolio positions.
   *
   * @param positions the position summaries to display
   * @throws IllegalArgumentException if positions is null
   */
  public void updatePositions(List<PositionSummary> positions) {
    if (positions == null) {
      throw new IllegalArgumentException("Position summary cannot be null.");
    }

    this.positions = positions;
    updateDisplay();
  }

  private void updateDisplay() {
    String searchText = root.getSearchField().getText();

    List<SearchRow> rows = positions
        .stream()
        .filter(position -> matchesSearch(position, searchText))
        .map(this::createRow)
        .toList();

    root.setRows(rows);
  }

  private SearchRow createRow(PositionSummary position) {
    SearchRow row = PortfolioRowFactory.createPortfolioRow(position);
    row.setOnMouseClicked(e -> onPositionSelected.accept(position));
    return row;
  }

  private boolean matchesSearch(PositionSummary position, String searchText) {
    if (searchText == null || searchText.isBlank()) {
      return true;
    }

    String lowerCase = searchText.toLowerCase();

    return position.stock().getSymbol().toLowerCase().contains(lowerCase)
        || position.stock().getCompany().toLowerCase().contains(lowerCase);
  }

  /**
   * Sets the action to run when a portfolio position is selected.
   *
   * @param handler the selection handler
   * @throws IllegalArgumentException if handler is null
   */
  public void setOnPositionSelected(Consumer<PositionSummary> handler) {
    if (handler == null) {
      throw new IllegalArgumentException("Handler cannot be null.");
    }

    this.onPositionSelected = handler;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Parent getRoot() {
    return root;
  }

}
