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
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class GameManagementServiceTest {
//
//    private QuestionService questionService;
//    private GameManagementService service;
//    private Game game;
//    private List<Game> games;
//
//    /**
//     * Setup before each test.
//     */
//
//    @BeforeEach
//    void setup() {
//        this.games = new ArrayList<>();
//        this.service = new GameManagementService(questionService, simpMessagingTemplate);
//        ArrayList<Player> players = new ArrayList<>();
//        ArrayList<Question> questions = new ArrayList<>();
//        long generateId = 0;
//        game = new Game(generateId, players, questions);
//    }
//
//    /**
//     * Make sure the service is created.
//     * Make sure the first ID is 0.
//     */
//
//    @Test
//    void constructorTest() {
//        assertNotNull(service);
//        assertEquals(0, service.getGenerateId());
//    }
//
//    /**
//     * Create a new game.
//     * This one should be with an id of 0
//     * since it is the first one.
//     */
//
//    @Test
//    void newLobby() {
//        Game game2 = service.newLobby();
//        assertTrue(game.equals(game2));
//    }
//
//    /**
//     * Test if lobby is made active.
//     */
//
//    @Test
//    void makeLobbyActive() {
//        service.makeLobbyActive(game);
//        games.add(game);
//        assertTrue(service.getGames().contains(game));
//    }
//
//    /**
//     * Gets the game with id 0.
//     */
//
//    @Test
//    void getById() {
//        service.makeLobbyActive(game);
//        assertTrue(game.equals(service.getById(0)));
//    }
//
//    /**
//     * Test if we get all active games.
//     */
//
//    @Test
//    void getGames() {
//        assertTrue(service.getGames().equals(games));
//    }
//
//}