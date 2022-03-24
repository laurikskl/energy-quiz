package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.event.ActionEvent;
import java.io.File;

public class MultiChoiceCtrl {

    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Question.MultiChoice multiChoice;
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

    public MultiChoiceCtrl() {
    }

    @Inject
    public MultiChoiceCtrl(ServerUtils server, MainCtrl mainCtrl, Question.MultiChoice multiChoice) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.multiChoice = multiChoice;
    }

    /**
     * Set how the screen is looking:
     * - Insert text into questions
     * - Set the correct images
     *
     * @param server
     * @param mainCtrl
     * @param multiChoice
     */
    @FXML
    public void initialize(ServerUtils server, MainCtrl mainCtrl, Question.MultiChoice multiChoice) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.multiChoice = multiChoice;
        String path1 = multiChoice.getActivities().get(0).getImagePath();
        String path2 = multiChoice.getActivities().get(1).getImagePath();
        String path3 = multiChoice.getActivities().get(2).getImagePath();

        image1.setImage(new Image(new File(path1).toURI().toString()));
        image2.setImage(new Image(new File(path2).toURI().toString()));
        image3.setImage(new Image(new File(path3).toURI().toString()));

        answer1.setText(multiChoice.getActivities().get(0).getName());
        answer2.setText(multiChoice.getActivities().get(1).getName());
        answer3.setText(multiChoice.getActivities().get(2).getName());
    }

    /**
     * This should be called when this question frame is called from the game controller,
     * when using initialize, it will load all the question frames when the application
     * is started, that could cause problems in the future
     */
//    @FXML
//    public void onOpen() {
//
//    }
    @FXML
    public void handleButtonPress(ActionEvent actionEvent) {
    }

    //  /**
//   * Paints the buttons, the wrong answers are painted red and the correct one is painted
//   * green. Also has the logic ready to check if the correct one is clicked, first we
//   * need to know how this information is going to be retrieved by the gameController
//   * @param actionEvent the Button that was clicked
//  **/
//  @FXML
//  public void handleButtonPress(ActionEvent actionEvent) {
//    Button correct = answer1;
//    Button wrong1 = answer2;
//    Button wrong2 = answer3;
//    if(answer2.getText().equals(multiChoice.getCorrect().getName())){
//      correct = answer2;
//      wrong1 = answer1;
//      wrong2 = answer3;
//    }
//    else if(answer3.getText().equals(multiChoice.getCorrect().getName())){
//      correct = answer3;
//      wrong1 = answer1;
//      wrong2 = answer2;
//    }
//    correct.setStyle("-fx-background-color: #00FF00; ");
//    wrong1.setStyle("-fx-background-color: #FF0000; ");
//    wrong2.setStyle("-fx-background-color: #FF0000; ");
//  }

}


