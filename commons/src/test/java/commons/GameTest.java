package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Player demi;
    Player adda;
    ArrayList<Player> players1 = new ArrayList<Player>();
    ArrayList<Player> players2 = new ArrayList<Player>();
    Question question;
    ArrayList<Question> questions = new ArrayList<Question>();
    Activity activity1;
    Activity activity2;
    Activity activity3;
    ArrayList<Activity> activities = new ArrayList<Activity>();
    Game game;


    /**
     * Initializing a game with an id, a list of 2 players and a list of 1 question for the rest of the tests
     */
    @BeforeEach
    public void initialize(){
        demi = new Player("xxdemixx", 666);
        adda = new Player("adduta", 0);

        activity1 = new Activity("Cycling", 420, "randomURL1", "activities/00/tesla.jpg");
        activity2 = new Activity("Cycling", 420, "randomURL2", "activities/01/tesla.jpg");
        activity3 = new Activity("Cycling", 69, "randomURL2", "activities/01/tesla.jpg");

        activities.add(activity1);
        activities.add(activity2);
        activities.add(activity3);

        question = new Question.MultiChoice(activities, 25);

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