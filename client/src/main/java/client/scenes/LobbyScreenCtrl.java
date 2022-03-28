package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
//import commons.Player;
//import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.text.Text;
import java.io.IOException;

public class LobbyScreenCtrl extends Controller {
//
//    @FXML
//    private Button backButton;
//    @FXML
//    private TableView<Player> table;
//    @FXML
//    private TableColumn<Player, String> colName;
//    @FXML
//    private Text playersText;
//    @FXML
//    private Text hintText;
//
//    private ObservableList<Player> data;

    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public LobbyScreenCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    /**
     * Initializing the colName values
     */
    @FXML
    private void initialize() {;

        //TODO: Fetch the players currently in the waiting room and insert them into the table
        //colName.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().userName));

        //TODO: Update the number of players currently in the waiting room

        //TODO: Add a hint
    }


    /**
     * Method that returns the application to the initial screen when the back button is pressed.
     *
     * @param actionEvent - pressing the back button triggers this function
     * @throws IOException
     */
    public void back(ActionEvent actionEvent) throws IOException {
        getMainCtrl().showSplash();
    }

    /**
     * Method that changes the stage to MPGameScreen
     * TODO: Should also be managing the start of the game, fetching questions etc.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void startGame(ActionEvent actionEvent) throws IOException {
        getMainCtrl().showMPGame();

        // TODO: Start a session, forward other players to the game, fetch questions.
    }
}
