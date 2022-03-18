package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class EnterNameCtrl {

    @FXML
    private ImageView backIMG;

    @FXML
    private Button back;

    @FXML
    private TextField userName;

    @FXML
    private Button play;

    private ServerUtils serverUtils;
    private SplashCtrl splashCtrl;

    String usernameString;

    @Inject
    public EnterNameCtrl(ServerUtils serverUtils, SplashCtrl splashCtrl) {
        this.serverUtils = serverUtils;
        this.splashCtrl = splashCtrl;
    }

    public EnterNameCtrl(){
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
        play = new Button("PLAY!");
    }

    /**
     * Exits the application, called by quit button
     */
    public void cancel() {
        Platform.exit();
    }

    public SplashCtrl getSplashCtrl() {
        return splashCtrl;
    }

    public void setSplashCtrl(SplashCtrl splashCtrl) {
        this.splashCtrl = splashCtrl;
    }

    @FXML
    public void startGame(ActionEvent actionEvent) {

        System.out.println(usernameString+"play");
    }

    @FXML
    public void setUsername(ActionEvent actionEvent) {
        usernameString = userName.getText();
    }

    /**
    public void back(ActionEvent actionEvent) throws IOException {
        URL url = new File("client/src/main/resources/client/scenes/splash.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        this.splashCtrl.getMainCtrl().setPrimaryStage((Stage) ((Node)actionEvent.getSource()).getScene().getWindow());
        this.splashCtrl.getMainCtrl().setSplash(new Scene(root));
        this.splashCtrl.getMainCtrl().getPrimaryStage().setScene(this.splashCtrl.getMainCtrl().getSplash());
        this.splashCtrl.getMainCtrl().getPrimaryStage().show();
    }
     */
}
