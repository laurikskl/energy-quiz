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
import commons.Player;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class MainCtrl{

    private Stage primaryStage;

    //Controllers
    private List<Controller> controllers;

    //Scenes
    private List<Scene> scenes;


    /**
     * Acts as constructor
     * @param primaryStage the primary stage
     * @param scenes List of pairs of Controller instances and roots for fxml loader
     */
    public void initialize (Stage primaryStage, List<Pair<Controller, Parent>> scenes) {
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
            this.scenes.get(5).getStylesheets().add(new File("client/src/main/resources/stylesheets/lobby.css").toURI().toURL().toExternalForm());
            this.scenes.get(6).getStylesheets().add(new File("client/src/main/resources/stylesheets/mp-game-screen.css").toURI().toURL().toExternalForm());
            this.scenes.get(7).getStylesheets().add(new File("stylesheets/how2Play.css").toURI().toURL().toExternalForm());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        showSplash();
    }

    /**
     * Internal helper method to show a scene
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
        showScene(this.scenes.get(0));}

    /**
     * Sets primaryStage's scene to the EnterNameSinglePlayer screen
     */
    public void showEnterNameSinglePlayer() {
        showScene(this.scenes.get(1));}

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
     * @param players the players for a game
     * @param player the player of the client
     */
    public void showLobbyScreen(List<Player> players, Player player) {
        showScene(this.scenes.get(5));

        //set up the lobby with the list of players
        LobbyCtrl ctrl = (LobbyCtrl) controllers.get(5);
        try {
            ctrl.createLobby(players, player);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * Sets the PlayerObj
     * @param player PlayerObj representing this player
     */
    public void startSPGame(Player player, ServerUtils server) {
        ((SPGameCtrl) this.controllers.get(4)).startGame(player, server);
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
}