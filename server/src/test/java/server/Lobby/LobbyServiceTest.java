package server.Lobby;

import commons.Game;
import commons.Player;
import commons.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class LobbyServiceTest {

  private Player rambo;
  private Player chuckNorris;
  private Game game;
  private LobbyService lobbyService;

  /**
   * Setup for tests.
   */

  @BeforeEach
  public void setup(){
    this.rambo = new Player("rambo", 0);
    this.chuckNorris = new Player("chuckNorris", 500);
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Question> questions = new ArrayList<>();
    this.game = new Game(0, players, questions);
    //this.lobbyService = new LobbyService();
  }
  @Test
  void onJoin() {
  }

  @Test
  void refreshLobbyTable() {
  }

  @Test
  void onLeave() {
  }

  @Test
  void getLobby() {
  }

  @Test
  void startLobby() {
  }

  @Test
  void nameCheck() {
    rambo.setUserName("chuckNorris");
    game.getPlayers().add(chuckNorris);
    //Game game = lobbyService.startLobby("hey", "s");
    //assertFalse(lobbyService);
  }
}