package client.scenes;

import client.utils.ServerUtils;
import javafx.application.Platform;

import javax.inject.Inject;

/**
 * Parent class for the controllers
 */
public abstract class Controller {

    protected final ServerUtils server;
    protected final MainCtrl mainCtrl;

    /**
     * Constructor that passes on the server and the mainCtrl
     * @param server
     * @param mainCtrl
     */
    @Inject
    public Controller(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Getter for the server
     * @return
     */
    public ServerUtils getServer() {
        return server;
    }

    /** Getter for mainCtrl
     *
     * @return
     */
    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    /**
     * Method for closing the application
     */
    public void close() {
        Platform.exit();
    }

}
