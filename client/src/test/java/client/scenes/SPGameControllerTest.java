package client.scenes;

import client.utils.ServerUtils;
import commons.Player;
import commons.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Tests for SPGameController class
 */

class SPGameControllerTest {

    @Mock
    private ServerUtils server;


    private SPGameController s1;
    private Player p1;


    /**
     * Setup before each test
     * Mock of ServerUtils and Text are created
     * p1 and s1 are created
     * s1 is initialized with p1 and the server mock
     */

    @BeforeEach
    void setup() {
        server = mock(ServerUtils.class);
        p1 = new Player("Max", 9000);
        s1 = new SPGameController();
        s1.initialize(p1, server);
    }


    /**
     * Testing if all fields are properly set
     */

    @Test
    void initialize() {
        assertEquals(p1, s1.getPlayer());
        assertEquals(server, s1.getServer());
        assertEquals(0, s1.getqCount());
        assertEquals(0, s1.getScore());
        assertEquals(new ArrayList<Question>(), s1.getQuestions());
    }


    /**
     * This method doesn't do a lot yet so this will be tested later
     */

    @Test
    void doAQuestion() {
    }

}