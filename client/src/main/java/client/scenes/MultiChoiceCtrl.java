package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MultiChoiceCtrl {

  @FXML
  private Button answer1;
  @FXML
  private Button answer2;
  @FXML
  private Button answer3;

  @FXML
  private ImageView image1;
  @FXML
  private ImageView image2;
  @FXML
  private ImageView image3;


  private final ServerUtils server;
  private final MainCtrl mainCtrl;
  private final Question.MultiChoice multiChoice;


  @Inject
  public MultiChoiceCtrl(ServerUtils server, MainCtrl mainCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
    this.multiChoice = server.getMultiChoice();
  }

  @FXML
  public void initialize() {
    image1.setImage(new Image(multiChoice.getActivities().get(0).getImagePath()));
    image2.setImage(new Image(multiChoice.getActivities().get(1).getImagePath()));
    image3.setImage(new Image(multiChoice.getActivities().get(2).getImagePath()));
    answer1.setText(multiChoice.getActivities().get(0).getName());
    answer2.setText(multiChoice.getActivities().get(1).getName());
    answer3.setText(multiChoice.getActivities().get(2).getName());
  }



  public void onButtonClick() {

  }

}
