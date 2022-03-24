package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class MPGameCtrl extends Controller{

    @FXML
    private Button backButton;
    @FXML
    private Button smileEmote;
    @FXML
    private Button sadEmote;
    @FXML
    private Button kissEmote;
    @FXML
    private Button deadEmote;
    @FXML
    private Button laughEmote;

    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public MPGameCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    /**
     * Method that returns the application to the initial screen when the back button is pressed.
     *
     * @param actionEvent - pressing the back button triggers this function
     * @throws IOException
     */
    public void back(ActionEvent actionEvent) throws IOException {
        this.mainCtrl.showSplash();
    }
}
