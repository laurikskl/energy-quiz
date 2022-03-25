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

package client.utils;

import commons.*;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * This class is for communication between client and server
 */

public class ServerUtils {
    /**
     * Temporary comment for checkstyle.
     */

    private static final String SERVER = "http://localhost:8080/";

    private static List<Player> players;


    /**
     * This comment is a temporary fix for checkstyle.
     */

    public void getQuotesTheHardWay() throws IOException {
        var url = new URL("http://localhost:8080/api/quotes");
        var is = url.openConnection().getInputStream();
        var br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }


    /**
     * This comment is a temporary fix for checkstyle.
     */

    public List<Quote> getQuotes() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/quotes") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Quote>>() {
                });
    }


    /**
     * @return A Multichoice question from MultiChoiceController
     */
    public Question.MultiChoice getMultiChoice() {
        return (Question.MultiChoice) ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/questions/multichoice")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<Question.MultiChoice>() {
                });
    }

    public List<Player> getLeaderboard() {
        return (List<Player>) ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/leaderboard")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Player>>() {
                });
    }

    /**
     * This comment is a temporary fix for checkstyle.
     */

    public Quote addQuote(Quote quote) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/quotes") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(quote, APPLICATION_JSON), Quote.class);
    }


    /**
     * @return a question from the server by making a request to the path defined
     */

    public Question getQuestion() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/questions/next")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(Question.class);
    }


    /**
     * @param name the name of a player
     * @return the score associated with the name of the player
     */

    public Player getPlayer(String name) {
        //update list of players
        players = getAllPlayers();
        //try to find player by name and return it
        for (Player player : players) {
            if (player.getUserName().equals(name)) return player;
        }
        return null;
    }

    public List<Player> getAllPlayers() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("player").
                request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).
                get(new GenericType<List<Player>>() {
                });
    }


    /**
     * @param name the name of a player
     * @return the score associated with the name of the player
     */

    public Integer getScore(String name) {
        //If list of players hasn't been generated yet, retrieve it from PlayerController
        if (players == null) {
            players = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("player").
                    request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON).
                    get(new GenericType<List<Player>>() {
                    });
        }
        //try to find player by name and return score
        for (Player player : players) {
            if (player.getUserName().equals(name)) return (int) player.getScore();
        }
        //return null if not found
        return null;
    }


    /**
     * @param name  the name of a player
     * @param score the score associated with the name of the player
     * @return the player sent to the server
     */

    public Player setPlayer(String name, int score) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("player/setPlayer")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(new Player(name, score), APPLICATION_JSON), Player.class);
    }

    public List<Activity> getAllActivities() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/activities/getAll").
                request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).
                get(new GenericType<List<Activity>>() {
                });
    }

    public List<Activity> getActivitiesByExample(String name, Long powerConsumptionMin, Long powerConsumptionMax, String source, String imagePath) {
        ActivitySearchRequest activitySearchRequest = new ActivitySearchRequest(name, powerConsumptionMin, powerConsumptionMax, source, imagePath);

        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/activities/getByName")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(activitySearchRequest, APPLICATION_JSON) , new GenericType<List<Activity>>() {});
    }

}