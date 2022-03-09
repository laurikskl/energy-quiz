package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActivityTest {

    Activity activity;
    Activity activityChanged;

    @BeforeEach
    public void initialize() {
        activity = new Activity("Cycling", 420);
        activityChanged = new Activity("Cycling", 420);
    }

    @Test
    public void constructorTest() {
        assertNotNull(activity);
    }

    @Test
    public void getIdTest() {
        assertEquals(0 ,activity.getId());
    }

    @Test
    public void getNameTest() {
        assertEquals("Cycling", activity.getName());
    }

    @Test
    public void getPowerConsumptionTest() {
        assertEquals(420, activity.getPowerConsumption());
    }

    @Test
    public void setIdTest() {
        activityChanged.setId(2);
        assertEquals(2, activityChanged.getId());
    }

    @Test
    public void setNameTest() {
        activityChanged.setName("Biking");
        assertEquals("Biking", activityChanged.getName());
    }

    @Test
    public void setPowerConsumptionTest() {
        activityChanged.setPowerConsumption(666);
        assertEquals(666, activityChanged.getPowerConsumption());
    }

    @Test
    public void equalsTest() {
        Activity activitySame = new Activity("Cycling", 420);
        Activity activityDiff = new Activity("Biking", 420);
        assertTrue(activity.equals(activitySame));
        assertTrue(activitySame.equals(activity));
        assertFalse(activity.equals(activityDiff));
        assertFalse(activityDiff.equals(activity));
    }

    @Test
    public void toStringTest() {
        assertEquals("Activity{" +
                "id=" + 0 +
                ", name='" + "Cycling" + '\'' +
                ", powerConsumption=" + 420 +
                '}', activity.toString());
    }
}
