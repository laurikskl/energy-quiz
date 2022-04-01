package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class EndGameCtrl extends Controller{

    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */

    @Inject
    public EndGameCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    @FXML
    private Text finalScore;

    @FXML
    private Button playAgain;

    public void initialize(int score){
        finalScore.setText("Your final score is: " + String.valueOf(score));
    }

    /**
     * This method takes the player back to the "Enter Name" screen - singleplayer
     * @param actionEvent - pressing the button "PLAY AGAIN!"
     */
    @FXML
    public void playAgain(ActionEvent actionEvent) {
        getMainCtrl().showEnterNameSinglePlayer();
    }

    /**
     * This method takes the player back to the main screen.
     * @param actionEvent - pressing the button "MAIN MENU"
     */
    public void goToSplash(ActionEvent actionEvent) {
        getMainCtrl().showSplash();
    }

    /**
     * This method closes the application.
     * @param actionEvent - pressing the button "EXIT"
     */
    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
