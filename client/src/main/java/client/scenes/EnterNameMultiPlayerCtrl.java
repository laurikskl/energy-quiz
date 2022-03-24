package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.io.IOException;

public class EnterNameMultiPlayerCtrl extends Controller {

    @FXML
    private TextField userName;
    @FXML
    private Text warningText;

    String usernameString;

    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public EnterNameMultiPlayerCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }


    /**
     * Exits the application, called by quit button
     */
    public void cancel() {
        Platform.exit();
    }

    /**
     * Method that changes the screen to the Lobby.
     *
     * @param actionEvent - pressing the play button triggers this function.
     * @throws IOException
     */
    @FXML
    public void startGame(ActionEvent actionEvent) throws IOException {

        usernameString = userName.getText();

        if (usernameString.isEmpty()) warningText.setText("Please provide a name!");

        else {
            this.mainCtrl.showLobbyScreen();
        }

    }

    /**
     * Method that returns the application to the initial screen when the back button is pressed.
     *
     * @param actionEvent - pressing the back button triggers this function
     * @throws IOException
     */
    public void back(ActionEvent actionEvent) throws IOException {
        this.mainCtrl.showSplash();
    }
}
