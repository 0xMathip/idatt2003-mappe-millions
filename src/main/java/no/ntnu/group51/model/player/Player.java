package no.ntnu.group51.model.player;

import java.math.BigDecimal;
import java.util.Objects;
import no.ntnu.group51.model.portfolio.Portfolio;
import no.ntnu.group51.model.transaction.TransactionArchive;

/**
 * Class for the player playing the market.
 */
public class Player {
  private final String name;
  private final BigDecimal startingMoney;
  private BigDecimal money;
  private final Portfolio portfolio;
  private final TransactionArchive transactionArchive;
  private PlayerLevel playerLevel;
  private BigDecimal xp;
  private final BigDecimal[] xpRequired = {BigDecimal.valueOf(2000), BigDecimal.valueOf(6000)};

  /**
   * Creates a new player.
   *
   * @param name          Name of the player
   * @param startingMoney Starting capital for the player
   */
  public Player(String name, BigDecimal startingMoney) {
    if (startingMoney == null) {
      throw new IllegalArgumentException("Starting money is null");
    }
    this.name = Objects.requireNonNullElse(name, "Player");
    this.money = startingMoney;
    this.startingMoney = startingMoney;
    this.portfolio = new Portfolio();
    this.transactionArchive = new TransactionArchive();
    this.playerLevel = PlayerLevel.NOVICE;
    this.xp = BigDecimal.ZERO;
  }

  /**
   * Adds money to the player.
   *
   * @param money How much you want to add
   */
  public void addMoney(BigDecimal money) {
    if (money == null) {
      return;
    }
    this.money = this.money.add(money);
  }

  /**
   * Adds xp to the player.
   *
   * @param amount The amount you want to add
   */
  public void addXp(BigDecimal amount) {
    xp = xp.add(amount);
  }

  /**
   * Returns the player's current XP.
   *
   * @return the current XP
   */
  public BigDecimal getXp() {
    return xp;
  }

  /**
   * Returns the XP required to reach the next level.
   *
   * @return the XP threshold for the current level
   */
  public BigDecimal getXpRequired() {
    if (playerLevel == PlayerLevel.NOVICE) {
      return xpRequired[0];

    } else if (playerLevel == PlayerLevel.INVESTOR) {
      return xpRequired[1];
    }
    return BigDecimal.ONE;
  }

  /**
   * Gets the XP required to reach the previous level.
   * Used to calculate progress within the current level.
   *
   * @return the XP threshold for the previous level (0 for NOVICE)
   */
  public BigDecimal getPreviousLevelXpThreshold() {
    if (playerLevel == PlayerLevel.NOVICE) {
      return BigDecimal.ZERO;  // NOVICE is the first level
    } else if (playerLevel == PlayerLevel.INVESTOR) {
      return xpRequired[0];  // Previous threshold is NOVICE's requirement
    } else {  // SPECULATOR
      return xpRequired[1];  // Previous threshold is INVESTOR's requirement
    }
  }

  /**
   * Updates the player's level based on current XP.
   */
  public void levelUp() {
    if (getXp().compareTo(xpRequired[0]) < 0) {
      this.playerLevel = PlayerLevel.NOVICE;

    } else if (getXp().compareTo(xpRequired[0]) > 0 && getXp().compareTo(xpRequired[1]) < 0) {
      this.playerLevel = PlayerLevel.INVESTOR;

    } else {
      this.playerLevel = PlayerLevel.SPECULATOR;
    }
  }

  /**
   * Withdraws or removes money from the player.
   *
   * @param money How much you want to withdraw
   */
  public void withdrawMoney(BigDecimal money) {
    if (money == null) {
      throw new IllegalArgumentException("Withdrawal amount cannot be null");
    }
    if (this.money.compareTo(money) < 0) {
      throw new IllegalArgumentException("Required funds not available");
    }
    this.money = this.money.subtract(money);
  }

  /**
   * Updates the player's level based on current XP.
   */
  public void updateLevel() {
    if (getXp().compareTo(xpRequired[0]) < 0) {
      this.playerLevel = PlayerLevel.NOVICE;

    } else if (getXp().compareTo(xpRequired[0]) > 0 && getXp().compareTo(xpRequired[1]) < 0) {
      this.playerLevel = PlayerLevel.INVESTOR;

    } else {
      this.playerLevel = PlayerLevel.SPECULATOR;
    }
  }

  /**
   * Used to do checks for the player level after events such as
   * a transaction or going to the stats page. Think of it as something
   * that updates the player level when conditions are met.
   *
   * @param totalWeeks The total weeks the player has been trading

  public void updatePlayerLevel(int totalWeeks) {
    if (this.playerLevel == PlayerLevel.INVESTOR
        && totalWeeks >= 20
        && getNetWorth().compareTo(this.startingMoney.multiply(BigDecimal.valueOf(2))) >= 0) {
      setPlayerLevel(PlayerLevel.SPECULATOR);

    } else if (this.playerLevel == PlayerLevel.NOVICE
        && totalWeeks >= 10
        && getNetWorth().compareTo(this.startingMoney.multiply(BigDecimal.valueOf(1.2))) >= 0) {
      this.playerLevel = PlayerLevel.INVESTOR;

    } else if (this.playerLevel == PlayerLevel.SPECULATOR
        && getNetWorth().compareTo(this.startingMoney.multiply(BigDecimal.valueOf(2))) <= 0) {
      this.playerLevel = PlayerLevel.INVESTOR;

    } else if (this.playerLevel == PlayerLevel.INVESTOR
        && getNetWorth().compareTo(this.startingMoney.multiply(BigDecimal.valueOf(1.2))) <= 0) {
      this.playerLevel = PlayerLevel.NOVICE;
    }
  }
  */

  /**
   * Returns the player's current level.
   *
   * @return the player's level
   */
  public PlayerLevel getPlayerLevel() {
    return this.playerLevel;
  }

  /**
   * Returns the player's name.
   *
   * @return the player's name
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the player's current money balance.
   *
   * @return the current money
   */
  public BigDecimal getMoney() {
    return money;
  }

  /**
   * Returns the player's starting capital.
   *
   * @return the starting money
   */
  public BigDecimal getStartingMoney() {
    return startingMoney;
  }

  /**
   * Returns the player's portfolio.
   *
   * @return the portfolio
   */
  public Portfolio getPortfolio() {
    return portfolio;
  }

  /**
   * Returns the player's transaction archive.
   *
   * @return the transaction archive
   */
  public TransactionArchive getTransactionArchive() {
    return transactionArchive;
  }

  /**
   * Returns the player's total net worth.
   *
   * @return the sum of cash and portfolio value
   */
  public BigDecimal getNetWorth() {
    return money.add(portfolio.getPortfolioNetWorth());
  }

  /**
   * Sets the player's current level.
   *
   * @param playerLevel the level to assign
   */
  public void setPlayerLevel(PlayerLevel playerLevel) {
    this.playerLevel = playerLevel;
  }

}
