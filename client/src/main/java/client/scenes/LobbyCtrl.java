package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controller class for LobbyScreen.fxml
 */

public class LobbyCtrl extends Controller {

    /**
     * FXML elements
     */

    @FXML
    private ImageView playIMG;
    @FXML
    private ImageView hintIMG;
    @FXML
    private Button backButton;
    @FXML
    private TableView<Player> table;
    @FXML
    private TableColumn<Player, String> colName;
    @FXML
    private Text playersText;
    @FXML
    private Text hintText;

    /**
     * players is the list of players in the lobby
     */

    private Player player;
    private List<Player> players;
    private List<String> hints;


    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public LobbyCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }


    /**
     * Initializing the colName values
     */

    @FXML
    private void initialize() {
        this.backButton.setGraphic(new ImageView(new Image("icons/BackButton.png")));
        colName.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().userName));
        try {
            this.backButton.setGraphic(new ImageView(new Image(new File("client/src/main/resources/icons/BackButton.png").toURI().toURL().toString())));
            this.hintIMG.setImage(new Image(new File("client/src/main/resources/icons/lightBulb.png").toURI().toURL().toString()));
            this.playIMG.setImage(new Image(new File("client/src/main/resources/icons/playButton.png").toURI().toURL().toString()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //TODO: Fetch the players currently in the waiting room and insert them into the table
        //colName.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().userName));

        //TODO: Update the number of players currently in the waiting room

        //TODO: Add a hint
    }


    /**
     * Method that returns the application to the initial screen when the back button is pressed.
     *
     * @param actionEvent - pressing the back button triggers this function
     * @throws IOException when files not found/misread
     */

    public void back(ActionEvent actionEvent) throws IOException {
        this.mainCtrl.showSplash();
        try {
            server.disconnected(null, player);
        } catch (Exception e) {
            System.out.println("Tried to send disconnect message to null server");
        }
    }


    /**
     * Method that changes the stage to MPGameScreen
     * TODO: Should also be managing the start of the game, fetching questions etc.
     *
     * @param actionEvent clicked mouse
     * @throws IOException when something goes wrong with files
     */

    public void startGame(ActionEvent actionEvent) throws IOException {
        this.mainCtrl.showMPGame();

        // TODO: Start a session, forward other players to the game, fetch questions.

    }


    /**
     * @param players resets the table containing player names
     */

    public void resetTable(List<Player> players) {
        this.players = players;
        table.getItems().setAll(players);
    }


    /**
     * resets the hint field with a random hint from the hints textfile
     *
     * @throws IOException when reading of file fails
     */

    public void resetHint() throws IOException {
        if(hints == null) {
            //reading the hints and selecting a random one to display
            File hintDoc = new File("client/src/main/resources/main/Hints.txt");
            BufferedReader reader = new BufferedReader(new FileReader(hintDoc));
            hints = new ArrayList<>();
            String line;
            do {
                line = reader.readLine();
                if(line != null) {
                    hints.add(line);
                }
            } while (line != null);
            reader.close();
        }
        Random random = new Random();
        hintText.setText(hints.get(random.nextInt(hints.size())));
    }


    /**
     * Resets the field displaying the amount of players in the lobby
     *
     * @param players the players in the game
     */

    public void resetPlayerAmount(List<Player> players) {
        this.players = players;
        playersText.setText("Players: " + players.size());
    }


    /**
     * The method called by EnterNameMultiplayerCtrl to get/create a lobby with a list of players
     * It also sets FXML fields' values
     *
     * @param players the players in this lobby
     */

    public void createLobby(List<Player> players, Player player) throws IOException {
        resetTable(players);
        resetHint();
        resetPlayerAmount(players);
    }

}
