package client.scenes;

import commons.Question;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.MalformedURLException;

public class DisconnectMessageCtrl{
    public MainCtrl mainCtrl;
    @FXML
    public Button stayButton;
    @FXML
    public Button exitButton;

    /**
     * Default constructor
     */
    public DisconnectMessageCtrl(){};


    /**
     * Setter for the mainCtrl
     * @param mainCtrl
     */
    public void setMainCtrl(MainCtrl mainCtrl){
        this.mainCtrl = mainCtrl;
    }

    /**
     * Handle user pressing the stay button
     * @param mouseEvent
     * @throws InterruptedException
     * @throws IOException
     */
    public void clickStay(MouseEvent mouseEvent) throws InterruptedException, IOException {
        mainCtrl.hideDisconnectMessage();
        mainCtrl.timer.start();
    }

    /**
     * Handle user pressing the exit button
     * @param mouseEvent
     * @throws InterruptedException
     * @throws IOException
     */
    public void clickExit(MouseEvent mouseEvent) throws InterruptedException, IOException {
        mainCtrl.showSplash();
        mainCtrl.hideDisconnectMessage();
    }

}
