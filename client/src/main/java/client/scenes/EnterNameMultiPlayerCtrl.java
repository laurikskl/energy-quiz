package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
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

public class EnterNameMultiPlayerCtrl {

    String usernameString;
    @FXML
    private Button button;
    @FXML
    private ImageView backIMG;
    @FXML
    private Button back;
    @FXML
    private TextField userName;
    @FXML
    private AnchorPane root;
    @FXML
    private Text warningText;
    private ServerUtils serverUtils;
    private MainCtrl mainCtrl;

    /**
     * Constructor for the controller.
     *
     * @param serverUtils
     * @param mainCtrl
     */
    @Inject
    public EnterNameMultiPlayerCtrl(ServerUtils serverUtils, MainCtrl mainCtrl) {
        this.serverUtils = serverUtils;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Default constructor.
     */
    public EnterNameMultiPlayerCtrl() {
    }

    /**
     * Is called after constructor (Initializable)
     * Sets the image of the ImageView in the splash screen to the logo
     * Should probably set the path to be non-relative but that's a problem for later
     */
    @FXML
    public void initialize(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
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

    public MainCtrl getMainCtrl() {
        return mainCtrl;
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
            URL url = new File("client/src/main/resources/client/scenes/LobbyScreen.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);

            Scene newScene = new Scene(root);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(newScene);
            window.show();
        }

    }

    /**
     * Method that returns the application to the initial screen when the back button is pressed.
     *
     * @param actionEvent - pressing the back button triggers this function
     * @throws IOException
     */
    public void back(ActionEvent actionEvent) throws IOException {

        //sets the scene back to the main screen
        URL url = new File("client/src/main/resources/client/scenes/splash.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);

        Scene newScene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(newScene);
        window.show();
    }
}
