package server.GameManagement;

import commons.Game;

public class GameManagementController {

  private final GameManagementService gameManagementService;

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
  public Game startGame(){
    return gameManagementService.startLobby();
  }
}
