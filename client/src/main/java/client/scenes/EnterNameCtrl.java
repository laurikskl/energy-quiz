package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;

import javax.inject.Inject;
import javax.swing.text.html.ImageView;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class EnterNameCtrl {

    @FXML
    private TextField textField;
    @FXML
    private Button play;
    @FXML
    private ImageView imageView;
    @FXML
    private Button quitButton;


    private ServerUtils server;
    private MainCtrl mainCtrl;

    /**
     *
     * @param server
     * @param mainCtrl
     */
    @Inject
    public EnterNameCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void mouseClickedPlay(MouseEvent mouseEvent) throws IOException {
        URL url = new File("client/src/main/resources/client/scenes/MPGameScreen.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        mainCtrl.setPrimaryStage(stage);
        mainCtrl.getPrimaryStage().show();
    }
}
