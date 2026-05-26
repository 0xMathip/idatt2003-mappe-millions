package no.ntnu.group51.view.util;

import java.math.BigDecimal;
import javafx.scene.Node;

/**
 * Utility class for applying price change styling.
 */
public final class PriceStyleHelper {

  /**
   * Prevents instantiation of this utility class.
   */
  private PriceStyleHelper() {
  }

  /**
   * Returns the CSS style class for a price change value.
   *
   * @param latestChange the price change value
   * @return the matching style class
   * @throws IllegalArgumentException if latestChange is null
   */
  public static String getPriceChangeStyle(BigDecimal latestChange) {
    if (latestChange == null) {
      throw new IllegalArgumentException("Latest change cannot be null.");
    }

    int sign = latestChange.signum();

    if (sign < 0) {
      return StyleClass.NEGATIVE_PRICE_CHANGE;
    }

    if (sign > 0) {
      return StyleClass.POSITIVE_PRICE_CHANGE;
    }

    return StyleClass.NEUTRAL_PRICE_CHANGE;
  }

  /**
   * Applies the correct price change style to a node.
   *
   * @param node         the node to style
   * @param latestChange the price change value
   * @throws IllegalArgumentException if node or latestChange is null
   */
  public static void applyPriceChangeStyle(Node node, BigDecimal latestChange) {
    if (latestChange == null) {
      throw new IllegalArgumentException("Latest change cannot be null.");
    }
    if (node == null) {
      throw new IllegalArgumentException("Node cannot be null.");
    }
    node.getStyleClass().removeAll(
        StyleClass.POSITIVE_PRICE_CHANGE,
        StyleClass.NEGATIVE_PRICE_CHANGE,
        StyleClass.NEUTRAL_PRICE_CHANGE
    );

    node.getStyleClass().add(getPriceChangeStyle(latestChange));
  }
}
