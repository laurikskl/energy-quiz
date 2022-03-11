package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SplashCtrl implements Initializable {

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
     * @param location no idea what this means yet
     * @param resources no idea what this means yet
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

}
