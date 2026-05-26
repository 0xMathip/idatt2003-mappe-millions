package no.ntnu.group51.model.xp;

import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.transaction.Purchase;
import no.ntnu.group51.model.transaction.Sale;
import no.ntnu.group51.model.transaction.Transaction;

import java.math.BigDecimal;

public class XpCalc {


  public static void calculateXp(Player player, Transaction transaction, BigDecimal amount) {

    if (transaction instanceof Purchase) {
      player.addXp(amount.multiply(BigDecimal.valueOf(0.5)));
    }
    if (transaction instanceof Sale) {
      player.addXp(amount.max(BigDecimal.ZERO).multiply(BigDecimal.valueOf(0.8)));
    }
  }
}
