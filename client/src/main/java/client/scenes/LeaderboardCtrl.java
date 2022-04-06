package client.scenes;

import client.utils.ServerUtils;
import commons.Player;
import commons.PlayerForTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardCtrl extends Controller {

    @FXML
    private TableView<PlayerForTable> table;
    @FXML
   private TableColumn<PlayerForTable, String> colPlace;
   @FXML
    private TableColumn<PlayerForTable, String> colName;
    @FXML
    private TableColumn<PlayerForTable, String> colScore;

    private ObservableList<PlayerForTable> data;

    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public LeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    /**
     * Initializes the server
     * Converts the top 15 players to String form and adds them to the table.
     */
    @FXML
    private void initialize() {

        // First, it fetches all the players from the database and their correspondent scores
        // in decreasing order by score.
        List<Player> leaderboardPlayers = new ArrayList<>();

        try {
            leaderboardPlayers = server.getLeaderboard();
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<PlayerForTable> leaderboardTable = new ArrayList<>();

        for (int i = 1; i <= leaderboardPlayers.size(); i++) {

            // For every player, create a new entity which has:
            // the same username as the player,
            // the same score,
            // and the placement in the leaderboard.
            String score = String.valueOf(leaderboardPlayers.get(i - 1).getScore());
            String userName = leaderboardPlayers.get(i - 1).getUserName();
            String place = Integer.toString(i);

            PlayerForTable playerWithPlace = new PlayerForTable(score, userName, place);
            // Add the player with his place to the leaderboard.
            leaderboardTable.add(playerWithPlace);
        }

        data = FXCollections.observableList(leaderboardTable);
        table.setItems(data);

        // Place the place, username and score in the table.
        colPlace.setCellValueFactory(col -> new SimpleStringProperty(col.getValue().place));
        colName.setCellValueFactory(col -> new SimpleStringProperty(col.getValue().userName));
        colScore.setCellValueFactory(col -> new SimpleStringProperty(col.getValue().score));

        //disable horizontal scrolling for table
        table.addEventFilter(ScrollEvent.ANY, event -> {
            if (event.getDeltaX() != 0) {
                event.consume();
            }
        });

        //aligning text in table
        colPlace.setStyle("-fx-alignment: CENTER;");
        colName.setStyle("-fx-alignment: CENTER;");
        colScore.setStyle("-fx-alignment: CENTER;");
    }

    /**
     * goes back to the splash screen
     *
     * @param mouseEvent is when the back button is clicked
     * @throws IOException when something in IO goes wrong
     */
    public void back(MouseEvent mouseEvent) throws IOException {
        getMainCtrl().showSplash();
    }
    /**
     * With this method, the player can refresh the Leaderboard table and see actual data.
     */
  public void refresh() {

    List<Player> leaderboardPlayers = server.getLeaderboard();
    List<PlayerForTable> leaderboardTable = new ArrayList<>();

    for(int i = 1; i <= leaderboardPlayers.size() ; i++){
        // Uses the same reasoning as above, where you create a Player with his place in the leaderboard.
      String score = Long.toString(leaderboardPlayers.get(i-1).getScore());
      String userName = leaderboardPlayers.get(i-1).getUserName();
      String place = Integer.toString(i);

      PlayerForTable playerWithPlace = new PlayerForTable(score, userName, place);
      leaderboardTable.add(playerWithPlace);
    }

    data = FXCollections.observableList(leaderboardTable);
    table.setItems(data);
  }
}
