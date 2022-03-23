package server.GameManagement;

import commons.Game;

public class GameManagementController {

  private final GameManagementService gameManagementService;

  public GameManagementController(GameManagementService gameManagementService){
    this.gameManagementService = gameManagementService;
  }

  public Game getGame(long id){
    return gameManagementService.getById(id);
  }
}
