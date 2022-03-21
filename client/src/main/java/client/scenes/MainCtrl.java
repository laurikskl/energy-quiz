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

import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Temporary comment for checkstyle.
 */

import java.io.File;

public class MainCtrl{

    private Stage primaryStage;

    private SplashCtrl splashCtrl;
    private How2PlayCtrl how2PlayCtrl;

    private Scene splash;
    private Scene how2Play;

    /**
     * Acts as constructor
     * @param primaryStage the primary stage
     * @param splashCtrl pair of SplashCtrl instance and root for fxml loader
     */
    public void initialize(Stage primaryStage, Pair<SplashCtrl, Parent> splashCtrl, Pair<How2PlayCtrl, Parent> how2PlayCtrl) {
        this.primaryStage = primaryStage;
        this.splashCtrl = splashCtrl.getKey();
        this.splash = new Scene(splashCtrl.getValue());

        this.how2PlayCtrl = how2PlayCtrl.getKey();
        this.how2Play = new Scene(how2PlayCtrl.getValue());

        showSplash();
        primaryStage.show();
    }

    /**
     * Sets the current stage's scene to the splash screen and adds the css to it
     * Should probably set the path to be non-relative but that's a problem for later
     */

    public void showSplash() {
        String sheet = new File("client/src/main/resources/main/splash.css").toURI().toString();
        splash.getStylesheets().add(sheet);
        primaryStage.setScene(splash);
    }


    /**
     * Sets the current stage's scene to the How2Play screen and adds the css to it
     */
    public void showHow2Play() {
        String sheet = Objects.requireNonNull(getClass().getResource("/stylesheets/How2Play.css")).toExternalForm();
        how2Play.getStylesheets().add(sheet);
        primaryStage.setScene(how2Play);
    }

    /**
     * Closes the primary stage to quit the application
     */

    public void close() {
        primaryStage.close();
    }


    /**
     * @param stage the primary stage
     */

    public void setPrimaryStage(Stage stage){
        this.primaryStage = stage;
    }


    /**
     * @return the primary stage
     */

    public Stage getPrimaryStage() {
        return primaryStage;
    }


    /**
     * @return controller for splash scene
     */

    public SplashCtrl getSplashCtrl() {
        return splashCtrl;
    }


    /**
     * @param splashCtrl controller for splash scene
     */

    public void setSplashCtrl(SplashCtrl splashCtrl) {
        this.splashCtrl = splashCtrl;
    }


    /**
     * @return the splash scene
     */

    public Scene getSplash() {
        return splash;
    }


    /**
     * @param splash the splash scene
     */

    public void setSplash(Scene splash) {
        this.splash = splash;
    }
}