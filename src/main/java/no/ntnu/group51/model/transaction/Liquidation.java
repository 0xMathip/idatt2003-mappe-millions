package no.ntnu.group51.model.transaction;

import no.ntnu.group51.model.stock.Share;

public class Liquidation extends Sale {

  public Liquidation(Share share, int week) {
    super(share, week);
  }
}