package client.scenes;

import client.utils.ServerUtils;
import commons.Emoji;
import commons.Player;
import commons.Question;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;

import javax.inject.Inject;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

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
    private VBox vbox;
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
        this.onCooldown = false;

        //setting up emoji cooldown
        cooldownText.setVisible(false);
        this.cooldown = new PauseTransition(new Duration(4000));
        cooldown.setOnFinished(event -> {
            onCooldown = false;
            cooldownText.setVisible(false);
        });

        //if statement to make tests work
        if (name == null || scoreCount == null || questionNumber == null) {
            throw new IllegalStateException("One or more FXML fields are null");
        }
        name.setText(player.getUserName());
        scoreCount.setText("Score: 0");
        questionNumber.setText("1/20");

        //setup for the chat
        nameCol.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getKey()));
        emojiCol.setCellValueFactory(q -> new ObservableObjectValue<ImageView>() {
            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }

            @Override
            public ImageView get() {
                return q.getValue().getValue();
            }

            @Override
            public void addListener(ChangeListener<? super ImageView> listener) {

            }

            @Override
            public void removeListener(ChangeListener<? super ImageView> listener) {

            }

            @Override
            public ImageView getValue() {
                return get();
            }
        });
        nameCol.setPrefWidth(350);
        emojiCol.setPrefWidth(70);
        nameCol.setStyle("-fx-alignment: CENTER-RIGHT;");
        emo1IMG.setImage(new Image(new FileInputStream("client/src/main/resources/emoticons/trophy.png")));
        emo2IMG.setImage(new Image(new FileInputStream("client/src/main/resources/emoticons/dead.png")));
        emo3IMG.setImage(new Image(new FileInputStream("client/src/main/resources/emoticons/kiss.png")));
        emo4IMG.setImage(new Image(new FileInputStream("client/src/main/resources/emoticons/laugh.png")));
        emo5IMG.setImage(new Image(new FileInputStream("client/src/main/resources/emoticons/sad.png")));
        emo6IMG.setImage(new Image(new FileInputStream("client/src/main/resources/emoticons/smile.png")));
        ImageView empty = new ImageView();
        empty.setFitHeight(25);
        for(int i = 0; i < 5; i++) {
            chat.getItems().add(new Pair<>("", empty)); //filling with empty rows to not show: no content in table
        }

        scoreAwardedVisibility(false, 0);

        //getting list of 20 questions from server
        while (questions.isEmpty()) {
            try {
                questions = getServer().getQuestions();
            } catch (Exception e) {
                System.out.println("something went wrong here");
            }
        }
        Collections.shuffle(questions);

        //start with first question
        doAQuestion(questions.get(0));
    }

    /**
     * This method gets called everytime the game moves on to the next question.
     * That is, either when the player has answered by pressing a button,
     * or when the timer of 15 seconds per question runs out.
     * @throws IOException
     * @throws InterruptedException
     */
    public void startNewQuestion() throws IOException, InterruptedException {

        //for now, I will make the application exit after the player has done 20 questions
        if (this.getqCount()==20) {
            Platform.exit();
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

            //if more than 15 seconds passed, move on to the next question
            if (seconds<0){

                timer.stop();

                try {
                    startNewQuestion();
                    return;
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            counterTimer.setText(seconds + " seconds");
        });

        timer.start();

    }

    /**
     * This method resets the seconds.
     */
    public void resetSeconds(){
        this.seconds = 16;
    }

    /** Getter for question frame
     *
     * @return BorderPane of question frame
     */
    public BorderPane getQuestionFrame() {
        return questionFrame;
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
        }
        refresh();
//        else if (q.getClass().equals(Question.AccurateEstimation.class)) {
//            doAccurateEstimation((Question.AccurateEstimation) q);
//        }
    }

    /**
     * This method inserts the frame, gets time and correctness of the answer from the controller
     * Then it adds points to score accordingly, using ScoreSystem
     *
     * @param multiChoice current multiChoice question
     * @throws IOException
     */
    public void doMultiChoice(Question.MostNRGQuestion multiChoice) throws IOException, InterruptedException {
        getMainCtrl().startMC(this, multiChoice);
    }

    /**
     * This method inserts the frame, gets time and correctness of the answer from the controller
     * Then it adds points to score accordingly, using ScoreSystem
     *
     * @param choiceEstimation current Estimation question
     * @throws IOException
     */
    public void doChoiceEstimationQuestion(Question.ChoiceEstimation choiceEstimation) throws IOException, InterruptedException {
        getMainCtrl().startCE(this, choiceEstimation);
    }

    /**
     * This method inserts the frame, gets time and correctness of the answer from the controller
     * Then it adds points to score accordingly, using ScoreSystem
     *
     * @param q current Matching question
     * @throws IOException
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

//    /**
//     * This method inserts the frame, gets time, distance and correctness of the answer from the controller
//     * Then it adds points to score accordingly, using ScoreSystem
//     *
//     * @param q current AccurateEstimation question
//     * @throws IOException
//     */
//    public void doAccurateEstimation(Question.AccurateEstimation q) throws IOException {
//        String pathToFxml = "client/src/main/resources/client/scenes/AccurateEstimation.fxml";
//        URL url = new File(pathToFxml).toURI().toURL();
//        FXMLLoader fxmlLoader = new FXMLLoader(url);
//        Parent root = fxmlLoader.load();
//
//        //TODO: Create AccurateEstimationCtrl
//        AccurateEstimationCtrl controller = fxmlLoader.<AccurateEstimationCtrl>getController();
//        controller.initialize(server, mainCtrl, (Question.AccurateEstimation) q);
//        Scene scene = new Scene(root);
//
//        questionFrame.setCenter(scene.getRoot());
//    }

    /**
     * This method takes you back to the splash screen when the back button is pressed
     *
     * @param actionEvent click
     * @throws IOException when file not found or misread
     */
    public void back(MouseEvent actionEvent) throws IOException {
        //sets the scene back to the main screen
        getMainCtrl().showSplash();
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
     * Update visible score and visible question counter.
     */
    public void refresh(){
        scoreCount.setText(String.valueOf(score));
        //questionNumber.setText(qCount + "/20");
    }

    /**
     * This method returns the timer.
     * @return timer
     */
    public Timer getTimer() {
        return timer;
    }


    /** Tell the server this player sent a specific kind of emoji
     *
     * @param kind the kind of emoji sent
     */

    public void sendEmoji(String kind) {
        if(!onCooldown) {
            Emoji sent = server.sendEmoji(new Emoji(player, kind));
            try {
                displayEmoji(sent);
            } catch (FileNotFoundException e) { //when emoji image file not found in displayEmoji
                e.printStackTrace();
            }
            onCooldown = true;
            cooldown.play();
        } else {
            int timeLeft = 4 - (int) cooldown.currentTimeProperty().get().toSeconds();
            if(timeLeft == 1) {
                cooldownText.setText("Wait " + timeLeft + " second before sending another message");
            } else {
                cooldownText.setText("Wait " + timeLeft + " seconds before sending another message");
            }

            cooldownText.setVisible(true);
        }
    }


    /** Displays emoji sent or received on the screen
     *
     * @param sent the emoji sent/received
     * @throws FileNotFoundException when emoji image files not found
     */

    private void displayEmoji(Emoji sent) throws FileNotFoundException {
        String name = sent.getSender().getUserName();
        /*
        if(sent.getSender().equals(player)) {
            name = "You";
        }*/
        Image img = null;
        switch(sent.getEmoji()) {
            case "Dead":
                img = new Image(new FileInputStream("client/src/main/resources/emoticons/dead.png"));
                break;
            case "Trophy":
                img = new Image(new FileInputStream("client/src/main/resources/emoticons/trophy.png"));
                break;
            case "Kiss":
                img = new Image(new FileInputStream("client/src/main/resources/emoticons/kiss.png"));
                break;
            case "Laugh":
                img = new Image(new FileInputStream("client/src/main/resources/emoticons/laugh.png"));
                break;
            case "Smile":
                img = new Image(new FileInputStream("client/src/main/resources/emoticons/smile.png"));
                break;
            case "Sad":
                img = new Image(new FileInputStream("client/src/main/resources/emoticons/sad.png"));
                break;
        }

        ImageView imgView = new ImageView();
        imgView.setFitWidth(25);
        imgView.setFitHeight(25);
        imgView.setImage(img);

        //adding message to the bottom of the chat by shifting all messages up by one
        Pair<String, ImageView> toAdd = new Pair<>(name, imgView);
        Stack<Pair<String, ImageView>> toStore = new Stack<>();
        for(int i = 4; i > 0; i--) {
            toStore.push(chat.getItems().get(i));
        }
        for(int i = 0; i < 4; i++) {
            chat.getItems().set(i, toStore.pop());
        }
        chat.getItems().set(4, toAdd);

        //remove message after 5 seconds
        PauseTransition pause = new PauseTransition();
        pause.setDuration(Duration.seconds(5));
        pause.setOnFinished(event -> {
            int index = chat.getItems().indexOf(toAdd);
            if(index >= 0) {
                chat.getItems().set(index, new Pair<>("", new ImageView()));
            }
        });
        pause.play();
    }


    /** Trophy emoji sent
     *
     * @param mouseEvent mouse clicked
     */

    public void TrophyEmoji(MouseEvent mouseEvent) {
        sendEmoji("Trophy");
    }


    /** Dead emoji sent
     *
     * @param mouseEvent mouse clicked
     */

    public void DeadEmoji(MouseEvent mouseEvent) {
        sendEmoji("Dead");
    }


    /** Laugh emoji sent
     *
     * @param mouseEvent mouse clicked
     */

    public void LaughEmoji(MouseEvent mouseEvent) {
        sendEmoji("Laugh");
    }


    /** Kiss emoji sent
     *
     * @param mouseEvent mouse clicked
     */

    public void KissEmoji(MouseEvent mouseEvent) {
        sendEmoji("Kiss");
    }


    /** Sad emoji sent
     *
     * @param mouseEvent mouse clicked
     */

    public void SadEmoji(MouseEvent mouseEvent) {
        sendEmoji("Sad");
    }


    /** Smile emoji sent
     *
     * @param mouseEvent mouse clicked
     */

    public void SmileEmoji(MouseEvent mouseEvent) {
        sendEmoji("Smile");
    }


    /** Set visibility of cooldown text
     *
     * @param visible true iff text should be visible
     */

    public void visibleCooldown(boolean visible) {
       cooldownText.setVisible(visible);
    }


}
