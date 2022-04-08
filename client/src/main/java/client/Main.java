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

import client.scenes.*;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static com.google.inject.Guice.createInjector;

/**
 * The main class of the client
 */

public class Main extends Application {

    /**
     * INJECTOR is an injector created from myModule
     * FXML is an instance of a custom FXML class
     */

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);


    /**
     * Run to start the client
     * launch() calls start method
     *
     * @param args arguments for main method
     * @throws URISyntaxException can throw this exception
     * @throws IOException        can throw this exception
     */

    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }


    /**
     * This method is called by launch() in main
     * Creates and instance of the splash and main controller
     * Initializes the main controller with the primary stage and the splash controller
     *
     * @param primaryStage the main stage we will be displaying our scenes in
     * @throws IOException can throw this exception
     */

    @Override
    public void start(Stage primaryStage) throws IOException {
        ArrayList<Pair<Controller, Parent>> scenes = new ArrayList<>();

        //0
        scenes.add(FXML.load(SplashCtrl.class, "client", "scenes", "splash.fxml"));
        //1
        scenes.add(FXML.load(EnterNameSinglePlayerCtrl.class, "client", "scenes", "EnterNameSinglePlayer.fxml"));
        //2
        scenes.add(FXML.load(EnterNameMultiPlayerCtrl.class, "client", "scenes", "EnterNameMultiPlayer.fxml"));
        //3
        scenes.add(FXML.load(LeaderboardCtrl.class, "client", "scenes", "LeaderboardScreen.fxml"));
        //4
        scenes.add(FXML.load(SPGameCtrl.class, "client", "scenes", "SPGameScreen.fxml"));
        //5
        scenes.add(FXML.load(LobbyCtrl.class, "client", "scenes", "LobbyScreen.fxml"));
        //6
        scenes.add(FXML.load(MPGameCtrl.class, "client", "scenes", "MPGameScreen.fxml"));
        //7
        scenes.add(FXML.load(How2PlayCtrl.class, "client", "scenes", "How2Play.fxml"));
        //8
        scenes.add(FXML.load(MultiChoiceCtrl.class, "client", "scenes", "MultiChoiceScreen.fxml"));
        //9
        scenes.add(FXML.load(ChoiceEstimationCtrl.class, "client", "scenes", "ChoiceEstimation.fxml"));
        //10
        scenes.add(FXML.load(AdminCtrl.class, "client", "scenes", "Admin.fxml"));
        //11
        scenes.add(FXML.load(EndGameCtrl.class, "client", "scenes", "EndGameScreen.fxml"));
        //12
        scenes.add(FXML.load(AccurateEstimationCtrl.class,"client", "scenes", "AccurateEstimation.fxml" ));
        //13
        scenes.add(FXML.load(MatchingCtrl.class,"client", "scenes", "Matching.fxml" ));
        //14
        scenes.add(FXML.load(MPMultiChoiceCtrl.class, "client", "scenes", "MPMultiChoiceScreen.fxml"));
        //15
        scenes.add(FXML.load(MPChoiceEstimationCtrl.class, "client", "scenes", "MPChoiceEstimation.fxml"));
        //16
        scenes.add(FXML.load(MPAccurateEstimationCtrl.class, "client", "scenes", "MPAccurateEstimation.fxml"));
        //17
        scenes.add(FXML.load(MPMatchingCtrl.class, "client", "scenes", "MPMatching.fxml"));
        //18
        scenes.add(FXML.load(MPLeaderboardCtrl.class, "client", "scenes", "MPLeaderboard.fxml"));
        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
        mainCtrl.initialize(primaryStage, scenes);
    }

}
