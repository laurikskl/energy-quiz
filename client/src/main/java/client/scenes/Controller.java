package client.scenes;

import client.utils.ServerUtils;
import javafx.application.Platform;

import javax.inject.Inject;

public abstract class Controller {

    protected final ServerUtils server;
    protected final MainCtrl mainCtrl;


    @Inject
    public Controller(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public ServerUtils getServer() {
        return server;
    }

    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    public void close() {
        Platform.exit();
    }

}
