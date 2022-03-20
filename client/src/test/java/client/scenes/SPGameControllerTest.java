package client.scenes;

import client.utils.ServerUtils;
import commons.Player;
import commons.Question;
import javafx.application.Platform;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class SPGameControllerTest {

    @Mock
    private ServerUtils server;

    @Mock
    private MainCtrl mainCtrl;

    @Mock
    private Question question;

    @Mock
    private Text textMock;

    private SPGameCtrl SPG1;


    /**
     * Start javafx application
     */

    @BeforeAll
    static void start() {
        Platform.startup(() ->{});
    }


    /**
     * Description of setup is in each section
     */

    @BeforeEach
    void setUp() {
        //creating mocks
        question = mock(Question.class);
        server = mock(ServerUtils.class);
        mainCtrl = mock(MainCtrl.class);
        textMock = mock(Text.class);

        Player p1 = new Player("Max", 800);
        SPG1 = new SPGameCtrl(server, mainCtrl);

        //setting text-fields to mocks to prevent nullpointerexceptions
        SPG1.setQuestionCounter(textMock);
        SPG1.setScoreTracker(textMock);
        SPG1.setTimer(textMock);

        //setting behaviour of methods of server mock
        //return player from database
        when(server.getPlayer(p1.getUserName())).thenReturn(p1);
        //return mock question from server when requested
        when(server.getQuestion()).thenReturn(question);
    }


    /**
     * Makes sure constructor successfully creates an object
     */

    @Test
    void constructorNullTest() {
        assertNotNull(SPG1);
    }


    /**
     * Makes sure all fields are properly set in constructor
     */
    @Test
    void constructorFieldsTest() {
        assertEquals(0, SPG1.getqCount());
        assertEquals(0, SPG1.getScore());
        assertNotNull(SPG1.getQuestions());
        assertEquals(0, SPG1.getQuestions().size());
    }


    /**
     * Testing initialize when the database shouldn't be updated (lower score than in database)
     */

    @Test
    void initializeLowerScore() {
        SPG1.initialize();
        assertEquals(20, SPG1.getQuestions().size());
        verify(server, times(0)).setPlayer("Max", 9000);
    }


    /**
     * Testing initialize when the database should be updated (higher score than in database)
     */

    @Test
    void initializeHigherScore() {
        SPG1.setScore(9000);
        SPG1.initialize();
        verify(server, times(1)).setPlayer("Max", 9000);
    }


    /**
     * I'll add to this method once doAQuestion is complete
     */

    @Test
    void doAQuestion() {
    }

    /**
     * Not sure if we have to test getters and setters
      */
}