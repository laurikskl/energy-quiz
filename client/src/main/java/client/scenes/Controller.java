package client.scenes;

import client.utils.ServerUtils;
import javafx.application.Platform;

import javax.inject.Inject;

public abstract class Controller {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;


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
        if(this instanceof SPGameCtrl) Platform.exit();
    }

}
