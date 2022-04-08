package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
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

    public void setScore(int score){
        finalScore.setText("Your final score is: " + String.valueOf(score));
    }

    /**
     * This method takes the player back to the "Enter Name" screen - singleplayer
     * @param mouseEvent - pressing the button "PLAY AGAIN!"
     */
    public void playAgain(MouseEvent mouseEvent) {
        getMainCtrl().showEnterNameSinglePlayer();
    }

    /**
     * This method takes the player back to the main screen.
     * @param mouseEvent - pressing the button "MAIN MENU"
     */
    public void goToSplash(MouseEvent mouseEvent) {
        getMainCtrl().showSplash();
    }

    /**
     * This method closes the application.
     * @param mouseEvent - pressing the button "EXIT"
     */
    public void exit(MouseEvent mouseEvent) {
        Platform.exit();
    }
}
