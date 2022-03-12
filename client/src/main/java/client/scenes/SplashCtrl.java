package client.scenes;

import client.Main;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class SplashCtrl implements MouseListener {

    @FXML
    private ImageView logoIMG;

    @FXML
    private TextField howToPlayText;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button singlePlayer;

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * @param server reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public SplashCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Exits the application, called by quit button
     */
    public void cancel() {
        mainCtrl.close();
    }


    /**
     * Is called after constructor (Initializable)
     * Sets the image of the ImageView in the splash screen to the logo
     * Should probably set the path to be non-relative but that's a problem for later
     */
    @FXML
    public void initialize() {
        logoIMG.setImage(new Image(Objects.requireNonNull(getClass().getResource("../../../../resources/main/main/Logo.png")).toExternalForm()));
        this.invisibleHowToPlay();
    }

    /**
     * Makes the how to play text visible (on hovering how2play button)
     */
    public void showHowToPlay() {
        howToPlayText.setVisible(true);
    }

    /**
     * Makes the how to play text invisible (on exiting how2play button and startup)
     */
    public void invisibleHowToPlay() {
        howToPlayText.setVisible(false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void mouseClickedSinglePlayer(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("EnterNameSinglePlayer.fxml"));
        Scene scene = new Scene(root);
        mainCtrl.getPrimaryStage().setScene(scene);
        mainCtrl.getPrimaryStage().show();
    }

    public void mouseClickedMultiPlayer(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        URL url = new File("client/src/main/resources/client/scenes/EnterNameMultiPlayer.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        mainCtrl.close();
        mainCtrl.setPrimaryStage(stage);
        mainCtrl.getPrimaryStage().show();
    }
}
