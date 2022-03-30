package client.scenes;

import client.ImageActivity;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminCtrl extends Controller {

    @FXML
    private ImageView backImg;

    //Restart
    @FXML
    private Button restartButton;
    @FXML
    private Label restartStatusLabel;

    //Search
    @FXML
    private TextField searchNameField;
    @FXML
    private TextField searchConsumptionMinField;
    @FXML
    private TextField searchConsumptionMaxField;
    @FXML
    private TextField searchSourceField;
    @FXML
    private Button searchButton;
    @FXML
    private Button searchShowAllButton;
    @FXML
    private Label searchStatusLabel;

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

    //Add By hand
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

    //Add From Activity Bank
    @FXML
    private TextField aBPathField;
    @FXML
    private Button aBPathBrowseButton;
    @FXML
    private CheckBox aBOverrideCB;
    @FXML
    private Button aBSubmitButton;
    @FXML
    private Label aBStatusLabel;

    //Remove
    @FXML
    private TextField removeByIDField;
    @FXML
    private Button removeSubmitButton;
    @FXML
    private Label removeStatusLabel;

    //Table
    @FXML
    private TableView<ImageActivity> tableView;

    //Fields
    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;
    private KeyCodeCombination copyKeyCode;

    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public AdminCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
        this.fileChooser = new FileChooser();
        this.directoryChooser = new DirectoryChooser();
        this.copyKeyCode = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);
    }

    /**
     * Initialize nodes in the scene just after the constructor has been called.
     */
    @FXML
    private void initialize() {
        this.backImg.setImage(new Image("icons/back.png"));

        //Restrict searchConsumptionMinField content to numbers
        this.searchConsumptionMinField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    searchConsumptionMinField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        //Restrict searchConsumptionMaxField content to numbers
        this.searchConsumptionMaxField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    searchConsumptionMaxField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        //Restrict removeByIDField content to numbers
        this.removeByIDField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    removeByIDField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        //Set up columns to automatically take in the correct attributes if an Activity gets added to the table as an item.
        List<TableColumn<ImageActivity, ?>> columns = this.tableView.getColumns();
        columns.get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        columns.get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        columns.get(2).setCellValueFactory(new PropertyValueFactory<>("powerConsumption"));
        columns.get(3).setCellValueFactory(new PropertyValueFactory<>("source"));
        columns.get(4).setCellValueFactory(new PropertyValueFactory<>("button"));

        //Enable cell selection
        this.tableView.getSelectionModel().setCellSelectionEnabled(true);
    }

    /**
     * Go back to the Splash screen
     * @param mouseEvent - the mouse clicked on the Back button
     * @throws IOException
     */
    public void back(MouseEvent mouseEvent) throws IOException {
        getMainCtrl().showSplash();
    }

    /**
     * Restart the server and show if it succeeded or not.
     *
     * @param mouseEvent - the mouse clicked on the Restart Server button
     */
    public void mouseClickedRestart(MouseEvent mouseEvent) {
        if (this.server.restart()) {
            this.restartStatusLabel.setText("Restarted");
        }
        else {
            this.restartStatusLabel.setText("Restart Failed");
        }
    }

    /** Copy the contents of the selected cell in string format if the table is selected and ctrl+c is pressed.
     * @param keyEvent Key combination
     */
    public void tableViewKeyEvent(KeyEvent keyEvent) {
        if (copyKeyCode.match(keyEvent) && keyEvent.getSource() instanceof TableView) {

            ObservableList<TablePosition> selectedCells = this.tableView.getSelectionModel().getSelectedCells();

            if (selectedCells.size() != 0) {
                //Get TablePosition of selected cell
                TablePosition tablePosition = selectedCells.get(0);

                //Get value in selected cell as a String
                String value = "" + tableView.getColumns()
                        .get(tablePosition.getColumn())
                        .getCellObservableValue(tablePosition.getRow())
                        .getValue();

                //Convert the value of the cell to a form that can be added to the clipboard
                final ClipboardContent content = new ClipboardContent();
                content.putString(value);

                //Copy to clipboard
                Clipboard.getSystemClipboard().setContent(content);

                //End the Key event
                keyEvent.consume();
            }
        }
    }

    /**
     * Helper method to display activities in tableView
     *
     * @param activities activities to show in the table
     */
    public void loadTable(List<Activity> activities) {
        List<ImageActivity> imageActivities = new ArrayList<>();
        activities.forEach(activity -> {
            imageActivities.add(new ImageActivity(activity));
        });

        this.tableView.getItems().clear();

        this.tableView.getItems().addAll(imageActivities);
    }

    /**
     * Helper method to choose images
     *
     * @return The absolute path of the selected file
     */
    private String chooseImage() {
        this.fileChooser.setTitle("Select Image");
        this.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        return this.fileChooser.showOpenDialog(getMainCtrl().getPrimaryStage()).getAbsolutePath();
    }

    /**
     * Helper method to choose paths
     *
     * @return The absolute path of the selected file
     */
    private String choosePath() {
        this.directoryChooser.setTitle("Select Activities Folder");

        return this.directoryChooser.showDialog(getMainCtrl().getPrimaryStage()).getAbsolutePath();
    }

    /**
     * Search the Activity Database and put the result in tableView
     * @param mouseEvent - the mouse clicked on searchButton
     */
    public void search(MouseEvent mouseEvent) {
        this.searchStatusLabel.setText("Retrieving...");

        String minConsumption = this.searchConsumptionMinField.getText();
        String maxConsumption = this.searchConsumptionMaxField.getText();

        Long minConsumptionLong;
        Long maxConsumptionLong;

        //Parse minConsumption to Long
        if (minConsumption.equals("")) {
            minConsumptionLong = null;
        } else {
            minConsumptionLong = Long.parseLong(minConsumption);
        }

        //Parse maxConsumption to Long
        if (maxConsumption.equals("")) {
            maxConsumptionLong = null;
        } else {
            maxConsumptionLong = Long.parseLong(maxConsumption);
        }

        List<Activity> activities = this.server.getActivitiesByExample(
                this.searchNameField.getText(),
                minConsumptionLong,
                maxConsumptionLong,
                this.searchSourceField.getText()
        );
        this.loadTable(activities);
        this.searchStatusLabel.setText("Activities found: " + activities.size());
    }

    /**
     * Show all activities from the Activity Database
     * @param mouseEvent - the mouse clicked on searchButton
     */
    public void showAll(MouseEvent mouseEvent) {
        this.searchStatusLabel.setText("Retrieving...");

        List<Activity> activities = this.server.getAllActivities();
        this.loadTable(activities);

        this.searchStatusLabel.setText("Activities found: " + activities.size());
    }

    /**
     * Browse for an image and put its absolute path in editImageField.
     * @param mouseEvent - the mouse clicked on editImageBrowseButton
     */
    public void editImageBrowse(MouseEvent mouseEvent){
        this.editImageField.setText(chooseImage());
    }

    /**
     * Edit an Activity
     * @param mouseEvent - the mouse clicked on editSubmitButton
     */
    public void editSubmit(MouseEvent mouseEvent){

    }

    /**
     * Browse for an image and put its absolute path in addImageField.
     * @param mouseEvent - the mouse clicked on addImageBrowseButton
     */
    public void addImageBrowse(MouseEvent mouseEvent){
        this.addImageField.setText(chooseImage());
    }

    /**
     * Add an Activity
     * @param mouseEvent - the mouse clicked on AddSubmitButton
     */
    public void addSubmit(MouseEvent mouseEvent){

    }

    /**
     * Browse for a folder and put its absolute path in ABPathField.
     * @param mouseEvent - the mouse clicked on ABPathBrowseButton
     */
    public void aBPathBrowse(MouseEvent mouseEvent){
        this.aBPathField.setText(choosePath());
    }

    /**
     * Add all Activities from an Activity Bank
     * @param mouseEvent - the mouse clicked on aBSubmitButton
     */
    public void aBSubmit(MouseEvent mouseEvent){

    }

    /**
     * Remove an Activity
     * @param mouseEvent - the mouse clicked on removeSubmitButton
     */
    public void removeSubmit(MouseEvent mouseEvent){
        Long id = Long.parseLong(this.removeByIDField.getText());
        this.removeStatusLabel.setText("Removing " + id);
        if (this.server.removeById(id)) {
            this.removeStatusLabel.setText("Removed " + id);
        }
        else {
            this.removeStatusLabel.setText("Failed Removing " + id);
        }
    }
}
