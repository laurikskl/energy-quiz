package client.scenes;

import client.utils.ServerUtils;
import commons.Player;
import commons.PlayerForTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    public void initialize() {
        List<Player> leaderboardPlayers = super.server.getLeaderboard();
        List<PlayerForTable> leaderboardTable = new ArrayList<>();

        for (int i = 1; i < 16; i++) {
            String score = Long.toString(leaderboardPlayers.get(i - 1).getScore());
            String userName = leaderboardPlayers.get(i - 1).getUserName();
            String place = Integer.toString(i);

            PlayerForTable playerWithPlace = new PlayerForTable(score, userName, place);
            leaderboardTable.add(playerWithPlace);
        }

        data = FXCollections.observableList(leaderboardTable);
        table.setItems(data);

        colPlace.setCellValueFactory(col -> new SimpleStringProperty(col.getValue().place));
        colName.setCellValueFactory(col -> new SimpleStringProperty(col.getValue().userName));
        colScore.setCellValueFactory(col -> new SimpleStringProperty(col.getValue().score));
    }

    /**
     * goes back to the splash screen
     *
     * @param actionEvent is when the back button is clicked
     * @throws IOException
     */
    public void back(ActionEvent actionEvent) throws IOException {
        this.mainCtrl.showSplash();
    }
    /**
     * Might add a refresh button later on.
     */
//  public void refresh() {
//
//    List<Player> leaderboardPlayers = server.getLeaderboard();
//    List<PlayerForTable> leaderboardTable = new ArrayList<>();
//
//    for(int i = 1; i < 16 ; i++){
//
//      String score = Long.toString(leaderboardPlayers.get(i-1).getScore());
//      String userName = leaderboardPlayers.get(i-1).getUserName();
//      String place = Integer.toString(i);
//
//      PlayerForTable playerWithPlace = new PlayerForTable(score, userName, place);
//      leaderboardTable.add(playerWithPlace);
//    }
//
//    data = FXCollections.observableList(leaderboardTable);
//    table.setItems(data);
//  }
}
