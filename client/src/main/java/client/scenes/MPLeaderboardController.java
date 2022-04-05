package client.scenes;

import client.utils.ServerUtils;
import commons.PlayerForTable;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.swing.*;
import java.util.List;


public class MPLeaderboardController extends Controller{

    private int seconds = 6;
    double progress = 0;

    @FXML
    private ProgressBar progressBar;
    @FXML
    private TableView<PlayerForTable> table;
    @FXML
    private TableColumn<PlayerForTable, String> colPlace;
    @FXML
    private TableColumn<PlayerForTable, String> colName;
    @FXML
    private TableColumn<PlayerForTable, String> colScore;

    private ObservableList<PlayerForTable> data;


    public MPLeaderboardController(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    private void showIntermediaryLeaderboard(List<PlayerForTable> playersInGame){

        progressBar.setStyle("-fx-accent: #6639D4");
        resetSeconds();

        mainCtrl.timer.start();
        data = FXCollections.observableList(playersInGame);
        table.setItems(data);

        // Place the place, username and score in the table.
        colPlace.setCellValueFactory(col -> new SimpleStringProperty(col.getValue().place));
        colName.setCellValueFactory(col -> new SimpleStringProperty(col.getValue().userName));
        colScore.setCellValueFactory(col -> new SimpleStringProperty(col.getValue().score));

        mainCtrl.timer = new Timer(1000, e -> {

            seconds--;
            progress+=0.2;
            progressBar.setProgress(1.0-progress);

            //if more than 15 seconds passed, move on to the next question
            if (seconds==0){
                Platform.runLater(() -> {
                    mainCtrl.timer.stop();
                    resetProgress();
                    progressBar.setProgress(1.0);
                    startNextQuestion();
                    return;
                });
            }
        });
    }

    private void startNextQuestion() {

    }

    private void resetSeconds (){
        this.seconds = 6;
    }

    private void resetProgress(){
        this.progress=0;
    }


}
