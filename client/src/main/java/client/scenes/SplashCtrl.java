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
     * @param server reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public SplashCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public SplashCtrl(){
    }

    /**
     * Exits the application, called by quit button
     */
    public void cancel() {
        Platform.exit();
    }

    /**
     * Is called after constructor (Initializable)
     * Sets the image of the ImageView in the splash screen to the logo
     * Should probably set the path to be non-relative but that's a problem for later
     */
    @FXML
    public void initialize() {
        //logoIMG = new ImageView();
        logoIMG.setImage(new Image(Objects.requireNonNull(getClass().getResource("../../../../resources/main/main/Logo.png")).toExternalForm()));
        //howToPlayText = new TextField();
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
        String sheet = Objects.requireNonNull(getClass().getResource("../../../../resources/main/main/enterNameSinglePlayer.css")).toExternalForm();
        mainCtrl.getSplash().getStylesheets().add(sheet);
        this.mainCtrl.getPrimaryStage().setScene(this.mainCtrl.getSplash());
        EnterNameCtrl enterNameCtrl = new EnterNameCtrl(server, this);
        enterNameCtrl.getSplashCtrl().mainCtrl.getPrimaryStage().show();
    }

    /**
     *Changes the scene with the screen for entering the username when pressing the MULTIPLAYER button.
     * @param actionEvent - the mouse clicked on the MULTIPLAYER button
     * @throws IOException
     */

    public void mouseClickedMultiPlayer(ActionEvent actionEvent) throws IOException {
        URL url = new File("client/src/main/resources/client/scenes/EnterNameMultiPlayer.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        this.mainCtrl.setPrimaryStage((Stage) ((Node)actionEvent.getSource()).getScene().getWindow());
        this.mainCtrl.setSplash(new Scene(root));
        this.mainCtrl.getPrimaryStage().setScene(this.mainCtrl.getSplash());
        EnterNameCtrl enterNameCtrl = new EnterNameCtrl(server, this);
        enterNameCtrl.getSplashCtrl().mainCtrl.getPrimaryStage().show();
    }
}
