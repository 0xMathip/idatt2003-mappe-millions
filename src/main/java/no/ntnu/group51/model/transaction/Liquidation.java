package no.ntnu.group51.model.transaction;

import no.ntnu.group51.model.stock.Share;

/**
 * Represents a forced sale caused by liquidation of a leveraged position.
 */
public class Liquidation extends Sale {

  /**
   * Creates a liquidation transaction.
   *
   * @param share the share being liquidated
   * @param week the current trading week
   */
  public Liquidation(Share share, int week) {
    super(share, week);
  }
}