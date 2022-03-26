package client.scenes;

import client.utils.ServerUtils;
import commons.Player;
import commons.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javax.inject.Inject;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.swing.Timer;

/**
 * Controller for single-player game screen
 */

public class SPGameCtrl extends Controller {

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

    private MainCtrl mainCtrl;
    private ServerUtils server;
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
    int seconds;


    /**
     * Zero argument constructor
     */

    public SPGameCtrl(){
        super(null, null);
    }


    /**
     * @param server ServerUtils instance
     * @param mainCtrl MainCtrl instance
     */

    @Inject
    public SPGameCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    public void initialize(){
        seconds = 14;
        simpleTimer();
        timer.start();
    }

    public void simpleTimer(){
        timer = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                seconds--;
                counterTimer.setText(seconds+"seconds");
            }
        });
    }




    /**
     * this method will take care of every individual question
     * @param q the current question
     */

    public void doAQuestion(Question q) {
        timer.stop();
        this.qCount++;
        questionNumber.setText(qCount + "/20");
        //load the question in the frame

        //start a timer for the question
        seconds = 10;
        simpleTimer();
        timer.start();
    }


    /**
     * This method takes you back to the splash screen when the back button is pressed
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
     * @return the list of questions
     */

    public List<Question> getQuestions() {
        return questions;
    }


    /**
     * @return player
     */

    public Player getPlayer() {
        return player;
    }


    /**
     * @return score of the player
     */

    public int getScore() {
        return score;
    }


    /**
     * @return the Text element of the scoreCount
     */

    public Text getScoreCount() {
        return scoreCount;
    }


    /**
     * @return the Text element of the name
     */

    public Text getName() {
        return name;
    }


    /**
     * @return the Text element of the questionNumber
     */

    public Text getQuestionNumber() {
        return questionNumber;
    }


    /**
     * @return instance of ServerUtils
     */

    public ServerUtils getServer() {
        return server;
    }


    /**
     * @param qCount current question number
     */

    public void setqCount(int qCount) {
        this.qCount = qCount;
    }


    /**
     * @param questions list of questions
     */

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }


    /**
     * @param player player of game
     */

    public void setPlayer(Player player) {
        this.player = player;
    }


    /**
     * @param score score of player
     */

    public void setScore(int score) {
        this.score = score;
    }


    /**
     * @param server instance of ServerUtils
     */

    public void setServer(ServerUtils server) {
        this.server = server;
    }


    /**
     * @param scoreCount Text element for score
     */

    public void setScoreCount(Text scoreCount) {
        this.scoreCount = scoreCount;
    }


    /**
     * @param name Text element for name
     */

    public void setName(Text name) {
        this.name = name;
    }


    /**
     * @param questionNumber Text element for questionNumber
     */

    public void setQuestionNumber(Text questionNumber) {
        this.questionNumber = questionNumber;
    }

    /**
     * This method:
     * sets up the fields
     * generates questions and iterates over them
     * does the cleanup after the game
     * @param player the player of the game
     */

    public void startGame(Player player, ServerUtils server) {
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
}
