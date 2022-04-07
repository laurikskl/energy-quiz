package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import commons.PlayerForTable;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MPLeaderboardCtrl extends Controller {

    double progress = 0;
    private int seconds = 3;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ImageView backImg;
    @FXML
    private TableView<PlayerForTable> table;
    @FXML
    private TableColumn<PlayerForTable, String> colPlace;
    @FXML
    private TableColumn<PlayerForTable, String> colName;
    @FXML
    private TableColumn<PlayerForTable, String> colScore;

    @Inject
    public MPLeaderboardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    @FXML
    private void initialize() {
        this.backImg.setImage(new Image("icons/back.png"));
    }

    /**
     * @param mouseEvent - activated on button click
     * @throws IOException - when something goes wrong in IO
     */
    public void back(MouseEvent mouseEvent) throws IOException {
        mainCtrl.timer.stop();
        ((MPGameCtrl) mainCtrl.getControllers().get(6)).disconnectMessage();
        this.mainCtrl.showSplash();
    }

    public void showIntermediaryLeaderboard(List<Player> leaderboardPlayers) {
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
        progressBar.setStyle("-fx-accent: #6639D4");
        resetSeconds();

        mainCtrl.timer.start();
        ObservableList<PlayerForTable> data = FXCollections.observableList(leaderboardTable);
        table.setItems(data);

        // Place the place, username and score in the table.
        colPlace.setCellValueFactory(col -> new SimpleStringProperty(col.getValue().place));
        colName.setCellValueFactory(col -> new SimpleStringProperty(col.getValue().userName));
        colScore.setCellValueFactory(col -> new SimpleStringProperty(col.getValue().score));

        mainCtrl.timer = new Timer(1000, e -> {

            seconds--;
            progress += 0.2;
            Platform.runLater(() -> progressBar.setProgress(1.0 - progress));

            //if more than 15 seconds passed, move on to the next question
            if (seconds == 0) {
                Platform.runLater(() -> {
                    mainCtrl.timer.stop();
                    resetProgress();
                    progressBar.setProgress(1.0);
                    startNextQuestion();
                });
            }
        });

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

    private void startNextQuestion() {

    }

    private void resetSeconds() {
        this.seconds = 6;
    }

    private void resetProgress() {
        this.progress = 0;
    }


}
