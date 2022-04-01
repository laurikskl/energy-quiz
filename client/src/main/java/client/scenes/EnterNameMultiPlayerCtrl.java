package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
     * @throws IOException when reading files goes wrong
     */

    @FXML
    public void startGame(MouseEvent actionEvent) throws IOException {

        usernameString = userName.getText();

        if(usernameString.isEmpty()) warningText.setText("Please provide a name!");
        else if(usernameString.length() > 15) warningText.setText("Your name can be 15 characters at most!");

        else {
            Player player;
            try {
                player = getServer().getPlayer(usernameString);
                if (player == null) {
                    player = new Player(usernameString, 0);
                    getServer().setPlayer(usernameString, 0);
                }
                //this should only happen when the server is null
            } catch (Exception e) {
                e.printStackTrace();
                player = new Player(usernameString, 0);
            }
            long id = server.getLobby();

            // If name isn't available, don't make a connection
            if(!server.nameCheck(player)){
                //if the username is broken, send a warning
                warningText.setText("This name is not available!");
                return;
            }

            // Make a connection to the lobby if name is available
            this.mainCtrl.makeConnection(player);
        }

    }

    /**
     * Method that returns the application to the initial screen when the back button is pressed.
     *
     * @param actionEvent - pressing the back button triggers this function
     * @throws IOException when reading files goes wrong
     */

    public void back(MouseEvent actionEvent) throws IOException {
        getMainCtrl().showSplash();
    }
}
