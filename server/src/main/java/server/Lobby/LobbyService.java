package server.Lobby;

import commons.Game;
import commons.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import server.GameManagement.GameManagementService;

import java.util.List;

import static commons.Screen.LOBBY;
import static commons.Type.LOBBYUPDATE;

/**
 * Logic for the lobby.
 */

@Service
@Component
public class LobbyService {
  public Game currentLobby;
  private final GameManagementService service;
  private final SimpMessagingTemplate simpMessagingTemplate;

  /**
   * Constructor that initializes a gameManagementService and a new lobby.
   * @param service the gameManagement service injected
   //* @param simpMessagingTemplate
   */

  @Autowired
  public LobbyService(GameManagementService service,
                      SimpMessagingTemplate simpMessagingTemplate) {
    this.service = service;
    currentLobby = service.newLobby();
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  /**
   * Adds the player that entered their name in the multiplayer enter
   * name screen to a lobby.
   * @param p the player that joined
   */


  public void onJoin(Player p){
    currentLobby.getPlayers().add(p);
    System.out.println("Player added to lobby");
    refreshLobbyTable();
  }

  /**
   * Sends the updated player list to the lobby.
   */
  public void refreshLobbyTable(){
    long id = currentLobby.getId();
    currentLobby.type = LOBBYUPDATE;
    currentLobby.screen = LOBBY;
    simpMessagingTemplate.convertAndSend("/topic/game/" + id, currentLobby );
  }

  /**
   * Removes the player that left the lobby by pressing back.
   * @param p the player that joined
   */

  public void onLeave(Player p){
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
    service.makeLobbyActive(toAdd);
    currentLobby = service.newLobby();
    refreshLobbyTable();
  }

  /**
   * Checks if the desired username is available.
   * @param player The data of the player that wishes to join
   * @return true if available, false if not available
   */

  public boolean nameCheck(Player player){
    String requestedUsername = player.getUserName();
    List<Player> lobbyPlayers = currentLobby.getPlayers();
    for(Player p : lobbyPlayers){
      if(p.getUserName().equals(requestedUsername)) return false;
    }
    return true;
  }

}
