package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

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
     * @throws IOException when reading files goes wrong
     */
    @FXML
    public void startGame(ActionEvent actionEvent) throws IOException {

        usernameString = userName.getText();

        if (usernameString.isEmpty()) warningText.setText("Please provide a name!");

        else {
            Player player;
            try {
                player = this.server.getPlayer(usernameString);
                if (player == null) {
                    player = new Player(usernameString, 0);
                    this.server.setPlayer(usernameString, 0);
                }
            } catch (Exception e) { //this should only happen when the server is null
                System.out.println("WARNING SERVER IS NOT ACTIVE");
                player = new Player(usernameString, 0);
            }
            this.mainCtrl.showLobbyScreen(List.of(player), player);
        }

    }

    /**
     * Method that returns the application to the initial screen when the back button is pressed.
     *
     * @param actionEvent - pressing the back button triggers this function
     * @throws IOException when reading files goes wrong
     */
    public void back(ActionEvent actionEvent) throws IOException {
        this.mainCtrl.showSplash();
    }
}
