package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdminCtrl extends Controller{

    //Search
    @FXML
    private ChoiceBox searchByChoiceBox;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button searchShowAllButton;

    //Edit
    @FXML
    private TextField editByIDField;
    @FXML
    private CheckBox editNameCB;
    @FXML
    private CheckBox editConsumptionCB;
    @FXML
    private CheckBox editSourceCB;
    @FXML
    private CheckBox editImageCB;
    @FXML
    private TextField editNameField;
    @FXML
    private TextField editConsumptionField;
    @FXML
    private TextField editSourceField;
    @FXML
    private TextField editImageField;
    @FXML
    private Button editImageBrowseButton;
    @FXML
    private Button editSubmitButton;
    @FXML
    private Label editStatusLabel;

    //Add
    @FXML
    private TextField addNameField;
    @FXML
    private TextField addConsumptionField;
    @FXML
    private TextField addSourceField;
    @FXML
    private TextField addImageField;
    @FXML
    private Button addImageBrowseButton;
    @FXML
    private Button addSubmitButton;
    @FXML
    private Label addStatusLabel;

    //Remove
    @FXML
    private TextField removeByIDField;
    @FXML
    private Button removeSubmitButton;
    @FXML
    private Label removeStatusLabel;

    //Table
    @FXML
    private TableView tableView;

    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public AdminCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    @FXML
    private void initialize() {
        this.searchByChoiceBox.getItems().addAll("ID", "Name", "Consumption", "Source");
    }

}
