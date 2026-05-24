package no.ntnu.group51.view.components.portfolio;

import java.util.List;
import javafx.scene.Parent;
import no.ntnu.group51.service.portfolio.PositionSummary;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.shared.SearchMenu;
import no.ntnu.group51.view.components.shared.SearchRow;
import no.ntnu.group51.view.factories.PortfolioRowFactory;

public class PortfolioSearchMenu implements View {
  private final SearchMenu root;
  private List<PositionSummary> positions = List.of();

  public PortfolioSearchMenu() {
    this.root = new SearchMenu("⌕ Search portfolio", false);

    root.getSearchField().textProperty().addListener(
        (obs, oldValue, newValue) -> updateDisplay()
    );
  }

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
        .map(PortfolioRowFactory::createPortfolioRow)
        .toList();

    root.setRows(rows);
  }

  private boolean matchesSearch(PositionSummary position, String searchText) {
    if (searchText == null || searchText.isBlank()) {
      return true;
    }

    String lowerCase = searchText.toLowerCase();

    return position.stock().getSymbol().toLowerCase().contains(lowerCase)
        || position.stock().getCompany().toLowerCase().contains(lowerCase);
  }

  @Override
  public Parent getRoot() {
    return root;
  }

}
