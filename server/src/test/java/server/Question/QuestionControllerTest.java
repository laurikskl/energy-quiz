package server.Question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionControllerTest {

    private QuestionController q1;

    @BeforeEach
    void setUp() {
        q1 = new QuestionController(new QuestionService(new TestActivityRepository()));
    }

    @Test
    void constructorTest() {
        assertNotNull(q1);
    }

    @Test
    void getRandomQuestion() {
        assertEquals(20, q1.getRandomQuestions().getBody().size());
    }
}