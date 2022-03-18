package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import commons.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiChoiceCtrl {

  @FXML
  private Button answer1;
  @FXML
  private Button answer2;
  @FXML
  private Button answer3;

  private final ServerUtils server;
  private final MainCtrl mainCtrl;
  private final Question.MultiChoice multiChoice;


  @Inject
  public MultiChoiceCtrl(ServerUtils server, MainCtrl mainCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
    this.multiChoice = server.getMultiChoice();
  }



  public void onButtonClick() {

  }

}
