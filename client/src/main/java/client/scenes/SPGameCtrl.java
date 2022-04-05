package client.scenes;

import client.utils.ServerUtils;
import commons.Player;
import commons.Question;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Pair;

import javax.inject.Inject;
import javax.swing.*;
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
    private Text scoreAwarded;
    @FXML
    private BorderPane questionFrame;
    @FXML
    private ImageView emo1IMG, emo2IMG, emo3IMG, emo4IMG, emo5IMG, emo6IMG;
    @FXML
    private TableColumn<Pair<String, ImageView>, String> nameCol;
    @FXML
    private TableColumn<Pair<String, ImageView>, ImageView> emojiCol;
    @FXML
    private TableView<Pair<String, ImageView>> chat;
    @FXML
    private Text cooldownText;

    private PauseTransition cooldown;
    private int qCount;
    private List<Question> questions;
    private Player player;
    private int score;
    private List<ImageView> emojis;
    private boolean onCooldown;
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

        //if statement to make tests work
        if (name == null || scoreCount == null || questionNumber == null) {
            throw new IllegalStateException("One or more FXML fields are null");
        }
        name.setText(player.getUserName());
        scoreCount.setText("Score: 0");
        questionNumber.setText("1/20");

        scoreAwardedVisibility(false, 0);

        //getting list of 20 questions from server
        while (questions.isEmpty()) {
            try {
                questions = getServer().getQuestions();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Couldn't get questions from server");
            }
        }

        Collections.shuffle(questions);

        simpleTimer();
        timer.stop();
        //start with first question
        doAQuestion(questions.get(0));
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
        this.qCount++;
        System.out.println("Question has started!");
        simpleTimer();
        questionNumber.setText(qCount+"/20");

        //Choose which type of question it is and load the appropriate frame with its controller
        if (q.getClass().equals(Question.MostNRGQuestion.class)) {
            doMultiChoice((Question.MostNRGQuestion) q);
        } else if (q.getClass().equals(Question.ChoiceEstimation.class)) {
            doChoiceEstimationQuestion((Question.ChoiceEstimation) q);
        } else if (q.getClass().equals(Question.Matching.class)) {
            doMatching((Question.Matching) q);
        } else if (q.getClass().equals(Question.AccurateEstimation.class)) {
            doAccurateEstimationQuestion((Question.AccurateEstimation) q);
        }
        refresh();

    }

    /**
     * This method gets called everytime the game moves on to the next question.
     * That is, either when the player has answered by pressing a button,
     * or when the timer of 15 seconds per question runs out.
     * @throws IOException when something goes wrong with file-reading or finding
     * @throws InterruptedException when a question is interrupted
     */
    public void startNewQuestion() throws IOException, InterruptedException {
        //Checks if the user has reached 20 questions and finished his turn.
        //If he did, show the final screen.
        if (this.getqCount()==20) {
            timer.stop();
            player.setScore(score);

            //Sets the player in the database with the final score of this round.

            if (server.getPlayer(player.userName)!=null && player.score < server.getPlayer(player.userName).getScore()){
                //If current score is lower than the highest score of the player, keep his highest score.
                server.setPlayer(player.userName, (int) server.getPlayer(player.userName).getScore());
            }
            else{
                //If the player doesn't exist or had reached a higher score this round, set it with the highest score.
                server.setPlayer(player.userName, player.score);
            }

            //Sets the screen with the final score of the player and then displays EndGame screen.
            getMainCtrl().setEndGame(player.score);
            getMainCtrl().showEndGame();

            //With this line, the method stops generating a new question after the user reached the 20th question.
            return;
        }

        doAQuestion(questions.get(this.getqCount()));
    }

    /**
     * @param visible true iff scoreAwarded should be visible
     * @param points the points awarded for a question
     */

    public void scoreAwardedVisibility(boolean visible, int points) {
        if(visible) {
            scoreAwarded.setVisible(true);
            scoreAwarded.setText("+" + points);
        } else {
            scoreAwarded.setVisible(false);
        }
    }


    /**
     * This method resets the text for the countdown timer every second.
     * If the timer hit 0 seconds and the player has not answered, it calls the method
     * to move on to the next question.
     */
    public void simpleTimer() {

        resetSeconds();

        timer = new Timer(1000, e -> {

            seconds--;
            counterTimer.setText(seconds + " seconds");

            //if more than 15 seconds passed, move on to the next question
            if (seconds==0){
                Platform.runLater(() -> {
                    timer.stop();
                    try {
                        startNewQuestion();
                        return;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        });

        timer.start();

    }

    /**
     * This method resets the seconds.
     */
    public void resetSeconds(){
        this.seconds = 16;
    }

    /**
     * Update visible score and visible question counter.
     */
    public void refresh(){

        scoreCount.setText(String.valueOf(score));
        //questionNumber.setText(qCount + "/20");
    }

    /** Getter for question frame
     *
     * @return BorderPane of question frame
     */
    public BorderPane getQuestionFrame() {
        return questionFrame;
    }

    /**
     * This method inserts the frame, gets time and correctness of the answer from the controller
     * Then it adds points to score accordingly, using ScoreSystem
     *
     * @param multiChoice current multiChoice question
     * @throws IOException when something goes wrong with file-reading or finding
     */
    public void doMultiChoice(Question.MostNRGQuestion multiChoice) throws IOException{
        getMainCtrl().startMC(this, multiChoice);
    }

    /**
     * This method inserts the frame, gets time and correctness of the answer from the controller
     * Then it adds points to score accordingly, using ScoreSystem
     *
     * @param choiceEstimation current Estimation question
     * @throws IOException cooldownText.setText("Wait " + timeLeft + " second before sending another message");
     */
    public void doChoiceEstimationQuestion(Question.ChoiceEstimation choiceEstimation) throws IOException {
        getMainCtrl().startCE(this, choiceEstimation);
    }

    /**
     * This method inserts the frame, gets time and correctness of the answer from the controller
     * Then it adds points to score accordingly, using ScoreSystem
     *
     * @param q current Matching question
     * @throws IOException when file-reading or finding goes wrong
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

    /**
     * This method inserts the frame, gets time, distance and correctness of the answer from the controller
     * Then it adds points to score accordingly, using ScoreSystem
     *
     * @param accurateEstimation current AccurateEstimation question
     * @throws IOException
     */
    public void doAccurateEstimationQuestion(Question.AccurateEstimation accurateEstimation) throws IOException, InterruptedException {
        getMainCtrl().startAE(this, accurateEstimation);
    }

    /**
     * This method displays a message when you press the back button in game, so you can confirm you want to exit
     *
     * @param mouseEvent click
     * @throws IOException when file not found or misread
     */
    public void back(MouseEvent mouseEvent) throws IOException {
        //sets the scene back to the main screen
        timer.stop();
        //shows popup
        mainCtrl.displayDisconnectMessage();
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
     * @param server instance of ServerUtils
     */
    public void setServer(ServerUtils server) {

        this.qCount = 0;
        this.score = 0;
        //if statement to make tests work
        if(name == null || scoreCount == null || questionNumber == null) {
            throw new IllegalStateException("One or more FXML fields are null");
        }
        name.setText(player.getUserName());
        scoreCount.setText("0");
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
     * This method returns the timer.
     * @return timer
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     *
     * @return the text with how many seconds this has left
     */
    public Text getCounterTimer() {
        return counterTimer;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }


}
