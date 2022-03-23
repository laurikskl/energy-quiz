package server.GameManagement;

import commons.Game;
import commons.Player;
import commons.Question;

import java.util.ArrayList;
import java.util.List;


public class GameManagementService {

  public List<Game> games;
  public long generateId;

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
      game = startLobby();
    }

    return game;
  }

  /**
   * Starts a new game with a unique id.
   * @return fresh game with unique id and empty players and questions lists
   */
  public Game startLobby(){
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Question> questions = new ArrayList<>();
    Game newGame = new Game(generateId, players, questions);
    generateId++;
    games.add(newGame);
    return newGame;
  }
}
