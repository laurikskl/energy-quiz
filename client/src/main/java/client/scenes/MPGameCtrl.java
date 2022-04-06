package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Emoji;
import commons.Game;
import commons.Player;
import commons.Question;
import javafx.animation.PauseTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

/**
 * Controller for FXML of the multiplayer game screen
 */

public class MPGameCtrl extends Controller {

    /**
     * FXML fields
     */
    @FXML
    private ImageView emo1IMG, emo2IMG, emo3IMG, emo4IMG, emo5IMG, emo6IMG;
    @FXML
    private TableColumn<Pair<String, ImageView>, String> nameCol;
    @FXML
    private TableColumn<Pair<String, ImageView>, ImageView> emojiCol;
    @FXML
    private TableView<Pair<String, ImageView>> chat;
    @FXML
    private Text cooldownText;
    @FXML
    private BorderPane questionFrame;

    /*For the timer.
     */
    @FXML
    private Text counterTimer;
    private int seconds;

    /**
     * Fields used in class
     */

    private boolean onCooldown;
    private PauseTransition cooldown;
    private Player player;
    private int score;
    private long lobbyId;
    private int round;
    private Question currentQuestion;
    private boolean bombJokerUsed;

    @FXML
    private ImageView backImg;
    @FXML
    private Text questionNumber;
    @FXML
    private Text name;
    @FXML
    private Text scoreCount;
    @FXML
    private Text scoreAwarded;
    @FXML
    private Button bombJoker;

    private Game game;

    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */

    @Inject
    public MPGameCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    @FXML
    private void initialize() {
        this.backImg.setImage(new Image("icons/back.png"));
    }

    public int getRound() {
        return round;
    }

    /**
     * Method that returns the application to the initial screen when the back button is pressed.
     *
     * @param mouseEvent - pressing the back button triggers this function
     * @throws IOException - something went wrong with reading/writing/finding files
     */
    // TODO: Maybe it should also handle disconnecting, actually
    public void back(MouseEvent mouseEvent) throws IOException {
        mainCtrl.timer.stop();
        disconnectMessage();
        this.mainCtrl.showSplash();
    }

    /**
     * Method that handles user pressing the "remove one incorrect answer" joker
     * @param mouseEvent
     * @throws IOException
     */
    public void removeAnswerJoker(MouseEvent mouseEvent) throws IOException {
        if(currentQuestion.getClass().equals(Question.ChoiceEstimation.class)) {
            MPChoiceEstimationCtrl mpChoiceEstimationCtrl = findController(MPChoiceEstimationCtrl.class);
            if (mpChoiceEstimationCtrl != null) {
                mpChoiceEstimationCtrl.removeWrongAnswer();
            }
        }
        else if (currentQuestion.getClass().equals(Question.MostNRGQuestion.class)) {
            MPMultiChoiceCtrl mpMultiChoiceCtrl = findController(MPMultiChoiceCtrl.class);
            if (mpMultiChoiceCtrl != null) {
                mpMultiChoiceCtrl.removeWrongAnswer();
            }
        }
        else if (currentQuestion.getClass().equals(Question.Matching.class)) {
            MPMatchingCtrl mpMatchingCtrl = findController(MPMatchingCtrl.class);
            if (mpMatchingCtrl != null) {
                mpMatchingCtrl.removeWrongAnswer();
            }
        }
        bombJoker.setDisable(true);
        bombJokerUsed = true;
        sendEmoji("BombJoker");
    }

    /**
     * Called when starting a game
     * THIS IS NOT USED YET!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * @param player the player of this client
     */

