package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import commons.Question;
import commons.RoundPlayer;
import commons.ScoreSystem;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MPMatchingCtrl extends Controller {

    private Question matching;
    private MPGameCtrl parentCtrl;

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
    public MPMatchingCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    public void start(Controller parentCtrl, Question matching) throws MalformedURLException {
        this.parentCtrl = (MPGameCtrl) parentCtrl;
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
        image.setImage(img1);

        //Setting the name of the current activity.
        nameOfActivity.setText(matching.getActivities().get(0).getName());

        possibleActivities = new ArrayList<>();
        possibleActivities.addAll(matching.getActivities());
        Collections.shuffle(possibleActivities);

        setButtons();

    }

    /**
     * Method for setting the buttons in a randomized way
     */
    public void setButtons() {

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
        if (answer1.getText().equals(correctActivityName)) {
            correct = answer1;
            wrong1 = answer2;
            wrong2 = answer3;
        } else if (answer2.getText().equals(correctActivityName)) {
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
     * Method for removing a random wrong answer - used for the bombJoker
     */
    public void removeWrongAnswer(){
        Random rand = new Random();
        int wrong = rand.nextInt(2);
        if (answer1.getText().equals(correctActivityName)){
            if (wrong == 0) answer2.setDisable(true);
            else answer3.setDisable(true);
        }
        else if (answer2.getText().equals(correctActivityName)){
            if (wrong == 0) answer1.setDisable(true);
            else answer3.setDisable(true);
        }
        else{
            if (wrong == 0) answer1.setDisable(true);
            else answer2.setDisable(true);
        }
    }

    /**
     * This method changes the color of the correct answer for 3 seconds.
     *
     * @param button - the answer to be changed
     */
    public void temporaryChangeButtonColorsCorrect(Button button) {

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
     *
     * @param button - the answer to be changed
     */
    public void temporaryChangeButtonColorWrong(Button button) {
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
    }


    /**
     * Add score for the question and refresh the SPGameScreen (visible update question counter and score)
     * Also show how much points awarded for the question
     *
     * @throws InterruptedException
     */
    public void handleCorrect() throws InterruptedException {
        int addScore = ScoreSystem.calculateScore(this.getTime());

        //double the score if the player is using the joker
        if ( parentCtrl.isDoublePointJokerUsed() == true){
            // double the score
            addScore = addScore * 2;
            // reset the value of the joker after it's powerup has been used.
            // however, the player won't be able to use it anymore, since
            // the joker button is disabled after being clicked once.
            parentCtrl.setDoublePointJokerToUsed(false);
        }


        parentCtrl.scoreAwardedVisibility(true, addScore);
        parentCtrl.setScore(parentCtrl.getScore() + addScore);
        String username = mainCtrl.thisPlayer.getUserName();
        int round = parentCtrl.getRound();

//      add in the roundplayer with the above parameters and send it to the server
        RoundPlayer sendingObject = new RoundPlayer(username, addScore, round);
        server.send("/app/game/" + mainCtrl.lobbyId + "/scoreupdate", sendingObject);
    }


    /**
     * Enable/disable all buttons
     *
     * @param enabled iff true buttons are enabled
     */

    public void buttonsEnabled(boolean enabled) {
        if (enabled) {
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
