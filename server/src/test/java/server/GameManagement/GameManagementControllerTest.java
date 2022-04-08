//package server.GameManagement;
//
//import commons.Game;
//import commons.Player;
//import commons.Question;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import server.Question.QuestionService;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class GameManagementControllerTest {
//
//    /**
//     * fields for setup
//     */
//    private QuestionService questionService;
//    private GameManagementController controller;
//    private Game game;
//
//    /**
//     * Setup before each method
//     */
//
//    @BeforeEach
//    void setup() {
//        controller = new GameManagementController(
//                new GameManagementService(questionService, simpMessagingTemplate));
//        ArrayList<Player> players = new ArrayList<>();
//        ArrayList<Question> questions = new ArrayList<>();
//        long generateId = 0;
//        game = new Game(generateId, players, questions);
//    }
//
//    /**
//     * Make sure instance of QuestionController is created
//     */
//
//    @Test
//    void constructorTest() {
//        assertNotNull(controller);
//    }
//
//    /**
//     * Makes a new lobby.
//     */
//
//    @Test
//    void newLobby() {
//        assertTrue(controller.newLobby().equals(game));
//    }
//
//    /**
//     * Assert that it throws exception if no such id.
//     */
//
//    @Test
//    void getGame() {
//        assertThrows(IllegalStateException.class, () -> controller.getGame(20));
//    }
//
//
//}