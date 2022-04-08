//package server.Lobby;
//
//import commons.Game;
//import commons.Player;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import server.Activity.ActivityController;
//import server.Activity.ActivityService;
//import server.GameManagement.GameManagementService;
//import server.Question.QuestionService;
//import server.Question.TestActivityRepository;
//import server.database.ActivityRepository;
//
//import java.security.Provider;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class LobbyControllerTest {
//  private LobbyService lobbyService;
//  private LobbyController lobbyController;
//  private SimpMessagingTemplate simpMessagingTemplate;
//  private Player player;
//  private Player player2;
//  private Game lobby;
//
//  @Autowired
//  @BeforeEach
//  void initialize(){
////    this.player = new Player("Ronaldo", 420);
////    this.player2 = new Player("KKKLEADERXXX", 420);
////    this.simpMessagingTemplate = null;
////    ActivityRepository repo = new TestActivityRepository();
////    ActivityService activityService = new ActivityService(repo);
////    ActivityController activityController = new ActivityController(activityService);
////    QuestionService questionService = new QuestionService(repo, activityController);
////    GameManagementService gameService = new GameManagementService(questionService, simpMessagingTemplate);
////    this.lobbyService = new LobbyService(gameService, simpMessagingTemplate);
////    this.lobbyController = new LobbyController(lobbyService);
//  }
//
//  @Test
//  void getLobbyID() {
//
//  }
//
//  @Test
//  void onJoin() {
////    lobbyService.startLobby("lol", "lol");
////    assertNotNull("llol");
//  }
//
//  @Test
//  void onLeave() {
//  }
//
//  @Test
//  void startLobby() {
//    //lobbyController.startLobby(0, "s");
//
//  }
//
//  @Test
//  void onEmoji() {
//  }
//
//  @Test
//  void nameCheck() {
//  }
//}