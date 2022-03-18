package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiChoiceButtonCtrl {

  @FXML
  private Button answer1;
  @FXML
  private Button answer2;
  @FXML
  private Button answer3;

  private final ServerUtils server;
  private final MainCtrl mainCtrl;
  private List<Activity> activities = new ArrayList<>();
  private final Random random;


  @Inject
  public MultiChoiceButtonCtrl(ServerUtils server, MainCtrl mainCtrl, Random random) {
    this.server = server;
    this.mainCtrl = mainCtrl;
    this.random = random;
  }



  public void onButtonClick() {

  }

}
