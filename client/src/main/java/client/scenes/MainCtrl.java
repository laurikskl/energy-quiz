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

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
/**
 * Temporary comment for checkstyle.
 */

import java.io.File;
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
    public void initialize(Stage primaryStage, List<Pair<Controller, Parent>> scenes) {
        this.primaryStage = primaryStage;

        for (Pair<Controller, Parent> pair : scenes) {
            this.controllers.add(scenes.get(0).getKey());
            this.scenes.add(new Scene(scenes.get(0).getValue()));
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
     */

    public void showLobbyScreen() {
        showScene(this.scenes.get(5));
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