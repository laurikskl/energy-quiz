package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import commons.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for single-player game screen
 */

public class SPGameCtrl extends Controller{

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
    private Button backButton;
    @FXML
    private Text scoreCount;
    @FXML
    private Text name;
    @FXML
    private Text questionNumber;

    private Player player;
    private int qCount;
    private List<Question> questions;
    private int score;


    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
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
     */

    public void startGame(Player player) {
        this.player = player;
        this.qCount = 0;
        this.score = 0;
        this.questions = new ArrayList<>();

        //if statement to make tests work
        if(name == null || scoreCount == null || questionNumber == null) {
            throw new IllegalStateException("One or more FXML fields are null");
        }

        name.setText(player.getUserName());
        scoreCount.setText("Score: 0");
        questionNumber.setText("0/20");

        /*
        //generate 20 questions
        while(questions.size() < 20) {
            questions.add(getServer().getQuestion());
        }
        //iterate over all questions
        for(Question q : questions) {
            this.doAQuestion(q);
        }
        //overwrite high-score if the current score is higher
        if(score > getServer().getPlayer(player.getUserName()).getScore()) {
            getServer().setPlayer(player.getUserName(), score);
        }
         */
    }

    /**
     * this method will take care of every individual question
     * @param q the current question
     */
    public void doAQuestion(Question q) {
        this.qCount++;
        questionNumber.setText(qCount + "/20");
        //load the question in the frame
        //start a timer for the question
    }


    /**
     * This method takes you back to the splash screen when the back button is pressed
     * @param actionEvent click
     * @throws IOException when file not found or misread
     */
    public void back(ActionEvent actionEvent) throws IOException {
        this.mainCtrl.showSplash();
    }

    /**
     * @return PlayerObj
     */
    public Player getPlayer() {
        return this.player;
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
     * @param player PlayerObj
     */
    public void setPlayer(Player player) {
        this.player = player;
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
     * @param score score of player
     */
    public void setScore(int score) {
        this.score = score;
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

}
