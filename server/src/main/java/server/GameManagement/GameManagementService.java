package server.GameManagement;

import commons.Game;
import commons.Player;
import commons.Question;
import commons.RoundPlayer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Logic behind the methods needed for gameController.
 */

@Service
public class GameManagementService {

  public List<Game> games;
  public int generateId;

  /**
   * Constructor for the gameService class that starts a new arrayList with games
   * and for this launch of the server, starts to count IDs for the games up
   * from 0.
   */

  public GameManagementService(List<Game> games) {
    this.games = new ArrayList<>();
    this.generateId = 0;
  }

  /**
   * fetches a game by its id.
   *
   * @param id the id of the game were looking for
   * @return returns the game with the id or if no such game exists, a new one
   */

  public Game getById(long id) {
    Game game = null;

    for (Game current : games) {
      if (current.getId() == id) {
        game = current;
        break;
      }
    }

    if (game == null) {
      throw new IllegalStateException();
    }

    return game;
  }

  /**
   * Starts a new game with a unique id.
   *
   * @return fresh game with unique id and empty players and questions lists
   */

  public Game newLobby() {
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Question> questions = new ArrayList<>();
    Game newGame = new Game(generateId, players, questions);
    generateId++;
    return newGame;
  }

  /**
   * Adds the lobby to the list of games.
   *
   * @param lobbyToAdd The lobby that just got started
   */

  public void makeLobbyActive(Game lobbyToAdd) {
    games.add(lobbyToAdd);
  }

  /**
   * gets the id of the next game that will be created.
   *
   * @return current id
   */

  public long getGenerateId() {
    return generateId;
  }

  /**
   * Get the list of all active games.
   * @return all active games
   */
  public List<Game> getGames() {
    return games;
  }

  /**
   * Update the score of the player that sent their new score
   * @param id the id of the game the player is in
   * @param roundPlayer the player that is updating their score
   */
  public void scoreUpdate(long id, RoundPlayer roundPlayer) {
    Game game = this.games.get((int) id);
    // the amount of points the player wants to add
    int score = roundPlayer.getScore();

    // if the round the player is sending the score from doesn't match
    // the current round of the game then we don't give the player
    // points
    if(game.getRound() != roundPlayer.getRound()) return;

    // we look through the players in the game to find the one
    // that wants to update their score
    for(Player player : game.getPlayers()){
      if(player.getUserName().equals(roundPlayer.getUserName())){
        //the score of the player before adding to it
        int currentScore = player.getScore();
        player.setScore((score + currentScore));
      }
    }
  }
}
