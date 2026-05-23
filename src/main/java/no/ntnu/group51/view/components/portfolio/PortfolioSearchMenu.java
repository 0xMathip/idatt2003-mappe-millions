package no.ntnu.group51.view.components.portfolio;

import java.util.List;
import javafx.scene.Parent;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.Observer;
import no.ntnu.group51.view.View;
import no.ntnu.group51.view.components.shared.SearchMenu;
import no.ntnu.group51.view.components.shared.SearchRow;
import no.ntnu.group51.view.factories.PortfolioRowFactory;

public class PortfolioSearchMenu implements View, Observer {
  private final SearchMenu root;
  private final GameModel gameModel;

  public PortfolioSearchMenu(GameModel gameModel) {
    this.gameModel = gameModel;
    this.root = new SearchMenu("⌕ Search portfolio", false);
    root.getSearchField().textProperty()
        .addListener((obs, oldValue, newValue) -> updateDisplay());

    gameModel.addObserver(this);
    updateDisplay();
  }

  private void updateDisplay() {
    List<SearchRow> rows = gameModel.getPlayer()
        .getPortfolio().getShares(root.getSearchField().getText())
        .stream()
        .map(PortfolioRowFactory::createPortfolioRow)
        .toList();

    root.setRows(rows);
  }

  @Override
  public Parent getRoot() {
    return root;
  }

  @Override
  public void update() {
    updateDisplay();
  }
}
