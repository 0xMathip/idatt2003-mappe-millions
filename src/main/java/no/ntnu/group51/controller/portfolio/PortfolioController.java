package no.ntnu.group51.controller.portfolio;

import no.ntnu.group51.model.GameModel;
import no.ntnu.group51.service.portfolio.PortfolioService;
import no.ntnu.group51.service.portfolio.PositionService;
import no.ntnu.group51.view.pages.PortfolioView;

public class PortfolioController {

  private final GameModel gameModel;
  private final PortfolioView portfolioView;
  private final PortfolioService portfolioService;
  private final PositionService positionService;

  public PortfolioController (
      GameModel gameModel,
      PortfolioView portfolioView,
      PortfolioService portfolioService,
      PositionService positionService
  ) {
    if (gameModel == null) {
      throw new IllegalArgumentException("Gamemodel cannot be null.");
    }

    if (portfolioView == null) {
      throw new IllegalArgumentException("PortfolioView cannot be null.");
    }

    if (portfolioService == null) {
      throw new IllegalArgumentException("PortfolioService cannot be null.");
    }

    if (positionService == null) {
      throw new IllegalArgumentException("PositionService cannot be null.");
    }

    this.gameModel = gameModel;
    this.portfolioView = portfolioView;
    this.portfolioService = portfolioService;
    this.positionService = positionService;

    initialize();
  }

  private void initialize() {
    updateView();
  }

  public void updateView() {

  }
}
