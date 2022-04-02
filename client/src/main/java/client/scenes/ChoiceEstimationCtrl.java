package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Question;
import commons.ScoreSystem;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.time.Instant;
import java.util.Collections;

/**
 * This class handles the choiceEstimation question type, by:
 * - displaying the correlated question frame when a question of this type is generated
 * - setting the information of the UI to the one generated in the question (activity with image + answers)
 * - handling the user input (the user pressing one of the 3 buttons)
 * - updating the score accordingly
 */
public class ChoiceEstimationCtrl extends Controller{

    private Question choiceEstimation;
    private SPGameCtrl parentCtrl;
    private boolean isCorrect;
    private Instant instant;
    private Long start;
    private Long finish;
    private Long correctAnswer;
    private String correctText;

    @FXML
    private Button answer1;
    @FXML
    private Button answer2;
    @FXML
    private Button answer3;
    @FXML
    private ImageView image;
    @FXML
    private Button activityButton;

    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public ChoiceEstimationCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    /**
     * Method for starting the question, setting all the UI and starting the timer
     * @param parentCtrl controller for the Singleplayer game
     * @param choiceEstimation question
     */
    public void start(Controller parentCtrl, Question choiceEstimation) throws MalformedURLException {
        this.parentCtrl = (SPGameCtrl) parentCtrl;
        //Set the isCorrect to false meaning there was no answer
        this.isCorrect = false;
        //Obtaining current state of clock
        this.instant = Instant.now();
        //Current time in second from some ancient date
        this.start = instant.getEpochSecond();
        //Set Question
        this.choiceEstimation = choiceEstimation;

        //Set the correct answer
        correctAnswer = choiceEstimation.getActivities().get(0).getPowerConsumption();
        correctText = String.valueOf(correctAnswer);
        //Set the activity, image and buttons
        byte[] byteArray = choiceEstimation.getActivities().get(0).getImageContent();
        Image img = new Image(new ByteArrayInputStream(byteArray));
        //if there was an error in getting the image, set it to a default image
        /*
        if(img.isError()) {
            image.setImage(new Image(new File("client/src/main/resources/entername/MaxThePlant.png").toURI().toURL().toString()));
        } else {

         */
        image.setImage(img);
        activityButton.setText(choiceEstimation.getActivities().get(0).getName());
        setButtons();
    }

    /**
     * Method for setting the buttons in a randomized way
     */
    public void setButtons(){
        Collections.shuffle(choiceEstimation.getConsumptions());

        answer1.setText(String.valueOf(choiceEstimation.getConsumptions().get(0)));
        answer2.setText(String.valueOf(choiceEstimation.getConsumptions().get(1)));
        answer3.setText(String.valueOf(choiceEstimation.getConsumptions().get(2)));

        //Set the style of the buttons when setting the answers, so they don't stay the same from question to question
        answer1.setStyle("-fx-background-color: #7CCADE; -fx-background-radius: 20; -fx-pref-width: 360; -fx-pref-height: 100;");
        answer2.setStyle("-fx-background-color: #7CCADE; -fx-background-radius: 20; -fx-pref-width: 360; -fx-pref-height: 100;");
        answer3.setStyle("-fx-background-color: #7CCADE; -fx-background-radius: 20; -fx-pref-width: 360; -fx-pref-height: 100;");
    }

    /**
     * Method for changing the buttons' colours to show which answer was correct
     */
    public void showCorrect() {
        Button correct = answer1;
        Button wrong1 = answer2;
        Button wrong2 = answer3;

        if (answer2.getText().equals(correctText)) {
            correct = answer2;
            wrong1 = answer1;
            wrong2 = answer3;
        }
        else if (answer3.getText().equals(correctText)) {
            correct = answer3;
            wrong1 = answer1;
            wrong2 = answer2;
        }

        correct.setStyle(correct.getStyle() + " -fx-background-color: #45ff9c; ");
        wrong1.setStyle(wrong1.getStyle() + " -fx-background-color: #ff4f75; ");
        wrong2.setStyle(wrong2.getStyle() + " -fx-background-color: #ff4f75; ");
    }

    /**
     * Getter for the time spent on a question
     *
     * @return
     */
    public Long getTime() {
        return finish - start;
    }

    /**
     * Getter returning true if the user was correct
     *
     * @return
     */
    public boolean getIsCorrect() {
        return isCorrect;
    }

    /**
     * When first answer button is pressed check if it was the correct answer
     * and set 'isCorrect' field accordingly. Also stop the timer and show correct
     * answers by colouring the fields
     *
     * @param actionEvent
     */
    public void handleButtonPress1(MouseEvent actionEvent) throws InterruptedException {
        instant = Instant.now();
        finish = instant.getEpochSecond();
        if (answer1.getText().equals(correctText)) {
            isCorrect = true;
            handleCorrect();
        } else {
            isCorrect = false;
        }
        buttonsEnabled(false);

        showCorrect();

        parentCtrl.setSeconds(4);
        parentCtrl.refresh();
    }

    /**
     * When second answer button is pressed check if it was the correct answer
     * and set 'isCorrect' field accordingly. Also stop the timer and show correct
     * answers by colouring the fields
     *
     * @param actionEvent
     */
    public void handleButtonPress2(MouseEvent actionEvent) throws InterruptedException {
        instant = Instant.now();
        finish = instant.getEpochSecond();
        if (answer2.getText().equals(correctText)) {
            isCorrect = true;
            handleCorrect();
        } else {
            isCorrect = false;
        }
        buttonsEnabled(false);

        showCorrect();

        parentCtrl.setSeconds(4);
        parentCtrl.refresh();
    }

    /**
     * When third answer button is pressed check if it was the correct answer
     * and set 'isCorrect' field accordingly. Also stop the timer and show correct
     * answers by colouring the fields
     *
     * @param actionEvent
     */
    public void handleButtonPress3(MouseEvent actionEvent) throws InterruptedException {
        instant = Instant.now();
        finish = instant.getEpochSecond();
        if (answer3.getText().equals(correctText)) {
            isCorrect = true;
            handleCorrect();
        } else {
            isCorrect = false;
        }
        buttonsEnabled(false);

        showCorrect();

        parentCtrl.setSeconds(4);
        parentCtrl.refresh();
    }

    /**
     * When the correct answer is pressed, the score for the question is calculated
     * and added to the score on the screen
     * 
     * @throws InterruptedException
     */
    public void handleCorrect() throws InterruptedException {
        int addScore = ScoreSystem.calculateScore(this.getTime());
        parentCtrl.scoreAwardedVisibility(true, addScore);
        parentCtrl.setScore(parentCtrl.getScore() + addScore);
        PauseTransition pause = new PauseTransition(
                Duration.seconds(2)
        );
        pause.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parentCtrl.scoreAwardedVisibility(false, 0);
            }
        });
        pause.play();
    }

    /**
     * Method to update a question counter
     */
    /*
    public void updateCounter() {
        parentCtrl.setqCount(parentCtrl.getqCount() + 1);
        parentCtrl.refresh();
    }
     */


    /**
     * Enable/disable all buttons
     *
     * @param enabled iff true buttons are enabled
     */

    public void buttonsEnabled(boolean enabled) {
        if(enabled) {
            answer1.setDisable(false);
            answer2.setDisable(false);
            answer3.setDisable(false);
        } else {
            answer1.setDisable(true);
            answer2.setDisable(true);
            answer3.setDisable(true);
        }
    }







}
