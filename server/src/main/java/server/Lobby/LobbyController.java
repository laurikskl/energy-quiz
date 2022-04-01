package server.Lobby;

import commons.Emoji;
import commons.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    return lobbyService.getLobby();
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

  /**
   * Calls the same method in the service.
   * @param id of the websocket subscription
   * @param p player that wants to join
   */
  @MessageMapping("/game/{id}/lobby/leave")
  public void onLeave(@DestinationVariable long id, Player p){
    lobbyService.onLeave(p);
  }

  /**
   * Calls the same method in the service.
   * @param id of the websocket subscription
   */
  @MessageMapping("/game/{id}/lobby/start")
  public void startLobby(@DestinationVariable long id){
    lobbyService.startLobby(id);
  }

  /**
   * Called when emoji is sent to the server for a specific lobby
   * @param id of the websocket subscription
   * @param e emoji sent
   */
  @MessageMapping("/game/{id}/lobby/emoji-received")
  public void onEmoji(@DestinationVariable long id, Emoji e) {
    lobbyService.onEmoji(e, id);
  }

  /**
   * Checks if the desired username is available.
   * @param player The data of the player that wishes to join
   * @return true if available, false if not available
   */

  @PostMapping("/namecheck")
  public boolean nameCheck(@RequestBody Player player){
    return lobbyService.nameCheck(player);
  }
}
