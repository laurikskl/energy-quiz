package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.*;
import javafx.animation.PauseTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.Stack;

/**
 * Controller for FXML of the multiplayer game screen
 */

public class MPGameMultiChoiceCtrl extends Controller {

    public int isCorrect;
    /**
     * FXML fields
     */
    @FXML
    private TableView<Player> scoreboard;
    @FXML
    private TableColumn<Player, String> colNameScoreboard;
    @FXML
    private TableColumn<Player, Number> colScoreScoreboard;
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
     * For the timer.
     */
    @FXML
    private Text counterTimer;
    private Timer timer;
    private int seconds;
    /**
     * Fields used in class
     */

    private boolean onCooldown;
    private PauseTransition cooldown;
    private int round;
    @FXML
    private Text questionNumber;
    private Game game;
    private ObservableList<Player> data;
    private long lobbyId;
    private Player player;
    private Activity correctActivity;
    private Question multiChoice;
    private Instant instant;
    private Long start;
    private Long finish;
    private String correctActivityName;

    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */
    @Inject
    public MPGameMultiChoiceCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    /**
     * Method that returns the application to the initial screen when the back button is pressed.
     *
     * @param actionEvent - pressing the back button triggers this function
     * @throws IOException - something went wrong with reading/writing/finding files
     */
    // TODO: Maybe it should also handle disconnecting, actually
    public void back(ActionEvent actionEvent) throws IOException {
        getMainCtrl().showSplash();
    }


    /**
     * Start a MultiChoice round in multiplayer
     * Set all the fields
     * Start the timer
     * Fetch the images from server for question
     * Display the question
     */
    public void startGame(Game game, Player player) throws IOException, InterruptedException {
        this.onCooldown = false;
        //Set the game
        this.game = game;
        //Set the lobbyId
        this.lobbyId = game.getId();
        //Set the round number
        this.round = game.getRound();
        //Set the question
        this.multiChoice = game.getQuestion();
        //Set the player
        this.player = player;
        //Set isCorrect to -1 meaning there wasn't an answer
        this.isCorrect = -1;
        //Obtaining current state of clock
        this.instant = Instant.now();
        //Current time in second from some ancient date
        this.start = instant.getEpochSecond();
        //Set correct activity
        this.correctActivity = multiChoice.getCorrect();
        //Finds the correct answer and inserts its name in the correctActivityName field
        this.correctActivityName = correctActivity.getName();

        resetSeconds();
        simpleTimer();
        questionNumber.setText(round + 1 + "/20");

        Image defaultIMG = new Image(String.valueOf(new File("client/src/main/resources/entername/MaxThePlant.png").toURI().toURL()));

        // TODO: Ask the server for the images and set them here

        image1.setImage(defaultIMG);
        image2.setImage(defaultIMG);
        image3.setImage(defaultIMG);

        answer1.setText(multiChoice.getActivities().get(0).getName());
        answer2.setText(multiChoice.getActivities().get(1).getName());
        answer3.setText(multiChoice.getActivities().get(2).getName());

        answer1.setStyle("-fx-pref-height: 450; -fx-pref-width: 250; -fx-background-radius: 20; -fx-background-color: #7CCADE; -fx-content-display: top;");
        answer2.setStyle("-fx-pref-height: 450; -fx-pref-width: 250; -fx-background-radius: 20; -fx-background-color: #7CCADE; -fx-content-display: top;");
        answer3.setStyle("-fx-pref-height: 450; -fx-pref-width: 250; -fx-background-radius: 20; -fx-background-color: #7CCADE; -fx-content-display: top;");

        refreshScoreboard();
        colNameScoreboard.setCellValueFactory(col -> new SimpleStringProperty(col.getValue().userName));
        colScoreScoreboard.setCellValueFactory(col -> new SimpleIntegerProperty(col.getValue().score));

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
        nameCol.setStyle("-fx-alignment: CENTER-RIGHT;");
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
     * This method resets the text for the countdown timer every second.
     * If the timer hit 0 seconds and the player has not answered, it calls the method
     * to move on to the next question.
     */
    public void simpleTimer() {

        resetSeconds();

        timer = new Timer(1000, e -> {

            seconds--;

            //if more than 15 seconds passed, move on to the next question
            if (seconds < 0) {

                timer.stop();

            }
            counterTimer.setText(seconds + " seconds");
        });

        timer.start();

    }

    /**
     * This method resets the seconds.
     */
    public void resetSeconds() {
        this.seconds = 16;
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
     * Set visibility of cooldown text
     *
     * @param visible true iff text should be visible
     */
    public void visibleCooldown(boolean visible) {
        cooldownText.setVisible(visible);
    }

    /**
     * Refreshes the data that scoreboard is using
     */
    public void refreshScoreboard() {
        data = FXCollections.observableList(game.getPlayers());
        scoreboard.setItems(data);
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
        button.setStyle(button.getStyle() + " -fx-background-color: #ff4f75; "); //red
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
     * Add score for the question and tell server about it (visible update question counter and score)
     *
     * @throws InterruptedException
     */
    public void handleCorrect() throws InterruptedException {
//        int addScore = ScoreSystem.calculateScore(this.getTime());
//        String username = mainCtrl.thisPlayer.getUserName();
//        int round = parentCtrl.getRound();
//
////        add in the roundplayer with the above parameters and send it to the server
//        RoundPlayer sendingObject = new RoundPlayer(username, addScore, round);
//        server.send("/app/game/" + mainCtrl.lobbyId + "/scoreupdate", sendingObject);
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
