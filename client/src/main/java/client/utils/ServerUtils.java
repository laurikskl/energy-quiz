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
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * This class is for communication between client and server
 */
public class ServerUtils {

    /**
     * SERVER is the server address
     * WSSERVER is the server address for the websockets
     * STOMPSESSION is the StompSession used by the websockets
     * players is the list of players for a game
     */

    private static String SERVER = "http://localhost:8080/";
    private static String WSSERVER = "ws://localhost:8080/";
    private static StompSession STOMPSESSION;

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
                .get(new GenericType<>() {
                });
    }

    /**
     * @param serverAddress Address to connect to.
     * @return If we can connect to the Server Address.
     */
    public boolean setServerAddress(String serverAddress) {
        String server = SERVER;
        String wsServer = WSSERVER;
        SERVER = "http://" + serverAddress + "/";
        WSSERVER = "ws://" + serverAddress + "/";

        //Test the connection
        boolean connected = checkServer();
        if (!connected) {
            //Reset Server Address
            ServerUtils.SERVER = server;
            ServerUtils.WSSERVER = wsServer;
        }

        return connected;
    }

    /**
     * @return if the server is running
     */
    public boolean checkServer() {
        Boolean bool = false;
        try {
            bool = ClientBuilder.newClient(new ClientConfig()) //
                    .target(SERVER).path("api/admin/running") //
                    .request(APPLICATION_JSON) //
                    .accept(APPLICATION_JSON) //
                    .get(new GenericType<Boolean>() {
                    });
        } catch (ProcessingException e) {
            e.printStackTrace();
        }

        return bool;
    }


    /**
     * @return the leaderboard from the server
     */

    public List<Player> getLeaderboard() {
        return (List<Player>) ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/api/leaderboard/")
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
     * @return 20 questions from the server by making a request to the path defined
     */

    public List<Question> getQuestions() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/questions/next")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Question>>() {
                });
    }

    /**
     * Called when a player disconnects from a lobby
     *
     * @param socket the socket associated with the lobby
     * @param player the player that disconnected
     */

    /**
     public Pair<WebSocket, Player> disconnected(WebSocket socket, Player player) {
     return ClientBuilder.newClient(new ClientConfig())
     .target(SERVER).path("player/disconnect")
     .request(APPLICATION_JSON)
     .accept(APPLICATION_JSON)
     .post(Entity.entity(new Pair<WebSocket, Player>(socket, player), APPLICATION_JSON), Pair.class);
     }
     */

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


    /**
     * @return return the list of al players in the database
     */

    public List<Player> getAllPlayers() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("player").
                request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).
                get(new GenericType<>() {
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
                    get(new GenericType<>() {
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

    /**
     * Register for messages on the specified address
     * @param destination the address you subscribe on
     * @param type the type of object you wish to receive here
     * @param consumer the object that will be received from the server
     * @param <T> the type of the object
     */

    public <T> void registerForMessages(String destination,Class<T> type, Consumer<T> consumer){
        STOMPSESSION.subscribe(destination, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }
            @SuppressWarnings("unchecked")
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((T) payload);
            }
        });
    }

    /**
     * Unsubscribe from the websocket session and set STOMPSESSION to null, if it exists.
     */

    public void disconnect(){
        if (STOMPSESSION != null) {
            try {
                STOMPSESSION.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            STOMPSESSION = null;
        }
    }

    /**
     * Connect to a websocket.
     */

    public boolean connect(){
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);
        stomp.setMessageConverter((new MappingJackson2MessageConverter()));

        boolean bool = false;
        try {
            STOMPSESSION = stomp.connect(WSSERVER + "websocket", new StompSessionHandlerAdapter() {}).get();
            bool = true;
        }
        catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        catch(ExecutionException e) {
                e.printStackTrace();
        }
        return bool;
    }

    /**
     * @return STOMPSESSION
     */
    public StompSession getStompSession() {
        return ServerUtils.STOMPSESSION;
    }

    /**
     * Send to a specific destination on the server side.
     * @param destination the URL
     * @param o the object that will be sent
     */

    public void send(String destination, Object o){
        STOMPSESSION.send(destination, o);
    }

    /**
     * Gets the id of the current lobby.
     * @return id of the lobby
     */

    public int getLobby() {
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER).path("api/lobby/getid")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(new GenericType<>() {
            });
    }

    /**
     * get all activities
     * @return all activities
     */
    public List<Activity> getAllActivities() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/admin/getAll").
                request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).
                get(new GenericType<List<Activity>>() {
                });
    }

    /**
     * Select and return all Activities that meet the criteria from the database
     * @param name if this is a substring of the property path "name", select that activity
     * @param powerConsumptionMin minimum of the powerConsumption range
     * @param powerConsumptionMax maximum of the powerConsumption range
     * @param source if this is a substring of the property path "source", select that activity
     * @return a list of selected Activities
     */
    public List<Activity> getActivitiesByExample(String id, String name, Long powerConsumptionMin, Long powerConsumptionMax, String source) {
        ActivitySearchRequest activitySearchRequest = new ActivitySearchRequest(id, name, powerConsumptionMin, powerConsumptionMax, source);

        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/admin/getByExample")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(activitySearchRequest, APPLICATION_JSON), new GenericType<List<Activity>>() {});
    }

    /**
     * Add activities from an Activity Bank with the choice to retain the old Activities or to override them.
     * @param activityBank Class containing a list of activities to add and a boolean if the previous activities should be deleted
     * @return the amount of Activities added
     */
    public Integer addBank(ActivityBank activityBank) {

        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/admin/addBank")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(activityBank, APPLICATION_JSON), new GenericType<Integer>() {});
    }

    /**
     * Remove activity by ID
     * @param ID
     * @return true if removing, false otherwise
     */
    public Boolean removeById(String ID) {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/admin/removeById").
                    request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON).
                    post(Entity.entity(ID, APPLICATION_JSON), new GenericType<Boolean>() {});
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Restart the server
     * @return true if restarting, false otherwise
     */
    public Boolean restart() {
        try {
            return ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/admin/restart").
                    request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON).
                    get(new GenericType<Boolean>() {
                    });
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if the name is available in the current lobby.
     * @param player the player that wishes to join
     * @return true if the name is available, false if not
     */

    public Boolean nameCheck(Player player) {
        return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER).path("api/lobby/namecheck")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .post(Entity.entity(player, APPLICATION_JSON), new GenericType<>() {
            });
    }

    /**
     * reload activities inside a question from the database by id
     * @param question question to repair
     * @return repaired question
     */
    public Question repairQuestion(Question question) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/questions/repairQuestion")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(question, APPLICATION_JSON),
                        new GenericType<Question>() {});
    }
}