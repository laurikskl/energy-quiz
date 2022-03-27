package client.scenes;

import client.utils.ServerUtils;
import commons.Player;
import commons.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controller for single-player game screen
 */

public class SPGameCtrl extends Controller {

    int seconds;
    /**
     * scoreCount is a Text element containing the score
     * text is a Text element containing the name
     * questionNumber is a Text element containing the question number
     * server is an instance of ServerUtils
     * qCount is the current question number
     * questions is the list of questions of this game
     * player is the player of this game
     * score is the current score of the player
     */

    @FXML
    private Text scoreCount;
    @FXML
    private Text name;
    @FXML
    private Text questionNumber;


    @FXML
    private BorderPane questionFrame;
    private int qCount;
    private List<Question> questions;
    private Player player;
    private int score;
    /**
     * For the countdown clock.
     */

    @FXML
    private Text counterTimer;
    private Timer timer;

    /**
     * @param server   ServerUtils instance
     * @param mainCtrl MainCtrl instance
     */

    @Inject
    public SPGameCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    /**
     * This method:
     * sets up the fields
     * generates questions and iterates over them
     * does the cleanup after the game
     *
     * @param player the player of the game
     */
    public void startGame(Player player) throws IOException, InterruptedException {
        this.player = player;
        this.qCount = 0;
        this.score = 0;
        this.questions = new ArrayList<>();

        seconds = 14;
        simpleTimer();
        timer.start();

        //if statement to make tests work
        if (name == null || scoreCount == null || questionNumber == null) {
            throw new IllegalStateException("One or more FXML fields are null");
        }
        name.setText(player.getUserName());
        scoreCount.setText("Score: 0");
        questionNumber.setText("1/20");

        while (questions.isEmpty()) { //Maybe questions.isEmpty()?
            try {
                questions = this.server.getQuestions();
            } catch (Exception e) {
                System.out.println("something went wrong here");
            }
        }
        Collections.shuffle(questions);

        doAQuestion(questions.get(0));
/*
        //generate 20 questions
        while (questions.size() < 20) {
            questions.add(getServer().getQuestion());
        }
        //iterate over all questions
        for (Question q : questions) {
            this.doAQuestion(q);
        }
        //overwrite high-score if the current score is higher
        if (score > getServer().getPlayer(player.getUserName()).getScore()) {
            getServer().setPlayer(player.getUserName(), score);
        }*/
    }

    public void simpleTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                seconds--;
                counterTimer.setText(seconds + "seconds");
            }
        });
    }

    public BorderPane getQuestionFrame() {
        return questionFrame;
    }

    /**
     * This method takes care of every individual question by
     * running the concrete method for each type of question.
     * This method also handles changing the score that is visible on the screen
     * and question counter
     *
     * @param q the current question
     */
    public void doAQuestion(Question q) throws IOException, InterruptedException {
        //Question has been run
        timer.stop();
        seconds = 10;
        simpleTimer();
        timer.start();
        this.qCount++;
        System.out.println("Question has started!");

        //Choose which type of question it is and load the appropriate frame with its controller
        if (q.getClass().equals(Question.MostNRGQuestion.class)) {
            doMultiChoice((Question.MostNRGQuestion) q);
        } else if (q.getClass().equals(Question.ChoiceEstimation.class)) {
            doChoiceEstimationQuestion((Question.ChoiceEstimation) q);
        } else if (q.getClass().equals(Question.Matching.class)) {
            doMatching((Question.Matching) q);
        }
//        else if (q.getClass().equals(Question.AccurateEstimation.class)) {
//            doAccurateEstimation((Question.AccurateEstimation) q);
//        }
    }

    /**
     * This method inserts the frame, gets time and correctness of the answer from the controller
     * Then it adds points to score accordingly, using ScoreSystem
     *
     * @param multiChoice current multiChoice question
     * @throws IOException
     */
    public void doMultiChoice(Question.MostNRGQuestion multiChoice) throws IOException, InterruptedException {
        this.mainCtrl.startMC(this, multiChoice);
    }

    /**
     * This method inserts the frame, gets time and correctness of the answer from the controller
     * Then it adds points to score accordingly, using ScoreSystem
     *
     * @param choiceEstimation current Estimation question
     * @throws IOException
     */
    public void doChoiceEstimationQuestion(Question.ChoiceEstimation choiceEstimation) throws IOException, InterruptedException {
        this.mainCtrl.startCE(this, choiceEstimation);
    }

    /**
     * This method inserts the frame, gets time and correctness of the answer from the controller
     * Then it adds points to score accordingly, using ScoreSystem
     *
     * @param q current Matching question
     * @throws IOException
     */
    public void doMatching(Question.Matching q) throws IOException {
        String pathToFxml = "client/src/main/resources/client/scenes/Matching.fxml";
        URL url = new File(pathToFxml).toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();

        //TODO: Create MatchingCtrl
//        MatchingCtrl controller = fxmlLoader.<MatchingCtrl>getController();
//        controller.initialize(server, mainCtrl, (Question.Matching) q);
        Scene scene = new Scene(root);

        questionFrame.setCenter(scene.getRoot());
    }

