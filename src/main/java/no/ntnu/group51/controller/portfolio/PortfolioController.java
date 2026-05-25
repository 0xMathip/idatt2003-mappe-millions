package no.ntnu.group51.controller.portfolio;

import java.util.List;
import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.model.Observer;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.service.portfolio.PortfolioService;
import no.ntnu.group51.service.portfolio.PositionService;
import no.ntnu.group51.service.portfolio.PortfolioSummary;
import no.ntnu.group51.service.portfolio.PositionSummary;
import no.ntnu.group51.view.pages.PortfolioView;

public class PortfolioController implements Observer {

  private final GameModel gameModel;
  private final PortfolioView portfolioView;
  private final PortfolioService portfolioService;
  private final PositionService positionService;
  private PositionSummary selectedPosition;

  public PortfolioController (
      GameModel gameModel,
      PortfolioView portfolioView,
      PortfolioService portfolioService,
      PositionService positionService
  ) {
    if (gameModel == null) {
      throw new IllegalArgumentException("Game model cannot be null.");
    }

    if (portfolioView == null) {
      throw new IllegalArgumentException("Portfolio view cannot be null.");
    }

    if (portfolioService == null) {
      throw new IllegalArgumentException("Portfolio service cannot be null.");
    }

    if (positionService == null) {
      throw new IllegalArgumentException("Position service cannot be null.");
    }

    this.gameModel = gameModel;
    this.portfolioView = portfolioView;
    this.portfolioService = portfolioService;
    this.positionService = positionService;

    gameModel.addObserver(this);
    initialize();
  }

  private void initialize() {
    portfolioView.setOnPositionSelected(position -> {
      selectedPosition = position;
      portfolioView.updateSelectedPosition(position);
    });

    updateView();
  }

  @Override
  public void update() {
    updateView();
  }

  public void updateView() {
    Player player = gameModel.getPlayer();

    PortfolioSummary portfolioSummary =
        portfolioService.createPortfolioSummary(player);

    List<PositionSummary> positionSummaries =
        positionService.createPositionSummaries(player.getPortfolio());

    portfolioView.updateSummary(portfolioSummary);
    portfolioView.updatePositions(positionSummaries);

    if (!positionSummaries.isEmpty()) {
      selectedPosition = positionSummaries.get(0);
      portfolioView.updateSelectedPosition(selectedPosition);
    } else {
      selectedPosition = null;
      portfolioView.clearSelectedPosition();
    }
  }

  public void setOnOpenMarketPress(Runnable runnable) {
    if (runnable == null) {
      throw new IllegalArgumentException("Runnable cannot be null.");
    }

    portfolioView.setOnOpenMarketPress(event -> {
      if (selectedPosition == null) {
        return;
      }

      gameModel.setSelectedStock(selectedPosition.stock());
      runnable.run();
      gameModel.notifyObservers();
    });
  }
}
