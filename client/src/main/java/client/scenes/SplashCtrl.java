package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SplashCtrl extends Controller {

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
     */

    @FXML
    private void initialize() {
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

    public void mouseClickedSinglePlayer(MouseEvent actionEvent) throws IOException {
        getMainCtrl().showEnterNameSinglePlayer();
    }


    /**
     * Changes the scene with the screen for entering the username when pressing the MULTIPLAYER button.
     *
     * @param actionEvent - the mouse clicked on the MULTIPLAYER button
     * @throws IOException when file is not found
     */
    public void mouseClickedMultiPlayer(MouseEvent actionEvent) throws IOException {
        getMainCtrl().showEnterNameMultiPlayer();
    }


    /**
     * Changes the scene to the HowToPlay Scene.
     *
     * @param actionEvent - the mouse clicked on the How To Play button
     * @throws IOException
     */
    public void mouseClickedHow2Play(MouseEvent actionEvent) throws IOException {
        getMainCtrl().showHow2Play();
    }

    /**
     * Changes the scene to the Admin Scene.
     *
     * @param actionEvent - the mouse clicked on Admin button
     * @throws IOException when file is not found
     */
    public void mouseClickedAdmin(MouseEvent actionEvent) throws IOException {
        getMainCtrl().showAdmin();
    }

    /**
     * Changes the scene to Leaderboard
     *
     * @param mouseEvent
     */
    public void mouseClickedLeaderboard(MouseEvent mouseEvent) {
        getMainCtrl().showLeaderboard();
    }
}