//    /**
//     * This method inserts the frame, gets time, distance and correctness of the answer from the controller
//     * Then it adds points to score accordingly, using ScoreSystem
//     *
//     * @param q current AccurateEstimation question
//     * @throws IOException
//     */
//    public void doAccurateEstimation(Question.AccurateEstimation q) throws IOException {
//        String pathToFxml = "client/src/main/resources/client/scenes/AccurateEstimation.fxml";
//        URL url = new File(pathToFxml).toURI().toURL();
//        FXMLLoader fxmlLoader = new FXMLLoader(url);
//        Parent root = fxmlLoader.load();
//
//        //TODO: Create AccurateEstimationCtrl
//        AccurateEstimationCtrl controller = fxmlLoader.<AccurateEstimationCtrl>getController();
//        controller.initialize(server, mainCtrl, (Question.AccurateEstimation) q);
//        Scene scene = new Scene(root);
//
//        questionFrame.setCenter(scene.getRoot());
//    }

    /**
     * This method takes you back to the splash screen when the back button is pressed
     *
     * @param actionEvent click
     * @throws IOException when file not found or misread
     */
    public void back(ActionEvent actionEvent) throws IOException {
        //sets the scene back to the main screen
        this.mainCtrl.showSplash();
    }


    /**
     * @return current question number
     */
    public int getqCount() {
        return qCount;
    }

    /**
     * @param qCount current question number
     */
    public void setqCount(int qCount) {
        this.qCount = qCount;
    }

    /**
     * @return the list of questions
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * @param questions list of questions
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    /**
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @param player player of game
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return score of the player
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score score of player
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return the Text element of the scoreCount
     */
    public Text getScoreCount() {
        return scoreCount;
    }

    /**
     * @param scoreCount Text element for score
     */
    public void setScoreCount(Text scoreCount) {
        this.scoreCount = scoreCount;
    }

    /**
     * @return the Text element of the name
     */
    public Text getName() {
        return name;
    }

    /**
     * @param name Text element for name
     */
    public void setName(Text name) {
        this.name = name;
    }

    /**
     * @return the Text element of the questionNumber
     */
    public Text getQuestionNumber() {
        return questionNumber;
    }

    /**
     * @param questionNumber Text element for questionNumber
     */
    public void setQuestionNumber(Text questionNumber) {
        this.questionNumber = questionNumber;
    }

    /**
     * Getter for server
     */
    public ServerUtils getServer() {
        return server;
    }

    /**
     * @param server instance of ServerUtils
     */
    public void setServer(ServerUtils server) {
        this.server = server;
        this.player = player;
        this.qCount = 0;
        this.score = 0;
        //if statement to make tests work
        if(name == null || scoreCount == null || questionNumber == null) {
            throw new IllegalStateException("One or more FXML fields are null");
        }
        name.setText(player.getUserName());
        scoreCount.setText("Score: 0");
        questionNumber.setText("0/20");
        while(questions == null) {
            try{
                questions = server.getQuestions();
            } catch (Exception e) {
                //server didn't send over questions
            }
        }
        Collections.shuffle(questions);
        /**
         //iterate over all questions
         for(Question q : questions) {
         this.doAQuestion(q);
         }
         //overwrite high-score if the current score is higher
         if(score > getServer().getPlayer(player.getUserName()).getScore()) {
         getServer().setPlayer(player.getUserName(), score);
         }*/
    }

    /**
     * Update visible score and visible question counter
     */
    public void refresh() {
        scoreCount.setText(String.valueOf(score));
        questionNumber.setText(String.valueOf(qCount) + "/20");
    }
}
