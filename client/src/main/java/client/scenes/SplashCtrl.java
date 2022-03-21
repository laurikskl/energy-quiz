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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class SplashCtrl {

    @FXML
    private ImageView logoIMG;

    @FXML
    private TextField howToPlayText;

    private ServerUtils server;
    private MainCtrl mainCtrl;

    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */

    @Inject
    public SplashCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = new ServerUtils();
        this.mainCtrl = mainCtrl;
    }

    public SplashCtrl() {
    }

    public ServerUtils getServer() {
        return server;
    }

    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    /**
     * Exits the application, called by quit button
     */

    public void cancel() {
        Platform.exit();
    }


    /**
     * Is called automatically after constructor
     * Sets the image of the ImageView in the splash screen to the logo
     * Should probably set the path to be non-relative but that's a problem for later
     *
     * @param mainCtrl
     */

    @FXML
    public void initialize(MainCtrl mainCtrl) {
        logoIMG.setImage(new Image(Objects.requireNonNull(getClass().getResource("../../../../resources/main/main/Logo.png")).toExternalForm()));
        this.invisibleHowToPlay();
        //System.out.println("RONALDOSIII");
    }


    /**
     * Makes the how to play text visible (on hovering how2play button)
     */

    public void showHowToPlay() {
        howToPlayText.setVisible(true);
    }


    /**
     * Makes the how to play text invisible (on exiting how2play button and startup)
     */

    public void invisibleHowToPlay() {
        howToPlayText.setVisible(false);
    }


    /**
     * Changes the scene with the screen for entering the username when pressing the SINGLEPLAYER button.
     *
     * @param actionEvent - the mouse clicked on the SINGLEPLAYER button
     * @throws IOException when file is not found
     */

    public void mouseClickedSinglePlayer(javafx.event.ActionEvent actionEvent) throws IOException {

        //set the root to the new scene
        URL url = new File("client/src/main/resources/client/scenes/EnterNameSinglePlayer.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene newScene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(newScene);
        window.show();

    }


    /**
     * Changes the scene with the screen for entering the username when pressing the MULTIPLAYER button.
     *
     * @param actionEvent - the mouse clicked on the MULTIPLAYER button
     * @throws IOException when file is not found
     */

    public void mouseClickedMultiPlayer(ActionEvent actionEvent) throws IOException {

        //set the root to the new scene
        URL url = new File("client/src/main/resources/client/scenes/EnterNameMultiPlayer.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene newScene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(newScene);
        window.show();

    }

    /**
     * Changes the scene with the Leaderboard screen when pressing the LEADERBOARD button
     *
     * @param actionEvent
     * @throws IOException
     */
    public void mouseClickedLeaderboard(ActionEvent actionEvent) throws IOException {
        //set the room to the new scene
        URL url = new File("client/src/main/resources/client/scenes/LeaderboardScreen.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene newScene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(newScene);
        window.show();
    }
}
