package no.ntnu.group51.model.player;

public enum PlayerLevel {
  NOVICE("Novice"),
  INVESTOR("Investor"),
  SPECULATOR("Speculator"),;

  private final String displayLevel;

  PlayerLevel(String displayLevel) {
    this.displayLevel = displayLevel;
  }

  @Override
  public String toString() {
    return this.displayLevel;
  }
}
