package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import commons.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.time.Instant;

public class MultiChoiceCtrl {

    private ServerUtils server;
    private MainCtrl mainCtrl;
    private Question.MultiChoice multiChoice;

    private boolean isCorrect;
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
     * Empty constructor
     */
    public MultiChoiceCtrl() {
    }

    /**
     * Constructor with server, mainCtrl and multiChoice question
     *
     * @param server
     * @param mainCtrl
     * @param multiChoice
     */
    @Inject
    public MultiChoiceCtrl(ServerUtils server, MainCtrl mainCtrl, Question.MultiChoice multiChoice) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.multiChoice = multiChoice;
    }

    /**
     * Set how the screen is looking:
     * - Insert text into questions
     * - Set the correct images
     *
     * @param server
     * @param mainCtrl
     * @param multiChoice
     */
    @FXML
    public void initialize(ServerUtils server, MainCtrl mainCtrl, Question.MultiChoice multiChoice) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.multiChoice = multiChoice;
        //Set the isCorrect to false
        this.isCorrect = false;
        //Obtaining current state of clock
        this.instant = Instant.now();
        //Current time in second from some ancient date
        this.start = instant.getEpochSecond();
        //Finds the correct answer and inserts its name in the correctActivityName field
        setCorrectAnswer();

        String path1 = multiChoice.getActivities().get(0).getImagePath();
        String path2 = multiChoice.getActivities().get(1).getImagePath();
        String path3 = multiChoice.getActivities().get(2).getImagePath();

        image1.setImage(new Image(new File(path1).toURI().toString()));
        image2.setImage(new Image(new File(path2).toURI().toString()));
        image3.setImage(new Image(new File(path3).toURI().toString()));

        answer1.setText(multiChoice.getActivities().get(0).getName());
        answer2.setText(multiChoice.getActivities().get(1).getName());
        answer3.setText(multiChoice.getActivities().get(2).getName());
    }

    /**
     * This should be called when this question frame is called from the game controller,
     * when using initialize, it will load all the question frames when the application
     * is started, that could cause problems in the future
     */
//    @FXML
//    public void onOpen() {
//
//    }
//    @FXML
//    public void handleButtonPress(ActionEvent actionEvent) {
//    }

    /**
     * Loops over activities and finds which one is the correct one
     */
    public void setCorrectAnswer() {
        int correctActivityIndex = multiChoice.getCorrectActivityIndex();
        String correctActivityName = "";
        for (Activity act : multiChoice.getActivities()) {
            if (act.getId() == correctActivityIndex)
                correctActivityName = act.getName();
        }
        this.correctActivityName = correctActivityName;
    }

    /**
     * Paints the buttons, the wrong answers are painted red and the correct one is painted
     * green
     **/
    public void showCorrect() {
        Button correct = answer1;
        Button wrong1 = answer2;
        Button wrong2 = answer3;

        if (answer2.getText().equals(correctActivityName)) {
            correct = answer2;
            wrong1 = answer1;
            wrong2 = answer3;
        } else if (answer3.getText().equals(correctActivityName)) {
            correct = answer3;
            wrong1 = answer1;
            wrong2 = answer2;
        }

        correct.setStyle(correct.getStyle() + " -fx-background-color: #00FF00; ");
        wrong1.setStyle(wrong1.getStyle() + " -fx-background-color: #FF0000; ");
        wrong2.setStyle(wrong2.getStyle() + " -fx-background-color: #FF0000; ");
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
    public void handleButtonPress1(ActionEvent actionEvent) {
        if (answer1.getText().equals(correctActivityName)) {
            isCorrect = true;
        }
        instant = Instant.now();
        finish = instant.getEpochSecond();
        showCorrect();
    }

    /**
     * When second answer button is pressed check if it was the correct answer
     * and set 'isCorrect' field accordingly. Also stop the timer and show correct
     * answers by colouring the fields
     *
     * @param actionEvent
     */
    public void handleButtonPress2(ActionEvent actionEvent) {
        if (answer2.getText().equals(correctActivityName)) {
            isCorrect = true;
        }
        instant = Instant.now();
        finish = instant.getEpochSecond();
        showCorrect();
    }

    /**
     * When third answer button is pressed check if it was the correct answer
     * and set 'isCorrect' field accordingly. Also stop the timer and show correct
     * answers by colouring the fields
     *
     * @param actionEvent
     */
    public void handleButtonPress3(ActionEvent actionEvent) {
        if (answer3.getText().equals(correctActivityName)) {
            isCorrect = true;
        }
        instant = Instant.now();
        finish = instant.getEpochSecond();
        showCorrect();
    }
}


