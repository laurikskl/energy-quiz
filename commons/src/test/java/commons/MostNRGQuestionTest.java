package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MostNRGQuestionTest {
    private MostNRGQuestion q1;
    private MostNRGQuestion q2;
    private MostNRGQuestion q3;
    private MostNRGQuestion q4;
    private Activity a1;
    private Activity a2;
    private List<Activity> activities;


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

    @Test
    void findMaxMultiple() {
        assertEquals(a1, q1.findMax());
    }

    @Test
    void findMaxSingle() {
        assertEquals(a2, q3.findMax());
    }

    @Test
    void findMaxNone() {
        assertNull(q4.findMax());
    }

    @Test
    void getCorrect() {
        assertEquals(a1, q1.getCorrect());
    }

    @Test
    void getActivities() {
        assertEquals(activities, q1.getActivities());
    }

    @Test
    void setCorrect() {
        q4.setCorrect(a1);
        assertEquals(a1, q4.getCorrect());
    }

    @Test
    void setActivities() {
        ArrayList<Activity> newAcs = new ArrayList<>();
        newAcs.add(a2);
        q4.setActivities(newAcs);
    }

    @Test
    void testEqualsTrue() {
        assertEquals(q1, q2);
    }

    @Test
    void testEqualsFalse() {
        assertNotEquals(q1, q3);
    }

    @Test
    void testToString() {
        assertEquals("MostNRGQuestion{correct=Activity{id=0, name='Charging a phone', powerConsumption=1000}, activities=[Activity{id=0, name='Charging a phone', powerConsumption=1000}, Activity{id=0, name='Browsing tiktok', powerConsumption=800}]}",
                q1.toString());
    }
}