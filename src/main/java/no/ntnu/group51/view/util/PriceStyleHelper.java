package no.ntnu.group51.view.util;

import java.math.BigDecimal;
import javafx.scene.Node;

public final class PriceStyleHelper {

  private PriceStyleHelper() {
  }

  public static String getPriceChangeStyle(BigDecimal latestChange) {
    int sign = latestChange.signum();

    if (sign < 0) {
      return StyleClass.NEGATIVE_PRICE_CHANGE;
    } else if (sign > 0) {
      return StyleClass.POSITIVE_PRICE_CHANGE;
    }

    return StyleClass.NEUTRAL_PRICE_CHANGE;
  }

  public static void applyPriceChangeStyle(Node node, BigDecimal latestChange) {
    node.getStyleClass().removeAll(
        StyleClass.POSITIVE_PRICE_CHANGE,
        StyleClass.NEGATIVE_PRICE_CHANGE,
        StyleClass.NEUTRAL_PRICE_CHANGE
    );

    node.getStyleClass().add(getPriceChangeStyle(latestChange));
  }
}
