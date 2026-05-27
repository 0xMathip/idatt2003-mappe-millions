package no.ntnu.group51.model.xp;

import java.math.BigDecimal;
import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.transaction.Purchase;
import no.ntnu.group51.model.transaction.Sale;
import no.ntnu.group51.model.transaction.Transaction;

/**
 * Utility class for calculating and awarding experience points
 * based on completed transactions.
 */
public class XpCalc {

  /**
   * Calculates and awards XP to the player based on transaction type.
   *
   * @param player the player receiving XP
   * @param transaction the completed transaction
   * @param amount the transaction amount used for XP calculation
   * @throws IllegalArgumentException if any argument is null
   */
  public static void calculateXp(Player player, Transaction transaction, BigDecimal amount) {

    if (transaction instanceof Purchase) {
      player.addXp(amount.multiply(BigDecimal.valueOf(0.5)));
    }
    if (transaction instanceof Sale) {
      player.addXp(amount.max(BigDecimal.ZERO).multiply(BigDecimal.valueOf(0.8)));
    }
  }
}
