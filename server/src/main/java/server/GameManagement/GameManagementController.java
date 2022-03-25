package server.GameManagement;

import commons.Game;
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
}
