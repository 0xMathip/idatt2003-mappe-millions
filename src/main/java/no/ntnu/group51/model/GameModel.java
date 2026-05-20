package no.ntnu.group51.model;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.group51.model.exchange.Exchange;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stocks.Stock;
import no.ntnu.group51.model.transaction.Transaction;


public class GameModel {
  private List<Observer> observers = new ArrayList<>();
  private final Player player;
  private final Exchange exchange;
  private Stock selectedStock;
  private Transaction selectedTransaction;

  public GameModel(Player player, Exchange exchange) {
    this.player = player;
    this.exchange = exchange;
    this.selectedStock = null;
    this.selectedTransaction = null;
  }

  public Player getPlayer(){
    return player;
  }

  public Exchange getExchange() {
    return exchange;
  }

  public Stock getSelectedStock() {
    return selectedStock;
  }

  public Transaction getSelectedTransaction() {
    return selectedTransaction;
  }

  public void setSelectedStock(Stock selectedStock) {
    this.selectedStock = selectedStock;
    notifyObservers();
  }

  public void setSelectedTransaction(Transaction selectedTransaction) {
    this.selectedTransaction = selectedTransaction;
    notifyObservers();
  }

  public void addObserver(Observer o) {
    observers.add(o);
  }

  public void notifyObservers() {
    for (Observer o : observers) {
      o.update();
    }
  }
}

