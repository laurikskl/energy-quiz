package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ButtonWhatRequiresMoreNRGCtrl {

  @FXML
  private Button answer1;
  @FXML
  private Button answer2;
  @FXML
  private Button answer3;

  private final ServerUtils server;
  private final MainCtrl mainCtrl;

  @Inject
  public ButtonWhatRequiresMoreNRGCtrl(ServerUtils server, MainCtrl mainCtrl) {
    this.server = server;
    this.mainCtrl = mainCtrl;
  }

  public onButtonClick() {

  }

}
