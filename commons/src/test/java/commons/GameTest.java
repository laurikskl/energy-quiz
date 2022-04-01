package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameTest {

    Player demi;
    Player adda;
    ArrayList<Player> players1 = new ArrayList<>();
    ArrayList<Player> players2 = new ArrayList<>();
    Question question;
    ArrayList<Question> questions = new ArrayList<>();
    Activity activity1;
    Activity activity2;
    Activity activity3;
    ArrayList<Activity> activities = new ArrayList<>();
    Game game;


    /**
     * Initializing a game with an id, a list of 2 players and a list of 1 question for the rest of the tests
     */
    @BeforeEach
    public void initialize() {
        demi = new Player("xxdemixx", 666);
        adda = new Player("adduta", 0);

        activity1 = new Activity("00-cycling-1", "Cycling", 420l, "randomURL1", null);
        activity2 = new Activity("00-cycling-2", "Cycling", 420l, "randomURL2", null);
        activity3 = new Activity("00-cycling-3", "Cycling", 69l, "randomURL2", null);

        activities.add(activity1);
        activities.add(activity2);
        activities.add(activity3);

        question = new Question.MostNRGQuestion(activities, activity1, null);

        questions.add(question);

        players1.add(demi);
        players1.add(adda);
        players2.add(demi);

        game = new Game(420, players1, questions);
    }

    /**
     * Testing if the constructor works
     */
    @Test
    void constructorTest() {
        assertNotNull(game);
    }

    /**
     * Testing if the id is right
     */
    @Test
    void getId() {
        assertEquals(420, game.getId());
    }

    /**
     * Testing if the game got initialized with 0 rounds
     */
    @Test
    void getRound() {
        assertEquals(0, game.getRound());
    }

    /**
     * Testing if setting the round to another number works
     */
    @Test
    void setRound() {
        game.setRound(5);
        assertEquals(5, game.getRound());
    }

    /**
     * Testing if the list of players is right
     */
    @Test
    void getPlayers() {
        assertEquals(players1, game.getPlayers());
    }

    /**
     * Testing if changing the players works
     */
    @Test
    void setPlayers() {
        game.setPlayers(players2);
        assertEquals(players2, game.getPlayers());
    }

    /**
     * Testing if the list of questions is right
     */
    @Test
    void getQuestions() {
        assertEquals(questions, game.getQuestions());
    }
}
