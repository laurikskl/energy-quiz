package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class AdminCtrl extends Controller{

    @FXML
    private ImageView backImg;

    //Search
    @FXML
    private ChoiceBox<String> searchByChoiceBox;
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
    private TableView tableView;

    //Fields
    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;

    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public AdminCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
        this.fileChooser = new FileChooser();
        this.directoryChooser = new DirectoryChooser();
    }

    @FXML
    private void initialize() {
        this.backImg.setImage(new Image("icons/back.png"));
        this.searchByChoiceBox.getItems().addAll("ID", "Name", "Consumption", "Source");
    }

    /**
     * Go back to the Splash screen
     * @param actionEvent - the mouse clicked on the Back button
     * @throws IOException
     */
    public void back(ActionEvent actionEvent) throws IOException {
        mainCtrl.showSplash();
    }

    /**
     * Helper method to choose images
     * @return The absolute path of the selected file
     */
    private String chooseImage() {
        this.fileChooser.setTitle("Select Image");
        this.fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        return this.fileChooser.showOpenDialog(this.mainCtrl.getPrimaryStage()).getAbsolutePath();
    }

    /**
     * Helper method to choose paths
     * @return The absolute path of the selected file
     */
    private String choosePath() {
        this.directoryChooser.setTitle("Select Activities Folder");

        return this.directoryChooser.showDialog(this.mainCtrl.getPrimaryStage()).getAbsolutePath();
    }

    /**
     * Search the Activity Database and put the result in tableView
     * @param actionEvent - the mouse clicked on searchButton
     */
    public void search(ActionEvent actionEvent) {
        System.out.println(this.searchByChoiceBox.getValue());
        switch (this.searchByChoiceBox.getValue()){
            case "ID":
                break;
            case "Name":
                break;
            case "Consumption":
                break;
            case "Source":
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this.searchByChoiceBox.getValue());
        }
    }

    /**
     * Show all activities from the Activity Database
     * @param actionEvent - the mouse clicked on searchButton
     */
    public void showAll(ActionEvent actionEvent) {

    }

    /**
     * Browse for an image and put its absolute path in editImageField.
     * @param actionEvent - the mouse clicked on editImageBrowseButton
     */
    public void editImageBrowse(ActionEvent actionEvent){
        this.editImageField.setText(chooseImage());
    }

    /**
     * Edit an Activity
     * @param actionEvent - the mouse clicked on editSubmitButton
     */
    public void editSubmit(ActionEvent actionEvent){

    }

    /**
     * Browse for an image and put its absolute path in addImageField.
     * @param actionEvent - the mouse clicked on addImageBrowseButton
     */
    public void addImageBrowse(ActionEvent actionEvent){
        this.addImageField.setText(chooseImage());
    }

    /**
     * Add an Activity
     * @param actionEvent - the mouse clicked on AddSubmitButton
     */
    public void addSubmit(ActionEvent actionEvent){

    }

    /**
     * Browse for a folder and put its absolute path in ABPathField.
     * @param actionEvent - the mouse clicked on ABPathBrowseButton
     */
    public void aBPathBrowse(ActionEvent actionEvent){
        this.aBPathField.setText(choosePath());
    }

    /**
     * Add all Activities from an Activity Bank
     * @param actionEvent - the mouse clicked on aBSubmitButton
     */
    public void aBSubmit(ActionEvent actionEvent){

    }

    /**
     * Remove an Activity
     * @param actionEvent - the mouse clicked on removeSubmitButton
     */
    public void removeSubmit(ActionEvent actionEvent){

    }
}
