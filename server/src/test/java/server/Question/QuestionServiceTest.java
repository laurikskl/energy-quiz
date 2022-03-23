package server.Question;

import commons.Activity;
import commons.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for QuestionService
 */

class QuestionServiceTest {

    /**
     * Field for setup for each test
     */

    private QuestionService q1;


    /**
     * Setup before each test
     */

    @BeforeEach
    void setup() {
        q1 = new QuestionService(new TestActivityRepository());
    }


    /**
     * Make sure instance of QuestionService is created in constructor
     */

    @Test
    void constructorTest() {
        assertNotNull(q1);
        assertTrue(q1 instanceof QuestionService);
    }


    /**
     * Make sure getQuestions returns a list of 20 questions
     */

    @Test
    void getQuestions() {
        List<Question> questions = q1.getQuestions();
        assertTrue(questions != null);
        assertEquals(20, questions.size());
    }


    /**
     * Make sure getRandomQuestion creates an instance of one of the subtypes of Question
     */

    @Test
    void getRandomQuestion() {
        Question random = q1.getRandomQuestion();
        assertNotNull(random);
        if(!(random instanceof Question.MostNRGQuestion) && !(random instanceof Question.ChoiceEstimation) &&
                !(random instanceof Question.Matching)) {
            fail("No random question of the right type was generated");
        }
    }


    /**
     * Testing if getRandomMatching:
     * Creates an instance of Question.Matching
     * Has a correct activity
     * Has a list of 4 activities
     */

    @Test
    void getRandomMatching() {
        Question.Matching matching = q1.getRandomMatching();
        assertNotNull(matching);
        assertNotNull(matching.getActivities());
        assertNotNull(matching.getCorrect());
        assertEquals(4, matching.getActivities().size());
    }


    /**
     * Makes sure getRandomChoiceEstimation:
     * Creates an instance of Question.ChoiceEstimation
     * Has no correct activity
     * Has a list of 1 activity
     * Has a list of 3 consumptions
     * The first number in consumptions is the consumption of the activity
     */

    @Test
    void getRandomChoiceEstimation() {
        Question.ChoiceEstimation estimate = q1.getRandomChoiceEstimation();
        assertNotNull(estimate);
        assertNotNull(estimate.getActivities());
        assertNotNull(estimate.getConsumptions());
        assertNull(estimate.getCorrect());
        assertEquals(1, estimate.getActivities().size());
        assertEquals(3, estimate.getConsumptions().size());
        assertEquals(estimate.getConsumptions().get(0), estimate.getActivities().get(0).getPowerConsumption());
    }


    /**
     * Makes sure getRandomMostNRG:
     * Creates an instance of Question.MostNRGQuestion
     * Has a list of 3 activities
     * Has a correct activity
     * The correct activity is actually the activity with the most consumption
     */

    @Test
    void getRandomMostNRG() {
        Question.MostNRGQuestion mostNRG = q1.getRandomMostNRG();
        assertNotNull(mostNRG);
        assertNotNull(mostNRG.getActivities());
        assertNotNull(mostNRG.getCorrect());
        assertEquals(3, mostNRG.getActivities().size());

        //finding activity with most consumption
        Activity max = mostNRG.getActivities().get(0);
        for (Activity a : mostNRG.getActivities()) {
            if (a.getPowerConsumption() > max.getPowerConsumption()) {
                max = a;
            }
        }
        assertEquals(mostNRG.getCorrect(), max);
    }


    /**
     * Test whether getRandomActivity only returns the activities in the database
     * or in this case returned by TestActivityRepository.
     */

    @Test
    void getRandomActivity() {
        //The only possible activities you can get from TestActivityRepository
        Activity a1 = new Activity("showering", 2000, "wikipedia.com");
        Activity a2 = new Activity("watching tv", 1000, "wikipedia.com");
        Activity a3 = new Activity("coding", 1500, "wikipedia.com");
        Activity a4 = new Activity("using e-bike", 2000, "wikipedia.com");

        Activity random = q1.getRandomActivity();
        assertNotNull(random);
        if(!random.equals(a1) && !random.equals(a2) && !random.equals(a3) && !random.equals(a4)) {
            fail("No random activity was generated");
        }
    }

}