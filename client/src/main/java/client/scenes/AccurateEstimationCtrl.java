package client.scenes;

import client.utils.ServerUtils;
import commons.Question;
import commons.ScoreSystem;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.time.Instant;
import java.util.Scanner;

/**
 * This class handles the accurateEstimation question type, by:
 * - displaying the correlated question frame when a question of this type is generated
 * - setting the information of the UI to the one generated in the question (activity with image)
 * - handling the user input (entering text in the textField)
 * - updating the score accordingly
 */
public class AccurateEstimationCtrl extends Controller {

    private SPGameCtrl parentCtrl;
    private Question accurateEstimation;
    private boolean isCorrect;
    private Instant instant;
    private Long start;
    private Long finish;
    private Long correctAnswer;
    private String correctText;
    private String userAnswer;
    private Long finalAnswer;

    @FXML
    private ImageView image;
    @FXML
    private Button activity;
    @FXML
    private Button submit;
    @FXML
    private TextField enterNumber;
    @FXML
    private Text numberError;
    @FXML
    private Text actualText;
    @FXML
    private Text actualAnswer;


    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public AccurateEstimationCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    /**
     * Method for starting the question, setting all the UI and starting the timer
     *
     * @param parentCtrl         controller for the Singleplayer game
     * @param accurateEstimation question
     */
    public void start(Controller parentCtrl, Question accurateEstimation) throws MalformedURLException {
        this.parentCtrl = (SPGameCtrl) parentCtrl;
        //Set the isCorrect to false meaning there was no answer
        this.isCorrect = false;
        //Obtaining current state of clock
        this.instant = Instant.now();
        //Current time in second from some ancient date
        this.start = instant.getEpochSecond();
        //Set Question
        this.accurateEstimation = accurateEstimation;

        //Set the error to be invisible
        numberError.setVisible(false);

        //Set the text field and button every time you start a question,
        //so they don't stay the same from question to question
        submit.setDisable(false);
        enterNumber.setDisable(false);
        enterNumber.clear();

        //Set the correct answer
        correctAnswer = accurateEstimation.getActivities().get(0).getPowerConsumption();
        correctText = String.valueOf(correctAnswer);
        //Set the activity and image
        byte[] byteArray = accurateEstimation.getActivities().get(0).getImageContent();
        Image img = new Image(new ByteArrayInputStream(byteArray));

        image.setImage(img);
//        if(img.isError()) {
//            image.setImage(new Image(new File("client/src/main/resources/entername/MaxThePlant.png").toURI().toURL().toString()));
//        } else {
//            image.setImage(img);
//        }

        activity.setText(accurateEstimation.getActivities().get(0).getName());

        //Set the actual answer and set it to be invisible
        actualText.setVisible(false);
        actualAnswer.setText(correctText);
        actualAnswer.setVisible(false);
    }

    /**
     * Method for showing what the correct answer actually was
     */
    public void showCorrect() {
        actualText.setVisible(true);
        actualAnswer.setVisible(true);
    }

    /**
     * Getter for time spent on question
     */
    public Long getTime() {
        return finish - start;
    }

    /**
     * Method that handles the user pressing the submit button
     *
     * @param actionEvent
     */
    public void handleSubmit(MouseEvent actionEvent) throws InterruptedException {
        //Verifying if the submitted text is a Long
        userAnswer = enterNumber.getText();
        Scanner answerScanner = new Scanner(userAnswer);
        if (answerScanner.hasNextLong()) {
            //If it is a Long, set the final answer to the user input and calculate the score
            finalAnswer = answerScanner.nextLong();
            instant = Instant.now();
            finish = instant.getEpochSecond();
            handleScore();

            //Disable the button and text field, disable error if visible from previous submits,
            //and show the correct answer
            numberError.setVisible(false);
            submit.setDisable(true);
            enterNumber.setDisable(true);
            showCorrect();

            //Keep the same question while the correct answer shown
//            PauseTransition pause = new PauseTransition(
//                    Duration.seconds(3)
//            );


            parentCtrl.setSeconds(4);
            parentCtrl.refresh();
//                    parentCtrl.startNewQuestion(); //move to the next question


        } else {
            //If the user submitted text is not a Long, show an error
            numberError.setVisible(true);
        }
    }

    /**
     * Method for setting the score using the correct score calculating system,
     * then adding it to the existing score and updating the game screen
     */
    public void handleScore() throws InterruptedException {
        int addScore = ScoreSystem.calculateScore(this.getTime(), finalAnswer, correctAnswer);
        parentCtrl.scoreAwardedVisibility(true, addScore);
        parentCtrl.setScore(parentCtrl.getScore() + addScore);
//        PauseTransition pause = new PauseTransition(
//                Duration.seconds(2)
//        );
//        pause.setOnFinished(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                parentCtrl.scoreAwardedVisibility(false, 0);
//            }
//        });
//        pause.play();
    }

}
