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

/**
 * This comment is a temporary fix for checkstyle.
 */
import java.util.Objects;

public class MainCtrl{

    private Stage primaryStage;
    private Controller currentCtrl;
    private Scene scene;

    /**
     * Acts as constructor
     * @param primaryStage the primary stage
     * @param current pair of controller instance and root for fxml loader
     */
    public void initialize(Stage primaryStage, Pair<Controller, Parent> current) {
        this.primaryStage = primaryStage;
        this.currentCtrl = current.getKey();
        this.scene = new Scene(current.getValue());
        showSplash();
        primaryStage.show();
    }

    /**
     * Sets the current stage's scene to the splash screen and adds the css to it
     * Should probably set the path to be non-relative but that's a problem for later
     */
    public void showSplash() {
        String sheet = Objects.requireNonNull(getClass().getResource("../../../../resources/main/main/splash.css")).toExternalForm();
        scene.getStylesheets().add(sheet);
        primaryStage.setScene(scene);
    }

    /**
     * Closes the primary stage to quit the application
     */
    public void close() {
        primaryStage.close();
    }

    public void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Controller getSplashCtrl() {
        return currentCtrl;
    }

    public void setCtrl(Controller controller) {
        this.currentCtrl = controller;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}