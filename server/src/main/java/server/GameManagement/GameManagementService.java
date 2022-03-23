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

  public Game startLobby(){
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Question> questions = new ArrayList<>();
    Game newGame = new Game(generateId, players, questions);
    generateId++;
    return newGame;
  }
}
