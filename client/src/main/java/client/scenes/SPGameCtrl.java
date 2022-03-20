package client.scenes;

import client.utils.ServerUtils;
import commons.Player;
import commons.Question;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SPGameCtrl extends Controller {

    private Player player;
    private List<Question> questions;
    private int qCount;
    private int score;

    @FXML
    private Text questionCounter;

    @FXML
    private Text scoreTracker;

    @FXML
    private Text time;

    @FXML
    private AnchorPane questionFrame;

    /**
     * @param server reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */

    @Inject
    public SPGameCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
        this.player = new Player("Max", 800);
        this.qCount = 0;
        this.score = 0;
        this.questions = new ArrayList<>();
    }


    /**
     * Called after constructor
     * Sets text fields to default values
     * Requests 20 questions from the server
     * Does operations on all questions
     * Update a player's high-score if necessary
     */

    @FXML
    public void initialize() {
        questionCounter.setText("0/20");
        scoreTracker.setText("Score: 0");
        time.setText("");
        /*
        //generate 20 questions
        while(questions.size() < 20) {
            questions.add(getServer().getQuestion());
        }
        //iterate over all questions
        for(Question q : questions) {
            try {
                this.doAQuestion(q);
            } catch (IOException e) {
                System.out.println("WhatRequiresMoreNRGScreen.fxml not found in that location");
                e.printStackTrace();
            }
        }
        //overwrite high-score if the current score is higher
        if(score > getServer().getPlayer(player.getUserName()).getScore()) {
            getServer().setPlayer(player.getUserName(), score);
        }
         */
    }


    /**
     * Update the questionCounter
     * Load the question frame with the current question\
     * Start a timer for the question
     * Update the score
     * @param q the current question
     * @throws IOException when url isn't correct
     */

    public void doAQuestion(Question q) throws IOException {
        qCount++;
        questionCounter.setText(qCount + "/20");
        time.setText("10s left");
        //load the question in the frame
        URL url = new File("client/src/main/resources/client/scenes/questionFrames/WhatRequiresMoreNRGScreen.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        questionFrame.getChildren().add(root);
        //start a timer for the question and update the time field every 0.1 seconds

        //increment score
        scoreTracker.setText("Score: " + score);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getqCount() {
        return qCount;
    }

    public void setqCount(int qCount) {
        this.qCount = qCount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Text getQuestionCounter() {
        return questionCounter;
    }

    public void setQuestionCounter(Text questionCounter) {
        this.questionCounter = questionCounter;
    }

    public Text getScoreTracker() {
        return scoreTracker;
    }

    public void setScoreTracker(Text scoreTracker) {
        this.scoreTracker = scoreTracker;
    }

    public Text getTimer() {
        return time;
    }

    public void setTimer(Text timer) {
        this.time = timer;
    }

    public AnchorPane getQuestionFrame() {
        return questionFrame;
    }

    public void setQuestionFrame(AnchorPane questionFrame) {
        this.questionFrame = questionFrame;
    }

}
