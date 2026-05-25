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

/**
 * Controller for the portfolio page.
 *
 * <p>Updates portfolio summaries, position lists, and selected position details.
 */
public class PortfolioController implements Observer {

  private final GameModel gameModel;
  private final PortfolioView portfolioView;
  private final PortfolioService portfolioService;
  private final PositionService positionService;
  private PositionSummary selectedPosition;

  /**
   * Creates a portfolio controller.
   *
   * @param gameModel        the game model
   * @param portfolioView    the portfolio view
   * @param portfolioService the portfolio service
   * @param positionService  the position service
   * @throws IllegalArgumentException if any argument is null
   */
  public PortfolioController(
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void update() {
    updateView();
  }

  private void updateView() {
    Player player = gameModel.getPlayer();

    PortfolioSummary portfolioSummary =
        portfolioService.createPortfolioSummary(player);

    List<PositionSummary> positionSummaries =
        positionService.createPositionSummaries(player.getPortfolio());

    portfolioView.updateSummary(portfolioSummary);
    portfolioView.updatePositions(positionSummaries);

    if (positionSummaries.isEmpty()) {
      selectedPosition = null;
      portfolioView.clearSelectedPosition();
      return;
    }

    if (selectedPosition == null) {
      selectedPosition = positionSummaries.getFirst();
    } else {
      selectedPosition = positionSummaries.stream()
          .filter(position -> position.stock().equals(selectedPosition.stock()))
          .findFirst()
          .orElse(positionSummaries.getFirst());
    }

    portfolioView.updateSelectedPosition(selectedPosition);
  }

  /**
   * Sets the action to run when opening the selected position in the market.
   *
   * @param runnable the navigation action
   * @throws IllegalArgumentException if runnable is null
   */
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
