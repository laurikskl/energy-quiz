package client.scenes;

import client.utils.ServerUtils;
import commons.Player;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.inject.Inject;

public class ScoreboardCtrl extends Controller {

    @FXML
    private TableView<Player> table;
    @FXML
    private TableColumn<Player, String> colName;
    @FXML
    private TableColumn<Player, Number> colScore;

    /**
     * Default constructor
     *
     * @param server
     * @param mainCtrl
     */
    @Inject
    public ScoreboardCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    /**
     * Initialize things after showing the screen
     */
    @FXML
    private void initialize() {
        colName.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().userName));
        colScore.setCellValueFactory(q -> new SimpleIntegerProperty(q.getValue().score));

        //TODO: Make the table look nice
    }


    /**
     * Asks server for an updated list of players and then displays them in the table
     */
    public void resetTable() {
    }
}
