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

import java.util.List;

public class How2PlayCtrl extends Controller{


    @FXML
    private TabPane tabPane;

    /**
     * @param server reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public How2PlayCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    /**
     * Is called after constructor (Initializable)
     * Sets the image of backButtonImg and the images in the tabs in tabPane
     */
    @FXML
    private void initialize() {

        //load in all images from the how2Play folder, each in a different tab
        List<Tab> tabs = tabPane.getTabs();
        for (int i = 0; i < tabs.size(); i++) {
            ImageView tmpImgView = new ImageView(new Image(String.format("/how2Play/%d.png", i)));
            tabs.get(i).setContent(tmpImgView);
        }
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
