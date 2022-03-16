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
import javafx.scene.input.KeyEvent;

import java.util.Objects;

public class EnterNameCtrl {

    @FXML
    private ImageView backIMG;

    @FXML
    private Button back;

    @FXML
    private TextField username;

    private ServerUtils serverUtils;
    private SplashCtrl splashCtrl;

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
        username = new TextField();
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


    public void startGame(ActionEvent actionEvent) {
        username = new TextField(username.getText());
        if (username.getText().equals("")) System.out.println("nu merge");

        else System.out.println(username.getText());
    }

    public void setNewPlayer(KeyEvent keyEvent) {
        if (keyEvent.getEventType() == KeyEvent.KEY_TYPED){
            if (username.getText().equals("")){
                System.out.println("merge");
            }
            else Platform.exit();
        }
    }
}
