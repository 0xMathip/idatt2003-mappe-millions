package no.ntnu.group51.view.factories;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.group51.view.util.StyleClass;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Factory for creating reusable statistic cards and text cards.
 */
public final class StatCardFactory {
  /**
   * Prevents instantiation of this utility class.
   */
  private StatCardFactory() {
  }

  /**
   * Creates a statistic card without subtitle.
   *
   * @param icon       the icon identifier
   * @param title      the card title
   * @param valueLabel the value label
   * @param iconStyle  the icon style class
   * @param valueStyle the value style class
   * @return the created card
   */
  public static HBox createCard(
      String icon,
      String title,
      Label valueLabel,
      String iconStyle,
      String valueStyle
  ) {
    return createCard(icon, title, valueLabel, null, iconStyle, valueStyle, null);
  }


  /**
   * Creates a statistic card with optional subtitle.
   *
   * @param icon          the icon identifier
   * @param title         the card title
   * @param valueLabel    the value label
   * @param subtitleLabel the subtitle label, or null
   * @param iconStyle     the icon style class
   * @param valueStyle    the value style class
   * @param subtitleStyle the subtitle style class
   * @return the created card
   */
  public static HBox createCard(
      String icon,
      String title,
      Label valueLabel,
      Label subtitleLabel,
      String iconStyle,
      String valueStyle,
      String subtitleStyle
  ) {
    FontIcon fontIcon = new FontIcon(icon);
    Label titleLabel = new Label(title);

    fontIcon.getStyleClass().add(iconStyle);
    titleLabel.getStyleClass().add(StyleClass.FACTORY_STAT_CARD_TOP_TEXT);
    valueLabel.getStyleClass().add(valueStyle);

    VBox textBox;

    if (subtitleLabel == null) {
      textBox = new VBox(4, titleLabel, valueLabel);
    } else {
      subtitleLabel.getStyleClass().add(subtitleStyle);
      textBox = new VBox(4, titleLabel, valueLabel, subtitleLabel);
    }

    textBox.setAlignment(Pos.CENTER_LEFT);

    HBox card = new HBox(16, fontIcon, textBox);
    card.setAlignment(Pos.CENTER_LEFT);
    card.getStyleClass().addAll(StyleClass.CARD, StyleClass.FACTORY_STAT_CARD);

    return card;
  }
}
