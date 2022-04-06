package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.*;
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
    private AnchorPane anchor;
    @FXML
    private ImageView playIMG;
    @FXML
    private ImageView hintIMG;
    @FXML
    private Button backButton;
    @FXML
    private Button playButton;
    @FXML
    private TableView<Player> table;
    @FXML
    private TableColumn<Player, String> colName;
    @FXML
    private Text playersText;
    @FXML
    private Text hintText;
    @FXML
    private Text pressToStart;

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
    private void initialize() throws IOException {
        colName.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().userName));
        hintIMG.setImage(new Image(new File("client/src/main/resources/icons/lightBulb.png").toURI().toURL().toString()));
        playIMG.setImage(new Image(new File("client/src/main/resources/icons/playButton.png").toURI().toURL().toString()));

        //setting font everywhere
        Font font = Font.loadFont(new FileInputStream("client/src/main/resources/fonts/Spartan-Bold.ttf"), 25);
        colName.setCellFactory(getCustomCellFactory(font)); //setting font for table
        for (Node node : anchor.getChildren()) {
            if (node instanceof Text) {
                ((Text) node).setFont(font);
            }
        }
        hintText.setFont(font);
        playersText.setStyle("-fx-font-size: 35px; -fx-alignment: CENTER; ");
        hintText.setStyle("-fx-font-size: 20px; ");
        pressToStart.setStyle("-fx-alignment: CENTER; -fx-font-size: 20px; ");

        //disable horizontal scrolling for table
        table.addEventFilter(ScrollEvent.ANY, event -> {
            if (event.getDeltaX() != 0) {
                event.consume();
            }
        });
    }


    /**
     * Sets the tables font by black magic
     *
     * @param font The font to be used
     * @return No idea pls help
     */

    private Callback<TableColumn<Player, String>, TableCell<Player, String>> getCustomCellFactory(Font font) {
        //credit to https://tousu.in/?qa=738424/
        return new Callback<TableColumn<Player, String>, TableCell<Player, String>>() {

            @Override
            public TableCell<Player, String> call(TableColumn<Player, String> param) {
                TableCell<Player, String> cell = new TableCell<Player, String>() {
                    @Override
                    public void updateItem(final String item, boolean empty) {
                        if (item != null) {
                            setText(item);
                        }
                    }
                };
                cell.setFont(font);
                cell.setStyle("-fx-font-size: 40px; ");
                return cell;
            }
        };
    }


    /**
     * Method that returns the application to the initial screen when the back button is pressed.
     * Unsubscribe from the websocket connection
     *
     * @param mouseEvent - pressing the back button triggers this function
     * @throws IOException when files not found/misread
     */

    public void back(MouseEvent mouseEvent) {
        leaveLobby();
        server.disconnect();
        getMainCtrl().showSplash();
    }

    /**
     * Removes the player from the lobby.
     */

    public void leaveLobby() {
        int id = getServer().getLobby();
        getServer().send("/app/game/" + id + "/lobby/leave", mainCtrl.thisPlayer);
    }


    /**
     * Method that changes the stage to MPGameScreen
     * TODO: Should also be managing the start of the game, fetching questions etc.
     *
     * @param mouseEvent clicked mouse
     * @throws IOException when something goes wrong with files
     */

    public void startGame(MouseEvent mouseEvent) throws IOException {
        playButton.setDisable(false);
        long id = getServer().getLobby();
        System.out.println("BUTTON PRESSED and disabled!!! id = " + id);
        String startGame = "Game started";
        String startLobby = "Lobby added to gameService";
        //Sends request to lobby to add current lobby to the game service
        getServer().send("/app/game/" + id + "/lobby/start", startLobby);
        //Sends request to GameManagementController to start a MP game
        getServer().send("/app/game/" + id + "/startGame", startGame);

        //getMainCtrl().showMPGame();

        // TODO: Start a session, forward other players to the game, fetch questions.
    }

    /**
     * Set up the table for players
     *
     * @param newPlayers the list of players in the lobby
     */

    public void createTable(List<Player> newPlayers) throws IOException {
        this.players = newPlayers;
        table.getItems().setAll(players);
        resetHint();
        resetPlayerAmount(players);
    }


    /**
     * resets the hint field with a random hint from the hints textfile
     *
     * @throws IOException when reading of file fails
     */

    public void resetHint() throws IOException {
        if (hints == null) {
            //reading the hints and selecting a random one to display
            File hintDoc = new File("client/src/main/resources/main/Hints.txt");
            BufferedReader reader = new BufferedReader(new FileReader(hintDoc));
            hints = new ArrayList<>();
            String line;
            do {
                line = reader.readLine();
                if (line != null) {
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

    public void createLobby(List<Player> players) throws IOException {
        createTable(players);
        resetHint();
        resetPlayerAmount(players);
    }

}
