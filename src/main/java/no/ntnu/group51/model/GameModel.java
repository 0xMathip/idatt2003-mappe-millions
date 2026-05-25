package no.ntnu.group51.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.group51.model.exchange.Exchange;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Stock;
import no.ntnu.group51.model.transaction.Transaction;

/**
 * Represents the shared game state used by controllers and views.
 *
 * <p>The model stores the active player, exchange, selected stock,
 * net worth history, and registered observers.
 */
public class GameModel {
  private final List<Observer> observers = new ArrayList<>();
  private final List<BigDecimal> netWorthHistory = new ArrayList<>();
  private final Player player;
  private final Exchange exchange;
  private Stock selectedStock;

  /**
   * Creates a game model for the given player and exchange.
   *
   * @param player   the active player
   * @param exchange the active exchange
   * @throws IllegalArgumentException if player or exchange is null
   */
  public GameModel(Player player, Exchange exchange) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null.");
    }
    if (exchange == null) {
      throw new IllegalArgumentException("Exchange cannot be null.");
    }

    this.player = player;
    this.exchange = exchange;
    this.selectedStock = null;
    netWorthHistory.add(player.getNetWorth());
  }

  /**
   * Returns the active player.
   *
   * @return the active player
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Returns the active exchange.
   *
   * @return the active exchange
   */
  public Exchange getExchange() {
    return exchange;
  }

  /**
   * Returns the stock selected by UI.
   *
   * @return the selected stock
   */
  public Stock getSelectedStock() {
    return selectedStock;
  }

  /**
   * Sets the selected stock and notifies observers.
   *
   * @param selectedStock the selected stock, or null if none is selected
   */
  public void setSelectedStock(Stock selectedStock) {
    this.selectedStock = selectedStock;
    notifyObservers();
  }

  /**
   * Returns the recorded net worth history for the player.
   *
   * @return an unmodifiable copy of the net worth history
   */
  public List<BigDecimal> getNetWorthHistory() {
    return List.copyOf(netWorthHistory);
  }

  /**
   * Adds the player's current net worth to the history.
   */
  public void recordNetWorth() {
    netWorthHistory.add(player.getNetWorth());
  }

  /**
   * Registers an observer to be notified when the model changes.
   *
   * @param o the observer to add
   * @throws IllegalArgumentException if observer is null
   */
  public void addObserver(Observer observer) {
    if (observer == null) {
      throw new IllegalArgumentException("Observer cannot be null.");
    }
    observers.add(observer);
  }

  /**
   * Notifies all registered observers.
   */
  public void notifyObservers() {
    for (Observer o : observers) {
      o.update();
    }
  }
}

