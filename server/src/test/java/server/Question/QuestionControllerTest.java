package server.Question;

import commons.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Activity.ActivityController;
import server.Activity.ActivityService;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for QuestionController
 */

class QuestionControllerTest {

    /**
     * fields for setup
     */

    private QuestionController q1;


    /**
     * Setup before each method
     */

    @BeforeEach
    void setUp() {
        TestActivityRepository t1 = new TestActivityRepository();
        q1 = new QuestionController(new QuestionService(
                t1, new ActivityController(new ActivityService(t1))));
    }


    /**
     * Make sure instance of QuestionController is created
     */

    @Test
    void constructorTest() {
        assertNotNull(q1);
    }


    /**
     * Make sure getRandomQuestion produces a list of 20 questions
     */

    @Test
    void getRandomQuestion() {
        List<Question> questions = q1.getRandomQuestions().getBody();
        assertNotNull(questions);
        assertEquals(20, questions.size());
    }

}