package no.ntnu.group51.Player;

import java.math.BigDecimal;
import no.ntnu.group51.Portfolio.Portfolio;
import no.ntnu.group51.Transaction.TransactionArchive;

/**
 * Class for the player playing the market.
 */
public class Player {
  private final String name;
  private BigDecimal startingMoney;
  private BigDecimal money;
  private final Portfolio portfolio;
  private final TransactionArchive transactionArchive;

  /**
   * Creates a new player.
   *
   * @param name Name of the player
   * @param startingMoney Starting capital for the player
   */
  public Player(String name, BigDecimal startingMoney) {
    this.name = name;
    this.money = startingMoney;
    this.portfolio = new Portfolio();
    this.transactionArchive = new TransactionArchive();
  }

  /**
   * Adds money to the player.
   *
   * @param money How much you want to add
   */
  public void addMoney(BigDecimal money) {
    this.money = this.money.add(money);
  }

  /**
   * Withdraws or removes money from the player.
   *
   * @param money How much you want to withdraw
   */
  public void withdrawMoney(BigDecimal money) {
    this.money = this.money.subtract(money);
  }

  public String getName() {
    return name;
  }

  public BigDecimal getMoney() {
    return money;
  }

  public Portfolio getPortfolio() {
    return portfolio;
  }

  public TransactionArchive getTransactionArchive() {
    return transactionArchive;
  }
}
