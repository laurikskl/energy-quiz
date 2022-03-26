package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for Question and its subclasses
 */

public class QuestionTest {

    /**
     * Fields used in setup
     */

    private Question.MostNRGQuestion mostNRGQuestion;
    private Question.Matching matching;
    private Question.ChoiceEstimation choiceEstimation;
    private Activity a1;
    private Activity a2;
    private Activity a3;
    private Activity a4;
    private List<Activity> activityList;


    /**
     * Setup before each test
     * Creating 4 activities
     * Creating instances of MostNRGQuestion, Matching and ChoiceEstimation with the activities
     */

    @BeforeEach
    public void setup() {
        a1 = new Activity("Coding", 1200l, "github.com");
        a2 = new Activity("Watching tv", 1200l, "github.com");
        a3 = new Activity("Writing tests", 900l, "github.com");
        a4 = new Activity("Lamp", 800l, "github.com");
        activityList = Arrays.asList(a1, a2, a3, a4);
        mostNRGQuestion = new Question.MostNRGQuestion(activityList, a1);
        matching = new Question.Matching(activityList);
        choiceEstimation = new Question.ChoiceEstimation(Arrays.asList(a1), Arrays.asList(1200L, 900L, 1100L));
    }


    /**
     * Testing getter for correct activity
     */

    @Test
    void getCorrectTest() {
        assertEquals(a1, mostNRGQuestion.getCorrect());
    }


    /**
     * Testing getter for activities list
     */

    @Test
    void getActivitiesTest() {
        assertEquals(activityList, mostNRGQuestion.getActivities());
    }


    /**
     * Testing setter for correct activity
     */

    @Test

    void setCorrectTest() {
        mostNRGQuestion.setCorrect(a2);
        assertEquals(a2, mostNRGQuestion.getCorrect());
    }

    /**
     * Testing setter for activities list
     */

    @Test
    void setActivitiesTest() {
        mostNRGQuestion.setActivities(new ArrayList<>());
        assertEquals(new ArrayList<>(), mostNRGQuestion.getActivities());
    }


    /**
     * Testing getter for consumptions
     */

    @Test
    void getConsumptions() {
        assertEquals(Arrays.asList(1200L, 900L, 1100L), choiceEstimation.getConsumptions());
    }


    /**
     * Making sure the constructors create instances
     */

    @Test
    void constructorNullTests() {
        assertNotNull(mostNRGQuestion);
        assertNotNull(matching);
        assertNotNull(choiceEstimation);
    }

}
