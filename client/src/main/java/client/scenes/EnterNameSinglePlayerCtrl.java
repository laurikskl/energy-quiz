package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import javax.swing.text.html.ImageView;
import java.io.IOException;

public class EnterNameSinglePlayerCtrl extends Controller {


    String usernameString;
    @FXML
    private Button button;
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView backIMG;
    @FXML
    private Button back;
    @FXML
    private TextField userName;
    @FXML
    private Text warningText;

    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public EnterNameSinglePlayerCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }


    /**
     * Exits the application, called by quit button
     */
    public void cancel() {
        Platform.exit();
    }

    /**
     * Method that changes the screen to the SP.
     *
     * @param actionEvent - pressing the play button triggers this function.
     */
    @FXML
    public void startGame(MouseEvent actionEvent) throws IOException, InterruptedException {
        if (this.server.checkServer() && this.server.connect()) {
            usernameString = userName.getText();

            //if the user doesn't provide a username, send a warning text
            if (usernameString.isEmpty()) warningText.setText("Please provide a name!");
            else if (usernameString.length() > 15) warningText.setText("Your name can be 15 characters at most!");

            else {
                //fetch player from database, if it doesn't exist store a new player with score 0
                Player player;
                try {
                    player = getServer().getPlayer(usernameString);

                    if (player == null) {
                        player = new Player(usernameString, 0);
                        getServer().setPlayer(usernameString, 0);
                    }
                    else {
                        player = getServer().getPlayer(usernameString);
                    }
                } catch (Exception e) { //this should only happen when the server is null
                    player = new Player(usernameString, 0);
                }
//            super.getMainCtrl().startSPGame(player, server);
//            super.getMainCtrl().showSPGame();
                getMainCtrl().startSPGame(player, getServer());
                getMainCtrl().showSPGame();
            }
        }
        else {
            this.warningText.setText("Couldn't connect to the server");
        }

    }

    /**
     * Method that returns the application to the initial screen when the back button is pressed.
     *
     * @param actionEvent - pressing the back button triggers this function
     * @throws IOException when files not found or misread
     */
    public void back(MouseEvent actionEvent) throws IOException {
        getMainCtrl().showSplash();
    }
}
