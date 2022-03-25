package server.Lobby;

import commons.Game;
import commons.Player;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import server.GameManagement.GameManagementService;

import java.util.List;

/**
 * Logic for the lobby.
 */
@Service
public class LobbyService {

  public Game currentLobby;
  private final GameManagementService service;
  private final SimpMessagingTemplate simpMessagingTemplate;

  /**
   * Constructor that initializes a gameManagementService and a new lobby.
   * @param service the gameManagement service injected
   * @param simpMessagingTemplate
   */

  public LobbyService(GameManagementService service,
                      SimpMessagingTemplate simpMessagingTemplate) {
    this.service = service;
    currentLobby = service.newLobby();
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  /**
   * Adds the player that entered their name in the multiplayer enter
   * name screen to a lobby.
   * @param dest the destination in the message mapping
   * @param p the player that joined
   */
  @MessageMapping("/game/{id}/lobby/join")
  public void onJoin(String dest, Player p){
    currentLobby.getPlayers().add(p);
    refreshLobbyTable();
  }

  /**
   * Sends the updated player list to the lobby.
   */
  public void refreshLobbyTable(){
    long id = currentLobby.getId();
    List<Player> players = currentLobby.getPlayers();
    simpMessagingTemplate.convertAndSend("/topic/game/" + id + "/table", players );
  }

  /**
   * Removes the player that left the lobby by pressing back.
   * @param dest the destination in the message mapping
   * @param p the player that joined
   */
  @MessageMapping("/game/{id}/lobby/leave")
  public void onLeave(String dest, Player p){
    currentLobby.getPlayers().remove(p);
    refreshLobbyTable();
  }

  /**
   * Gets the id of the current game.
   * @return the id as a long
   */
  public long getLobby(){
    long id = currentLobby.getId();
    return id;
  }

  /**
   * Adds the lobby to the game management service list of active games.
   */
  @MessageMapping("/game/{id}/lobby/start")
  public void startLobby(String dest, String msg){
    Game toAdd = currentLobby;
    service.addLobby(toAdd);
    currentLobby = service.newLobby();
    refreshLobbyTable();
  }

}
