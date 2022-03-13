package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MostNRGQuestion
 */
class MostNRGQuestionTest {
    private MostNRGQuestion q1;
    private MostNRGQuestion q2;
    private MostNRGQuestion q3;
    private MostNRGQuestion q4;
    private Activity a1;
    private Activity a2;
    private List<Activity> activities;


    /**
     * Creating two different activities a1 and a2
     * Adding both to a list activities and only a2 to a list single
     * Creating two "equal" instances q1 and q2 with activities
     * Creating an instance with single
     * Creating an instance with no activities
     */
    @BeforeEach
    void setUp() {
        a1 = new Activity("Charging a phone", 1000);
        a2 = new Activity("Browsing tiktok", 800);
        activities = new ArrayList<>();
        List<Activity> single = new ArrayList<>();
        activities.add(a1);
        activities.add(a2);
        single.add(a2);
        q1 = new MostNRGQuestion(activities); //two activities
        q2 = new MostNRGQuestion(activities); //two activities
        q3 = new MostNRGQuestion(single); //one activity
        q4 = new MostNRGQuestion(new ArrayList<>()); //no activities
    }

    /**
     * Testing findMax with multiple activities
     */
    @Test
    void findMaxMultiple() {
        assertEquals(a1, q1.findMax());
    }

    /**
     * Testing findMax with one activity
     */
    @Test
    void findMaxSingle() {
        assertEquals(a2, q3.findMax());
    }

    /**
     * Testing findMax with no activities
     */
    @Test
    void findMaxNone() {
        assertNull(q4.findMax());
    }

    /**
     * Testing getCorrect method
     */
    @Test
    void getCorrect() {
        assertEquals(a1, q1.getCorrect());
    }

    /**
     * Testing getter for activities
     */
    @Test
    void getActivities() {
        assertEquals(activities, q1.getActivities());
    }

    /**
     * Testing setter for correct activity
     */
    @Test
    void setCorrect() {
        q4.setCorrect(a1);
        assertEquals(a1, q4.getCorrect());
    }

    /**
     * Testing setter for activities
     */
    @Test
    void setActivities() {
        ArrayList<Activity> newAcs = new ArrayList<>();
        newAcs.add(a2);
        q4.setActivities(newAcs);
    }

    /**
     * Equals test with two instances which should be equal
     */
    @Test
    void testEqualsTrue() {
        assertEquals(q1, q2);
    }

    /**
     * Equals test with two instances which shouldn't be equal
     */
    @Test
    void testEqualsFalse() {
        assertNotEquals(q1, q3);
    }

    /**
     * Testing the toString method
     */
    @Test
    void testToString() {
        assertEquals("MostNRGQuestion{correct=Activity{id=0, name='Charging a phone', powerConsumption=1000}, activities=[Activity{id=0, name='Charging a phone', powerConsumption=1000}, Activity{id=0, name='Browsing tiktok', powerConsumption=800}]}",
                q1.toString());
    }
}