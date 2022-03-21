package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class How2PlayCtrl {

    @FXML
    private Button backButton;

    @FXML
    private ImageView backButtonImg;

    @FXML
    private TabPane tabPane;

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * @param server reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public How2PlayCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Is called after constructor (Initializable)
     * Sets the image of backButtonImg and the images in the tabs in tabPane
     */
    @FXML
    private void initialize() {
//        backButtonImg.setImage(new Image(Objects.requireNonNull(getClass().getResource("../../../../resources/main/main/back.png").toExternalForm())));

//        for (Tab tab : tabPane.getTabs()) {
//            ImageView tmpImgView = new ImageView(new Image("client/src/main/resources/icons/back.png"));
//            tab.setGraphic(tmpImgView);
//        }
    }

    /**
     * Go back to the Splash Screen.
     * @param actionEvent
     */
    public void goBack(ActionEvent actionEvent) {
        mainCtrl.showSplash();
        mainCtrl.getPrimaryStage().show();
    }
}
