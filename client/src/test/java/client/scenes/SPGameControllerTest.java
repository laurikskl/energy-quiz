package client.scenes;

import client.utils.ServerUtils;
import commons.Activity;
import commons.Player;
import commons.Question;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Tests for SPGameController class
 */

class SPGameCtrlTest {

    @Mock
    private MainCtrl mainCtrl;

    @Mock
    private ServerUtils server;

    @Mock
    private Text text;

    private SPGameCtrl s1;
    private Player p1;


    /**
     * Setup before each test
     * Mock of ServerUtils and Text are created
     * p1 and s1 are created
     * s1 is initialized with p1 and the server mock
     */

    @BeforeEach
    void setup() {
        text = mock(Text.class);
        server = mock(ServerUtils.class);
        mainCtrl = mock(MainCtrl.class);
        p1 = new Player("Max", 9000);
        s1 = new SPGameCtrl(server, mainCtrl);
        try {
            s1.startGame(p1);
        } catch (IllegalStateException | IOException | InterruptedException | NullPointerException ignored) {
        }
    }


    /**
     * Constructor test
     */
    @Test
    void constructor() {
        assertNotNull(s1);
    }

    /**
     * This method doesn't do a lot yet so this will be tested later
     */

    @Test
    void doAQuestion() {
    }


    /**
     * Testing getter for qCount
     */

    @Test
    void getqCount() {
        assertEquals(0, s1.getqCount());
    }


    /**
     * Testing getter for questions
     */

    @Test
    void getQuestions() {
        assertEquals(0, s1.getQuestions().size());
    }


    /**
     * Testing getter for player
     */

    @Test
    void getPlayer() {
        assertEquals(p1, s1.getPlayer());
    }


    /**
     * Testing getting for score
     */

    @Test
    void getScore() {
        assertEquals(0, s1.getScore());
    }


    /**
     * Testing getter for server
     */

    @Test
    void getServer() {
        assertEquals(server, s1.getServer());
    }

    /**
     * Testing getter for ScoreCount
     */

    @Test
    void getScoreCount() {
        assertNull(s1.getScoreCount()); //also tested in setter
    }


    /**
     * Testing getter for name
     */

    @Test
    void getName() {
        assertNull(s1.getName()); //also tested in setter
    }


    /**
     * Testing getter for QuestionNumber
     */

    @Test
    void getQuestionNumber() {
        assertNull(s1.getQuestionNumber()); //also tested in setter
    }


    /**
     * Testing setter for qCount
     */

    @Test
    void setqCount() {
        s1.setqCount(5);
        assertEquals(5, s1.getqCount());
    }


    /**
     * Testing setter for questions
     */

    @Test
    void setQuestions() {
        Activity a1 = new Activity();
        Activity a2 = new Activity();
        List<Activity> acs = Arrays.asList(a1, a2);
        Question q = new Question.Matching(acs, null);
        List<Question> qs = List.of(q);
        s1.setQuestions(qs);
        assertEquals(qs, s1.getQuestions());
    }


    /**
     * Testing setter for player
     */

    @Test
    void setPlayer() {
        Player p2 = new Player("Dula Peep", 5000);
        s1.setPlayer(p2);
        assertEquals(p2, s1.getPlayer());
    }


    /**
     * Testing setter for score
     */

    @Test
    void setScore() {
        s1.setScore(59009);
        assertEquals(59009, s1.getScore());
    }

    /**
     * Testing setter for scoreCount
     */

    @Test
    void setScoreCount() {
        s1.setScoreCount(text);
        assertEquals(text, s1.getScoreCount());
    }


    /**
     * Testing setter for name
     */

    @Test
    void setName() {
        s1.setName(text);
        assertEquals(text, s1.getName());
    }


    /**
     * Testing setter for questionNumber
     */

    @Test
    void setQuestionNumber() {
        s1.setQuestionNumber(text);
        assertEquals(text, s1.getQuestionNumber());
    }

}