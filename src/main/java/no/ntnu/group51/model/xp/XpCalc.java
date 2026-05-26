package no.ntnu.group51.model.xp;

import no.ntnu.group51.model.player.Player;
import no.ntnu.group51.model.stock.Share;
import no.ntnu.group51.model.transaction.Purchase;
import no.ntnu.group51.model.transaction.Sale;
import no.ntnu.group51.model.transaction.Transaction;

import java.math.BigDecimal;

public class XpCalc {


  public static void calculateXp(Player player, Transaction transaction, Share share) {

    if (transaction instanceof Purchase) {
      player.addXp(share.getPurchasePrice().multiply(BigDecimal.valueOf(0.5)));
    }
    if (transaction instanceof Sale) {
      BigDecimal amount = BigDecimal.valueOf(Math.max(0.0, share.getStock().getLatestPriceChange().doubleValue()));
      player.addXp(amount.multiply(BigDecimal.valueOf(2)));
    }
  }
}
