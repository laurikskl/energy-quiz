package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class EnterNameSinglePlayerCtrl extends Controller {

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

    String usernameString;

    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public EnterNameSinglePlayerCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    /**
     * Is called after constructor (Initializable)
     * Sets the image of the ImageView in the splash screen to the logo
     * Should probably set the path to be non-relative but that's a problem for later
     */
    @FXML
    public void initialize() {
        backIMG = new ImageView();
        backIMG.setImage(new Image(Objects.requireNonNull(getClass().getResource("../../../../resources/main/main/BackButton.png")).toExternalForm()));
        back = new Button("", backIMG);
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
     * @throws IOException
     */
    @FXML
    public void startGame(ActionEvent actionEvent) throws IOException {

        usernameString = userName.getText();

        //if the user doesn't provide a username, send a warning text
        if (usernameString.isEmpty()) warningText.setText("Please provide a name!");

        else {
            //fetch player from database, if it doesn't exist store a new player with score 0
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

            this.mainCtrl.setPlayer(player);
            this.mainCtrl.showSPGame();
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
