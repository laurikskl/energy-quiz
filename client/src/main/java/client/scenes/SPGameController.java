package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import commons.Question;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.List;

public class SPGameController {

    private ServerUtils server;
    private MainCtrl mainCtrl;
    private int qCount;
    private List<Question> questions;
    private Player player;
    private int score;


    /**
     * @param server reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */

    @Inject
    public SPGameController(ServerUtils server, MainCtrl mainCtrl, Player player) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.player = player;
        this.qCount = 0;
        this.score = 0;
        this.questions = new ArrayList<>();
    }

    @FXML
    public void initialize() {
        while(questions.size() < 20) {
            questions.add(server.getQuestion());
        }
        for(Question q : questions) {
           this.doAQuestion(q);
        }
        if(score > server.getScore(player.getUserName())) {
            server.setPlayer(player.getUserName(), score);
        }
    }

    public void doAQuestion(Question q) {
        this.qCount++;
        //load the question in the frame
        //start a timer for the question
    }

}
