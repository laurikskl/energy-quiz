package client.scenes;

import client.utils.ServerUtils;
import commons.Player;
import commons.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for single-player game screen
 */

public class SPGameController {

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

    private ServerUtils server;
    private MainCtrl mainCtrl;
    private int qCount;
    private List<Question> questions;
    private Player player;
    private int score;


    /**
     * empty constructor
     */

    public SPGameController() {
    }


    /**
     * This method:
     * sets up the fields
     * generates questions and iterates over them
     * does the cleanup after the game
     *
     * @param player the player of the game
     * @param server an instance of ServerUtils
     */

    @FXML
    public void initialize(Player player, ServerUtils server, MainCtrl mainCtrl) throws IOException {
        this.player = player;
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.qCount = 0;
        this.score = 0;
        this.questions = new ArrayList<>();
        //if statement to make tests work
        if (name == null || scoreCount == null || questionNumber == null) {
            throw new IllegalStateException("One or more FXML fields are null");
        }
        name.setText(player.getUserName());
        scoreCount.setText("Score: 0");
        questionNumber.setText("0/20");

        questions.add(getServer().getQuestion());
        Question q = questions.get(0);
        doAQuestion(q);

        System.out.println(q.getClass());
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
     *
     * @param q the current question
     */

    public void doAQuestion(Question q) throws IOException {
        //Increment and display question counter
        this.qCount++;
        questionNumber.setText(qCount + "/20");

        //Choose which type of question it is and load the appropriate frame with its controller
        if (q.getClass().equals(Question.MultiChoice.class)) {
            String pathToFxml = "client/src/main/resources/client/scenes/MultiChoiceScreen.fxml";
            URL url = new File(pathToFxml).toURI().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent root = fxmlLoader.load();

            MultiChoiceCtrl controller = fxmlLoader.<MultiChoiceCtrl>getController();
            controller.initialize(server, mainCtrl, (Question.MultiChoice) q);
            Scene scene = new Scene(root);

            questionFrame.setCenter(scene.getRoot());
        } else if (q.getClass().equals(Question.EstimationQuestion.class)) {
            String pathToFxml = "client/src/main/resources/client/scenes/ChoiceEstimation.fxml";
            URL url = new File(pathToFxml).toURI().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent root = fxmlLoader.load();

            //TODO: Create ChoiceEstimationCtrl
//            ChoiceEstimationCtrl controller = fxmlLoader.<ChoiceEstimationCtrl>getController();
//            controller.initialize(server, mainCtrl, (Question.EstimationQuestion) q);
            Scene scene = new Scene(root);

            questionFrame.setCenter(scene.getRoot());
        } else if (q.getClass().equals(Question.Matching.class)) {
            String pathToFxml = "client/src/main/resources/client/scenes/Matching.fxml";
            URL url = new File(pathToFxml).toURI().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent root = fxmlLoader.load();

            //TODO: Create MatchingCtrl
//            MatchingCtrl controller = fxmlLoader.<MatchingCtrl>getController();
//            controller.initialize(server, mainCtrl, (Question.Matching) q);
            Scene scene = new Scene(root);

            questionFrame.setCenter(scene.getRoot());
        } else if (q.getClass().equals(Question.AccurateEstimation.class)) {
            String pathToFxml = "client/src/main/resources/client/scenes/AccurateEstimation.fxml";
            URL url = new File(pathToFxml).toURI().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent root = fxmlLoader.load();

            //TODO: Create AccurateEstimationCtrl
//            AccurateEstimationCtrl controller = fxmlLoader.<AccurateEstimationCtrl>getController();
//            controller.initialize(server, mainCtrl, (Question.AccurateEstimation) q);
            Scene scene = new Scene(root);

            questionFrame.setCenter(scene.getRoot());
        }

        //start a timer for the question
    }


    /**
     * This method takes you back to the splash screen when the back button is pressed
     *
     * @param actionEvent click
     * @throws IOException when file not found or misread
     */

    public void back(ActionEvent actionEvent) throws IOException {
        //sets the scene back to the main screen
        URL url = new File("client/src/main/resources/client/scenes/splash.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);

        Scene newScene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(newScene);
        window.show();
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
     * @return instance of ServerUtils
     */

    public ServerUtils getServer() {
        return server;
    }

    /**
     * @param server instance of ServerUtils
     */

    public void setServer(ServerUtils server) {
        this.server = server;
    }

}
