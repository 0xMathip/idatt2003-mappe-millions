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
   * @param icon the icon identifier
   * @param title the card title
   * @param valueLabel the value label
   * @param iconStyle the icon style class
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
   * @param icon the icon identifier
   * @param title the card title
   * @param valueLabel the value label
   * @param subtitleLabel the subtitle label, or null
   * @param iconStyle the icon style class
   * @param valueStyle the value style class
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

  /**
   * Creates a text-based statistic card without subtitle.
   *
   * @param title the card title
   * @param value the displayed value
   * @param valueStyle the value style class
   * @return the created card
   */
  public static VBox createTextCard(
      String title,
      String value,
      String valueStyle
  ) {
    return createTextCard(title, new Label(value), null, valueStyle,
        StyleClass.FACTORY_STAT_CARD_TOP_TEXT, null);
  }

  /**
   * Creates a text-based statistic card with optional subtitle.
   *
   * @param title the card title
   * @param value the displayed value
   * @param subtitle the subtitle text
   * @param valueStyle the value style class
   * @param subtitleStyle the subtitle style class
   * @return the created card
   */
  public static VBox createTextCard(
      String title,
      String value,
      String subtitle,
      String valueStyle,
      String subtitleStyle
  ) {
    return createTextCard(title, new Label(value), new Label(subtitle),
        valueStyle, subtitleStyle, null);
  }

  /**
   * Creates a text-based statistic card with optional state styling.
   *
   * @param title the card title
   * @param value the displayed value
   * @param subtitle the subtitle text
   * @param valueStyle the value style class
   * @param subtitleStyle the subtitle style class
   * @param valueStateStyle optional shared state style
   * @return the created card
   */
  public static VBox createTextCard(
      String title,
      String value,
      String subtitle,
      String valueStyle,
      String subtitleStyle,
      String valueStateStyle
  ) {
    return createTextCard(title, new Label(value), new Label(subtitle),
        valueStyle, subtitleStyle, valueStateStyle);
  }


  /**
   * Creates a fully configurable text-based statistic card.
   *
   * @param title the card title
   * @param valueLabel the main value label
   * @param subtitleLabel the subtitle label, or null
   * @param valueStyle the value style class
   * @param subtitleStyle the subtitle style class
   * @param valueStateStyle optional shared state style
   * @return the created card
   */
  public static VBox createTextCard(
      String title,
      Label valueLabel,
      Label subtitleLabel,
      String valueStyle,
      String subtitleStyle,
      String valueStateStyle
  ) {
    Label top = new Label(title);

    top.getStyleClass().add(StyleClass.FACTORY_STAT_CARD_TOP_TEXT);
    valueLabel.getStyleClass().add(valueStyle);

    if (subtitleLabel != null) {
      subtitleLabel.getStyleClass().add(subtitleStyle);
    }

    if (valueStateStyle != null && !valueStateStyle.isBlank()) {
      valueLabel.getStyleClass().add(valueStateStyle);

      if (subtitleLabel != null) {
        subtitleLabel.getStyleClass().add(valueStateStyle);
      }
    }

    VBox textCard = subtitleLabel == null
        ? new VBox(4, top, valueLabel)
        : new VBox(4, top, valueLabel, subtitleLabel);

    textCard.setAlignment(Pos.CENTER_LEFT);
    textCard.getStyleClass().addAll(StyleClass.CARD, StyleClass.FACTORY_STAT_CARD);

    return textCard;
  }
}