    public void startGame(Player player, long lobbyId, Game game) throws IOException, InterruptedException {
        this.lobbyId = lobbyId;
        this.onCooldown = false;
        this.player = player;
        this.round = 0;
        this.game = game;

        name.setText(player.getUserName());
        scoreCount.setText("Score: 0");
        questionNumber.setText("1/20");

        bombJokerUsed = false;

        scoreAwardedVisibility(false, 0);

        simpleTimer();

        //display emoji when received
        server.registerForMessages("/topic/game/" + lobbyId + "/emoji", Emoji.class, emoji -> {
            try {
                displayEmoji(emoji);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        //setting up emoji cooldown for 4 seconds
        cooldownText.setVisible(false);
        this.cooldown = new PauseTransition(new Duration(4000));
        cooldown.setOnFinished(event -> {
            //turn off cooldown and set its text to invisible after 4 seconds
            onCooldown = false;
            cooldownText.setVisible(false);
        });

        //setup for the chat
        nameCol.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getKey()));
        emojiCol.setCellValueFactory(q -> new ObservableObjectValue<>() {
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
        nameCol.setStyle("-fx-alignment: CENTER-RIGHT; ");
        try {
            emo1IMG.setImage(new Image(new FileInputStream("client/src/main/resources/emoticons/trophy.png")));
            emo2IMG.setImage(new Image(new FileInputStream("client/src/main/resources/emoticons/dead.png")));
            emo3IMG.setImage(new Image(new FileInputStream("client/src/main/resources/emoticons/kiss.png")));
            emo4IMG.setImage(new Image(new FileInputStream("client/src/main/resources/emoticons/laugh.png")));
            emo5IMG.setImage(new Image(new FileInputStream("client/src/main/resources/emoticons/sad.png")));
            emo6IMG.setImage(new Image(new FileInputStream("client/src/main/resources/emoticons/smile.png")));
        } catch (FileNotFoundException e) { //when files for emoji images not found
            e.printStackTrace();
        }
        //filling with empty rows to not show: no content in table
        ImageView empty = new ImageView();
        empty.setFitHeight(25);
        for (int i = 0; i < 5; i++) {
            chat.getItems().add(new Pair<>("", empty));
        }
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
        currentQuestion = q;
        System.out.println("Question has started!");
        resetSeconds();
        mainCtrl.timer.start();
        questionNumber.setText(++round + "/20");

        scoreAwardedVisibility(false, 0);

        System.out.println("Question class = " + q.getClass());

        q = this.server.repairQuestion(q);
        game.setQuestion(q);

        //Choose which type of question it is and load the appropriate frame with its controller
        if (q.getClass().equals(Question.MostNRGQuestion.class)) {
            doMultiChoice((Question.MostNRGQuestion) q);
            if(!bombJokerUsed) bombJoker.setDisable(false);
        } else if (q.getClass().equals(Question.ChoiceEstimation.class)) {
            doChoiceEstimationQuestion((Question.ChoiceEstimation) q);
            if(!bombJokerUsed) bombJoker.setDisable(false);
        } else if (q.getClass().equals(Question.Matching.class)) {
            doMatching((Question.Matching) q);
            if(!bombJokerUsed) bombJoker.setDisable(false);
        } else if (q.getClass().equals(Question.AccurateEstimation.class)) {
            doAccurateEstimationQuestion((Question.AccurateEstimation) q);
            bombJoker.setDisable(true);
        }

    }

    /**
     * This method inserts the frame, gets time and correctness of the answer from the controller
     * Then it adds points to score accordingly, using ScoreSystem
     *
     * @throws IOException when something goes wrong with file-reading or finding
     */
    public void doMultiChoice(Question multiChoice) throws IOException {
        System.out.println("MultiChoice question has started");
        this.mainCtrl.MPstartMC(this, multiChoice);
    }

    /**
     * This method inserts the frame, gets time and correctness of the answer from the controller
     * Then it adds points to score accordingly, using ScoreSystem
     *
     * @param choiceEstimation current Estimation question
     * @throws IOException cooldownText.setText("Wait " + timeLeft + " second before sending another message");
     */
    public void doChoiceEstimationQuestion(Question.ChoiceEstimation choiceEstimation) throws IOException {
        System.out.println("Choice estimation start");
        getMainCtrl().MPstartCE(this, choiceEstimation);
    }

    /**
     * This method inserts the frame, gets time and correctness of the answer from the controller
     * Then it adds points to score accordingly, using ScoreSystem
     *
     * @param matching current Matching question
     * @throws IOException when file-reading or finding goes wrong
     */
    public void doMatching(Question.Matching matching) throws IOException {
        System.out.println("Matching start");
        getMainCtrl().MPstartMatching(this, matching);
    }

    /**
     * This method inserts the frame, gets time, distance and correctness of the answer from the controller
     * Then it adds points to score accordingly, using ScoreSystem
     *
     * @param accurateEstimation current AccurateEstimation question
     * @throws IOException
     */
    public void doAccurateEstimationQuestion(Question.AccurateEstimation accurateEstimation) throws IOException, InterruptedException {
        System.out.println("Accurate Estimation start");
        getMainCtrl().MPstartAE(this, accurateEstimation);
    }

    /**
     * Getter for question frame
     *
     * @return BorderPane of question frame
     */
    public BorderPane getQuestionFrame() {
        return questionFrame;
    }


    /** Tell the server this player sent a specific kind of emoji
     *
     * @param kind the kind of emoji sent
     */

    /**
     * This method resets the text for the countdown timer every second.
     * If the timer hit 0 seconds and the player has not answered, it calls the method
     * to move on to the next question.
     */
    public void simpleTimer() {

        resetSeconds();

        mainCtrl.timer = new Timer(1000, e -> {

            seconds--;

            //if more than 15 seconds passed, move on to the next question
            if (seconds <= 0) {

                mainCtrl.timer.stop();

            }
            counterTimer.setText(seconds + " seconds");
        });

    }

    /**
     * This method resets the seconds.
     */
    public void resetSeconds() {
        this.seconds = 16;
    }

    /**
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
        this.scoreCount.setText("SCORE: " + score);
    }

    /**
     * @param visible true iff scoreAwarded should be visible
     * @param points  the points awarded for a question
     */

    public void scoreAwardedVisibility(boolean visible, int points) {
        if (visible) {
            scoreAwarded.setVisible(true);
            scoreAwarded.setText("+" + points);
        } else {
            scoreAwarded.setVisible(false);
        }
    }

    public void sendEmoji(String kind) {
        if (!onCooldown) {
            //send emoji if not on cooldown
            server.send("/app/game/" + lobbyId + "/lobby/emoji-received", new Emoji(player, kind));
            onCooldown = true;
            //start 4 second cooldown defined in initialize
            cooldown.play();
        } else {
            //if on cooldown show cooldown text with time remaining
            int timeLeft = 4 - (int) cooldown.currentTimeProperty().get().toSeconds();
            if (timeLeft == 1) {
                cooldownText.setText("Wait " + timeLeft + " second before sending another message");
            } else {
                cooldownText.setText("Wait " + timeLeft + " seconds before sending another message");
            }
            cooldownText.setVisible(true);
        }
    }


    /**
     * Displays emoji sent or received on the screen
     *
     * @param sent the emoji sent/received
     * @throws FileNotFoundException when emoji image files not found
     */

    private void displayEmoji(Emoji sent) throws FileNotFoundException {
        String name = sent.getSender().getUserName();
        Image img = null;
        switch (sent.getEmoji()) {
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
            case "Disconnect": //we treat the disconnect message as an emoji for convenience
                img = new Image(new FileInputStream("client/src/main/resources/icons/disconnect.png"));
                break;
            case "BombJoker":
                img = new Image(new FileInputStream("client/src/main/resources/icons/bombChat.png"));
        }

        ImageView imgView = new ImageView();
        imgView.setFitWidth(25);
        imgView.setFitHeight(25);
        imgView.setImage(img);

        //adding message to the bottom of the chat by shifting all messages up by one
        Pair<String, ImageView> toAdd = new Pair<>(name, imgView);
        Stack<Pair<String, ImageView>> toStore = new Stack<>();
        for (int i = 4; i > 0; i--) {
            toStore.push(chat.getItems().get(i));
        }
        for (int i = 0; i < 4; i++) {
            chat.getItems().set(i, toStore.pop());
        }
        chat.getItems().set(4, toAdd);

        //remove message after 5 seconds
        PauseTransition pause = new PauseTransition();
        pause.setDuration(Duration.seconds(5));
        pause.setOnFinished(event -> {
            int index = chat.getItems().indexOf(toAdd);
            if (index >= 0) {
                chat.getItems().set(index, new Pair<>("", new ImageView()));
            }
        });
        pause.play();
    }

    /**
     * Trophy emoji sent
     *
     * @param mouseEvent mouse clicked
     */

    public void TrophyEmoji(MouseEvent mouseEvent) {
        sendEmoji("Trophy");
    }


    /**
     * Dead emoji sent
     *
     * @param mouseEvent mouse clicked
     */

    public void DeadEmoji(MouseEvent mouseEvent) {
        sendEmoji("Dead");
    }


    /**
     * Laugh emoji sent
     *
     * @param mouseEvent mouse clicked
     */

    public void LaughEmoji(MouseEvent mouseEvent) {
        sendEmoji("Laugh");
    }


    /**
     * Kiss emoji sent
     *
     * @param mouseEvent mouse clicked
     */

    public void KissEmoji(MouseEvent mouseEvent) {
        sendEmoji("Kiss");
    }


    /**
     * Sad emoji sent
     *
     * @param mouseEvent mouse clicked
     */

    public void SadEmoji(MouseEvent mouseEvent) {
        sendEmoji("Sad");
    }


    /**
     * Smile emoji sent
     *
     * @param mouseEvent mouse clicked
     */


    public void SmileEmoji(MouseEvent mouseEvent) {
        sendEmoji("Smile");
    }


    /**
     * Send the disconnect message to all players
     */

    public void disconnectMessage() {
        try {
            sendEmoji("Disconnect");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set visibility of cooldown text
     *
     * @param visible true iff text should be visible
     */

    public void visibleCooldown(boolean visible) {
        cooldownText.setVisible(visible);
    }

    /**
     * Helper method, removeWrongAnswer() gave errors when used in a static context, so I had to
     * provide a correct controller
     * @param controllerClass
     * @param <T>
     * @return
     */
    private <T extends Controller> T findController(Class<T> controllerClass) {
        List<Controller> controllers = mainCtrl.getControllers();
        for (Controller controller : controllers) {
            if (controller.getClass().getName().equals(controllerClass.getName())) {
                return (T) controller;
            }
        }
        return null;
    }
}
