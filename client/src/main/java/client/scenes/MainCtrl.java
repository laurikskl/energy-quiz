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

import client.utils.ServerUtils;
import commons.Game;
import commons.Player;
import commons.Question;
import commons.Screen;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static commons.Screen.ENTERNAME;
import static commons.Screen.LOBBY;

public class MainCtrl {

    private Stage primaryStage;

    //Controllers
    private List<Controller> controllers;

    /**
     * Controller and scenes indexes.
     * 0 - Splash
     * 1 - EnterNameSinglePlayer
     * 2 - EnterNameMultiPlayer
     * 3 - Leaderboard
     * 4 - SPGame
     * 5 - Lobby
     * 6 - MPGame
     * 7 - How2Play
     * 8 - MultiChoice
     * 9 - ChoiceEstimation
     * 10 - Admin
     */

    //Scenes
    private List<Scene> scenes;

    private ServerUtils server;

    //the client's player for multiplayer with their name
    public Player thisPlayer;

    // Current scene as an enum
    private Screen current;

    //the lobby we are in
    public long lobbyId;

    /**
     * Injects server utils.
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
     * @param scenes       List of pairs of Controller instances and roots for fxml loader
     */
    public void initialize(Stage primaryStage, List<Pair<Controller, Parent>> scenes) throws FileNotFoundException {
        this.primaryStage = primaryStage;
        this.controllers = new ArrayList<>();
        this.scenes = new ArrayList<>();

        for (int i = 0; i < scenes.size(); i++) {
            this.controllers.add(scenes.get(i).getKey());
            this.scenes.add(new Scene(scenes.get(i).getValue()));
        }

        //add css
        try {
            this.scenes.get(0).getStylesheets().add(new File("client/src/main/resources/stylesheets/splash.css").toURI().toURL().toExternalForm());
            this.scenes.get(1).getStylesheets().add(new File("client/src/main/resources/stylesheets/enterNameSingleplayer.css").toURI().toURL().toExternalForm());
            this.scenes.get(2).getStylesheets().add(new File("client/src/main/resources/stylesheets/enterNameSingleplayer.css").toURI().toURL().toExternalForm());
            this.scenes.get(4).getStylesheets().add(new File("client/src/main/resources/stylesheets/SPGame.css").toURI().toURL().toExternalForm());
            this.scenes.get(5).getStylesheets().add(new File("client/src/main/resources/stylesheets/lobby.css").toURI().toURL().toExternalForm());
            this.scenes.get(6).getStylesheets().add(new File("client/src/main/resources/stylesheets/mp-game-screen.css").toURI().toURL().toExternalForm());
            this.scenes.get(7).getStylesheets().add(new File("stylesheets/how2Play.css").toURI().toURL().toExternalForm());
            this.scenes.get(8).getStylesheets().add(new File("stylesheets/endGame.css").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        primaryStage.getIcons().add(new Image(new FileInputStream("client/src/main/resources/entername/MaxThePlant.png")));
        primaryStage.setTitle("Save Max The Plant");
        showSplash();
    }

    /**
     * gets the id of the current ongoing lobby and sends the player
     * to the relevant destination.
     * @param player The player who is typing in their name
     */
    public void makeConnection(Player player){
        //save this player's username in main ctrl
        this.thisPlayer = player;
        this.lobbyId = server.getLobby();
        current = ENTERNAME;
        // Choose what action to take, depending on type of message
        server.registerForMessages("/topic/game/"+ lobbyId, Game.class, game -> {
            if(current != game.screen) {
                switch (game.screen) {
                    case LOBBY:
                        showLobbyScreen();
                        current = LOBBY;
                        break;
                    case QUESTION:

                }
            }
            switch(game.type){
                case LOBBYUPDATE:
                    updateLobby(game);
                    break;
                case STARTMP:
                    startMPGame();
                    break;
            }
        });
        server.send("/app/game/" + lobbyId + "/lobby/join", player);

    }

    /**
     * Internal helper method to show a scene
     *
     * @param scene Scene to show
     */
    private void showScene(Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Sets primaryStage's scene to the splash screen
     */
    public void showSplash() {
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
     *
     *  removed the player parameter at the moment
     */
    public void showLobbyScreen() {
        Platform.runLater(() -> showScene(this.scenes.get(5))); ;

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
        showScene(this.scenes.get(6));
    }

    /**
     * Sets primaryStage's scene to the How2Play screen
     */
    public void showHow2Play() {
        showScene(this.scenes.get(7));
    }

    public void showEndGame(){
        showScene(this.scenes.get(8));
    }

    /**
     * Sets primaryStage's scene to the Admin screen
     */
    public void showAdmin() {
        showScene(this.scenes.get(10));
    }

    public void startSPGame(Player player, ServerUtils server) throws IOException, InterruptedException {
        ((SPGameCtrl) this.controllers.get(4)).startGame(player);
    }

    /**
     * Load the MultipleChoice question frame
     * Enable buttons after the question for the next question
     * @param parentCtrl
     * @param multiChoice
     */
    public void startMC(Controller parentCtrl, Question multiChoice) throws MalformedURLException {
        MultiChoiceCtrl multiChoiceCtrl = (MultiChoiceCtrl) this.controllers.get(8);
        multiChoiceCtrl.start(parentCtrl, multiChoice);
        ((SPGameCtrl) parentCtrl).getQuestionFrame().setCenter(this.scenes.get(8).getRoot());
        multiChoiceCtrl.buttonsEnabled(true);
    }

    /**
     * Load the ChoiceEstimation question frame
     * Enable buttons after the question for the next question
     * @param parentCtrl
     * @param choiceEstimation
     */
    public void startCE(Controller parentCtrl, Question choiceEstimation) throws MalformedURLException {
        ((ChoiceEstimationCtrl) this.controllers.get(9)).start(parentCtrl, choiceEstimation);
        ((SPGameCtrl) parentCtrl).getQuestionFrame().setCenter(this.scenes.get(9).getRoot());
        ((ChoiceEstimationCtrl) this.controllers.get(9)).buttonsEnabled(true);
    }

    /**
     * Load the AccurateEstimation question frame
     * @param parentCtrl
     * @param accurateEstimation
     */
    public void startAE(Controller parentCtrl, Question accurateEstimation) throws MalformedURLException{
        ((AccurateEstimationCtrl) this.controllers.get(11)).start(parentCtrl, accurateEstimation);
        ((SPGameCtrl) parentCtrl).getQuestionFrame().setCenter(this.scenes.get(11).getRoot());
    }


    /**
     * Sets the PlayerObj
     *
     */
    public void startMPGame() {
        ((MPGameCtrl) this.controllers.get(6)).startGame(thisPlayer, lobbyId);
    }

    /**
     * Load the MultipleChoice question frame
     * Enable buttons after the question for the next question
     * @param parentCtrl
     * @param multiChoice
     */
    public void MPstartMC(Controller parentCtrl, Question multiChoice) throws MalformedURLException {
        MPMultiChoiceCtrl multiChoiceCtrl = (MPMultiChoiceCtrl) this.controllers.get(8);
        multiChoiceCtrl.start(parentCtrl, multiChoice);
        ((MPGameCtrl) parentCtrl).getQuestionFrame().setCenter(this.scenes.get(8).getRoot());
        multiChoiceCtrl.buttonsEnabled(true);
    }

    public void doQuestion(Question q) throws IOException, InterruptedException {
        MPGameCtrl ctrl = (MPGameCtrl) this.controllers.get(6);
        ctrl.doAQuestion(q);
    }

    /**
     * Load the ChoiceEstimation question frame
     * Enable buttons after the question for the next question
     * @param parentCtrl
     * @param choiceEstimation
     */
    public void MPstartCE(Controller parentCtrl, Question choiceEstimation) throws MalformedURLException {
        ((MPChoiceEstimationCtrl) this.controllers.get(9)).start(parentCtrl, choiceEstimation);
        ((MPGameCtrl) parentCtrl).getQuestionFrame().setCenter(this.scenes.get(9).getRoot());
        ((MPChoiceEstimationCtrl) this.controllers.get(9)).buttonsEnabled(true);
    }

    /**
     * Load the AccurateEstimation question frame
     * @param parentCtrl
     * @param accurateEstimation
     */
    public void MPstartAE(Controller parentCtrl, Question accurateEstimation) throws MalformedURLException{
        ((MPAccurateEstimationCtrl) this.controllers.get(11)).start(parentCtrl, accurateEstimation);
        ((MPGameCtrl) parentCtrl).getQuestionFrame().setCenter(this.scenes.get(11).getRoot());
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
     *
     */
    public void updateLobby(Game game){
        LobbyCtrl ctrl = (LobbyCtrl) controllers.get(5);
        try {
            ctrl.createTable(game.getPlayers());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}