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

import commons.Player;
import commons.Quote;
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
 * This comment is a temporary fix for checkstyle.
 */

public class ServerUtils {
  /**
   * Temporary comment for checkstyle.
   */

  private static final String SERVER = "http://localhost:8080/";

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
   * Method for returning all players.
   * @return
   */
  public List<Player> getPlayers(){
    return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER).path("player")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .get(new GenericType<List<Player>>() {
            });
  }
  /**
   * A method for posting a new player to the database.
   * @param player
   * @return
   */
  public Player addPlayer(Player player){
    return ClientBuilder.newClient(new ClientConfig())
            .target(SERVER).path("player")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .post(Entity.entity(player, APPLICATION_JSON), Player.class);
  }
}