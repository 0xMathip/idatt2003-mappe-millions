package no.ntnu.group51.view.factories;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public final class StatCardFactory {
  private StatCardFactory() {
  }

  public static HBox createIconCard(
      String icon,
      String title,
      String value,
      String subtitle,
      String iconStyle,
      String valueStyle,
      String subtitleStyle
  ) {
    FontIcon fontIcon = new FontIcon(icon);

    Label top = new Label(title);
    Label middle = new Label(value);
    Label bottom = new Label(subtitle);

    fontIcon.getStyleClass().add(iconStyle);
    top.getStyleClass().add("factory-stat-card-top-text");
    middle.getStyleClass().add(valueStyle);
    bottom.getStyleClass().add(subtitleStyle);

    VBox textBox = new VBox(4, top, middle, bottom);
    textBox.setAlignment(Pos.CENTER_LEFT);

    HBox iconCard = new HBox(16, fontIcon, textBox);
    iconCard.setAlignment(Pos.CENTER_LEFT);
    iconCard.getStyleClass().addAll("card", "factory-stat-card");

    return iconCard;
  }

  public static VBox createTextCard (
      String title,
      String value,
      String valueStyle
  ) {
    return createTextCard(title, value, "", valueStyle, "factory-stat-card-bot-text", null);
  }

  public static VBox createTextCard (
      String title,
      String value,
      String subtitle,
      String valueStyle,
      String subtitleStyle
  ) {
    return createTextCard(title, value, subtitle, valueStyle, subtitleStyle, null);
  }

  public static VBox createTextCard (
      String title,
      String value,
      String subtitle,
      String valueStyle,
      String subtitleStyle,
      String valueStateStyle
  ) {
    Label top = new Label(title);
    Label middle = new Label(value);
    Label bottom = new Label(subtitle);

    top.getStyleClass().add("factory-stat-card-top-text");
    middle.getStyleClass().add(valueStyle);

    if (valueStateStyle != null && !valueStateStyle.isBlank()) {
      middle.getStyleClass().add(valueStateStyle);
      bottom.getStyleClass().add(valueStateStyle);
    }

    bottom.getStyleClass().add(subtitleStyle);

    VBox textCard = new VBox(4, top, middle, bottom);
    textCard.setAlignment(Pos.CENTER_LEFT);
    textCard.getStyleClass().addAll("card", "factory-stat-card");

    return textCard;
  }
}
