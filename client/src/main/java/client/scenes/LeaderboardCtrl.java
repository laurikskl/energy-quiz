package client.scenes;

import client.utils.ServerUtils;
import commons.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Pair;

import javax.inject.Inject;
//import java.awt.Button;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardCtrl {

  private final ServerUtils server;
  private ObservableList<Pair<Integer, Player>> data;

  @FXML
  private TableView<Pair<Integer, Player>> table;
  @FXML
  private TableColumn<Pair, Integer> colPlace;
  @FXML
  private TableColumn<Player, String> colName;
  @FXML
  private TableColumn<Player, Integer> colScore;


  @Inject
  public LeaderboardCtrl(ServerUtils server){
    this.server = server;
  }

  @FXML
  public void initialize() {

    colPlace.setCellValueFactory(col -> new SimpleStringProperty(col.getValue())

//    ObservableList<Player> leaders = FXCollections.observableArrayList(leaderboardPlayers);
//    ObservableList<Integer> place = FXCollections.observableArrayList({1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
//    colName.setCellValueFactory(new PropertyValueFactory<>("userName"));
  }

  public void refresh() {

    List<Player> leaderboardPlayers = server.getLeaderboard();
    List<Pair<Integer, Player>> pairs = new ArrayList<>();

    for(int i = 1; i < 16 ; i++){
      Pair<Integer, Player> pair = new Pair(i, leaderboardPlayers.get(i-1));
      pairs.add(pair);
    }

    data = FXCollections.observableList(pairs);
    table.setItems(data);
  }
}
