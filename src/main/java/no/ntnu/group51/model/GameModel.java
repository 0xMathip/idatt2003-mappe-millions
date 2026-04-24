package no.ntnu.group51.model;

import java.util.ArrayList;
import java.util.List;


public class GameModel {
  private List<Observer> observers = new ArrayList<>();

  public void addObserver(Observer o) {
    observers.add(o);
  }

  public void notifyObservers() {
    for (Observer o : observers) {
      o.update();
    }
  }
}

