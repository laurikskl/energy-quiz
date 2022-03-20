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

package client;

import client.scenes.Controller;
import client.scenes.MainCtrl;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.google.inject.Guice.createInjector;

public class Main extends Application {

  private static final Injector INJECTOR = createInjector(new MyModule());
  private static final MyFXML FXML = new MyFXML(INJECTOR);

    /**
     * Run to start the client
     * launch() calls start method
     * @param args arguments for main method
     * @throws URISyntaxException can throw this exception
     * @throws IOException can throw this exception
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    /**
     * This method is called by launch() in main
     * Creates and instance of the splash and main controller
     * Initializes the main controller with the primary stage and the splash controller
     * @param primaryStage the main stage we will be displaying our scenes in
     * @throws IOException can throw this exception
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        var splash = FXML.load(Controller.class, "client", "scenes", "splash.fxml");
        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        mainCtrl.initialize(primaryStage, splash);
    }
}