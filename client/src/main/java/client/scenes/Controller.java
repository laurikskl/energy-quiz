
package client.scenes;
import client.utils.ServerUtils;
import com.google.inject.Inject;

public class Controller {

    //Fields
    protected ServerUtils server;
    protected MainCtrl mainCtrl;

    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
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
}
