package server.GameManagement;

import commons.Game;
import commons.RoundPlayer;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;

/**
 * Starts new games.
 * Gets games by their id.
 * Uses logic from the GameService layer.
 */
public class GameManagementController {

  /**
   * The gameService layer where the logic is.
   */
  private final GameManagementService gameManagementService;

  /**
   * Constructor where we inject the service layer into the controller
   * @param gameManagementService - the service used
   */

  public GameManagementController(GameManagementService gameManagementService){
    this.gameManagementService = gameManagementService;
  }

  /**
   * gets a game by its id.
   * @param id the id of the game we are getting
   * @return the game with the correct id, or a new game
   */
  public Game getGame(long id){
    return gameManagementService.getById(id);
  }

  /**
   * starts a new game.
   * @return A new game
   */
  public Game newLobby(){
    return gameManagementService.newLobby();
  }

  /**
   * Update the score of the player that sent their new score
   * @param id the id of the game the player is in
   * @param player the player that is updating their score
   */

  @MessageMapping("/game/{id}/scoreupdate")
  public void scoreUpdate(@DestinationVariable long id, RoundPlayer player){
    gameManagementService.scoreUpdate(id, player);
  }
}
