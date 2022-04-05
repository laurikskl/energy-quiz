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
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Instant;

/**
 * This class handles the multiChoice question type, by:
 * - displaying the correlated question frame when a question of this type is generated
 * - setting the information of the UI to the one generated in the question (3 buttons each displaying an activity with an image)
 * - handling the user input (the user pressing one of the 3 buttons)
 * - updating the score accordingly
 */
public class MultiChoiceCtrl extends Controller {

    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Question multiChoice;
    private SPGameCtrl parentCtrl;


    private Activity correctActivity;
    private int isCorrect;
    private Instant instant;
    private Long start;
    private Long finish;
    private String correctActivityName;

    @FXML
    private Button answer1;
    @FXML
    private Button answer2;
    @FXML
    private Button answer3;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;

    /**
     * Constructor with server, mainCtrl and multiChoice question
     *
     * @param server
     * @param mainCtrl
     */
    @Inject
    public MultiChoiceCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    public void start(Controller parentCtrl, Question multiChoice) throws MalformedURLException {
        this.parentCtrl = (SPGameCtrl) parentCtrl;
        //Set the isCorrect to -1 meaning there was no answer
        this.isCorrect = -1;
        //Obtaining current state of clock
        this.instant = Instant.now();
        //Current time in second from some ancient date
        this.start = instant.getEpochSecond();
        //Set Question
        this.multiChoice = multiChoice;
        //Set correct activity
        this.correctActivity = multiChoice.getCorrect();
        //Finds the correct answer and inserts its name in the correctActivityName field
        this.correctActivityName = correctActivity.getName();



        byte[] byteArr1 = multiChoice.getActivities().get(0).getImageContent();
        byte[] byteArr2 = multiChoice.getActivities().get(1).getImageContent();
        byte[] byteArr3 = multiChoice.getActivities().get(2).getImageContent();

        Image img1 = new Image(new ByteArrayInputStream(byteArr1));
        Image img2 = new Image(new ByteArrayInputStream(byteArr2));
        Image img3 = new Image(new ByteArrayInputStream(byteArr3));

        Image defaultIMG = new Image(String.valueOf(new File("client/src/main/resources/entername/MaxThePlant.png").toURI().toURL()));

        image1.setImage(img1);
        image2.setImage(img2);
        image3.setImage(img3);

        //set to default if there was an error in getting the images
        if(img1.isError()) {
            image1.setImage(defaultIMG);
        }
        if(img2.isError()) {
            image2.setImage(defaultIMG);
        }
        if(img3.isError()) {
            image3.setImage(defaultIMG);
        }

        answer1.setText(multiChoice.getActivities().get(0).getName());
        answer2.setText(multiChoice.getActivities().get(1).getName());
        answer3.setText(multiChoice.getActivities().get(2).getName());

        //Setting the colour of buttons when the question is initialized, so they don't stay the same colour after a question
        //answer1.setStyle("-fx-pref-height: 450; -fx-pref-width: 360; -fx-background-radius: 20; -fx-background-color: #7CCADE; -fx-content-display: top;");
        //answer2.setStyle("-fx-pref-height: 450; -fx-pref-width: 360; -fx-background-radius: 20; -fx-background-color: #7CCADE; -fx-content-display: top;");
        //answer3.setStyle("-fx-pref-height: 450; -fx-pref-width: 360; -fx-background-radius: 20; -fx-background-color: #7CCADE; -fx-content-display: top;");
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
        if(answer1.getText().equals(correctActivityName)) {
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
    }

    /**
     * This method changes the color of the wrong answer for 3 seconds.
     * @param button - the answer to be changed
     */
    public void temporaryChangeButtonColorWrong(Button button){
        button.setStyle(button.getStyle() + " -fx-background-color: #ff4f75 "); //red
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
     * @param actionEvent
     */
    public void handleButtonPress1(MouseEvent actionEvent) throws InterruptedException, IOException {
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
     * @param actionEvent
     */
    public void handleButtonPress2(MouseEvent actionEvent) throws InterruptedException, IOException {
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
     * @param actionEvent
     */
    public void handleButtonPress3(MouseEvent actionEvent) throws InterruptedException, IOException {
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


