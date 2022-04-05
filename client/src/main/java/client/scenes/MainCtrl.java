/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package client.scenes;

import client.MyFXML;
import client.MyModule;
import client.utils.ServerUtils;
import com.google.inject.Injector;
import commons.Game;
import commons.Player;
import commons.Question;
import commons.Screen;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.inject.Inject;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static com.google.inject.Guice.createInjector;
import static commons.Screen.*;

public class MainCtrl {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);
    //the client's player for multiplayer with their name
    public Player thisPlayer;
    //the lobby we are in
    public long lobbyId;
    public Timer timer;
    private Stage primaryStage;
    //Controllers
    private List<Controller> controllers;
    private Popup disconnectMessage;
    /**
     * Controller and scenes indexes.
     * 0 - splash
     * 1 - EnterNameSinglePlayer
     * 2 - EnterNameMultiPlayer
     * 3 - LeaderboardScreen
     * 4 - SPGameScreen
     * 5 - LobbyScreen
     * 6 - MPGameScreen
     * 7 - How2Play
     * 8 - MultiChoiceScreen
     * 9 - ChoiceEstimation
     * 10 - Admin
     * 11 - EndGameScreen
     * 12 - AccurateEstimationCtrl
     * 13 - MatchingCtrl
     * 14 - MPMultiChoice
     * 15 - MPChoiceEstimation
     * 16 - MPAccurateEstimation
     * 17 - MPGameMultiChoiceCtrl
     * 18 - MPLeaderboard
     */

    //Scenes
    private List<Parent> scenes;

    private Scene display;

    private ServerUtils server;
    // Current scene as an enum
    private Screen current;

    /**
     * Injects server utils.
     *
     * @param server the server utils
     */
    @Inject
    public MainCtrl(ServerUtils server) {
        this.server = server;
    }

    /**
     * Acts as constructor
     *
     * @param primaryStage the primary stage
     * @param /*scenes     List of pairs of Controller instances and roots for fxml loader
     */
    public void initialize(Stage primaryStage, List<Pair<Controller, Parent>> scenes) throws FileNotFoundException {
        this.primaryStage = primaryStage;
        this.controllers = new ArrayList<>();
        this.scenes = new ArrayList<>();

        for (int i = 0; i < scenes.size(); i++) {
            this.controllers.add(scenes.get(i).getKey());
            this.scenes.add(scenes.get(i).getValue());
        }


        //add css
        try {
            this.scenes.get(0).getStylesheets().add(new File("client/src/main/resources/stylesheets/splash.css").toURI().toURL().toExternalForm());
            this.scenes.get(1).getStylesheets().add(new File("client/src/main/resources/stylesheets/enterNameSingleplayer.css").toURI().toURL().toExternalForm());
            this.scenes.get(2).getStylesheets().add(new File("client/src/main/resources/stylesheets/enterNameSingleplayer.css").toURI().toURL().toExternalForm());
            this.scenes.get(3).getStylesheets().add(new File("client/src/main/resources/stylesheets/leaderboard.css").toURI().toURL().toExternalForm());
            this.scenes.get(4).getStylesheets().add(new File("client/src/main/resources/stylesheets/SPGame.css").toURI().toURL().toExternalForm());
            this.scenes.get(5).getStylesheets().add(new File("client/src/main/resources/stylesheets/lobby.css").toURI().toURL().toExternalForm());
            this.scenes.get(6).getStylesheets().add(new File("client/src/main/resources/stylesheets/mp-game-screen.css").toURI().toURL().toExternalForm());
            this.scenes.get(7).getStylesheets().add(new File("client/src/main/resources/stylesheets/how2Play.css").toURI().toURL().toExternalForm());
            this.scenes.get(11).getStylesheets().add(new File("client/src/main/resources/stylesheets/endGame.css").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        primaryStage.getIcons().add(new Image(new FileInputStream("client/src/main/resources/entername/MaxThePlant.png")));
        primaryStage.setTitle("Save Max The Plant");
        display = new Scene(this.scenes.get(0));
        primaryStage.setScene(display);
        primaryStage.show();
    }

    /**
     * gets the id of the current ongoing lobby and sends the player
     * to the relevant destination.
     *
     * @param player The player who is typing in their name
     */
    public void makeConnection(Player player) {
        //save this player's username in main ctrl
        this.thisPlayer = player;
        int id = server.getLobby();
        current = ENTERNAME;
        // Choose what action to take, depending on type of message
        server.registerForMessages("/topic/game/" + id, Game.class, game -> {
            System.out.println("Received game object!");
            System.out.println(game.screen + " " + game.type);
            if (!current.equals(game.screen)) {
                System.out.println("CURRENT != game.screen");
                switch (game.screen) {
                    case LOBBY:
                        System.out.println("Showing Lobby");
                        showLobbyScreen();
                        current = LOBBY;
                        break;
                    case QUESTION:
                        System.out.println("Showing MPGameScreen");
                        showMPGame();
                        current = QUESTION;
                        break;
                    case SCOREBOARD:
                        System.out.print("Showing Scoreboard");
                        //showScoreboard();
                        current = SCOREBOARD;
                        break;
                }
            }

            System.out.println("Switch moment");
            switch (game.type) {
                case LOBBYUPDATE:
                    System.out.println("Update lobby");
                    updateLobby(game);
                    break;
                case STARTMP:
                    System.out.println("Start MPGame");
                    try {
                        startMPGame(game);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case QUESTION:
                    System.out.println("Do a question");
                    try {
                        doQuestion(game.getQuestion());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        });
        server.send("/app/game/" + id + "/lobby/join", player);
    }

    /**
     * Internal helper method to show a scene
     *
     * @param scene Scene to show
     */
    private void showScene(Parent scene) {
        display.setRoot(scene);
        primaryStage.show();
    }

    /**
     * Sets primaryStage's scene to the splash screen;
     * Disconnects from the websocket if necessary;
     */
    public void showSplash() {
        this.server.disconnect();
        showScene(this.scenes.get(0));
    }

    /**
     * Sets primaryStage's scene to the EnterNameSinglePlayer screen
     */
    public void showEnterNameSinglePlayer() {
        showScene(this.scenes.get(1));
    }

    /**
     * Sets primaryStage's scene to the EnterNameMultiplayer screen
     */
    public void showEnterNameMultiPlayer() {
        showScene(this.scenes.get(2));
    }

    /**
     * Sets primaryStage's scene to the Leaderboard screen
     */
    public void showLeaderboard() {
        showScene(this.scenes.get(3));
    }

    /**
     * Sets primaryStage's scene to the SPGame screen
     */
    public void showSPGame() {
        showScene(this.scenes.get(4));
    }

    /**
     * Sets primaryStage's scene to the Lobby screen
     * <p>
     * removed the player parameter at the moment
     */
    public void showLobbyScreen() {
        Platform.runLater(() -> showScene(this.scenes.get(5)));

//        //set up the lobby with the list of players
//        LobbyCtrl ctrl = (LobbyCtrl) controllers.get(5);
//        try {
//            ctrl.createLobby(players);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Sets primaryStage's scene to the MPGame screen
     */
    public void showMPGame() {
        Platform.runLater(() -> showScene(this.scenes.get(6)));
    }

    /**
     * Sets primaryStage's scene to the How2Play screen
     */
    public void showHow2Play() {
        showScene(this.scenes.get(7));
    }

    public void setEndGame(int score) {
        ((EndGameCtrl) controllers.get(11)).initialize(score);
    }

    public void showEndGame() {
        showScene(this.scenes.get(11));
    }

    /**
     * Sets primaryStage's scene to the Admin screen
     */
    public void showAdmin() {
        showScene(this.scenes.get(10));
    }

    public void startSPGame(Player player, ServerUtils server) throws IOException, InterruptedException {
        ((SPGameCtrl) this.controllers.get(4)).startGame(player);

        //Defining a new Popup when starting the Singleplayer game,
        //setting the fxml and controller
        disconnectMessage = new Popup();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DisconnectMessage.fxml"));
        disconnectMessage.getContent().add(loader.load());
        DisconnectMessageCtrl controller = loader.getController();
        controller.setMainCtrl(this);
    }

    /**
     * Load the MultipleChoice question frame
     * Enable buttons after the question for the next question
     *
     * @param parentCtrl
     * @param multiChoice
     */
    public void startMC(Controller parentCtrl, Question multiChoice) throws MalformedURLException {
        MultiChoiceCtrl multiChoiceCtrl = (MultiChoiceCtrl) controllers.get(8);
        multiChoiceCtrl.start(parentCtrl, multiChoice);
        Platform.runLater(() -> {
            ((SPGameCtrl) parentCtrl).getQuestionFrame().setCenter(this.scenes.get(8));
        });

        multiChoiceCtrl.buttonsEnabled(true);
    }

    /**
     * Load the ChoiceEstimation question frame
     * Enable buttons after the question for the next question
     *
     * @param parentCtrl
     * @param choiceEstimation
     */
    public void startCE(Controller parentCtrl, Question choiceEstimation) throws MalformedURLException {
        ChoiceEstimationCtrl choiceEstimationCtrl = (ChoiceEstimationCtrl) controllers.get(9);
        choiceEstimationCtrl.start(parentCtrl, choiceEstimation);
        Platform.runLater(() -> {
            ((SPGameCtrl) parentCtrl).getQuestionFrame().setCenter(this.scenes.get(9));
        });
        choiceEstimationCtrl.buttonsEnabled(true);
    }

    /**
     * Load the AccurateEstimation question frame
     *
     * @param parentCtrl
     * @param accurateEstimation
     */
    public void startAE(Controller parentCtrl, Question accurateEstimation) throws MalformedURLException {
        AccurateEstimationCtrl accurateEstimationCtrl = (AccurateEstimationCtrl) controllers.get(12);
        accurateEstimationCtrl.start(parentCtrl, accurateEstimation);
        Platform.runLater(() -> {
            ((SPGameCtrl) parentCtrl).getQuestionFrame().setCenter(this.scenes.get(12));
        });
    }


    /**
     * Load the Matching question frame.
     *
     * @param parentCtrl
     * @param matching
     * @throws MalformedURLException
     */

    public void startMatching(Controller parentCtrl, Question matching) throws MalformedURLException {
        MatchingCtrl matchingCtrl = (MatchingCtrl) this.controllers.get(13);
        matchingCtrl.start(parentCtrl, matching);
        Platform.runLater(() -> {
            ((SPGameCtrl) parentCtrl).getQuestionFrame().setCenter(this.scenes.get(13));
        });
        matchingCtrl.buttonsEnabled(true);
    }

    /**
     * Method for setting the fxml of the disconnectMessage popup and displaying it
     *
     * @param spGameCtrl
     * @throws IOException
     */
    public void displayDisconnectMessage(SPGameCtrl spGameCtrl) throws IOException {
        disconnectMessage.show(primaryStage);
    }

    /**
     * Method for hiding the disconnect message
     *
     * @throws IOException
     */
    public void hideDisconnectMessage() throws IOException {
        disconnectMessage.hide();
    }

    /**
     * Sets the PlayerObj
     */
    public void startMPGame(Game game) throws IOException, InterruptedException {
        try {
            ((MPGameCtrl) this.controllers.get(6)).startGame(thisPlayer, lobbyId, game);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Run the MPGameMultipleChoice question startGame method
     * Show the screen for MPGameMultipleChoice
     * Enable buttons after the question for the next question
     */
    public void MPstartMC(Controller parentCtrl, Question multiChoice) throws IOException {
        MPMultiChoiceCtrl multiChoiceCtrl = (MPMultiChoiceCtrl) this.controllers.get(14);
        multiChoiceCtrl.start(parentCtrl, multiChoice);
        Platform.runLater(() -> {
                    ((MPGameCtrl) parentCtrl).getQuestionFrame().setCenter(this.scenes.get(14));
                }
        );
        multiChoiceCtrl.buttonsEnabled(true);
    }

    /**
     * Get the MPGameController and invoke a method doAQuestion from it
     *
     * @param q
     * @throws IOException
     * @throws InterruptedException
     */
    public void doQuestion(Question q) throws IOException, InterruptedException {
        MPGameCtrl ctrl = (MPGameCtrl) this.controllers.get(6);
        ctrl.doAQuestion(q);
    }

    /**
     * Load the ChoiceEstimation question frame
     * Enable buttons after the question for the next question
     *
     * @param parentCtrl
     * @param choiceEstimation
     */
    public void MPstartCE(Controller parentCtrl, Question choiceEstimation) throws MalformedURLException {
        ((MPChoiceEstimationCtrl) this.controllers.get(15)).start(parentCtrl, choiceEstimation);
        ((MPGameCtrl) parentCtrl).getQuestionFrame().setCenter(this.scenes.get(15));
        ((MPChoiceEstimationCtrl) this.controllers.get(15)).buttonsEnabled(true);
    }

    /**
     * Load the AccurateEstimation question frame
     *
     * @param parentCtrl
     * @param accurateEstimation
     */
    public void MPstartAE(Controller parentCtrl, Question accurateEstimation) throws MalformedURLException {
        ((MPAccurateEstimationCtrl) this.controllers.get(16)).start(parentCtrl, accurateEstimation);
        ((MPGameCtrl) parentCtrl).getQuestionFrame().setCenter(this.scenes.get(16));
    }

    /**
     * Load the AccurateEstimation question frame
     *
     * @param parentCtrl
     * @param matching
     */
    public void MPstartMatching(Controller parentCtrl, Question matching) throws MalformedURLException {
        ((MPMatchingCtrl) this.controllers.get(17)).start(parentCtrl, matching);
        ((MPGameCtrl) parentCtrl).getQuestionFrame().setCenter(this.scenes.get(17));
    }

    /**
     * Closes the primary stage to quit the application
     */
    public void close() {
        primaryStage.close();
    }

    /**
     * @return the primary stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * @return list of controllers
     */
    public List<Controller> getControllers() {
        return controllers;
    }

    /**
     * This method returns the timer.
     *
     * @return timer
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     *
     */
    public void updateLobby(Game game) {
        LobbyCtrl ctrl = (LobbyCtrl) controllers.get(5);
        try {
            ctrl.createTable(game.getPlayers());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
