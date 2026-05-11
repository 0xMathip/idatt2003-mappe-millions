package no.ntnu.group51.view;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class SidebarView implements View{

  private final VBox root = new VBox();

  public SidebarView() {



  }



  @Override
  public Parent getRoot() {
    return root;
  }
}
