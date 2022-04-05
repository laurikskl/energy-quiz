package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Emoji;
import commons.Player;
import javafx.animation.PauseTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    /**
     * Fields used in class
     */

    private boolean onCooldown;
    private PauseTransition cooldown;
    private Player player;
    private long lobbyId;


    /**
     * @param server   reference to an instance of ServerUtils
     * @param mainCtrl reference to an instance of mainCtrl
     */

    @Inject
    public MPGameCtrl(ServerUtils server, MainCtrl mainCtrl) {
        super(server, mainCtrl);
    }


    /**
     * Method that returns the application to the initial screen when the back button is pressed.
     *
     * @param actionEvent - pressing the back button triggers this function
     * @throws IOException - something went wrong with reading/writing/finding files
     */

    public void back(ActionEvent actionEvent) throws IOException {
        getMainCtrl().showSplash();
    }


    /** Called when starting a game
     * THIS IS NOT USED YET!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * @param player the player of this client
     */

    public void startGame(Player player, long lobbyId) {
        this.lobbyId = lobbyId;
        this.onCooldown = false;
        this.player = player;

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
        for(int i = 0; i < 5; i++) {
            chat.getItems().add(new Pair<>("", empty));
        }
    }


    /** Tell the server this player sent a specific kind of emoji
     *
     * @param kind the kind of emoji sent
     */

    public void sendEmoji(String kind) {
        if(!onCooldown) {
            //send emoji if not on cooldown
            server.send("/app/game/" + lobbyId + "/lobby/emoji-received", new Emoji(player, kind));
            onCooldown = true;
            //start 4 second cooldown defined in initialize
            cooldown.play();
        } else {
            //if on cooldown show cooldown text with time remaining
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
