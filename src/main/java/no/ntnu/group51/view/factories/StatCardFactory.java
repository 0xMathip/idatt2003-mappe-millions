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
      String titleStyle,
      String valueStyle,
      String subtitleStyle
  ) {
    FontIcon fontIcon = new FontIcon(icon);

    Label top = new Label(title);
    Label middle = new Label(value);
    Label bottom = new Label(subtitle);

    fontIcon.getStyleClass().add(iconStyle);
    top.getStyleClass().add(titleStyle);
    middle.getStyleClass().add(valueStyle);
    bottom.getStyleClass().add(subtitleStyle);

    VBox textBox = new VBox(2, top, middle, bottom);
    textBox.setAlignment(Pos.CENTER_LEFT);

    HBox iconCard = new HBox(8, fontIcon, textBox);
    iconCard.setAlignment(Pos.CENTER_LEFT);
    iconCard.getStyleClass().addAll("factory-stat-card", "factory-stat-card-icon-card");

    return iconCard;
  }

  public static VBox createTextCard(
      String title,
      String value,
      String subtitle,
      String titleStyle,
      String valueStyle,
      String subtitleStyle
  ) {
    Label top = new Label(title);
    Label middle = new Label(value);
    Label bottom = new Label(subtitle);

    top.getStyleClass().add(titleStyle);
    middle.getStyleClass().add(valueStyle);
    bottom.getStyleClass().add(subtitleStyle);

    VBox textCard = new VBox(2, top, middle, bottom);
    textCard.setAlignment(Pos.CENTER_LEFT);

    textCard.getStyleClass().addAll("factory-stat-card", "factory-stat-card-text-card");

    return textCard;
  }

  public static VBox createTextCard(
      String title,
      String value,
      String titleStyle,
      String valueStyle
  ) {
    return createTextCard(title, value, "", titleStyle, valueStyle, "factory-stat-card-bot-text");
  }
}
