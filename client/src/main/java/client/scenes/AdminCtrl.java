package client.scenes;

import client.ImageActivity;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import commons.ActivityBank;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
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
    private TextField searchIDField;
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
    private KeyCodeCombination copyKeyCode;

    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public AdminCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
        this.fileChooser = new FileChooser();
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
        AdminCtrl adminCtrl = this;

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                if (!adminCtrl.server.restart()) {
                    this.cancel();
                }

                return null;
            }

            @Override
            protected void running() {
                super.running();
                adminCtrl.restartStatusLabel.setText("Restarting...");
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                adminCtrl.restartStatusLabel.setText("Restarted");
            }

            @Override
            protected void cancelled() {
                super.cancelled();
                adminCtrl.restartStatusLabel.setText("Restart Failed");
            }
        };


        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

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
     * Helper method to choose paths
     *
     * @return The absolute path of the selected file
     */
    private String chooseJson() {
        this.fileChooser.setTitle("Select A Json file in the Activity Bank");
        this.fileChooser.getExtensionFilters().setAll(
                new FileChooser.ExtensionFilter("JSON", "*.json")
        File file = this.fileChooser.showOpenDialog(getMainCtrl().getPrimaryStage());

        if (file == null) return null;

        return file.getAbsolutePath();
    }

    /**
     * Search the Activity Database and put the result in tableView
     * @param mouseEvent - the mouse clicked on searchButton
     */
    public void search(MouseEvent mouseEvent) {
        AdminCtrl adminCtrl = this;

        Task<Integer> task = new Task() {
            @Override
            protected Integer call() {
                String minConsumption = adminCtrl.searchConsumptionMinField.getText();
                String maxConsumption = adminCtrl.searchConsumptionMaxField.getText();

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

                List<Activity> activities = adminCtrl.server.getActivitiesByExample(
                        adminCtrl.searchIDField.getText(),
                        adminCtrl.searchNameField.getText(),
                        minConsumptionLong,
                        maxConsumptionLong,
                        adminCtrl.searchSourceField.getText()
                );
                adminCtrl.loadTable(activities);

                return activities.size();
            }

            @Override
            protected void running() {
                super.running();
                adminCtrl.searchStatusLabel.setText("Retrieving...");
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                adminCtrl.searchStatusLabel.setText("Activities found: " + this.getValue());
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

    }

    /**
     * Show all activities from the Activity Database
     * @param mouseEvent - the mouse clicked on searchButton
     */
    public void showAll(MouseEvent mouseEvent) {
        AdminCtrl adminCtrl = this;

        Task<Integer> task = new Task<>() {
            @Override
            protected Integer call() {

                List<Activity> activities = adminCtrl.server.getAllActivities();
                adminCtrl.loadTable(activities);

                return activities.size();
            }

            @Override
            protected void running() {
                super.running();
                adminCtrl.searchStatusLabel.setText("Retrieving...");
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                adminCtrl.searchStatusLabel.setText("Activities found: " + this.getValue());
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

    }

    /**
     * Browse for a Json file and put its absolute path in ABPathField.
     * @param mouseEvent - the mouse clicked on ABPathBrowseButton
     */
    public void aBPathBrowse(MouseEvent mouseEvent){
        this.aBPathField.setText(chooseJson());
    }

    /**
     * Add all Activities from an Activity Bank
     * @param mouseEvent - the mouse clicked on aBSubmitButton
     */
    public void aBSubmit(MouseEvent mouseEvent) {
        AdminCtrl adminCtrl = this;

        Task<Integer> task = new Task<>() {
            @Override
            protected Integer call() {
                Integer n = 0;

                try {
                    Path path = Path.of(adminCtrl.aBPathField.getText());

                    ActivityBank activityBank = ActivityBank.JsonReader(path);

                    activityBank.setOverride(adminCtrl.aBOverrideCB.isSelected());

                    n = adminCtrl.server.addBank(activityBank);


                } catch (Exception e) {
                    e.printStackTrace();
                    this.cancel();
                }

                return n;
            }

            @Override
            protected void running() {
                super.running();
                adminCtrl.aBStatusLabel.setText("Adding Activities...");
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                adminCtrl.aBStatusLabel.setText("Added " + this.getValue() + " Activities");
            }

            @Override
            protected void cancelled() {
                super.cancelled();
                adminCtrl.aBStatusLabel.setText("Provide a valid Activity Bank please");
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

    }

    /**
     * Remove an Activity
     * @param mouseEvent - the mouse clicked on removeSubmitButton
     */
    public void removeSubmit(MouseEvent mouseEvent){
        AdminCtrl adminCtrl = this;

        Task<Void> task = new Task<>() {
            private String id;

            @Override
            protected Void call() {
                this.id = adminCtrl.removeByIDField.getText();

                if (!adminCtrl.server.removeById(this.id)) {
                    this.cancel();
                }
                return null;
            }

            @Override
            protected void running() {
                super.running();
                adminCtrl.removeStatusLabel.setText("Removing " + this.id + "...");
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                adminCtrl.aBStatusLabel.setText("Added " + this.id + " Activities");
            }

            @Override
            protected void cancelled() {
                super.cancelled();
                adminCtrl.removeStatusLabel.setText("Failed Removing " + id);
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

    }
}
