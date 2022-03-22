package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class LobbyScreenCtrl extends Controller {

    @FXML
    private TableView<Player> table;

    @FXML
    private TableColumn<Player, String> colName;

    @FXML
    private Text playersText;

    @FXML
    private Text hintText;

    private ObservableList<Player> data;

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
    public void initialize() {;
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
        //sets the scene back to the main screen
        URL url = new File("client/src/main/resources/client/scenes/splash.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene newScene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(newScene);
        window.show();
    }

    /**
     * Method that changes the stage to MPGameScreen
     * TODO: Should also be managing the start of the game, fetching questions etc.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void startGame(ActionEvent actionEvent) throws IOException {
        URL url = new File("client/src/main/resources/client/scenes/MPGameScreen.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene newScene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(newScene);
        window.show();

        // TODO: Start a session, forward other players to the game, fetch questions.
    }
}
