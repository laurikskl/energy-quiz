package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Question;
import javafx.event.ActionEvent;
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
  public void onOpen() {
    image1.setImage(new Image(multiChoice.getActivities().get(0).getImagePath()));
    image2.setImage(new Image(multiChoice.getActivities().get(1).getImagePath()));
    image3.setImage(new Image(multiChoice.getActivities().get(2).getImagePath()));
    answer1.setText(multiChoice.getActivities().get(0).getName());
    answer2.setText(multiChoice.getActivities().get(1).getName());
    answer3.setText(multiChoice.getActivities().get(2).getName());
  }

  @FXML
  public void handleButtonPress(ActionEvent actionEvent) {
    Button correct = answer1;
    Button wrong1 = answer2;
    Button wrong2 = answer3;
    if(answer2.getText().equals(multiChoice.getCorrect().getName())){
      correct = answer2;
      wrong1 = answer1;
      wrong2 = answer3;
    }
    else if(answer3.getText().equals(multiChoice.getCorrect().getName())){
      correct = answer3;
      wrong1 = answer1;
      wrong2 = answer2;
    }
    correct.setStyle("-fx-background-color: #00FF00; ");
    wrong1.setStyle("-fx-background-color: #FF0000; ");
    wrong2.setStyle("-fx-background-color: #FF0000; ");
  }

  }


