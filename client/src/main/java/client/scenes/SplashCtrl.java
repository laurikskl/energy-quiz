package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;

public class SplashCtrl extends Controller {

    @FXML
    private ImageView logoIMG;

    @FXML
    private TextField howToPlayText;

    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public SplashCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    public void cancel() {
        Platform.exit();
    }

    /**
     * Is called automatically after constructor
     * Sets the image of the ImageView in the splash screen to the logo
     * Should probably set the path to be non-relative but that's a problem for later
     *
     */

    @FXML
    private void initialize() {
        logoIMG.setImage(new Image("/main/Logo.png"));
        this.invisibleHowToPlay();
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
        this.mainCtrl.showEnterNameSinglePlayer();
    }


    /**
     * Changes the scene with the screen for entering the username when pressing the MULTIPLAYER button.
     *
     * @param actionEvent - the mouse clicked on the MULTIPLAYER button
     * @throws IOException when file is not found
     */
    public void mouseClickedMultiPlayer(ActionEvent actionEvent) throws IOException {
        mainCtrl.showEnterNameMultiPlayer();
    }

    public void setServer(ServerUtils server) {
        this.server = server;
    }
}
