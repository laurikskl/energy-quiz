package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Injector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static com.google.inject.Guice.createInjector;

public class SplashCtrl extends Controller{

    @FXML
    private ImageView logoIMG;

    @FXML
    private TextField howToPlayText;

    @Inject
    public SplashCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);


    /**
     * Exits the application, called by quit button
     */
    public void cancel() {
        this.getMainCtrl().close();
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
     * @throws IOException when file not found or misread
     */

    public void mouseClickedSinglePlayer(ActionEvent actionEvent) throws IOException {
        URL url = new File("client/src/main/resources/client/scenes/SPGameScreen.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Controller controller = new SPGameCtrl(this.getServer(), this.getMainCtrl());
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        this.getMainCtrl().setCtrl(controller);
        this.getMainCtrl().getPrimaryStage().setScene(scene);
        this.getMainCtrl().getPrimaryStage().show();
    }

/*
    public void mouseClickedSinglePlayer(javafx.event.ActionEvent actionEvent) throws IOException {
        URL url = new File("client/src/main/resources/client/scenes/SPGameScreen.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        this.mainCtrl.getPrimaryStage().setScene(scene);
        this.mainCtrl.getPrimaryStage().show();
        client/src/main/resources/client/scenes/EnterNameSinglePlayer.fxml
    }
*/
    /**
     *Changes the scene with the screen for entering the username when pressing the MULTIPLAYER button.
     * @param actionEvent - the mouse clicked on the MULTIPLAYER button
     * @throws IOException when file not found or misread
     */

    public void mouseClickedMultiPlayer(ActionEvent actionEvent) throws IOException {
        URL url = new File("client/src/main/resources/client/scenes/EnterNameMultiPlayer.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        this.getMainCtrl().getPrimaryStage().setScene(scene);
        this.getMainCtrl().getPrimaryStage().show();
    }
}
