package client.scenes;

import client.utils.ServerUtils;

public class LobbyCtrl {

    private ServerUtils serverUtils;
    private MainCtrl mainCtrl;
    

    /**
     * Constructor for the controller
     *
     * @param serverUtils
     * @param mainCtrl
     */
    public LobbyCtrl(ServerUtils serverUtils, MainCtrl mainCtrl) {
        this.serverUtils = serverUtils;
        this.mainCtrl = mainCtrl;
    }


}
