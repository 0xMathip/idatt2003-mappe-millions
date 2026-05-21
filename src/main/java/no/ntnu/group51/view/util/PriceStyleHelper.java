package no.ntnu.group51.view.util;

import java.math.BigDecimal;
import javafx.scene.Node;

public final class PriceStyleHelper {

  private PriceStyleHelper() {

  }

  public static void applyPriceChangeStyle(Node node, BigDecimal latestChange) {
    node.getStyleClass().removeAll(
        "positive-price-change",
        "negative-price-change",
        "neutral-price-change"
    );

    int sign = latestChange.signum();

    if (sign < 0) {
      node.getStyleClass().add("negative-price-change");
    } else if (sign > 0) {
      node.getStyleClass().add("positive-price-change");
    } else {
      node.getStyleClass().add("neutral-price-change");
    }
  }
}
