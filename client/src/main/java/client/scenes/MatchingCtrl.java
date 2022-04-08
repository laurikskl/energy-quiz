package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
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
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class handles the Matching question type, by:
 *  * - displaying the correlated question frame when a question of this type is generated
 *  * - setting the information of the UI to the one generated in the question (activity with image) + 3 buttons with activities
 *  * - handling the user input (pressing an answer button)
 *  * - updating the score accordingly
 */
public class MatchingCtrl extends Controller{

    private Question matching;
    private SPGameCtrl parentCtrl;

    private Activity correctActivity;
    private int isCorrect;
    private Instant instant;
    private Long start;
    private Long finish;
    private String correctActivityName;
    private List<Activity> possibleActivities;

    @FXML
    private ImageView image;
    @FXML
    private Text nameOfActivity;
    @FXML
    private Button answer1;
    @FXML
    private Button answer2;
    @FXML
    private Button answer3;

    /**
     * Constructor with server, mainCtrl and multiChoice question
     *
     * @param server
     * @param mainCtrl
     */
    @Inject
    public MatchingCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    public void start(Controller parentCtrl, Question matching) throws MalformedURLException {
        this.parentCtrl = (SPGameCtrl) parentCtrl;
        //Set the isCorrect to -1 meaning there was no answer
        this.isCorrect = -1;
        //Obtaining current state of clock
        this.instant = Instant.now();
        //Current time in second from some ancient date
        this.start = instant.getEpochSecond();
        //Set Question
        this.matching = matching;
        //Set correct activity, which should be the second item from the list of activities specific to this question.
        this.correctActivity = matching.getActivities().get(1);
        //Finds the correct answer and inserts its name in the correctActivityName field
        this.correctActivityName = correctActivity.getName();

        byte[] byteArr1 = matching.getActivities().get(0).getImageContent();
        Image img1 = new Image(new ByteArrayInputStream(byteArr1));
        if(img1.isError()) {
            image.setImage(new Image(new File("client/src/main/resources/entername/MaxThePlant.png").toURI().toURL().toString()));
        } else {
            image.setImage(img1);
        }
        //Setting the name of the current activity.
        nameOfActivity.setText(matching.getActivities().get(0).getName());

        possibleActivities = new ArrayList<>();
        possibleActivities.add(matching.getActivities().get(1));
        possibleActivities.add(matching.getActivities().get(2));
        possibleActivities.add(matching.getActivities().get(3));
        Collections.shuffle(possibleActivities);

        setButtons();

    }

    /**
     * Method for setting the buttons in a randomized way
     */
    public void setButtons(){

        answer1.setText(String.valueOf(possibleActivities.get(0).getName()));
        answer2.setText(String.valueOf(possibleActivities.get(1).getName()));
        answer3.setText(String.valueOf(possibleActivities.get(2).getName()));

    }

    /**
     * Paints the buttons, the wrong answers are painted red and the correct one is painted
     * green
     **/
    public void showCorrect() {
        Button correct = null;
        Button wrong1 = null;
        Button wrong2 = null;

        //set the correct and wrong buttons
        if( answer1.getText().equals(correctActivityName)) {
            correct = answer1;
            wrong1 = answer2;
            wrong2 = answer3;
        } else if(answer2.getText().equals(correctActivityName)) {
            correct = answer2;
            wrong1 = answer1;
            wrong2 = answer3;
        } else {
            correct = answer3;
            wrong1 = answer1;
            wrong2 = answer2;
        }

        //change the color for the correct answer with green for 3 seconds
        final Button correct1 = correct;
        temporaryChangeButtonColorsCorrect(correct1);

        //change the colors for the wrong answers with red for 3 seconds
        final Button wrong11 = wrong1;
        temporaryChangeButtonColorWrong(wrong11);

        final Button wrong12 = wrong2;
        temporaryChangeButtonColorWrong(wrong12);
    }

    /**
     * This method changes the color of the correct answer for 3 seconds.
     * @param button - the answer to be changed
     */
    public void temporaryChangeButtonColorsCorrect(Button button){

        button.setStyle(button.getStyle() + " -fx-background-color: #45ff9c; "); //green
        PauseTransition pause = new PauseTransition(
                Duration.seconds(3)
        );
        pause.setOnFinished(event -> {
            button.setStyle(button.getStyle() + " -fx-background-color: #7CCADE; "); //back to blue
        });
        pause.play();
    }

    /**
     * This method changes the color of the wrong answer for 3 seconds.
     * @param button - the answer to be changed
     */
    public void temporaryChangeButtonColorWrong(Button button){
        button.setStyle(button.getStyle() + " -fx-background-color: #ff4f75 "); //red
        PauseTransition pause = new PauseTransition(
                Duration.seconds(3)
        );
        pause.setOnFinished(event -> {
            button.setStyle(button.getStyle() + " -fx-background-color: #7CCADE; "); //back to blue
        });
        pause.play();
    }


    /**
     * Getter for the time spend on a question
     *
     * @return
     */
    public Long getTime() {
        return finish - start;
    }

    /**
     * When first answer button is pressed check if it was the correct answer
     * and set 'isCorrect' field accordingly. Also stop the timer and show correct
     * answers by colouring the fields
     *
     * @param mouseEvent
     */
    public void handleButtonPress1(MouseEvent mouseEvent) throws InterruptedException, IOException {
        instant = Instant.now();
        finish = instant.getEpochSecond();
        if (answer1.getText().equals(correctActivityName)) {
            isCorrect = 1;
            handleCorrect();
        } else {
            isCorrect = 0;
        }
        buttonsEnabled(false);

        //show which answer was the correct one (for 3 seconds)
        showCorrect();

        parentCtrl.setSeconds(4);
        parentCtrl.refresh();
    }

    /**
     * When second answer button is pressed check if it was the correct answer
     * and set 'isCorrect' field accordingly. Also stop the timer and show correct
     * answers by colouring the fields
     *
     * @param mouseEvent
     */
    public void handleButtonPress2(MouseEvent mouseEvent) throws InterruptedException, IOException {
        instant = Instant.now();
        finish = instant.getEpochSecond();
        if (answer2.getText().equals(correctActivityName)) {
            isCorrect = 1;
            handleCorrect();
        } else {
            isCorrect = 0;
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
     * @param mouseEvent
     */
    public void handleButtonPress3(MouseEvent mouseEvent) throws InterruptedException, IOException {
        instant = Instant.now();
        finish = instant.getEpochSecond();
        if (answer3.getText().equals(correctActivityName)) {
            isCorrect = 1;
            handleCorrect();
        } else {
            isCorrect = 0;
        }
        buttonsEnabled(false);

        showCorrect();

        parentCtrl.setSeconds(4);
        parentCtrl.refresh();

    }


    /**
     * Add score for the question and refresh the SPGameScreen (visible update question counter and score)
     * Also show how much points awarded for the question
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
