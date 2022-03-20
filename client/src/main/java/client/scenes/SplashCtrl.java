package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
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

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * @param server reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public SplashCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Exits the application, called by quit button
     */
    public void cancel() {
        mainCtrl.close();
    }

    /**
     * Is called after constructor (Initializable)
     * Sets the image of the ImageView in the splash screen to the logo
     * Should probably set the path to be non-relative but that's a problem for later
     */
    @FXML
    public void initialize() {
        logoIMG.setImage(new Image(Objects.requireNonNull(getClass().getResource("../../../../resources/main/main/Logo.png")).toExternalForm()));
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
     * @param actionEvent - the mouse clicked on the SINGLEPLAYER button
     * @throws IOException
     */

    public void mouseClickedSinglePlayer(javafx.event.ActionEvent actionEvent) throws IOException {
        URL url = new File("client/src/main/resources/client/scenes/EnterNameSinglePlayer.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        this.mainCtrl.setPrimaryStage((Stage) ((Node)actionEvent.getSource()).getScene().getWindow());
        this.mainCtrl.setSplash(new Scene(root));
        this.mainCtrl.getPrimaryStage().setScene(this.mainCtrl.getSplash());
        mainCtrl.getPrimaryStage().show();
    }

    /**
     *Changes the scene with the screen for entering the username when pressing the MULTIPLAYER button.
     * @param actionEvent - the mouse clicked on the MULTIPLAYER button
     * @throws IOException
     */

    public void mouseClickedMultiPlayer(ActionEvent actionEvent) throws IOException {
        URL url = new File("client/src/main/resources/client/scenes/EnterNameMultiPlayer.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        this.mainCtrl.getPrimaryStage().setScene(scene);
        mainCtrl.getPrimaryStage().show();
    }

    /**
     * Changes the scene to the HowToPlay Scene.
     * @param actionEvent - the mouse clicked on the How To Play button
     * @throws IOException
     */
    public void mouseClickedHow2Play(ActionEvent actionEvent) throws IOException {
        mainCtrl.showHow2Play();
        mainCtrl.getPrimaryStage().show();
    }
}
