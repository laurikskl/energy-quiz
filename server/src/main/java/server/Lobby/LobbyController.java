package server.Lobby;

import commons.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the lobbies
 */
@RestController
@RequestMapping("api/lobby")
public class LobbyController {

  private final LobbyService lobbyService;

  /**
   * Instantiates and injects the lobby service.
   * @param lobbyService the service.
   */
  @Autowired
  public LobbyController(LobbyService lobbyService) {
    this.lobbyService = lobbyService;
  }

  /**
   * Gets a lobby by its ID.
   * @return A game with the correct ID.
   */
  @GetMapping("/getid")
  public long getLobbyID(){
    long id = lobbyService.getLobby();
    return id;
  }

  /**
   * Calls the same method in the service.
   * @param id of the websocket subscription
   * @param p player that wants to join
   */
  @MessageMapping("/game/{id}/lobby/join")
  public void onJoin(@DestinationVariable long id, Player p){
    lobbyService.onJoin(p);
  }
}
