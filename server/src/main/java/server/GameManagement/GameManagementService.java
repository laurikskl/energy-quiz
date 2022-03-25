package server.GameManagement;

import commons.Game;
import commons.Player;
import commons.Question;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

  /**
    * Logic behind the methods needed for gameController.
    */

  @Service
public class GameManagementService {

  public List<Game> games;
  public long generateId;

  /**
    *  Constructor for the gameService class that starts a new arrayList with games
    *  and for this launch of the server, starts to count IDs for the games up 
    *  from 0.
    */
  public GameManagementService(List<Game> games) {
    this.games = new ArrayList<>();
    this.generateId = 0;
  }

  /**
   * fetches a game by its id.
   * @param id the id of the game were looking for
   * @return returns the game with the id or if no such game exists, a new one
   */
  public Game getById(long id){
    Game game = null;

    for(Game current : games){
      if(current.getId() == id){
        game = current;
        break;
      }
    }

    if(game == null){
      game = newLobby();
    }

    return game;
  }

  /**
   * Starts a new game with a unique id.
   * @return fresh game with unique id and empty players and questions lists
   */
  public Game newLobby(){
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Question> questions = new ArrayList<>();
    Game newGame = new Game(generateId, players, questions);
    generateId++;
    games.add(newGame);
    return newGame;
  }

    /**
     * Adds the lobby to the list of games.
     * @param lobbyToAdd The lobby that just got started
     */
  public void addLobby(Game lobbyToAdd){
    games.add(lobbyToAdd);
  }
}
