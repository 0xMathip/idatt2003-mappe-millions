package no.ntnu.group51.view.util;

import java.math.BigDecimal;
import javafx.scene.Node;

public final class PriceStyleHelper {

  private PriceStyleHelper() {
  }

  public static String getPriceChangeStyle(BigDecimal latestChange) {
    int sign = latestChange.signum();

    if (sign < 0) {
      return "negative-price-change";
    } else if (sign > 0) {
      return "positive-price-change";
    }

    return "neutral-price-change";
  }

  public static void applyPriceChangeStyle(Node node, BigDecimal latestChange) {
    node.getStyleClass().removeAll(
        "positive-price-change",
        "negative-price-change",
        "neutral-price-change"
    );

    node.getStyleClass().add(getPriceChangeStyle(latestChange));
  }

}
