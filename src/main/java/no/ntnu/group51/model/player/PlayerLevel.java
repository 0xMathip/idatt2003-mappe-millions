package no.ntnu.group51.model.player;

/**
 * Represents the player's progression level in the trading game.
 *
 * <p>Levels progress from NOVICE to INVESTOR to SPECULATOR based on XP.</p>
 */
public enum PlayerLevel {
  NOVICE("Novice"),
  INVESTOR("Investor"),
  SPECULATOR("Speculator");

  private final String displayLevel;

  PlayerLevel(String displayLevel) {
    this.displayLevel = displayLevel;
  }

  /**
   * Returns the display name of this player level.
   *
   * @return the human-readable level name
   */
  @Override
  public String toString() {
    return this.displayLevel;
  }
}
